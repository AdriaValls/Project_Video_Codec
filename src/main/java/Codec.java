import Display.DisplayImg;
import Display.ScheduledCall;
import Display.ScheduledVideoCaller;
import FileManagement.JPEG_Handler;
import FileManagement.ZipHandler;
import Parser.Args;
import com.beust.jcommander.JCommander;
import com.beust.jcommander.ParameterException;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DecimalFormat;

import Codec.Encoder;
import Codec.Decoder;

public class Codec {
    //private static Args main_args;

    public static void main(String[] args) {
        System.out.print("TM codec"+"\n");

        Args argParser = new Args();
        //main_args = argParser; //copy the arguments so we can use them in the runnable.

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
        //Decode images of a file
        if(argParser.isDecode()){
            decode(argParser);
            //test_Unzip_file(argParser); //for testing
        }
        //Encode images of a file
        if(argParser.isEncode()){
            encode(argParser);
            //test_Zip_file(argParser); //for testing
        }

        //try video
        //callRunnable(argParser);

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
    public static void decode(Args arguments){

        long startTime = System.currentTimeMillis();

        Decoder decoder = new Decoder();
        decoder.decode(arguments.getZipPath(),arguments.getOutputName()+"_Decoded",
                arguments.getnTiles(), arguments.getGOP());

        long encodingtime = System.currentTimeMillis() - startTime;

        System.out.println("Files decoded!");
        System.out.println("Encoding time: " + (double) encodingtime /1000 + "s");


    }
    public static void encode(Args arguments){

        long startTime = System.currentTimeMillis(); //to keep track of the execution time
        //read ZIP files
        ZipHandler zipHandler = new ZipHandler();
        //pass the input path and output path
        zipHandler.readZip(arguments.getZipPath(), arguments.getOutputName());

        //once the Zip has been unzipped, we will now apply the encoding process
        Encoder encoder = new Encoder();
        encoder.encode(arguments.getOutputName(),arguments.getOutputName()+"_Encoded",
                arguments.getnTiles(), arguments.getSeekRange(), arguments.getGOP(), arguments.getQuality());

        //now that we have saved our encoded images as jpeg in another file, we can now zip it.
        zipHandler.writeZip(arguments.getOutputName()+"_Encoded", arguments.getOutputName()+"_Finished.zip");

        long encodingtime = System.currentTimeMillis() - startTime;

        System.out.println("Files encoded!");
        System.out.println("Encoding time: " + (double) encodingtime /1000 + "s");

        //now lets print the gain in data size
        Path old_path = Paths.get(arguments.getZipPath());
        Path encoded_path = Paths.get(arguments.getOutputName()+"_Finished.zip");
        try {

            //size of files (in bytes)
            long old = Files.size(old_path);
            long encoded = Files.size(encoded_path);

            System.out.println(String.format("File size improvement: "+ "%,d kilobytes", (old - encoded)/1024));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void callRunnable(Args arguments){ //start Display, and start Thread for Scheduler

        System.out.println("fps: " + arguments.getFps());

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

        //display one image, to start the window
        DisplayImg displayImg = new DisplayImg(img, arguments.getFilter());
        displayImg.setVisible(true);

        //make first call to start the Scheduler, to control the frame rate on a separate Thread
        ScheduledVideoCaller svc = new ScheduledVideoCaller(displayImg, arguments.getFps(), inputFile.getAbsolutePath());
        Thread thread1 = new Thread(svc);
        thread1.start();
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
