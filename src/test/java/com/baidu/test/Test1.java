/**
 * 
 */
package com.baidu.test;

/**
 * @author 三国
 *
 */
public class Test1 {
	
	
	/**
	 * 方法概述
	 * <p>Title: Test1.java</p>  
	 * <p>Description: </p>  
	 * <p>Copyright: Copyright (c) 2017</p>  
	 * @author shenlan  
	 * @date 2020年10月20日  
	 * @version 1.0  
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String sql="select * from user where user_id = #{userId} and user_password=#{userPassword}";
		
		String replece = "#{"+"userId"+"}";
		
		int index=sql.indexOf(replece);
		
		System.out.println(index);
		
		replece = "#{" + "userPassword"+ "}";
		
		index=sql.indexOf(replece);
		
		System.out.println(index);
	}

}
