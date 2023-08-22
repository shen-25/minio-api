package com.zs.minio.utils;

import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.net.NetUtil;
import cn.hutool.core.util.IdUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * 雪花算法生成工具类
 * @author 35536
 */
@Slf4j
@Component
public class IdGenerator {

    //第几号机房
    private  long workerId = 0;
    //第几号机器
    private final long datacenterId = 1;
    private final Snowflake snowflake = IdUtil.getSnowflake(workerId, datacenterId);

    //构造后开始执行，加载初始化工作
    @PostConstruct
    public void init(){
        try{
            //获取本机的ip地址编码
            workerId = NetUtil.ipv4ToLong(NetUtil.getLocalhostStr());
            log.info("当前机器的workerId: " + workerId);
        }catch (Exception e){
            e.printStackTrace();
            log.warn("当前机器的workerId获取失败 ----> " + e);
            workerId = NetUtil.getLocalhostStr().hashCode();
        }
    }

    public synchronized long getId(){
        return snowflake.nextId();
    }

    public static void main(String[] args) {
        IdGenerator idGenerator = new IdGenerator();
        for (int i = 0; i < 10; i++) {
            long id = idGenerator.getId();
            System.out.println(id);
        }
    }
}
