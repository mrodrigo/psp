package serpis.psp;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Scanner;


public class TcpClient {

	private static final String SERVER_IP = "127.0.0.1";
	private static final int SERVER_PORT = 12345;
	/**
	 * @param args
	 * @throws IOException 
	 * @throws UnknownHostException 
	 * @throws InterruptedException 
	 */
	public static void main(String[] args) throws UnknownHostException, IOException, InterruptedException {

		System.out.printf("TcpClient SERVER_IP=%s port=%s\n", SERVER_IP, SERVER_PORT);
		Socket socket = new Socket(SERVER_IP, SERVER_PORT);
		
		PrintWriter printWriter = new PrintWriter(socket.getOutputStream());
		
		Scanner scanner = new Scanner(socket.getInputStream());
		
		String fileName = args[0];
		System.out.printf("TcpClient fileName=%s\n", fileName);
		List<String> lines = Files.readAllLines(Paths.get(fileName), StandardCharsets.UTF_8);
		
		for (String line : lines) {
			System.out.printf("TcpClient Enviado='%s'\n", line);
			printWriter.printf("%s\n", line);
			printWriter.flush();
			String lineIn = scanner.nextLine();
			System.out.printf("TcpClient Recibido='%s'\n", lineIn);
			
			Thread.sleep(5000);
		}
		System.out.printf("TcpClient printf '.'\n");
		printWriter.printf(".\n");
		printWriter.flush();
		Thread.sleep(5000);
		
		System.out.printf("TcpClient end.\n");
		
		socket.close();
	}

}
