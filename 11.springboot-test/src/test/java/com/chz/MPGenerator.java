package com.chz;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class MPGenerator {
    @Test
    public void generator() {
        //代码生成器
        AutoGenerator autoGenerator = new AutoGenerator();
        //全局配置 调用generator.config下的
        GlobalConfig gc = new GlobalConfig();
        //获取当前项目的路径
        String path = System.getProperty("user.dir");
        //设置是否开启AR
        gc.setActiveRecord(true)
                .setAuthor("chz")
                //文件输出路径
                .setOutputDir(path + "/src/main/java")
                //是否覆盖文件
                .setFileOverride(true)
                //设置主键自增策略
                .setIdType(IdType.AUTO)
                //是否开启resultMap,默认false
                .setBaseResultMap(true)
                //是否开启sql片段,默认false
                .setBaseColumnList(true);


        //数据源配置
        DataSourceConfig dataSourceConfig = new DataSourceConfig();
        dataSourceConfig.setDbType(DbType.MYSQL)
                .setDriverName("com.mysql.cj.jdbc.Driver")
                .setUrl("jdbc:mysql://localhost:3306/mp?userSSL=false&serverTimezone=Asia/Shanghai")
                .setUsername("root")
                .setPassword("12345");

        //策略配置
        StrategyConfig strategyConfig = new StrategyConfig();
        //是否开启大写命名,默认不开启
        strategyConfig.setCapitalMode(false)
                //数据库表映射到实体类命名策略
                .setNaming(NamingStrategy.underline_to_camel)
                //设置想要生成的表
                .setInclude("tbl_employee")
                //生成的dao,service,entity不再带tbl_前缀
                .setTablePrefix("tbl_");


        //包配置
        PackageConfig packageConfig = new PackageConfig();
        //setParent设置统一的包路径
        packageConfig.setParent("com.chz")
                .setMapper("mapper")
                .setService("service")
                .setController("controller")
                .setEntity("entity")
                .setXml("mapper");

        //整合配置
        autoGenerator.setPackageInfo(packageConfig)
                .setDataSource(dataSourceConfig)
                .setGlobalConfig(gc)
                .setStrategy(strategyConfig);
        //执行
        autoGenerator.execute();
    }
}
