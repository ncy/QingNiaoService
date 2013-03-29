package com.scujcc.qingniao.controller;

import java.io.OutputStream;

import org.apache.ibatis.session.SqlSession;

import com.google.gson.Gson;
import com.scujcc.qingniao.container.ChannelContainer;
import com.scujcc.qingniao.domain.Message;
import com.scujcc.qingniao.domain.Users;
import com.scujcc.qingniao.factory.SessionFac;
import com.scujcc.qingniao.mapper.UserMapper;
import com.scujcc.qingniao.tools.AllMessageCut;

public class AllMessController {
	private byte[] allMessByte;
	private String who;
	private int wantdo;
	private String how;
	private OutputStream stream;

	public AllMessController(byte[] allByte, OutputStream stream) {
		this.allMessByte = allByte;
		this.stream = stream;
	}

	public void control() throws Exception {
		this.setMess();
		Gson gson = new Gson();
		Framer framer = new LengthFramer();
		if (wantdo == DoWhat.sendMessage) {

			Message newMess = gson.fromJson(how, Message.class);
			ChannelController channelController = new ChannelController(
					ChannelContainer.getServerAddr().getChanneler(
							newMess.getRecordUser()));
			if (channelController.addMess(newMess)) {
				String record = "1";
				framer.frameMsg(record.getBytes(), this.stream);
			} else {
				String record = "0";
				framer.frameMsg(record.getBytes(), this.stream);
			}
		}
		if (wantdo == DoWhat.registerUser) {
			Users users = gson.fromJson(how, Users.class);
			SqlSession sqlSession= SessionFac.getSession();
			UserMapper userMapper =sqlSession.getMapper(
					UserMapper.class);
			userMapper.addUser(users);
			sqlSession.commit();
			sqlSession.close();
			if (users.getId() != 0) {
				String record = "1," + users.getId();
				framer.frameMsg(record.getBytes(), this.stream);
			} else {
				String record = "0";
				framer.frameMsg(record.getBytes(), this.stream);
			}
		}
         if(wantdo==DoWhat.timer)
         {
        	 framer.frameMsg("good boy you right".getBytes(), this.stream);
         }
	}

	@SuppressWarnings("unused")
	private void setMess() throws Exception {
		String[] allStr = AllMessageCut.cutAllMeaa(allMessByte);
		who = allStr[0];
		wantdo = Integer.parseInt(allStr[1]);
		how = allStr[2];
	}
}