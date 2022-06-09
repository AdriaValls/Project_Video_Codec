package FileManagement;

import java.io.*;

public class MatchReader {

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
