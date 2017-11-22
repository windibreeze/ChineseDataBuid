public class tThread extends Thread {
	public tThread() {
		super();
	}

	@Override
	public void run() {
		for (int i = 1; i < 10; i++){
			System.out.println(i);
			try {
				sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
			
//		interrupt();
	}

}