package serpis.psp;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Scanner;
import java.net.Socket;

//VERSIÓN MULTIHILO

public class ThreadServer implements Runnable {
	
	private static final String newLine = "\r\n";
	private final String defaultFileName = "index.html";
	private final String response200 = "HTTP/1.0 200 OK";
	private final String response404 = "HTTP/1.0 404 Not Found";
	private final String fileNameError404 = "fileError404.html";
	
	private Socket socket;
	private InputStream inputStream;
	private OutputStream outputStream;
	private String fileName;
	private Boolean fileExists;
	
	public ThreadServer(Socket socket){
		this.socket = socket;
	}
	
	@Override
	public void run() {
		
		System.out.println(Thread.currentThread().getName() + " inicio");
		
		try{
			inputStream = socket.getInputStream();
			outputStream = socket.getOutputStream();
			getFileName();
			getFileExists();
			writeHeader();
			writeFile();
			socket.close();
		}catch(IOException ex){
		}catch(InterruptedException ex){
		}
		
		System.out.println(Thread.currentThread().getName() + " fin.");
	}
	
	private void getFileName(){
		Scanner scanner = new Scanner (inputStream);
		String fileName = " ";

		//Linea GET
		while(true){
			String line = scanner.nextLine();
			if (line.contains("GET")){ //GET /index.html HTTP 1/1
				fileName=line.split(" ")[1].substring(1); //Se obtiene index.html		 
				fileName = line.substring(5, line.indexOf(" ", 5));
	
			}
			if (line.equals("")){
				break;
			}
				
		}
		//return fileName.equals("") ? fileName : defaultFileName;	
		if (fileName.equals(""))
			fileName = defaultFileName;
		System.out.println(Thread.currentThread().getName() + "fileName=" + fileName);
	}
	
	private void getFileExists(){
		File file = new File(fileName);
		fileExists = file.exists();
		
	}
	
	private void writeHeader() throws IOException{
		String response = fileExists ? response200 : response404;
		
		String header = response + newLine + newLine;
		byte[] headerBuffer = header.getBytes();
		
		outputStream.write(headerBuffer);
	}
	
	private void writeFile() throws IOException, InterruptedException{

		
		File file = new File(fileName);
		String responseFileName = file.exists() ? fileName : fileNameError404;
		
		final int bufferSize = 2048;
		byte[] buffer = new byte[bufferSize];
		
		FileInputStream fileInputStream = new FileInputStream(responseFileName);
		
		int count;
		
		while ((count = fileInputStream.read(buffer)) != -1){
			System.out.print(Thread.currentThread().getName()+ ".");
			//Retardo
			Thread.sleep (1000); 
			outputStream.write(buffer, 0, count);
		}
		
		fileInputStream.close();
	}
}
