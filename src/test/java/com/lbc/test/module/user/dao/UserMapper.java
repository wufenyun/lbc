package com.lbc.test.module.user.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.lbc.test.module.user.entity.UserEntity;

/**
 * Description:  
 * Date: 2018年3月2日 下午2:38:28
 * @author wufenyun 
 */
@Mapper
public interface UserMapper {

    @Select("SELECT * FROM users")
    /*@Results({
        @Result(property = "userSex",  column = "user_sex", javaType = UserSexEnum.class),
        @Result(property = "nickName", column = "nick_name")
    })*/
    List<UserEntity> getAll();
    
    @Select("SELECT * FROM users limit 0,1000")
    List<UserEntity> get1000();

    @Select("SELECT * FROM users WHERE id = #{id}")
    /*@Results({
        @Result(property = "userSex",  column = "user_sex"),
        @Result(property = "nickName", column = "nick_name")
    })*/
    UserEntity getUserById(Long id);
    
    @Select("SELECT * FROM users WHERE name = #{name}")
    /*@Results({
        @Result(property = "insertTime",  column = "insert_time"),
        @Result(property = "updateIime", column = "update_time")
    })*/
    UserEntity getUserByName(String name);
    
    @Select("SELECT * FROM users WHERE name = #{name} and address=#{address}")
   /* @Results({
        @Result(property = "insertTime",  column = "insert_time"),
        @Result(property = "updateIime", column = "update_time")
    })*/
    UserEntity getUser(@Param("name")String name,@Param("address")String address);

    @Insert("INSERT INTO users(name,address,mail,insertTime,updatetime) VALUES(#{name}, #{address}, #{mail}, #{insertTime},#{updateIime})")
    int insert(UserEntity user);

    @Update("UPDATE users SET name=#{name} WHERE id =#{id}")
    void update(UserEntity user);

    @Delete("DELETE FROM users WHERE id =#{id}")
    void delete(Long id);

}