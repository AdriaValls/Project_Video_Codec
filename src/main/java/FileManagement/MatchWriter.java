package FileManagement;


import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

//Each
public class MatchWriter {
    List<Integer> xList;
    List<Integer> yList;



    //TODO: for decoder, initialize match list from the file
    public MatchWriter()  {
        xList = new ArrayList<>();
        yList = new ArrayList<>();
    }

    public void addMatch(int xCoord, int yCoord){
        xList.add(xCoord);
        yList.add(yCoord);
    }

    public void saveToFile(File file){
        File matchFile = file;
        try {

            FileOutputStream fos = new FileOutputStream(matchFile, true);
            String outputString = "";
            outputString += xList.size();

            for(int i=0; i<xList.size(); i++){

                outputString += (xList.get(i).toString());
                System.out.println("x = "+xList.get(i));
                outputString += (yList.get(i).toString());
                System.out.println("y = "+yList.get(i));
            }
            byte[] b= outputString.getBytes();
            fos.write(b);
            fos.close();

        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

    }


}
