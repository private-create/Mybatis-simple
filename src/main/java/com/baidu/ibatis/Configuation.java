/**
 * 
 */
package com.baidu.ibatis;

import java.lang.reflect.Proxy;

/**
 * mybatis配置类
 * @author 三国
 *
 */
public class Configuation {

	/**
	 * 方法概述
	 * <p>Title: Configuation.java</p>  
	 * <p>Description: </p>  
	 * <p>Copyright: Copyright (c) 2017</p>  
	 * @author shenlan  
	 * @date 2020年10月19日  
	 * @version 1.0  
	 */
	public <T> T getMapper(Class clazz,SqlSession sqlSession) {
		
		return (T) Proxy.newProxyInstance(this.getClass().getClassLoader(),
				new Class[]{clazz}, 
				new MapperProxy(sqlSession));
	}

}
