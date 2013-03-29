package com.scujcc.qingniao.server;

import java.util.List;
import java.util.LinkedList;
import java.nio.channels.SocketChannel;
import java.nio.channels.SelectionKey;
import java.util.Date;
import java.nio.ByteBuffer;
import java.io.IOException;

import com.scujcc.qingniao.main.AppServer;

/**
 * <p>
 * Title: 读线程
 * </p>
 * 
 * @author 聂臣圆
 * 
 */
public class Reader extends Thread {
	private static List<SelectionKey> repool = new LinkedList<SelectionKey>();
	private static Notifier notifier = Notifier.getNotifier();
	private static int BUFFER_SIZE = 1024;
	private static int SIZE_POSiTION = 0;// 消息长度在接收字节中的开始位置
	private static int USERID_POSiTION = 4;// 用户Id在接收字节中的开始位置
	private static int WANTDO_POSiTION = 8;// 操 作类型在接收字节中德开始位置
	private static int MESS_POSiTION = 10;// 消息在接收字节中的开始位置
	public Reader() {
	}

	public void run() {
		while (true) {
			try {
				SelectionKey key;
				synchronized (repool) {
					while (repool.isEmpty()) {
						repool.wait();
					}
					key = repool.remove(0);
				}

				// 读取数据
				read(key);
			} catch (Exception e) {
				continue;
			}
		}
	}

	/**
	 * 读取客户端发出请求数据
	 * 
	 * @param sc
	 *            套接通道
	 */


	public static Request readRequest(SocketChannel sc, Request request)
			throws IOException {
		ByteBuffer buffer = ByteBuffer.allocate(BUFFER_SIZE);
		buffer.clear();
		int r = 0;
		r = sc.read(buffer);
		if (r > 0) {
			int size = buffer.getInt(SIZE_POSiTION);
			request.setUserId(buffer.getInt(USERID_POSiTION));
			request.setWantDo(buffer.getShort(WANTDO_POSiTION));
			byte[] mesbyte = new byte[size];
			buffer.get(mesbyte, MESS_POSiTION, size);
			request.setDataInput(mesbyte);
			return request;
		} else {
			return null;
		}
	}

	/**
	 * 处理连接数据读取
	 * 
	 * @param key
	 *            SelectionKey
	 */
	public void read(SelectionKey key) {
		try {
			// 读取客户端数据
			SocketChannel sc = (SocketChannel) key.channel();
			Request request = (Request) key.attachment();
			request = readRequest(sc, request);
			if (request != null) {

				// request.setDataInput(clientData);
				// 触发onRead
				notifier.fireOnRead(request);
				AppServer.processWriteRequest(key);

			}
			// 提交主控线程进行写处理
			// AppServer.processWriteRequest(key);
		} catch (Exception e) {
			key.cancel();
			notifier.fireOnError("Error occured in Reader: " + e.getMessage());
		}
	}

	/**
	 * 处理客户请求,管理用户的联结池,并唤醒队列中的线程进行处理
	 */
	public static void processRequest(SelectionKey key) {
		synchronized (repool) {
			repool.add(repool.size(), key);
			repool.notifyAll();
		}
	}

	/**
	 * 数组扩容
	 * 
	 * @param src
	 *            byte[] 源数组数据
	 * @param size
	 *            int 扩容的增加量
	 * @return byte[] 扩容后的数组
	 * @deprecated
	 */
	public static byte[] grow(byte[] src, int size) {
		byte[] tmp = new byte[src.length + size];
		System.arraycopy(src, 0, tmp, 0, src.length);
		return tmp;
	}
}
