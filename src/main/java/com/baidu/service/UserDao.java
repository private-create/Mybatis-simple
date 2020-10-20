/**
 * 
 */
package com.baidu.service;

import java.util.List;

import com.baidu.entity.User;
import com.baidu.ibatis.annotation.Param;
import com.baidu.ibatis.annotation.Select;
import com.baidu.ibatis.annotation.Update;

/**
 * @author 三国
 *
 */
public interface UserDao {
	
	@Select("select * from user where user_id = #{userId} ")
	public List<User> seachByUserId(@Param("userId")String userId);
	
	@Select("select * from user where user_id = #{userId} and user_password=#{userPassword}")
	public List<User> seachByIdAnd(@Param("userId")String userId,@Param("userPassword") String userPassword);
	
	@Update("UPDATE user set remark=#{remark} where user_id='admin' ")
	public int updateRemark(@Param("remark")String remark);
}
