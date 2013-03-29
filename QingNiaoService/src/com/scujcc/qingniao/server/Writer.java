package com.scujcc.qingniao.server;

import java.util.List;
import java.util.LinkedList;
import java.nio.channels.SocketChannel;
import java.nio.channels.SelectionKey;

import com.scujcc.qingniao.main.AppServer;


/**
 * * <p>
 * Title: 回应线程
 * </p>
 * @author 聂臣圆
 *
 */
public final class Writer extends Thread {
	private static List<SelectionKey> wrpool = new LinkedList<SelectionKey>();
	private static Notifier notifier = Notifier.getNotifier();

	public Writer() {
	}

	/**
	 * 发送线程主控服务方法,负责调度整个处理过程
	 */
	public void run() {
		while (true) {
			try {
				SelectionKey key;
				synchronized (wrpool) {
					while (wrpool.isEmpty()) {
						wrpool.wait();
					}
					key = wrpool.remove(0);
				}

				// 处理写事件
				write(key);
			} catch (Exception e) {
				continue;
			}
		}
	}

	/**
	 * 处理向客户发送数据
	 * 
	 * @param key  SelectionKey
	 */
	public void write(SelectionKey key) {
		try {
			SocketChannel sc = (SocketChannel) key.channel();
			Response response = new Response(sc);
			// 触发onWrite事件
			notifier.fireOnWrite((Request) key.attachment(), response);
			AppServer.processReadRequest(key);
		} catch (Exception e) {
			key.cancel();
			notifier.fireOnError("Error occured in Writer: " + e);
		}
	}

	/**
	 * 处理客户请求,管理用户的联结池,并唤醒队列中的线程进行处理
	 */
	public static void processRequest(SelectionKey key) {
		synchronized (wrpool) {
			wrpool.add(wrpool.size(), key);
			wrpool.notifyAll();
		}
	}
}
