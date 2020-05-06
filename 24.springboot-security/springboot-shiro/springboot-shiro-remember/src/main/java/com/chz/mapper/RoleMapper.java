package com.chz.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.chz.entity.Role;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author chz
 * @since 2020-05-03
 */

public interface RoleMapper extends BaseMapper<Role> {
    @Select("SELECT * FROM t_role WHERE r_id IN (SELECT r_id FROM t_u_r WHERE u_id = #{u_id})")
    @Results({
            @Result(property = "rId", column = "r_id"),
            @Result(property = "perms",column = "r_id",
            many =@Many(select = "com.chz.mapper.PermMapper.queryPerms",fetchType = FetchType.LAZY))
    })
    Role queryRole(@Param("u_id") Integer uid);
}
