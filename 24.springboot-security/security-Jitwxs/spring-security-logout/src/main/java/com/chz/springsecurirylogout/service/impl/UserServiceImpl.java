package com.chz.springsecurirylogout.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chz.springsecurirylogout.entity.UserDO;
import com.chz.springsecurirylogout.mapper.UserMapper;
import com.chz.springsecurirylogout.service.IUserService;
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
