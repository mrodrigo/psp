package serpis.psp;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.Calendar;
import java.util.TimeZone;

public class UdpServer {
	
	private static final int PORT = 12345;
	private static final int MAX_PACKET_SIZE = 2048; //max. udp size 2^16
	private static final String LOG_FILE_NAME = "UdpServer.log";
	
	private static final String LOG_START = "[%1$tF %1$tT UTC] UdpServer Start\n";
	private static final String LOG_CLIENT = "[%1$tF %1$tT UTC] client %2$s %3$s length %4$s message %5$s\n";
	private static final String LOG_END = "[%1$tF %1$tT UTC] UdpServer End\n";
	

	/**
	 * @param args
	 * @throws   
	 */
	public static void main(String[] args) throws IOException  {
		//System.out.println("UdpServer");
		DatagramSocket datagramSocket = new DatagramSocket( PORT );

//		final PrintWriter logPrintWriter = new PrintWriter( 
//				new FileOutputStream(LOG_FILE_NAME, true)); //append true
		
		final PrintWriter logPrintWriter = new PrintWriter( System.out, true ); //autoFlush true 
		
		
		logPrintWriter.printf(LOG_START, Calendar.getInstance(TimeZone.getTimeZone("UTC")));

		Runtime.getRuntime().addShutdownHook(new Thread() {
			public void run() {
				logPrintWriter.printf(LOG_END, Calendar.getInstance(TimeZone.getTimeZone("UTC")));
				logPrintWriter.flush();
				logPrintWriter.close();
		    }			
		});
		
		while (true) {
			byte[] inBuf = new byte[ MAX_PACKET_SIZE ];
			int inLength = inBuf.length;
			DatagramPacket inDatagramPacket = new DatagramPacket(inBuf, inLength);
	
			datagramSocket.receive(inDatagramPacket); //aquí espera hasta recibir
//			System.out.println("remote ip  =" + inDatagramPacket.getAddress());
//			System.out.println("remote port=" + inDatagramPacket.getPort());
//			System.out.println("getLength()=" + inDatagramPacket.getLength());
			String inMessage = new String(inBuf, 0, inDatagramPacket.getLength());
//			System.out.println("inMessage  =" + inMessage);
			
			logPrintWriter.printf(LOG_CLIENT,
					Calendar.getInstance(TimeZone.getTimeZone("UTC")),
					inDatagramPacket.getAddress(),
					inDatagramPacket.getPort(),
					inDatagramPacket.getLength(),
					inMessage
			);
			
			String outMessage = inMessage.toUpperCase();
//			System.out.println("outMessage =" + outMessage);
			byte[] outBuf = outMessage.getBytes();
			int outLength = outBuf.length;
			InetAddress outInetAddress = inDatagramPacket.getAddress(); //ip del remitente
			int outPort = inDatagramPacket.getPort(); //port del remitente
			DatagramPacket outDatagramPacket = 
					new DatagramPacket(outBuf, outLength, outInetAddress, outPort);
			datagramSocket.send(outDatagramPacket);
		}
		//datagramSocket.close();
		//System.out.println("UdpServer end.");
	}

}
