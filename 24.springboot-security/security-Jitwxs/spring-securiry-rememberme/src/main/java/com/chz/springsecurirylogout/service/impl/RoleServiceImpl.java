package com.chz.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chz.entity.Role;
import com.chz.mapper.RoleMapper;
import com.chz.service.IRoleService;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author chz
 * @since 2020-05-14
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements IRoleService {

}
