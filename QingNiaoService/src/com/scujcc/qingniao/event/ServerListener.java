package com.scujcc.qingniao.event;

import com.scujcc.qingniao.server.Request;
import com.scujcc.qingniao.server.Response;


/**
 * <p>Title: 服务器事件监听器</p>
 * @author 聂臣圆
 *
 */
public interface ServerListener {

   /**
    * 服务器端处理产生错误时触发本事件
    * @param error 错误信息
    */
   public void onError(String error);

   /**
    * 当有客户端发来请求时触发本事件
    */
   public void onAccept() throws Exception;

   /**
    * 当服务端接受客户端请求后触发本事件
    * @param request 客户端请求
    */
   public void onAccepted(Request request) throws Exception;

   /**
    * 当客户端发来数据，并已被服务器控制线程正确读取时，触发该事件
    * @param request 客户端请求
    */
   public void onRead(Request request) throws Exception;

   /**
    * 当可以向客户端发送请求触发本事件
    * @param request 客户端请求
    */
   public void onWrite(Request request, Response response) throws Exception;

   /**
    * 当客户端与服务器结束连接后触发本事件
    * @param request 客户端请求
    */
   public void onClosed(Request request) throws Exception;
}
