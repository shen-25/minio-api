package com.zs.minio.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zs.minio.model.entity.UploadTask;
import org.springframework.stereotype.Repository;

/**
 * 分片上传-分片任务记录(SysUploadTask)表数据库访问层
 * @author 35536
 * */
@Repository
public interface UploadTaskMapper extends BaseMapper<UploadTask> {

}
