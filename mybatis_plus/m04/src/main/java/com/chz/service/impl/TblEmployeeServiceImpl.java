package com.chz.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chz.entity.TblEmployee;
import com.chz.mapper.TblEmployeeMapper;
import com.chz.service.TblEmployeeService;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author chz
 * @since 2020-04-11
 */
@Service
public class TblEmployeeServiceImpl extends ServiceImpl<TblEmployeeMapper, TblEmployee> implements TblEmployeeService {

}
