/**
 * 
 */
package com.lbc.test.module.user;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lbc.test.module.user.dao.UserMapper;
import com.lbc.test.module.user.entity.UserEntity;

/**
 * @Description: 
 * @author wufenyun
 * @date 2017年10月12日 下午11:22:42
 */
@RestController()
public class UserRestController {

	@Autowired
	private UserMapper userMapper;
	
	@RequestMapping("user/query100")
	public List<UserEntity> query100() {
		long startTime = System.currentTimeMillis();
		List<UserEntity> users = userMapper.get1000();
		System.out.println("本次查询时间：" + (System.currentTimeMillis() - startTime)+"ms");
		return users;
	}
	
	@RequestMapping("user/queryUserByName")
	public UserEntity queryUserByName(String name) {
		long startTime = System.currentTimeMillis();
		UserEntity user = userMapper.getUserByName(name);
		System.out.println("本次查询时间：" + (System.currentTimeMillis() - startTime)+"ms");
		return user;
	}
	
	@RequestMapping("user/query2C")
	public UserEntity query2C(String name,String address) {
		long startTime = System.currentTimeMillis();
		UserEntity user = userMapper.getUser(name,address);
		System.out.println("本次查询时间：" + (System.currentTimeMillis() - startTime)+"ms");
		return user;
	}
	
	@RequestMapping("user/insert")
	public int insert() {
		UserEntity user = new UserEntity();
		user.setName("name" + System.currentTimeMillis());
		user.setAddress("中国重庆市" + System.currentTimeMillis());
		user.setMail("9203002221@qq.com");
		user.setInsertTime(new Date());
		return userMapper.insert(user);
	}
}