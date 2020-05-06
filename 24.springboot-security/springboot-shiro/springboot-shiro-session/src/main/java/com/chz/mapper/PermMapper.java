package com.chz.mapper;

import com.chz.entity.Perm;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author chz
 * @since 2020-05-03
 */
public interface PermMapper extends BaseMapper<Perm> {
    @Select("SELECT * FROM t_perm WHERE p_id IN (SELECT p_id FROM t_r_p WHERE r_id = #{r_id})")
    List<Perm> queryPerms(@Param("r_id") Integer rId);
}
