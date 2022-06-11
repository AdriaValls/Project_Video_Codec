package Display;

import FileManagement.JPEG_Handler;

import javax.swing.*;
import java.awt.*;
import java.awt.color.ColorSpace;
import java.awt.image.BufferedImage;
import java.awt.image.ColorConvertOp;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;
import java.io.File;
import java.util.TimerTask;
/**
 *
 * @author Adrià Valls, Sebastian Andrade 2022
 */
public class DisplayImg extends JFrame{
    private String filter;
    private Boolean isFilter;

    //constructor
    public DisplayImg(BufferedImage img, String filter){ //which will be our window, the panel will then go ON the window
        int imgWidth = img.getWidth();
        int imgHeight = img.getHeight() + 35; //to make up for some space

        setSize(imgWidth,imgHeight); //use the image size for the window
        setTitle("Project Window");
        setLocationRelativeTo(null); //set it to the center

        if(filter.equals("null")){
            isFilter = false;
        }else{
            this.filter = filter;
            isFilter = true;
        }
        //System.out.println("creating panel...");
        createPanel(img);
        //setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }



    private void createPanel(BufferedImage img){
        JPanel panel = new JPanel();
        this.getContentPane().add(panel); //put panel on the window
        //add components

        JLabel image = new JLabel(new ImageIcon(img)); //make the image a swing component, so we can add it to the panel
        //changeColor(img);
        add(image);
        remove(image);
        //changeColor(img); //it doesn't matter if we change it before or after we add the image

    }

    public void playVideo(String inPath, int fps){
        //this function was used before, when we didn't have the Scheduler. Now its not in use
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
            if(isFilter){
                applyFilter(img);
            }
            //System.out.println(frame.getAbsolutePath()); //print for debugging
            JLabel image = new JLabel(new ImageIcon(img));
            //add and remove the images, making the illusion of updating the panel
            add(image);
            setVisible(true);
            remove(image);

            progressBar = progressBar.substring(0, count) + "=" + progressBar.substring(count+1,progressBar.length());
            //to control the frame rate, anything with more than one 0 is probably too slow
            FrameRateControl frameRate = new FrameRateControl(1000/fps); //in milliseconds
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

    public void playVideo2(String inPath, int count){
        //Now using the Scheduler, as we call the task ScheduledPlayVideo, count is initialized and updated,
        //and it is used to control up until when the Panel is updated

        JPEG_Handler jpeg_handler = new JPEG_Handler();
        File inputFile = new File(inPath);
        File[] file_allPaths = inputFile.listFiles();
        String progressBar = new String(new char[file_allPaths.length]).replace('\0', '_');

        if (file_allPaths.length == 0){
            throw new IllegalArgumentException("This file is empty " + inputFile.getAbsolutePath());
        }

        if (count < file_allPaths.length){

            File frame = file_allPaths[count];
            BufferedImage img = jpeg_handler.readImage(frame.getAbsolutePath());

            if(isFilter){
                applyFilter(img);
            }
            //System.out.println(frame.getAbsolutePath()); //print for debugging
            JLabel image = new JLabel(new ImageIcon(img));
            //add and remove the images, making the illusion of updating the panel
            add(image);
            setVisible(true);
            remove(image);
            progressBar = progressBar.substring(0, count) + "=" + progressBar.substring(count+1,progressBar.length());

            System.out.print(progressBar + "\r");
            //imageUpdate(image);
        }

    }

    public void applyFilter(BufferedImage img){
        if(filter.equals("color")){
            changeColor(img);
        }else if(filter.equals("average")){
            averageFilter(img);
        }else{
            System.out.print(filter+" does not exist, no filter was applied. \n");
        }

    }
    public void averageFilter(BufferedImage img){

        float[] SHARPEN3x3 = {
                0.f, -1.f, 0.f,
                -1.f, 5.0f, -1.f,
                0.f, -1.f, 0.f};

        Kernel kernel = new Kernel(3,3,SHARPEN3x3);
        ConvolveOp cop = new ConvolveOp(kernel, ConvolveOp.EDGE_NO_OP,null);

        //System.out.println("Changing color...");
        ColorSpace cs = ColorSpace.getInstance(ColorSpace.CS_GRAY);
        ColorConvertOp op = new ColorConvertOp(cs, null);
        BufferedImage greyscale = new BufferedImage(img.getWidth(), img.getHeight(), img.TYPE_BYTE_GRAY);
        op.filter(img, greyscale);
        cop.filter(greyscale,img);

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


}
