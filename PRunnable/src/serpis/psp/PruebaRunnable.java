package serpis.psp;

import java.util.Random;

public class PruebaRunnable {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println("main");

		//Solución I: ContadorEC
//		for (int hilo = 1; hilo <= 4; hilo++) {
//			Thread thread = new Thread(new ContadorEC(hilo));
//			thread.start();
//		}
		
		//Solución II: ContadorIC
		//new PruebaRunnable();
		
		//Solución III: ContadorLC
		
		//Solución IV: clase anónima
		
		//Solución V: que PruebaRunnable implemente Runnable
		
		System.out.println("main end.");
	}

	public PruebaRunnable() {
		//Solución II: ContadorIC
		for (int hilo = 1; hilo <= 4; hilo++) {
			Thread thread = new Thread(new ContadorIC(hilo));
			thread.start();
		}
	}
	
	private class ContadorIC implements Runnable {
		private int hilo;
		public ContadorIC(int hilo){
			this.hilo = hilo;
		}
		
		@Override
		public void run() {
			for (int contador = 1; contador <= 10; contador++) {
				System.out.printf("hilo=%s contador=%s\n", hilo, contador);
				sleep( getRandomMilis(1000, 5000) );
			}
		}
		
		private int getRandomMilis(int low, int high) {
			Random random = new Random();
			return low + random.nextInt(high - low + 1);
		}
		
		private void sleep(int milis) {
			try {
				Thread.sleep(milis);
			} catch (InterruptedException interruptedException) {
				//
			}
		}
		
	}

}
