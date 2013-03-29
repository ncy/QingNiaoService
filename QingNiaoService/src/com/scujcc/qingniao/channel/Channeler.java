package com.scujcc.qingniao.channel;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import com.scujcc.qingniao.domain.Message;

public class Channeler {
	private Vector<Message> messages;// 消息集
	private boolean isNews;//兴趣用户状态是否有更新
	private List<Integer> sendUsers;//状态发送集
	private Map<Integer, Short> intrestUsers;// 兴趣用户状态集

	/**
	 * @deprecated
	 * @return 整个消息队列
	 */
	public synchronized Vector<Message> getMessages() {
		return messages;
	}

	/**
	 * 往消息信道中增加消息
	 * 
	 * @param message
	 *            需要增加的消息
	 */
	public synchronized void addMessage(Message message) {
		synchronized (messages) {
			if (messages == null) {
				messages = new Vector<Message>();
			}
			messages.add(messages.size(), message);
		}
	}
	/**
	 * 从信道中的到并移除最大条数为10条的消息集
	 * @return 最先发送的最大条数为10条的消息集
	 */
   public synchronized List<Message> getMessage()
   {
	   List<Message> reMess=null;
	   if(messages!=null)
	   {
		   reMess=new ArrayList<Message>();
		   if(messages.size()>=10)
		   {
			   for(int i=0;i<10;i++)
			   {
				   reMess.add(messages.remove(0));
			   }
		   }else
		   {
			   for(int i=messages.size();i>0;i--)
			   {
				   reMess.add(messages.remove(0));
			   }
		   }
		   
	   }
	   return reMess;
   }
	/**
	 * @deprecated
	 * @param messages
	 */
	public synchronized void setMessages(Vector<Message> messages) {
		this.messages = messages;
	}

	public void refeshUsers(int user, short status) {
		
   	}

}
