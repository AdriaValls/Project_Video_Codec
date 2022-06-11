package Codec;

import FileManagement.JPEG_Handler;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 *
 * @author Adrià Valls, Sebastian Andrade 2022
 */
public class Decoder {

    public Decoder(){}

    public void decode(String inPath, String outPath, int nTiles, int GOP){
        File dir = new File(outPath); //we create the directory in which we will save all the images
        dir.mkdir();

        JPEG_Handler jpeg_handler = new JPEG_Handler();
        File inputFile = new File(inPath);
        File[] file_allPaths = inputFile.listFiles();
        if (file_allPaths.length == 0) {
            throw new IllegalArgumentException("This file is empty " + inputFile.getAbsolutePath());
        }

        String fileName = "\\MatchFile.txt";
        File matchFile = new File(inPath + fileName);
        try{
            FileInputStream fis = new FileInputStream(matchFile);

            BufferedImage baseImg;
            BufferedImage destImg;

            int GOPcount = 0;
            int baseNum = 0;
            int destNum = 0;
            //Save first Base Image

            //Frame Num
            int numberOfFrames = fis.read();

            File destImgFile;
            File baseImgFile = file_allPaths[baseNum];
            baseImg = jpeg_handler.readImage(baseImgFile.getAbsolutePath());
            jpeg_handler.writeImage(baseImg, outPath + File.separator + "00.jpeg");

            for(int photoNum=1; photoNum<numberOfFrames; photoNum++){

                if (GOPcount == GOP) {
                    GOPcount = 0;
                    baseNum = photoNum;
                    //IMG BASE = NEXT IMAGE
                    baseImgFile = file_allPaths[baseNum];
                    baseImg = jpeg_handler.readImage(baseImgFile.getAbsolutePath());
                    jpeg_handler.writeImage(baseImg, outPath + File.separator + photoNum + ".jpeg");

                } else {
                    GOPcount += 1;
                    //IMG Dest = NEXT IMAGE
                    destImgFile = file_allPaths[photoNum];
                    destImg = jpeg_handler.readImage(destImgFile.getAbsolutePath());
                    //COMPARACION

                    int nMatches = fis.read();
                    for(int matchNum=0; matchNum<nMatches; matchNum++){
                        int cellNum = fis.read();
                        int xCoord = fis.read();
                        int yCoord = fis.read();
                        destImg = patchMatches(baseImg, destImg, cellNum, xCoord, yCoord, nTiles);
                    }
                    jpeg_handler.writeImage(destImg, outPath + File.separator + photoNum + ".jpeg");
                }
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public BufferedImage patchMatches(BufferedImage baseImg, BufferedImage destImg, int cellNumber, int xCoord, int yCoord, int nTiles){
        BufferedImage newDest = destImg;
        int x = 0;
        int y = 0;

        for(int i=0; i<=cellNumber; i++){
            if(x+nTiles> destImg.getWidth()){
                x = 0;
                y++;
            }else{
                x += nTiles;
            }
        }
        BufferedImage baseTessle = baseImg.getSubimage(x,y,nTiles,nTiles);

        for (int i = 0; y*nTiles < baseTessle.getHeight(); y ++) {
            for (int j = 0; x*nTiles < baseTessle.getWidth(); x ++) {
                newDest.setRGB(xCoord+i, yCoord+j, baseTessle.getRGB(i, j));
            }
        }
        return newDest;
    }
}
