package com.scujcc.qingniao.server;

import java.nio.channels.SocketChannel;
import java.nio.ByteBuffer;
import java.io.IOException;

/**
 * * <p>
 * Title: 回应器
 * </p>
 * @author 聂臣圆
 *
 */
public class Response {
	private SocketChannel sc;

	public Response(SocketChannel sc) {
		this.sc = sc;
	}

	/**
	 * 向客户端写数据
	 * 
	 * @param data  byte[]　待回应数据
	 */
	public void send(byte[] data) throws IOException {
		ByteBuffer buffer = ByteBuffer.allocate(data.length);
		buffer.clear();
		buffer.put(data);
		buffer.flip();
		sc.write(buffer);
		buffer.compact();

	}
}
