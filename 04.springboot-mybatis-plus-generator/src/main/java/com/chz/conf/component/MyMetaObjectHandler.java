package com.chz.conf.component;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/*
自动填充,一般用于自动填充date

使用Slf4j 可以省略 private final Logger logger = LoggerFactory.getLogger(LoggerTest.class);
直接可以通过log.info(),将该类的日志消息输出到控制台
 */
@Slf4j
@Component
public class MyMetaObjectHandler implements MetaObjectHandler {
    /*
    insert时自动填充指定字段
     */
    @Override
    public void insertFill(MetaObject metaObject) {
        log.info("start insert fill ....");
        this.strictUpdateFill(metaObject, "date", LocalDateTime.class, LocalDateTime.now()); // 起始版本 3.3.0(推荐使用)
        this.fillStrategy(metaObject, "date", LocalDateTime.now());
    }

    /*
    update时自动填充指定字段
     */
    @Override
    public void updateFill(MetaObject metaObject) {

    }
}
