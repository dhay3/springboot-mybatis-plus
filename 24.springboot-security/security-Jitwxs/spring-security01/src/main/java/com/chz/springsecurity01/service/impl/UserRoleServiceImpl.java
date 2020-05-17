package com.chz.springsecurity01.service.impl;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chz.springsecurity01.entity.UserRole;
import com.chz.springsecurity01.mapper.UserRoleMapper;
import com.chz.springsecurity01.service.IUserRoleService;
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
public class UserRoleServiceImpl extends ServiceImpl<UserRoleMapper, UserRole> implements IUserRoleService {

}
