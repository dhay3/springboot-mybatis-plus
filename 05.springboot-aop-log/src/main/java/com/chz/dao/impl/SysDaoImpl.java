package com.chz.dao.impl;

import com.chz.dao.SysDao;
import com.chz.domain.SysLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class SysDaoImpl implements SysDao {
    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public void saveSysLog(SysLog log) {
        String sql = new StringBuilder("insert into sys_log").
                append("(username,operation,time,method,params,ip,create_time)").
                append("values(?,?,?,?,?,?,?)").toString();
        jdbcTemplate.update(sql,
                log.username(), log.operation(), log.time(),
                log.method(), log.params(), log.ip(), log.createTime());
    }
}
