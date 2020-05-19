package com.chz.conf.component;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;

import java.time.LocalDateTime;

/*
自动填充,一般用于自动填充date

使用Slf4j 可以省略 private final Logger logger = LoggerFactory.getLogger(LoggerTest.class);
直接可以通过log.info(),将该类的日志消息输出到控制台
 */
@Slf4j
//@Component
public class MyMetaObjectHandler implements MetaObjectHandler {

    /*
        insert时自动填充指定字段
           FieldFill.INSERT_UPDATE
           FieldFill.INSERT
         */
    @Override
    public void insertFill(MetaObject metaObject) {
        log.info("start insert fill ....");
        //表中字段的值叫做metaObject(元数据), 二者选一即可
        this.strictInsertFill(metaObject, "date", LocalDateTime.class, LocalDateTime.now()); // 起始版本 3.3.0(推荐使用)
        //元数据,对应属性名,自动填充的值
//        this.fillStrategy(metaObject, "date", LocalDateTime.now());
    }

    /*
    update时自动填充指定字段
    FieldFill.INSERT_UPDATE
    FieldFill.UPDATE
     */
    @Override
    public void updateFill(MetaObject metaObject) {
        log.info("start update fill {}", "update");
        //二者选一即可
//        this.strictUpdateFill();
//        this.fillStrategy()
    }
}
