package com.chz.conf;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.baomidou.mybatisplus.core.parser.ISqlParser;
import com.baomidou.mybatisplus.extension.parsers.BlockAttackSqlParser;
import com.baomidou.mybatisplus.extension.plugins.OptimisticLockerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;
import java.util.ArrayList;

/**
 * mybatis-plus 配置类
 */
//将其他的组件也可以放入配置类中
//@MapperScan()最好将相关配置放入一个配置类
@Configuration
public class MybatisPlusConf {
    //分页配置
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        PaginationInterceptor paginationInterceptor = new PaginationInterceptor();
        //设置数据库类型
        paginationInterceptor.setDbType(DbType.MYSQL);
        //防止恶意sql注入
        ArrayList<ISqlParser> sqlParsers = new ArrayList<>();
        sqlParsers.add(new BlockAttackSqlParser());
        paginationInterceptor.setSqlParserList(sqlParsers);
        return paginationInterceptor;
    }

    @Bean//乐观锁插件
    public OptimisticLockerInterceptor optimisticLockerInterceptor() {
        return new OptimisticLockerInterceptor();
    }

    @Bean
    public MyMetaObjectHandler objectHandler() {
        return new MyMetaObjectHandler();
    }

    /**
     * 自动填充,一般用于自动填充date
     * <p>
     * 使用Slf4j 可以省略 private final static Logger logger = LoggerFactory.getLogger(LoggerTest.class);
     * 直接可以通过log.info(),将该类的日志消息输出到控制台
     */
    @Slf4j
    //@Component
    //log实际是static所以要使用静态内部类
    private static class MyMetaObjectHandler implements MetaObjectHandler {
        /**
         * insert时自动填充指定字段
         * FieldFill.INSERT_UPDATE
         * FieldFill.INSERT
         */
        @Override
        public void insertFill(MetaObject metaObject) {
            log.info("start insert fill ....");
            //表中字段的值叫做metaObject(元数据), 二者选一即可
            this.strictInsertFill(metaObject, "date", LocalDateTime.class, LocalDateTime.now()); // 起始版本 3.3.0(推荐使用)
            //元数据,对应属性名,自动填充的值
//        this.fillStrategy(metaObject, "date", LocalDateTime.now());
        }

        /**
         * update时自动填充指定字段
         * FieldFill.INSERT_UPDATE
         * FieldFill.UPDATE
         */
        @Override
        public void updateFill(MetaObject metaObject) {
            log.info("start update fill {}", "update");
            //二者选一即可
//        this.strictUpdateFill();
//        this.fillStrategy()
        }
    }

}
