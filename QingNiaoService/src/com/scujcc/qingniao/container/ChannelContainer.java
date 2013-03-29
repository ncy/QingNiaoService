package com.scujcc.qingniao.container;

import java.util.HashMap;
import java.util.Map;

import com.scujcc.qingniao.channel.Channeler;

public class ChannelContainer {
	private volatile static ChannelContainer channelContainer;
	private Map<Integer, Channeler> channels;

	private ChannelContainer() {
		this.channels = new HashMap<Integer, Channeler>();
	}

	private ChannelContainer(int channelContainerName, Channeler channeler) {
		this.channels = new HashMap<Integer, Channeler>();
		this.channels.put(channelContainerName, channeler);
	}

	public static ChannelContainer getServerAddr() {
		if (channelContainer == null) {
			synchronized (ChannelContainer.class) {
				if (channelContainer == null) {
					channelContainer = new ChannelContainer();
				}
			}
		}
		return channelContainer;
	}
    /**
     * 注册新的消息信道
     * @param channelName
     */
	private synchronized void addChanneler(int channelName) {
		this.channels.put(channelName, new Channeler());
	}
    /**
     * 得到消息信道
     * @param channelName
     * @return
     */
	public Channeler getChanneler(int channelName) {
		if (this.channels.get(channelName) == null)
			this.addChanneler(channelName);
		return this.channels.get(channelName);
	}

}
