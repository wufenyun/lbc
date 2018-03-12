package com.lbc.test.module.user;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lbc.exchanger.CacheExchanger;
import com.lbc.test.module.user.dao.UserMapper;
import com.lbc.test.module.user.entity.UserEntity;

/**
 * Description:  
 * Date: 2018年3月2日 下午2:38:28
 * @author wufenyun 
 */
@Service
public class UserLoader implements CacheExchanger<String, UserEntity> {

	@Autowired
	private UserMapper userMapper;
	@Autowired
	private UserService userService;

	@Override
	public Collection<UserEntity> prelaoding() throws Exception {
	    List<UserEntity> list = userMapper.getAll();
		return list;
	}

	@Override
	public Collection<UserEntity> load(String key) throws Exception {
		// TODO Auto-generated method stub
		return prelaoding();
	}

	@Override
	public String prelaodingKey() {
		return "user";
	}



}
