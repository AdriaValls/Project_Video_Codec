import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;


public class Main {
    public static void main(String[] args) throws IOException {

        File file = null;
        BufferedImage img = null;
        int width, height;

        //read and write JPGE files
        //try to read an image
        JPEG_Handler jpeg_handler = new JPEG_Handler();
        img = jpeg_handler.readImage("C:/Users/sebas/IdeaProjects/Project_Video_Codec/src/images/Cubo/Cubo00.png");


        //display one image
        DisplayImg displayImg = new DisplayImg(img);
        //displayImg.setVisible(true);
        displayImg.playVideo("C:/Users/sebas/IdeaProjects/Project_Video_Codec/src/images/Cubo");


        //try to write an image
        /*
        //TODO-pass str with name of file, or some other way to name it
        jpeg_handler.writeImage(img);

        //read and write ZIP files
        ZipHandler zipHandler = new ZipHandler();
        //pass input path and output path
        zipHandler.readZip("C:/Users/sebas/IdeaProjects/Project_Video_Codec/src/images/Cubo.zip","C:/Users/sebas/IdeaProjects/Project_Video_Codec/src/Out_images/Cubo");
        zipHandler.writeZip("C:/Users/sebas/IdeaProjects/Project_Video_Codec/src/Out_images/Cubo", "C:/Users/sebas/IdeaProjects/Project_Video_Codec/src/Out_images/Cubo.zip");

         */
    }
}
