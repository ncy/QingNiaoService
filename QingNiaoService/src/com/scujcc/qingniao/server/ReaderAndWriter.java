package com.scujcc.qingniao.server;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;
import java.util.LinkedList;
import java.util.List;

import com.scujcc.qingniao.main.AppServer;

public class ReaderAndWriter implements Runnable {
	private static List pool = new LinkedList();// 读写池
	private static Notifier notifier = Notifier.getNotifier();

	@Override
	public void run() {
		// TODO Auto-generated method stub
		while (true) {
			try {
				SelectionKey key;
				synchronized (pool) {
					while (pool.isEmpty()) {
						pool.wait();
					}
					key = (SelectionKey) pool.remove(0);
				}

				// 读取数据
				readAndWrite(key);
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
	private static int BUFFER_SIZE = 1024;

	public static byte[] readRequest(SocketChannel sc) throws IOException {
		ByteBuffer buffer = ByteBuffer.allocate(BUFFER_SIZE);
		int size = 0;// messge size
		int off = 0;
		int r = 0;
		byte[] data = new byte[BUFFER_SIZE * 10];

		buffer.clear();
		r = sc.read(buffer);
		// byte[] mess = new byte[buffer.getInt(0)];
		// buffer.get(mess, 8, 8 + mess.length);
		byte[] req = new byte[off];
		System.arraycopy(data, 0, req, 0, off);
		if (r > 0) {
			System.out.println("mess size " + buffer.getInt(0) + " it id is "
					+ buffer.getInt(4));

			return buffer.array();
		} else
			return null;
	}

	public void readAndWrite(SelectionKey key) {
		try {
			// 读取客户端数据
			SocketChannel sc = (SocketChannel) key.channel();
			byte[] clientData = readRequest(sc);
			if (clientData != null) {
				Request request = (Request) key.attachment();
				request.setDataInput(clientData);
				// 触发onRead
				notifier.fireOnRead(request);
			//	sc.register(key.selector(), SelectionKey.OP_WRITE, request);
				Response response = new Response(sc);
				// 触发onWrite事件
				notifier.fireOnWrite((Request) key.attachment(), response);
				// 触发onClosed事件
				key.interestOps(SelectionKey.OP_READ);
			}
		//	AppServer.processReadAndWriteRequest(key);

		} catch (Exception e) {
			key.cancel();
			notifier.fireOnError("Error occured in Reader: " + e.getMessage());
		}
	}

	/**
	 * 处理客户请求,管理用户的联结池,并唤醒队列中的线程进行处理
	 */
	public static void processRequest(SelectionKey key) {
		synchronized (pool) {
			pool.add(pool.size(), key);
			pool.notifyAll();
		}
	}

	/**
	 * 数组扩容
	 * @deprecated 
	 * @param src
	 *            byte[] 源数组数据
	 * @param size
	 *            int 扩容的增加量
	 * @return byte[] 扩容后的数组
	 */
	public static byte[] grow(byte[] src, int size) {
		byte[] tmp = new byte[src.length + size];
		System.arraycopy(src, 0, tmp, 0, src.length);
		return tmp;
	}
}
