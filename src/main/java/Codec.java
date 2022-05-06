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
        //try to unzip folder
        if(argParser.isDecode()){
            test_Unzip_file(argParser);
        }
        //try to zip folder
        if(argParser.isEncode()){
            test_Zip_file(argParser);
        }

        //try video
        testVideo(argParser);
    }
    public static void test_Unzip_file(Args arguments){

        //read ZIP files
        ZipHandler zipHandler = new ZipHandler();
        //pass input path and output path
        zipHandler.readZip(arguments.getZipPath(), arguments.getOutputName());
        //TODO-Maybe create an if arg for this
        zipHandler.copy_file_as_jpeg(arguments.getOutputName(), arguments.getOutputName() +"JPEG_Copy");

    }
    public static void test_Zip_file(Args arguments){

        //write ZIP files
        ZipHandler zipHandler = new ZipHandler();
        //pass input path and output path
        zipHandler.writeZip(arguments.getZipPath(), arguments.getOutputName());

    }

    public static void testVideo(Args arguments){

        BufferedImage img = null;
        File inputFile = null;
        if(arguments.isDecode()){
            inputFile = new File(arguments.getOutputName()); //use output path

        }else if(arguments.isEncode()){ //try to zip folder

            inputFile = new File(arguments.getZipPath()); //use input path
        }else{
            inputFile = new File(arguments.getZipPath()); //else use input path
        }

        File[] file_allPaths = inputFile.listFiles();

        //read and write JPGE files
        //try to read an image
        JPEG_Handler jpeg_handler = new JPEG_Handler();
        img = jpeg_handler.readImage(file_allPaths[0].getAbsolutePath());

        //display one image
        DisplayImg displayImg = new DisplayImg(img, arguments.getFilter());
        displayImg.setVisible(true);
        displayImg.playVideo(inputFile.getAbsolutePath(), arguments.getFps());
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
