package com.scujcc.qingniao.channel;

import java.util.Vector;

import com.scujcc.qingniao.domain.Message;

public class Channeler {
  private  Vector<Message> messages;

public synchronized Vector<Message> getMessages() {
	return messages;
}

public synchronized void setMessages(Vector<Message> messages) {
	this.messages = messages;
}
  
  
}
