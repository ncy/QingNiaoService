package com.scujcc.qingniao.event;

import com.scujcc.qingniao.server.Request;
import com.scujcc.qingniao.server.Response;


/**
 * <p>Title: ÊÂ¼þÊÊÅäÆ÷</p>
 * @author Äô³¼Ô²
 *
 */
public abstract class EventAdapter implements ServerListener {
    public EventAdapter() {
    }
    public void onError(String error) {
    }
    public void onAccept() throws Exception {
    }
    public void onAccepted(Request request)  throws Exception {
    }
    public void onRead(Request request)  throws Exception {
    }
    public void onWrite(Request request, Response response)  throws Exception {
    }
    public void onClosed(Request request)  throws Exception{
    }
}
