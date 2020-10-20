/**
 * 
 */
package com.baidu.ibatis;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.baidu.ibatis.annotation.Delete;
import com.baidu.ibatis.annotation.Insert;
import com.baidu.ibatis.annotation.Param;
import com.baidu.ibatis.annotation.Select;
import com.baidu.ibatis.annotation.Update;

/**
 * @author 三国
 *
 */
public class MapperProxy implements InvocationHandler{
	
	private SqlSession sqlSession;
	
	public MapperProxy(SqlSession sqlSession){
		this.sqlSession=sqlSession;
	}

	/* (non-Javadoc)
	 * @see java.lang.reflect.InvocationHandler#invoke(java.lang.Object, java.lang.reflect.Method, java.lang.Object[])
	 */
	@Override
	public Object invoke(Object arg0, Method method, Object[] params)throws Throwable {
		
		Map<String, List<Object>> createSqlAndParam = createSqlAndParam(method, params);
		
		String sql=createSqlAndParam.keySet().iterator().next();
		
		List<Object> paramList = createSqlAndParam.get(sql);
		
		return sqlSession.seachOne(sql, paramList, method);
	}
	
	
	/**
	 * 
	 * 方法概述 解析sql
	 * <p>Title: MapperProxy.java</p>  
	 * <p>Description: </p>  
	 * <p>Copyright: Copyright (c) 2017</p>  
	 * @author shenlan  
	 * @date 2020年10月20日  
	 * @version 1.0
	 */
	public Map<String, List<Object>> createSqlAndParam(Method method,Object[] params){
		
		if(method.isAnnotationPresent(Select.class)){
			
			Select select = method.getAnnotation(Select.class);
			
			 return getSql(method, select.value().trim(), params);
			
		}
		if(method.isAnnotationPresent(Insert.class)){
			
			Insert select = method.getAnnotation(Insert.class);
			
			 return getSql(method, select.value().trim(), params);
		}
		if(method.isAnnotationPresent(Update.class)){
			Update select = method.getAnnotation(Update.class);
			
			 return getSql(method, select.value().trim(), params);
		}
		if(method.isAnnotationPresent(Delete.class)){
			Delete select = method.getAnnotation(Delete.class);
			
			 return getSql(method, select.value().trim(), params);
		}
		return null;
	}
	
	
	
	/**
	 * 
	 * 方法概述 组装参数及sql
	 * <p>Title: MapperProxy.java</p>  
	 * <p>Description: </p>  
	 * <p>Copyright: Copyright (c) 2017</p>  
	 * @author shenlan  
	 * @date 2020年10月20日  
	 * @version 1.0
	 */
	public Map<String,List<Object>> getSql(Method method,String sql,Object[] params){
		
		Map<Integer,Object> hashParam = new HashMap<Integer, Object>();
		
		int[] paramIndex = new int[params.length];
		
		int index=0;
		
		List<Object> resultParam = new ArrayList<Object>(); 
		
		Annotation[][] parameterAnnotations = method.getParameterAnnotations();
		
		/**
		 * 1将sql带有#{}替换? 
		 * 2获取参数在sql的位置
		 */
		for (Annotation[] annotations : parameterAnnotations) {
			
			for (Annotation annotation : annotations) {
				
				if(annotation instanceof Param){
					
					Param param=(Param)annotation;
					String parameterName = param.value();
					String replece = "\\#\\{"+parameterName+"\\}";
					String indexOf = "#{"+parameterName+"}";
					paramIndex[index]=sql.indexOf(indexOf);
					hashParam.put(paramIndex[index], params[index]);
					index++;
					sql=sql.replaceAll(replece, "?");
				}
			}
		}
		
		/**
		 * 将参数位置进行从小到大排序
		 */
		for (int i = 0; i < paramIndex.length; i++) {
			for (int j = 1; j < paramIndex.length; j++) {
				if(paramIndex[i]>paramIndex[j]){
					int temp = paramIndex[i];
					paramIndex[i]=paramIndex[j];
					paramIndex[j]=temp;
				}
			}
		}
		
		/**
		 * 按照排序位置组装对应的参数
		 */
		for (int i = 0; i < paramIndex.length; i++) {
			resultParam.add(hashParam.get(paramIndex[i]));
		}
		Map<String,List<Object>> sqlAndParam = new HashMap<String, List<Object>>();
		
		sqlAndParam.put(sql, resultParam);
		
		return sqlAndParam;
	}
}
