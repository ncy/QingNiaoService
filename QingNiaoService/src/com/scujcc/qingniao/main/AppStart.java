package com.scujcc.qingniao.main;

import com.scujcc.qingniao.handler.LogHandler;
import com.scujcc.qingniao.handler.TimeHandler;
import com.scujcc.qingniao.server.Notifier;

/**
 * <p>Title: Æô¶¯Àà</p>
 * @author Äô³¼Ô²
 *
 */
public class AppStart {

    public static void main(String[] args) {
        try {
            LogHandler loger = new LogHandler();
            TimeHandler timer = new TimeHandler();
            Notifier notifier = Notifier.getNotifier();
            notifier.addListener(loger);
            notifier.addListener(timer);

            System.out.println("Server starting ...");
            AppServer server = new AppServer(5100);
            Thread tServer = new Thread(server);
            tServer.start();
        }
        catch (Exception e) {
            System.out.println("Server error: " + e.getMessage());
            System.exit(-1);
        }
    }
}
