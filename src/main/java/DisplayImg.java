import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

public class DisplayImg extends JFrame{

    //constructor
    public DisplayImg(BufferedImage img){ //which will be our window, the panel will then go ON the window
        int imgWidth = img.getWidth();
        int imgHeight = img.getHeight() + 35; //to make up for some space

        setSize(imgWidth,imgHeight); //use the image size for the window
        setTitle("Project Window");
        setLocationRelativeTo(null); //set it to the center

        //System.out.println("creating panel...");
        createPanel(img);
        //setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    private void createPanel(BufferedImage img){
        JPanel panel = new JPanel();
        this.getContentPane().add(panel); //put panel on the window
        //add components
        /*
        JLabel image = new JLabel(new ImageIcon(img)); //make the image a swing component, so we can add it to the panel
        changeColor(img);
        add(image);
        remove(image);
        //changeColor(img); //it doesn't matter if we change it before or after we add the image
         */

    }

    public void changeColor(BufferedImage img){
        //System.out.println("Changing color...");
        for (int i = 0; i < img.getWidth(); i++) {
            for (int j = 0; j < img.getHeight(); j++) {
                int rgb = img.getRGB(i,j);
                Color color = new Color(rgb);
                //Color redIsh = new Color(255, color.getGreen(),color.getBlue()); //keeps green and blue values, sets red to max
                //Color redIsh = new Color(color.getRed(), 0,0);
                Color greenIsh = new Color(0, color.getGreen(),0);
                img.setRGB(i, j, greenIsh.getRGB());
            }
        }

    }

    public void playVideo(String inPath, int fps){
        JPEG_Handler jpeg_handler = new JPEG_Handler();
        File inputFile = new File(inPath);
        File[] file_allPaths = inputFile.listFiles();
        String progressBar = new String(new char[file_allPaths.length]).replace('\0', '_');
        int count = 0;

        if (file_allPaths.length == 0){
            throw new IllegalArgumentException("This file is empty " + inputFile.getAbsolutePath());
        }

        for (File frame : file_allPaths) {

            BufferedImage img = jpeg_handler.readImage(frame.getAbsolutePath());
            //System.out.println(frame.getAbsolutePath()); //print for debugging
            JLabel image = new JLabel(new ImageIcon(img));
            //add and remove the images, making the illusion of updating the panel
            add(image);
            changeColor(img);
            setVisible(true);
            remove(image);

            progressBar = progressBar.substring(0, count) + "=" + progressBar.substring(count+1,progressBar.length());
            //to control the frame rate, anything with more than one 0 is probably too slow
            FrameRateControl frameRate = new FrameRateControl(1/fps); //in milliseconds
            frameRate.start();
            try {
                frameRate.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.print(progressBar + "\r");
            //imageUpdate(image);
            count++;

        }

    }
}
