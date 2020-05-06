package com.chz.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.chz.entity.User;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author chz
 * @since 2020-05-03
 */
public interface IUserService extends IService<User> {
    User queryUser(String username);
}
