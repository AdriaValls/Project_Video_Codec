import FileManagement.MatchReader;
import FileManagement.MatchWriter;
import org.junit.Test;
import java.io.File;  // Import the File class
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;

public class MatchWriterTest {

    @Test
    public void fileWritingTest(){
        String filePath = "C:\\Users\\adriv\\IdeaProjects\\Project_Video_Codec\\MatchFile.txt";
        File matchFile = new File(filePath);
        boolean fileCreated;
        try{
            fileCreated = matchFile.createNewFile();
            if(fileCreated){
                FileOutputStream fos = new FileOutputStream(matchFile, true);
                String outstr = "";
                int numFrames = 1;
                outstr += numFrames;
                byte[] b= outstr.getBytes();
                fos.write(b);
                fos.close();

                System.out.print("file Created");
                MatchWriter match = new MatchWriter();
                match.addMatch(1,1 , 2);
                match.addMatch(4,5, 6);
                match.saveToFile(matchFile);
            }else{
                System.out.print("file Created");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void fileReadingTest(){
        String filePath = "C:\\Users\\adriv\\IdeaProjects\\Project_Video_Codec\\MatchFile.txt";
        File matchFile = new File(filePath);
        boolean fileCreated;
        try{
            fileCreated = matchFile.createNewFile();

            if(fileCreated){

                FileOutputStream fos = new FileOutputStream(matchFile, true);
                int numFrames = 1;
                fos.write(numFrames);
                fos.close();

                System.out.print("file Created");
                MatchWriter match = new MatchWriter();
                match.addMatch(1,1 , 2);
                match.addMatch(4,5, 6);
                match.saveToFile(matchFile);
            }else{
                System.out.print("file Created");
            }
            MatchReader reader = new MatchReader(matchFile);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
