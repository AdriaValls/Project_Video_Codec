import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class JPEG_Handler {

    //constructor
    public JPEG_Handler(){

    }

    public BufferedImage readImage(String str ){
        try{
            File file = new File(str);
            BufferedImage img = ImageIO.read(file);

            //System.out.println("Image reading: correct");

            return img;
        }catch (IOException error){
            System.out.println("Error reading image: " + error);
        }
        return null;
    }

    public void writeImage(BufferedImage img){
        try{
            File file = new File("C:/Users/sebas/IdeaProjects/Project_Video_Codec/src/Out_images/outCubo00.png");
            ImageIO.write(img, "png",file);
            //System.out.println("Image writing: correct");

        }catch (IOException error){
            System.out.println("Error writing image: " + error);
        }
    }
}
