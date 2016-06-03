import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.util.Timer;
import java.util.TimerTask;


public class NoLockScreen {
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
			r.keyPress(KeyEvent.VK_CAPS_LOCK);
			r.keyRelease(KeyEvent.VK_CAPS_LOCK);
			r.keyPress(KeyEvent.VK_CAPS_LOCK);
			r.keyRelease(KeyEvent.VK_CAPS_LOCK);
			//System.out.println("The program executes 4 mins once.\tIt has called " + (++l) + " times.\tTime is totally " + ((l-1)*4/60) + "h " + ((l-1)*4%60) + "m");
		} catch (AWTException e) {
			e.printStackTrace();
		}

	}

}
