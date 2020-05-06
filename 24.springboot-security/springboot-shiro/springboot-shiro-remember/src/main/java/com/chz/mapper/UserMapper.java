package com.chz.mapper;

import com.chz.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author chz
 * @since 2020-05-03
 */
public interface UserMapper extends BaseMapper<User> {
    @Select("SELECT * FROM t_user WHERE name =#{username}")
    @Results({
            @Result(property = "uId",column = "u_id",id = true),
            @Result(property = "roles",column = "u_id",
                    many = @Many(select = "com.chz.mapper.RoleMapper.queryRole",fetchType = FetchType.LAZY))
    })
    User queryUser(@Param("username") String username);
}
