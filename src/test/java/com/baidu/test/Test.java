/**
 * 
 */
package com.baidu.test;

import java.util.List;

import com.alibaba.fastjson.JSON;
import com.baidu.entity.User;
import com.baidu.ibatis.SqlSession;
import com.baidu.service.UserDao;

/**
 * @author 三国
 *
 */
public class Test {
	
	public static void main(String[] args){
		SqlSession sqlSession = new SqlSession();
		
		UserDao userDao = (UserDao)sqlSession.getMapper(UserDao.class);
		
		List<User> seachByUserId = userDao.seachByUserId("admin");
		
		System.out.println(JSON.toJSONString(seachByUserId));
		
		List<User> seachByIdAnd = userDao.seachByIdAnd("admin", "1");
		
		System.out.println(JSON.toJSONString(seachByIdAnd));
		
		int updateRemark = userDao.updateRemark("mybatis");
		
		sqlSession.commit();
		System.out.println(updateRemark);
	}
}
