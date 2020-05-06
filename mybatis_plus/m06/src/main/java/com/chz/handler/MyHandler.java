package com.chz.handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component//注入功能组件
public class MyHandler implements MetaObjectHandler {
    /*
    插入操作自动填充
     */
    @Override
    public void insertFill(MetaObject metaObject) {
        System.out.println("插入------------");
        this.strictInsertFill(metaObject, "date", Date.class, new Date());
    }

    /*
    更行操作自动填充
     */
    @Override
    public void updateFill(MetaObject metaObject) {
        this.strictUpdateFill(metaObject, "age", Integer.class, "404");
    }
}
