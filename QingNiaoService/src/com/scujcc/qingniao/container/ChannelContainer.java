package com.scujcc.qingniao.container;

import java.util.HashMap;
import java.util.Map;

import com.scujcc.qingniao.channel.Channeler;

public class ChannelContainer {
	private volatile static ChannelContainer channelContainer;
	private Map<String, Channeler> channels;

	private ChannelContainer() {
		this.channels = new HashMap<String, Channeler>();
	}

	private ChannelContainer(String channelContainerName, Channeler channeler) {
		this.channels = new HashMap<String, Channeler>();
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

	private synchronized void addChanneler(String channelName) {
		this.channels.put(channelName, new Channeler());
	}

	public Channeler getChanneler(String channelName) {
		if (this.channels.get(channelName) == null)
			this.addChanneler(channelName);
		return this.channels.get(channelName);
	}

}
