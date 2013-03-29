package com.scujcc.qingniao.server;

import java.nio.channels.SocketChannel;

/**
 * <p>Title: 客户端请求信息类</p>
 * @author 聂臣圆
 *
 */
public class Request {
    private SocketChannel sc;
    private int userId;
    private short wantDo;
    private byte[] dataInput = null;
    Object obj;
   
    public Request(SocketChannel sc) {
        this.sc = sc;
    }
    public java.net.InetAddress getAddress() {
        return sc.socket().getInetAddress();
    }
    public int getPort() {
        return sc.socket().getPort();
    }
    public boolean isConnected() {
        return sc.isConnected();
    }
    public boolean isBlocking() {
        return sc.isBlocking();
    }
    public boolean isConnectionPending() {
        return sc.isConnectionPending();
    }
    public boolean getKeepAlive() throws java.net.SocketException {
        return sc.socket().getKeepAlive();
    }
    public int getSoTimeout() throws java.net.SocketException {
        return sc.socket().getSoTimeout();
    }
    public boolean getTcpNoDelay() throws java.net.SocketException {
        return sc.socket().getTcpNoDelay();
    }
    public boolean isClosed() {
        return sc.socket().isClosed();
    }
    public void attach(Object obj) {
        this.obj = obj;
    }
    public Object attachment() {
        return obj;
    }
    public byte[] getDataInput() {
        return dataInput;
    }
    public void setDataInput(byte[] dataInput) {
        this.dataInput = dataInput;
    }
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public short getWantDo() {
		return wantDo;
	}
	public void setWantDo(short wantDo) {
		this.wantDo = wantDo;
	}
   
}
