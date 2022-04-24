import Parser.Args;
import com.beust.jcommander.JCommander;
import com.beust.jcommander.ParameterException;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Codec {

    public static void main(String[] args) {
        System.out.print("TM codec"+"\n");

        Args argParser = new Args();

        JCommander jCommander = new JCommander(argParser);
        jCommander.setProgramName("TMCodec");


        try{
            jCommander.parse(args);
            testParser(argParser);
        }catch (ParameterException exception){
            System.out.print(exception.getMessage());
        }

        if (argParser.isHelp()){
            jCommander.usage();
            System.exit(0);
        }


    }

    public void testVideo(Args arguments){

        File file = null;
        BufferedImage img = null;
        int width, height;

        //read and write JPGE files
        //try to read an image
        JPEG_Handler jpeg_handler = new JPEG_Handler();
        img = jpeg_handler.readImage("C:/Users/sebas/IdeaProjects/Project_Video_Codec_NEW/src/main/resources/images/Cubo/Cubo00.png");

        //display one image
        DisplayImg displayImg = new DisplayImg(img);
        //displayImg.setVisible(true);
        displayImg.playVideo(arguments.getZipPath());
    }

    public static void testParser(Args arguments){

        if(arguments.isEncode()){
            System.out.print("Encode active"+"\n");
        }
        if(arguments.isDecode()){
            System.out.print("Decode active"+"\n");
        }
        String zipPath = arguments.getZipPath();
        System.out.print("Zip file path: " + zipPath +"\n");

        System.out.print("FPS: " + arguments.getFps()+"\n");
        System.out.print("nTiles: " + arguments.getnTiles()+"\n");
        System.out.print("seekRange: " + arguments.getSeekRange()+"\n");
        System.out.print("GOP: " + arguments.getGOP()+"\n");
        System.out.print("Quality: " + arguments.getQuality()+"\n");



        File file;
        BufferedImage img = null;
        int width, height;

        //TODO- fis.close???
        //try to read an image
        try{
            file = new File(zipPath);
            img = ImageIO.read(file);
            System.out.println("Image reading: correct");
        }catch (IOException error){
            System.out.println("Error reading image: " + error);
        }
    }
}
