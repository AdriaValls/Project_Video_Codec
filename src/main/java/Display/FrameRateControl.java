package Display;

/**
 *
 * @author Adrià Valls, Sebastian Andrade 2022
 */
public class FrameRateControl extends Thread{
    /*
    This is supposed to be used as a frame rate control, to control the speed.
     */
    private long milli_seconds;

    public FrameRateControl(long millis){
        this.milli_seconds = millis;
    }

    @Override
    public void run() {
        try {
            sleep(this.milli_seconds); //wait for this.waitInMs miliseconds
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
