import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.util.Timer;
import java.util.TimerTask;


public class NoScreenLock {
	public static void main(String[] args) {
	new Timer().schedule(new myTimerTask(),1000,1000*60*4);

	}

}
class myTimerTask extends TimerTask{
	private static long l = 0;
	@Override
	public void run() {
		try {
			Robot r = new Robot();
			if(l==0){
				r.keyPress(KeyEvent.VK_WINDOWS);
				r.keyPress(KeyEvent.VK_D);
				r.keyRelease(KeyEvent.VK_D);
				r.keyRelease(KeyEvent.VK_WINDOWS);
			}
			r.keyPress(KeyEvent.VK_CAPS_LOCK);
			r.keyRelease(KeyEvent.VK_CAPS_LOCK);
			r.keyPress(KeyEvent.VK_CAPS_LOCK);
			r.keyRelease(KeyEvent.VK_CAPS_LOCK);
			System.out.println("4 mins a time,called " + (++l) + " times");
		} catch (AWTException e) {
			e.printStackTrace();
		}

	}

}
