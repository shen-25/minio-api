package com.zs.minio.controller;


import com.amazonaws.util.IOUtils;
import com.zs.minio.constant.ApiRestResponse;
import com.zs.minio.model.dto.TaskInfoDTO;
import com.zs.minio.model.entity.UploadTask;
import com.zs.minio.model.param.InitTaskParam;
import com.zs.minio.service.FileService;
import org.apache.commons.io.FilenameUtils;
import org.springframework.http.*;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


/**
 * 分片上传-分片任务记录(SysUploadTask)表控制层
 * @author 35536
 */
@RestController
@RequestMapping("/v1/minio/tasks")
public class FileController {
    /**
     * 服务对象
     */
    @Resource
    private FileService fileService;


    /**
     * 获取上传进度
     *
     * @param identifier 文件md5
     */
    @GetMapping("/{identifier}")
    public ApiRestResponse<TaskInfoDTO> taskInfo(@PathVariable("identifier") String identifier) {
        TaskInfoDTO taskInfo = fileService.getTaskInfo(identifier);
        return ApiRestResponse.ok(taskInfo);
    }

    /**
     * 创建一个上传任务
     */
    @PostMapping("/{bucketName}")
    public ApiRestResponse<TaskInfoDTO> initTask(@PathVariable String bucketName,
                                                 @Valid @RequestBody InitTaskParam param) {
        TaskInfoDTO taskInfoDTO = fileService.initTask(bucketName, param);
        return ApiRestResponse.ok(taskInfoDTO);
    }


    /**
     * 获取每个分片的预签名上传地址
     *
     */
    @GetMapping("/{identifier}/{partNumber}")
    public ApiRestResponse<Object> preSignUploadUrl(@PathVariable("identifier") String identifier, @PathVariable("partNumber") Integer partNumber) {
        UploadTask task = fileService.getByIdentifier(identifier);
        if (task == null) {
            return ApiRestResponse.error("分片任务不存在");
        }
        Map<String, String> params = new HashMap<>();
        params.put("partNumber", partNumber.toString());
        params.put("uploadId", task.getUploadId());
        String url = fileService.genPreSignUploadUrl(task.getBucketName(), task.getObjectKey(), params);
        return ApiRestResponse.ok(url);
    }

    /**
     * 合并分片
     */
    @PostMapping("/merge/{identifier}")
    public ApiRestResponse<String> merge(@PathVariable("identifier") String identifier) {
        fileService.merge(identifier);
        return ApiRestResponse.ok();
    }

    /**
     * 下载
     * @param id 任务id,数据库中的upload_task的主键
     */
    @GetMapping("/download")
    public ResponseEntity download(@RequestParam String id) throws IOException {
        UploadTask uploadTask = fileService.getById(id);
        if (uploadTask != null) {
            File file = fileService.downloadFile(uploadTask.getBucketName(),uploadTask.getObjectKey());
            byte[] bytes = IOUtils.toByteArray(new FileInputStream(file));
            HttpHeaders httpHeaders = new HttpHeaders();
            String extension = FilenameUtils.getExtension(uploadTask.getFileName());
            httpHeaders.setContentType(MediaType.valueOf(MediaTypeFactory.getMediaType(extension).orElse(MediaType.APPLICATION_OCTET_STREAM).toString()));
            httpHeaders.setContentDispositionFormData("attachment", uploadTask.getFileName());
            return new ResponseEntity(bytes, httpHeaders, HttpStatus.OK);
        }
        return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }



}
