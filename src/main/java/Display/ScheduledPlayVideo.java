package Display;
import FileManagement.JPEG_Handler;

import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Timer;
import java.util.TimerTask;

public class ScheduledPlayVideo extends TimerTask {

    int count = 0;
    String my_inPath;
    DisplayImg displayImg;

    //constructor
    public ScheduledPlayVideo(DisplayImg displayImg, String inPath) {
        this.displayImg = displayImg;
        this.my_inPath = inPath;
    }

    @Override
    public void run() {
        //here we want to de what we did in PlayVideo2
        //Somewhere else, like DisplayImg, we should run the caller, where we do
        //timer.scheduleAtFixedRate(), and call this function several times.
        displayImg.playVideo2(this.my_inPath, this.count);
        count++;
    }
}
