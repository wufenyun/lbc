package com.lbc.test.module.user;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lbc.exchanger.CacheExchanger;
import com.lbc.test.module.user.dao.UserMapper;
import com.lbc.test.module.user.entity.UserEntity;

@Service
public class UserLoader implements CacheExchanger<String, UserEntity> {

	@Autowired
	private UserMapper userMapper;
	@Autowired
	private UserService userService;
	

	@Override
	public boolean needRefresh(String key) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Collection<UserEntity> initialize() throws Exception {
	    List<UserEntity> list = userMapper.getAll();
		return list;
	}

	@Override
	public Collection<UserEntity> load(String key) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String initializeKey() {
		return "user";
	}



}
