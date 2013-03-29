package com.scujcc.qingniao.handler;


import java.util.*;
import java.text.DateFormat;

import com.scujcc.qingniao.event.EventAdapter;
import com.scujcc.qingniao.server.Request;
import com.scujcc.qingniao.server.Response;

/**
 * 时间查询服务器
 */
public class TimeHandler extends EventAdapter {
    public TimeHandler() {
    }

    public void onWrite(Request request, Response response) throws Exception {
        String command = new String(request.getDataInput());
        System.out.println("get "+command+" and start to write to client");
        String time = null;
        Date date = new Date();

        // 判断查询命令
        if (command.equals("GB")) {
            // 中文格式
            DateFormat cnDate = DateFormat.getDateTimeInstance(DateFormat.FULL,
                DateFormat.FULL, Locale.CHINA);
            time = cnDate.format(date);
        }
        else {
            // 英文格式
            DateFormat enDate = DateFormat.getDateTimeInstance(DateFormat.FULL,
                DateFormat.FULL, Locale.US);
            time = enDate.format(date);
        }
       String s= "hello i get you meess.";  
        response.send(s.getBytes());
    }
}
