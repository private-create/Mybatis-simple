/**
 * 
 */
package com.baidu.initdata;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Properties;

/**
 * @author 三国
 *
 */
public class InitData {
	
private static Properties pro ;

	
	static{
		InputStream resourceAsStream = InitData.class.getClassLoader().getResourceAsStream("jdbc.properties");
		try {
			pro = new Properties();
			pro.load(resourceAsStream);
			
			Class.forName(pro.getProperty("driver").trim());
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(resourceAsStream != null){
				try {
					resourceAsStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
	}
	
	public static Connection getConnection(){
		Connection connection = null;
		try {
			 connection=DriverManager.getConnection(pro.getProperty("url"), pro.getProperty("userName"), pro.getProperty("password"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return connection;
	}
	
	
	
	public static void closeResource(ResultSet rs,PreparedStatement pst,Connection conn){
		
		try {
			if(rs != null){
				rs.close();
			}
			if(pst != null){
				pst.close();
			}
			if(conn != null){
				conn.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void closeResource(ResultSet rs,PreparedStatement pst){
		closeResource(rs, pst, null);
	}
	
	public static void beginConnection(Connection conn){
		
		try {
			
			if(conn != null){
				conn.setAutoCommit(false);
			}else{
				throw new Exception("没有获取数据库连接或连接已关闭");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
