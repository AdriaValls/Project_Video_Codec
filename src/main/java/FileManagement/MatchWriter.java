package FileManagement;

import com.sun.org.apache.xerces.internal.impl.xpath.regex.Match;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

//Each
public class MatchWriter {
    List<Integer> x;
    List<Integer> y;
    String filePath;


    //TODO: for decoder, initialize match list from the file
    public MatchWriter(String matchFilePath)  {
        x = new ArrayList<>();
        y = new ArrayList<>();
        filePath = matchFilePath;

    }

    public void addMatch(int xCoord, int yCoord){
        x.add(xCoord);
        y.add(yCoord);
    }

    public void saveToDir(){
        try {
            FileWriter myWriter = new FileWriter(filePath);
            myWriter.write(x.size());
            for(int i=0; i<=x.size(); i++){
                myWriter.write(x.get(i));
                myWriter.write(y.get(i));
            }

            myWriter.close();


        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

    }


}
