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
        //Since this is called by the scheduler, we can call the playVideo2 functions, which updates the
        //video image by adding and removing images to the Panel in DisplayImg
        displayImg.playVideo2(this.my_inPath, this.count);
        count++;
    }
}
