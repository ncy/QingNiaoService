package com.scujcc.qingniao.main;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Logger;

public class TCPEchoServerThread {
	public static void main(String[] args) throws IOException {
		
			ServerSocket servSock = new ServerSocket(2343);
			Logger logger = Logger.getLogger("practical"); // Run forever,
			while (true) {
				Socket clntSock = servSock.accept();
				// Block waiting for connection 23
				// Spawn thread to handle new connection 24
				Thread thread = new Thread(new EchoProtocol(clntSock, logger));
				thread.start();
				logger.info("Created and started Thread " + thread.getName());
			}
			/* NOT REACHED */
		}
	

}
