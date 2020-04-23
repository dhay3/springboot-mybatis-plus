package com.chz.springbootmybatisplusdruid.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.chz.springbootmybatisplusdruid.entity.Role;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;

import java.util.List;

//@Mapper
//如果添加了@MapperScann就可以不用配置@Mapper
public interface RoleMapper extends BaseMapper<Role> {
    //使用内嵌查询,User传过来uid通过第三张表拿到对应的r_id
    @Select("select * from t_role t where r_id = (select r_id from t_u_r ur where  ur.u_id = #{u_id})")
    @Results({
            @Result(property = "rId", column = "r_id", id = true),
            //执行queryPerms
            @Result(property = "perms", column = "r_id",
                    many = @Many(select = "com.chz.springbootmybatisplusdruid.dao.PermMapper.queryPerms",
                            fetchType = FetchType.LAZY))
    })
    List<Role> queryRole(@Param("u_id") Integer uId);
}
