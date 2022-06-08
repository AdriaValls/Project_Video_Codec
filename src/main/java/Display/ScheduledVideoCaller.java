package Display;

import java.util.Timer;

public class ScheduledVideoCaller implements Runnable{

    private int my_fps;
    DisplayImg my_displayImg;
    private String my_inputPath;

    //constructor
    public ScheduledVideoCaller(DisplayImg displayImg, int fps, String inPath) {
        my_fps = fps; //set fps to control the frame rate
        my_displayImg = displayImg;
        my_inputPath = inPath;
    }

    @Override
    public void run() {
        System.out.println("Scheduler Starts!");
        Timer timer = new Timer();
        ScheduledPlayVideo spv = new ScheduledPlayVideo(my_displayImg, my_inputPath);
        //use scheduler to update the pictures according to the fps
        timer.scheduleAtFixedRate(spv,0,1000/my_fps); //TODO-hangle fps format
    }
}

