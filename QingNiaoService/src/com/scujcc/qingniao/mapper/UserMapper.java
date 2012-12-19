package com.scujcc.qingniao.mapper;

import java.util.List;

import com.scujcc.qingniao.domain.Users;

public interface UserMapper {
	List<Users> getUsers();
	int addUser(Users user);
}
