package com.scujcc.qingniao.main;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.scujcc.qingniao.controller.AllMessController;
import com.scujcc.qingniao.controller.Framer;
import com.scujcc.qingniao.controller.LengthFramer;

public class EchoProtocol implements Runnable {
	private Socket clntSock; // Socket connect to client
	private Logger logger; // Server logger 11 12
   
	public EchoProtocol(Socket clntSock, Logger logger) {
		this.clntSock = clntSock;
		this.logger = logger;
	}

	public static void handleEchoClient(Socket clntSock, Logger logger) throws Exception {
		try {
			
			// Get the input and output I/O streams from socket
			InputStream in = clntSock.getInputStream();
			OutputStream out = clntSock.getOutputStream();
			Framer frame=new LengthFramer(in);
			byte[] getbytes=frame.nextMsg();
			AllMessController allMessController=new AllMessController(getbytes, out);
			allMessController.control();
			System.out.println("I get clientBytes "+new String(getbytes,"UTF-8"));
			logger.info("Client " + clntSock.getRemoteSocketAddress()
					+ ", echoed " +getbytes.length + " bytes.");
		} catch (IOException ex) {
			logger.log(Level.WARNING, "Exception in echo protocol", ex);
		} finally {
			try {
				clntSock.close();
			} catch (IOException e) {
				
				logger.info("");
			}
		}
	}

	public void run() {
		try {
			handleEchoClient(clntSock, logger);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
