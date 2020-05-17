package com.chz.springsecurityimagecode.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chz.springsecurityimagecode.entity.UserDO;
import com.chz.springsecurityimagecode.mapper.UserMapper;
import com.chz.springsecurityimagecode.service.IUserService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author chz
 * @since 2020-05-14
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, UserDO> implements IUserService {


}
