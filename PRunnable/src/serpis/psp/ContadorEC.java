package serpis.psp;

import java.util.Random;

public class ContadorEC implements Runnable {
	private int hilo;
	public ContadorEC(int hilo){
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
