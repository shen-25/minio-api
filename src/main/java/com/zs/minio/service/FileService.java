package com.zs.minio.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.zs.minio.model.dto.TaskInfoDTO;
import com.zs.minio.model.entity.UploadTask;
import com.zs.minio.model.param.InitTaskParam;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.Map;

/**
 * 分片上传-分片任务记录(SysUploadTask)表服务接口
 *
 * @author word
 */
public interface FileService extends IService<UploadTask> {

    /**
     * 根据md5标识获取分片上传任务
     */
    UploadTask getByIdentifier (String identifier);

    /**
     * 初始化一个任务
     */
    TaskInfoDTO initTask (String bucketName,InitTaskParam param);

    /**
     * 获取文件地址
     */
    String getPath (String bucket, String objectKey);

    /**
     * 获取上传进度
     */
    TaskInfoDTO getTaskInfo (String identifier);

    /**
     * 生成预签名上传url
     */
    String genPreSignUploadUrl (String bucket, String objectKey, Map<String, String> params);

    /**
     * 合并分片
     */
    void merge (String identifier);


     File downloadFile(String bucketName, String objectKey) throws IOException;
}
