/**
 * 
 */
package com.lbc.test.module.user;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lbc.LocalCache;
import com.lbc.query.Criteria;
import com.lbc.query.Query;
import com.lbc.test.module.user.dao.SignMapper;
import com.lbc.test.module.user.dao.UserMapper;
import com.lbc.test.module.user.entity.UserEntity;
import com.lbc.wrap.QueryingCollection;

/**
 * @Description: 
 * @author wufenyun
 * @date 2017年10月12日 下午11:22:42
 */
@RestController()
public class UserRestController {

	@Autowired
	private UserMapper userMapper;
	@Autowired
    private LocalCache localCache;
    @Autowired
    private UserLoader userLoader;
    @Autowired
    private SignMapper signMapper;
    
    @Autowired
    private CategoryLoader categoryLoader;
	
	@RequestMapping("user/query100")
	public List<UserEntity> query100(String name) {
		long startTime = System.currentTimeMillis();
		//List<UserEntity> users = userMapper.get1000();
		QueryingCollection<String,UserEntity> list = localCache.get("user", UserLoader.class);
		List<UserEntity> users = list.query(Query.query(Criteria.where("name").is(name).and("id").is(1)
		        .and("insertTime").is(new Date(1520415942000L))));
		System.out.println(signMapper.get("user"));
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
