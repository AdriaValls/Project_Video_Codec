import java.awt.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class JPEG_Handler {

    //constructor
    public JPEG_Handler(){

    }

    public BufferedImage readImage(String str ){
        BufferedImage img = null;
        try{
            File file = new File(str);
            img = ImageIO.read(file);

            //System.out.println("Image reading: correct");

        }catch (IOException error){
            System.out.println("Error reading image: " + error);
        }
        return img;
    }

    public void writeImage(BufferedImage img, String outPath){
        try{
            File file = new File(outPath);
            ImageIO.write(img, "jpeg",file);
            //System.out.println("Image writing: correct");

        }catch (IOException error){
            System.out.println("Error writing image: " + error);
        }
    }

    public BufferedImage png_to_jpeg(BufferedImage imgIN ){
        BufferedImage Jpeg_Image = null;
        Jpeg_Image = new BufferedImage( imgIN.getWidth(), imgIN.getHeight(), BufferedImage.TYPE_INT_RGB);
        Jpeg_Image.createGraphics().drawImage( imgIN, 0, 0, Color.BLACK, null);
        return Jpeg_Image;
    }
}
