package Display;

import java.awt.image.BufferedImage;
import java.util.Timer;

public class ScheduledVideoCaller implements Runnable{

    private int my_fps;
    DisplayImg my_displayImg;
    private String my_inputPath;
    //constructor
    public ScheduledVideoCaller(DisplayImg displayImg, int fps, String inPath) {
        my_fps = fps;
        my_displayImg = displayImg;
        my_inputPath = inPath;

    }

    @Override
    public void run() {
        System.out.println("Scheduler Starts!");
        Timer timer = new Timer();
        ScheduledPlayVideo spv = new ScheduledPlayVideo(my_displayImg, my_inputPath);

        timer.scheduleAtFixedRate(spv,0,1000/my_fps); //TODO-hangle fps format
    }
}

