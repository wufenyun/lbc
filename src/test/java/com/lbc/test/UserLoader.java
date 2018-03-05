package com.lbc.test;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lbc.cacheloader.CacheLoader;
import com.lbc.test.module.user.dao.UserMapper;
import com.lbc.test.module.user.entity.UserEntity;

@Service
public class UserLoader implements CacheLoader<String, UserEntity> {

	@Autowired
	private UserMapper userMapper;

	@Override
	public boolean isNeedRefresh() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Collection<UserEntity> initialize() throws Exception {
		return userMapper.get1000();
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
