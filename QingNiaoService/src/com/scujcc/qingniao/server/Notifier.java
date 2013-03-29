package com.scujcc.qingniao.server;

import java.util.ArrayList;

import com.scujcc.qingniao.event.ServerListener;

/**
 * <p>
 * Title: 事件触发器
 * </p>
 * 
 * @author 聂臣圆
 * 
 */
public class Notifier {
	private static ArrayList<ServerListener> listeners = null;
	private static Notifier instance = null;

	private Notifier() {
		listeners = new ArrayList<ServerListener>();
	}

	/**
	 * 获取事件触发器
	 * 
	 * @return 返回事件触发器
	 */
	public static Notifier getNotifier() {
		if (instance == null) {
			synchronized (Notifier.class) {
				if (instance == null)
					instance = new Notifier();
			}
		}
		return instance;
	}

	/**
	 * 添加事件监听器
	 * 
	 * @param serverListener
	 *            监听器
	 */
	public void addListener(ServerListener serverListener) {
		synchronized (listeners) {
			if (!listeners.contains(serverListener))
				listeners.add(serverListener);
		}
	}

	public void fireOnAccept() throws Exception {
		for (int i = listeners.size() - 1; i >= 0; i--)
			listeners.get(i).onAccept();
	}

	public void fireOnAccepted(Request request) throws Exception {
		for (int i = listeners.size() - 1; i >= 0; i--)
			listeners.get(i).onAccepted(request);
	}

	void fireOnRead(Request request) throws Exception {
		for (int i = listeners.size() - 1; i >= 0; i--)
			listeners.get(i).onRead(request);

	}

	void fireOnWrite(Request request, Response response) throws Exception {
		for (int i = listeners.size() - 1; i >= 0; i--)
			listeners.get(i).onWrite(request, response);

	}

	public void fireOnClosed(Request request) throws Exception {
		for (int i = listeners.size() - 1; i >= 0; i--)
			listeners.get(i).onClosed(request);
	}

	public void fireOnError(String error) {
		for (int i = listeners.size() - 1; i >= 0; i--)
			listeners.get(i).onError(error);
	}
}
