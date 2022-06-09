package Display;
import java.awt.image.BufferedImage;
import java.util.Timer;

public class ScheduledCall {
    int my_fps;
    String my_absolutePath;
    BufferedImage my_img;
    String my_filter;

    //constructor
    public ScheduledCall(String absolutePath, int fps,BufferedImage img, String filter) {
        this.my_fps = fps;
        this.my_absolutePath = absolutePath;
        this.my_img = img;
        this.my_filter = filter;
    }

    public void main(String[] args) {
        Timer timer = new Timer();
        //call DisplayImg class to reference the playvideo function
        System.out.println("hello");
        DisplayImg displayImg = new DisplayImg(this.my_img, this.my_filter);
        displayImg.setVisible(true);

        //timer.scheduleAtFixedRate(displayImg.playVideo2(this.my_absolutePath, 0),0, this.my_fps);

    }
}
