/**
 * 
 */
package com.baidu.ibatis;

import java.lang.reflect.Method;
import java.sql.Connection;
import java.util.List;

import com.baidu.initdata.InitData;

/**
 * mybatis会话
 * @author 三国
 *
 */
public class SqlSession {
	
	private Configuation configuation = new Configuation();
	
	private Execute execute = new Execute();
	
	/**
	 * 
	 * 方法概述 ：执行查询方法
	 * <p>Title: SqlSession.java</p>  
	 * <p>Description: </p>  
	 * <p>Copyright: Copyright (c) 2017</p>  
	 * @author shenlan  
	 * @date 2020年10月20日  
	 * @version 1.0
	 */
	public Object seachOne(String sql,List<Object> params,Method method){
		
		return execute.query(sql,params,method);
	}
	
	/**
	 * 
	 * 方法概述 获取bean
	 * <p>Title: SqlSession.java</p>  
	 * <p>Description: </p>  
	 * <p>Copyright: Copyright (c) 2017</p>  
	 * @author shenlan  
	 * @date 2020年10月20日  
	 * @version 1.0
	 */
	public <T> T getMapper(Class clazz){
		
		return configuation.getMapper(clazz,this);
	}
	
	public void commit(){
		
		Connection conn = execute.getConn();
		try {
			if(conn != null){
				conn.commit();
			}
		} catch (Exception e) {

		}
	}
	
	public void close(){
		
		InitData.closeResource(execute.getRs(), execute.getPst(), execute.getConn());
	}
}
