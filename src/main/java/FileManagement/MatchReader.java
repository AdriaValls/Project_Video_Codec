package FileManagement;

import java.io.*;

/**
 *
 * @author Adrià Valls, Sebastian Andrade 2022
 */
public class MatchReader {

    /**
     * @param matchFile the file that contains the Matched Cell, and its coordinates
     *
     * this function reads MatchFile.txt to interpret the encoded information.
     */
    public MatchReader(File matchFile){
        try{
            FileInputStream fis = new FileInputStream(matchFile);
            int numFrames = fis.read();
            System.out.print("Nuber of frame: " + numFrames +"\n");

            for(int photoNum=0; photoNum<numFrames; photoNum++){

                System.out.print("Frame: " + numFrames +"\n");
                int nMatches = fis.read();
                System.out.print("Matches in frame " + nMatches +"\n");

                for(int matchNum=0; matchNum<nMatches; matchNum++){
                    System.out.print("Match number: " + matchNum +"\n");
                    System.out.print("Cell number: "+fis.read() +"\n");
                    System.out.print("X coord: "+fis.read() +"\n");
                    System.out.print("Y coord: "+fis.read() +"\n");
                }
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


}
