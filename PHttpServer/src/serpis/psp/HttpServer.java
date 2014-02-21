package serpis.psp;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class HttpServer 
{
	
	public static void main(String[] args) throws IOException, InterruptedException {
		//final String newLine = "\r\n";
		final int port = 8080;
		ServerSocket serverSocket = new ServerSocket (port);
						
		while (true) {
			
			Socket socket = serverSocket.accept();
			
			//SimpleServer.Process(socket);
			
			Runnable runnable = new ThreadServer(socket);
			Thread thread = new Thread(runnable);
			thread.start();

		}
		//serverSocket.close();
	}
	
}