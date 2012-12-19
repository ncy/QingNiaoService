package com.scujcc.qingniao.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import com.scujcc.qingniao.channel.Channeler;
import com.scujcc.qingniao.domain.Message;

public class ChannelController {
  private Channeler channeler;
  public ChannelController(Channeler channeler)
  {this.channeler=channeler;
  }
  
  private Channeler getChanneler() {
	return channeler;
}
private  boolean addMessage(Message message){
	if(this.getChanneler().getMessages()==null)
		this.getChanneler().setMessages(new Vector<Message>());
	return this.getChanneler().getMessages().add(message);
}
private synchronized boolean removeMessage(Message message)
{
	return this.getChanneler().getMessages().removeElement(message);
}
private synchronized void removeMessages()
{
	 this.getChanneler().getMessages().removeAllElements();
}


@SuppressWarnings("unchecked")
public synchronized List<Message> getMessages(){
	  List<Message> messages=new ArrayList<Message>();
	  messages=(List<Message>) this.getChanneler().getMessages().clone();
	  this.removeMessages();
	  return messages;
	  
  }
public boolean addMess(Message message)
{
	return this.addMessage(message);
}
}
