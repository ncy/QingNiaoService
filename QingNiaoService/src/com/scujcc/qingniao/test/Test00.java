package com.scujcc.qingniao.test;

import java.util.List;

import org.apache.ibatis.session.SqlSession;

import com.scujcc.qingniao.domain.Users;
import com.scujcc.qingniao.factory.SessionFac;
import com.scujcc.qingniao.mapper.UserMapper;

public class Test00 {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		SqlSession session=SessionFac.getSession();
    UserMapper userMapper=session.getMapper(UserMapper.class);
    //List<Users> users=userMapper.getUsers();
    Users users=new Users();
    users.setDr(0);
    users.setName("nimei");
    users.setPwd("23213213");
    int a=userMapper.addUser(users);
    session.commit();
    session.close();
    
    if(a!=0)
    {
    	System.out.println(a);
    System.out.println(users.getId());
    }
	}


}
