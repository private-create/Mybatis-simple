/**
 * 
 */
package com.baidu.ibatis;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.print.attribute.standard.Fidelity;

import com.baidu.ibatis.annotation.Select;
import com.baidu.initdata.InitData;

/**
 * @author 三国
 *
 */
/**
 *  sql执行器
 * @author 三国
 *
 */
public class Execute {
	
	private Connection conn;
	
	private ResultSet rs ;
	
	private PreparedStatement pst ;
	/**
	 * @return the conn
	 */
	public Connection getConn() {
		return conn;
	}

	/**
	 * @param conn the conn to set
	 */
	public void setConn(Connection conn) {
		this.conn = conn;
	}
	
	
	/**
	 * @return the rs
	 */
	public ResultSet getRs() {
		return rs;
	}

	/**
	 * @param rs the rs to set
	 */
	public void setRs(ResultSet rs) {
		this.rs = rs;
	}

	/**
	 * @return the pst
	 */
	public PreparedStatement getPst() {
		return pst;
	}

	/**
	 * @param pst the pst to set
	 */
	public void setPst(PreparedStatement pst) {
		this.pst = pst;
	}

	/**
	 * 方法概述
	 * <p>Title: Execute.java</p>  
	 * <p>Description: </p>  
	 * <p>Copyright: Copyright (c) 2017</p>  
	 * @author shenlan  
	 * @date 2020年10月19日  
	 * @version 1.0  
	 */
	public Object query(String sql, List<Object> params, Method method) {
		ResultSet rs = null;
		PreparedStatement pst = null;
		Connection conn = null;
		try {
			
			conn = InitData.getConnection();
			
			setConn(conn);
			
			pst=conn.prepareStatement(sql);
			for (int i = 0; i < params.size(); i++) {
				pst.setObject(i+1, params.get(i));
			}
			if(method.isAnnotationPresent(Select.class)){
				
				rs = pst.executeQuery();
				
				
				return createResltEntity(method, rs);
				
			}else{
				InitData.beginConnection(conn);
				return pst.executeUpdate();
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			setConn(conn);
			setPst(pst);
			setRs(rs);
		}
		return null;
	}
	
	/**
	 * 
	 * 方法概述 组装查询返回参数
	 * <p>Title: Execute.java</p>  
	 * <p>Description: </p>  
	 * <p>Copyright: Copyright (c) 2017</p>  
	 * @author shenlan  
	 * @date 2020年10月20日  
	 * @version 1.0
	 */
	public List<Object> createResltEntity(Method method,ResultSet rs){
		
		List<Object> result = new ArrayList<Object>();
		
		try {
			
			Class<?> returnType = method.getReturnType();
			
			if(returnType.getName().equals(List.class.getName())){
				
				String typeName = method.getGenericReturnType().getTypeName();
				
				typeName = typeName.replaceAll(List.class.getName()+"<", "");
				
				typeName=typeName.replaceAll(">", "");
				
				returnType = Class.forName(typeName);
				
			}
			
			Object obj = null;
			
			while(rs.next()){
				obj = returnType.newInstance();
				for(Field field: returnType.getDeclaredFields()){
					
					if(!field.isAnnotationPresent(com.baidu.ibatis.annotation.Field.class)){continue;}
					
					com.baidu.ibatis.annotation.Field annotation =(com.baidu.ibatis.annotation.Field) field.getAnnotation(com.baidu.ibatis.annotation.Field.class);
					
					boolean accessible = field.isAccessible();
					
					field.setAccessible(true);
					
					if(field.getType().getName().equals(String.class.getName())){
						field.set(obj, rs.getString(annotation.column()));
					}else if(field.getType().getName().equals(Integer.class.getName())){
						field.set(obj, rs.getInt(annotation.column()));
					}else if(field.getType().getName().equals(Double.class.getName())){
						field.set(obj, rs.getDouble(annotation.column()));
					}
					field.setAccessible(accessible);
				}
				result.add(obj);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
}
