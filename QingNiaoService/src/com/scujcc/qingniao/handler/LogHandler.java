package com.scujcc.qingniao.handler;


import java.util.Date;

import com.scujcc.qingniao.event.EventAdapter;
import com.scujcc.qingniao.server.Request;

/**
 * ÈÕÖ¾¼ÇÂ¼
 */
public class LogHandler extends EventAdapter {
    public LogHandler() {
    }

    public void onClosed(Request request) throws Exception {
        String log = new Date().toString() + " from " + request.getAddress().toString();
        System.out.println(log);
    }

    public void onError(String error) {
        System.out.println("Error: " + error);
    }
}
