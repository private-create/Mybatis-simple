/**
 * 
 */
package com.baidu.entity;

import com.baidu.ibatis.annotation.Entity;
import com.baidu.ibatis.annotation.Field;

/**
 * @author 三国
 *
 */
@Entity
public class User {
	
	@Field(column="user_name")
	private String userName;
	
	@Field(column="user_id")
	private String userId;

	/**
	 * @return the userName
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * @param userName the userName to set
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}

	/**
	 * @return the userId
	 */
	public String getUserId() {
		return userId;
	}

	/**
	 * @param userId the userId to set
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	
}
