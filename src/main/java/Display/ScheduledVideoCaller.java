package Display;

import java.awt.image.BufferedImage;
import java.util.Timer;

public class ScheduledVideoCaller {

    private int my_fps;

    //constructor
    public ScheduledVideoCaller(DisplayImg displayImg, int fps, String inPath) {

        Timer timer = new Timer();
        ScheduledPlayVideo spv = new ScheduledPlayVideo(displayImg, inPath);

        timer.scheduleAtFixedRate(spv,0,fps/1000); //TODO-hangle fps format
    }
}

