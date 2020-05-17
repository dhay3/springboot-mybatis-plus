package com.chz.springsecurity01.service.impl;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chz.springsecurity01.entity.Role;
import com.chz.springsecurity01.mapper.RoleMapper;
import com.chz.springsecurity01.service.IRoleService;
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
