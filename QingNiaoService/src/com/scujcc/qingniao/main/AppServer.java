package com.scujcc.qingniao.main;

import java.util.List;
import java.util.LinkedList;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.channels.Selector;
import java.nio.channels.SelectionKey;
import java.util.Iterator;
import java.util.Set;
import com.scujcc.qingniao.server.Notifier;
import com.scujcc.qingniao.server.Reader;
import com.scujcc.qingniao.server.ReaderAndWriter;
import com.scujcc.qingniao.server.Request;
import com.scujcc.qingniao.server.Writer;

/**
 * 主控制线程
 * 
 * @author 聂臣圆
 * 
 */
public class AppServer implements Runnable {
	private static List<SelectionKey> wpool = new LinkedList<SelectionKey>(); // 回应池
	private static List<SelectionKey> rpool = new LinkedList<SelectionKey>(); // 回应池
	private static Selector selector;
	private ServerSocketChannel sschannel;
	private InetSocketAddress address;
	protected Notifier notifier;
	private int port;

	private static int MAX_THREADS = 4;// 读线程.写线程数量

	/**
	 * 
	 * @param port
	 *            服务端口
	 * @throws Exception
	 */
	public AppServer(int port) throws Exception {
		this.port = port;

		// 获取事件触发器
		notifier = Notifier.getNotifier();

		// 创建读写线程池
		for (int i = 0; i < MAX_THREADS; i++) {
			Thread r = new Reader();
			Thread w = new Writer();

			r.start();
			w.start();
		}

		// 创建无阻塞网络套接
		selector = Selector.open();
		sschannel = ServerSocketChannel.open();
		sschannel.configureBlocking(false);
		address = new InetSocketAddress(port);
		ServerSocket ss = sschannel.socket();
		ss.bind(address);
		sschannel.register(selector, SelectionKey.OP_ACCEPT);
	}

	public void run() {
		System.out.println("Server started ...");
		System.out.println("Server listening on port: " + port);
		// 监听
		while (true) {
			try {
				int num = 0;
				num = selector.select();
				if (num >= 2) {
					System.out.println("why hehehhehehhehehe");
				}
				System.out.println("num size ..." + num);
				if (num > 0) {
					Set selectedKeys = selector.selectedKeys();
					Iterator it = selectedKeys.iterator();

					while (it.hasNext()) {
						SelectionKey key = (SelectionKey) it.next();
						// 处理IO事件
						if ((key.readyOps() & SelectionKey.OP_ACCEPT) == SelectionKey.OP_ACCEPT) {
							// Accept the new connection
							ServerSocketChannel ssc = (ServerSocketChannel) key
									.channel();
							notifier.fireOnAccept();

							SocketChannel sc = ssc.accept();
							sc.configureBlocking(false);

							// 触发接受连接事件
							Request request = null;
							if (!sc.isRegistered()) {
								request = new Request(sc);
							} else {
								request = (Request) key.attachment();
								System.out.println("key is already register");
							}
							notifier.fireOnAccepted(request);
							System.out.println("start register read ");
							// 注册读操作,以进行下一步的读操作
							sc.register(selector, SelectionKey.OP_READ, request);
						} else if ((key.readyOps() & SelectionKey.OP_READ) == SelectionKey.OP_READ) {
							System.out.println("go to read ");
							Reader.processRequest(key); // 提交读服务线程读取客户端数据
							key.cancel();
						} else if ((key.readyOps() & SelectionKey.OP_WRITE) == SelectionKey.OP_WRITE) {
							System.out.println("why goto opwritre ");
							Writer.processRequest(key); // 提交写服务线程读取客户端数据
							key.cancel();
						}
						it.remove();
					}

				} else if (num == 0) {
					addRegisterRead();
					addRegisterWrite();
				}

			} catch (Exception e) {
				notifier.fireOnError("Error occured in Server: " + e);
				continue;
			}
		}
	}

	/**
	 * @deprecated 此方法完全废弃，不可用 添加新的读写通道注册
	 */
	private void addRegisterReadAndWrite() {
		synchronized (rpool) {
			while (!rpool.isEmpty()) {
				SelectionKey key = rpool.remove(0);
				SocketChannel schannel = (SocketChannel) key.channel();
				try {
					// Request request = new Request(schannel);
					schannel.register(selector, SelectionKey.OP_ACCEPT,
							key.attachment());
				} catch (Exception e) {
					try {
					} catch (Exception e1) {
					}
					notifier.fireOnError("Error occured in addRegister: "
							+ e.getMessage());
				}
			}
		}
	}

	/**
	 * 添加新的写通道注册
	 */
	private void addRegisterWrite() {
		synchronized (wpool) {
			while (!wpool.isEmpty()) {
				SelectionKey key = wpool.remove(0);
				SocketChannel schannel = (SocketChannel) key.channel();
				try {
					// Request request = new Request(schannel);
					schannel.register(selector, SelectionKey.OP_WRITE,
							key.attachment());
				} catch (Exception e) {
					try {
					} catch (Exception e1) {
					}
					notifier.fireOnError("Error occured in addRegister: "
							+ e.getMessage());
				}
			}
		}
	}

	/**
	 * 添加新的读通道注册
	 */
	private void addRegisterRead() {
		synchronized (rpool) {
			while (!rpool.isEmpty()) {
				SelectionKey key = rpool.remove(0);
				SocketChannel schannel = (SocketChannel) key.channel();
				try {
					// Request request = new Request(schannel);
					schannel.register(selector, SelectionKey.OP_READ,
							key.attachment());
				} catch (Exception e) {
					try {
					} catch (Exception e1) {
					}
					notifier.fireOnError("Error occured in addRegister: "
							+ e.getMessage());
				}
			}
		}
	}

	/**
	 * @deprecated
	 * @param key
	 */
	public static void processReadAndWriteRequest(SelectionKey key) {
		synchronized (rpool) {
			rpool.add(rpool.size(), key);
			rpool.notifyAll();
		}
		selector.wakeup(); // 解除selector的阻塞状态，以便注册新的通道
	}

	public static void processReadRequest(SelectionKey key) {
		synchronized (rpool) {
			rpool.add(rpool.size(), key);
			rpool.notifyAll();
		}
		selector.wakeup(); // 解除selector的阻塞状态，以便注册新的通道
	}

	public static void processWriteRequest(SelectionKey key) {
		synchronized (wpool) {
			wpool.add(wpool.size(), key);
			wpool.notifyAll();
		}
		selector.wakeup(); // 解除selector的阻塞状态，以便注册新的通道
	}
}
