import FileManagement.MatchWriter;
import org.junit.Test;
import java.io.File;  // Import the File class
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
                System.out.print("file Created");
                MatchWriter match = new MatchWriter(matchFile);
                match.addMatch(1,1);
                match.addMatch(4,5);
                match.saveToDir();
            }else{
                System.out.print("file Created");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        /*
        try {
            FileWriter myWriter = new FileWriter("C:\\Users\\adriv\\IdeaProjects\\Project_Video_Codec\\MatchFile.txt");
            MatchWriter match = new MatchWriter();
            match.addMatch(1,1);
            match.addMatch(4,5);
            match.saveToDir(myWriter);
            myWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

*/

    }
}
