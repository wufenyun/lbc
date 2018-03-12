/**
 * Package: com.lbc.test.module.user.dao
 * Description: 
 */
package com.lbc.test.module.user.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.lbc.test.module.user.entity.Sign;

/**
 * Description:  
 * Date: 2018年3月12日 下午2:39:20
 * @author wufenyun 
 */
@Mapper
public interface SignMapper {

    @Select("SELECT * FROM sign WHERE cachekey = #{key}")
    Sign get(String key);
}
