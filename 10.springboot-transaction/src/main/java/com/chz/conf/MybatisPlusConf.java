package com.chz.conf;

import com.baomidou.mybatisplus.core.parser.ISqlParser;
import com.baomidou.mybatisplus.extension.parsers.BlockAttackSqlParser;
import com.baomidou.mybatisplus.extension.plugins.OptimisticLockerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;

/**
 * mybatis-plus 配置类
 */
//@EnableTransactionManagement可以不用添加,springboot已经自动实现,直接使用@Transcational即可
@Configuration
public class MybatisPlusConf {
    //分页配置
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        PaginationInterceptor paginationInterceptor = new PaginationInterceptor();
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
}
