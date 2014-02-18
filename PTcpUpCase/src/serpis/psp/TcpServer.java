package serpis.psp;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class TcpServer {

	//private static final String SERVER_IP = "127.0.0.1";
	private static final int SERVER_PORT = 12345;
	/**
	 * @param args
	 * @throws IOException 
	 * @throws UnknownHostException 
	 */
	public static void main(String[] args) throws UnknownHostException, IOException{
		
		
		ServerSocket serverSocket = new ServerSocket(SERVER_PORT);
		
		while (true) {
			System.out.printf("TcpServer port=%s\n", SERVER_PORT);
		
			final Socket socket = serverSocket.accept();

//			System.out.printf("TcpServer socket.getInetAddress()=%s socket.getPort()=%s\n", socket.getInetAddress(), socket.getPort());
//			processClient(socket);
//			System.out.printf("TcpServer socket.close()\n");
//			socket.close();
			
			new Thread( new Runnable() {
				@Override
				public void run() {
					System.out.printf("TcpServer socket.getInetAddress()=%s socket.getPort()=%s\n", socket.getInetAddress(), socket.getPort());
					//try {
					processClient(socket);
					System.out.printf("TcpServer socket.close()\n");
					socket.close();
					//} catch (IOException iOException) {
					//	System.err.printf("%s\n", iOException.getMessage());
					//} 
				}
			}).start();
			
		}
	}
	
	private static void processClient(Socket socket) {
		try {
			Scanner scanner = new Scanner(socket.getInputStream());
			PrintWriter printWriter = new PrintWriter(socket.getOutputStream());
			
			while (true) {
				String lineIn = scanner.nextLine();
				if (lineIn.equals("."))
					break;
				
				System.out.printf("TcpServer Recibido='%s'\n", lineIn);
				String lineOut = lineIn.toUpperCase();
				System.out.printf("TcpServer Enviado='%s'\n", lineOut);
				printWriter.printf("%s\n", lineOut);
				printWriter.flush();
			}
		} catch (IOException iOException) {
			//TODO registrar excecpción
		}
	}

}
