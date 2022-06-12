package FileManagement;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

//Each
/**
 *
 * @author Adrià Valls, Sebastian Andrade 2022
 */
public class MatchWriter {
    List<Integer> cellList;
    List<Integer> xList;
    List<Integer> yList;

    //TODO: for decoder, initialize match list from the file
    public MatchWriter()  {
        cellList = new ArrayList<>();
        xList = new ArrayList<>();
        yList = new ArrayList<>();
    }
    public void clearData(){
        cellList = new ArrayList<>();
        xList = new ArrayList<>();
        yList = new ArrayList<>();
    }

    public void addMatch(int cellNumber, int xCoord, int yCoord){
        cellList.add(cellNumber);
        xList.add(xCoord);
        yList.add(yCoord);
    }

    public void saveToFile(File matchFile){

        try {

            FileOutputStream fos = new FileOutputStream(matchFile, true);

            fos.write(xList.size());

            for(int i=0; i<xList.size(); i++){
                //Cell number on the destiny image
                fos.write(cellList.get(i));
                //X coordinate on the base image
                fos.write(xList.get(i));
                //Y coordinate on the base image
                fos.write(yList.get(i));
            }

            fos.close();

        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

    }


}
