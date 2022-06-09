package Codec;

import FileManagement.JPEG_Handler;
import FileManagement.MatchWriter;
import sun.security.krb5.internal.crypto.Des;

import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.PublicKey;

public class Encoder {

    public Encoder() {
    }

    public void encode(String inPath, String outPath, int nTiles, int seekRange, int GOP, int quality) {

        JPEG_Handler jpeg_handler = new JPEG_Handler();
        File inputFile = new File(inPath);
        File[] file_allPaths = inputFile.listFiles();
        if (file_allPaths.length == 0) {
            throw new IllegalArgumentException("This file is empty " + inputFile.getAbsolutePath());
        }
        //Create matchFile and Save num of frames
        String fileName = "\\MatchFile.txt";
        File matchFile = new File(outPath + fileName);
        boolean fileCreated;
        try {
            fileCreated = matchFile.createNewFile();
            if (fileCreated) {
                System.out.print("Match file Created");
                FileOutputStream fos = new FileOutputStream(matchFile, true);
                fos.write(file_allPaths.length);
                fos.close();
            }
        } catch (IOException e) {
            System.out.print("Cannot create MatchFile");
            e.printStackTrace();
        }


        int GOPcount = 0;
        int baseNum = 0;
        int destNum = 0;

        BufferedImage baseImg;
        BufferedImage destImg;

        //Save first Base Image
        File baseImgFile = file_allPaths[baseNum];
        baseImg = jpeg_handler.readImage(baseImgFile.getAbsolutePath());
        jpeg_handler.writeImage(baseImg, outPath);

        MatchWriter matches = new MatchWriter();

        while (destNum + 1 <= file_allPaths.length) {
            destNum += 1;

            if (GOPcount == GOP) {
                GOPcount = 0;
                baseNum = destNum;
                //IMG BASE = NEXT IMAGE
                baseImgFile = file_allPaths[baseNum];
                baseImg = jpeg_handler.readImage(baseImgFile.getAbsolutePath());
                //SAVE IMAGE TAL CUAL
                jpeg_handler.writeImage(baseImg, outPath);
                //SAVE MATCHES
                matches.clearData();
                matches.saveToFile(matchFile);


            } else {
                GOPcount += 1;
                //IMG Dest = NEXT IMAGE
                baseImgFile = file_allPaths[baseNum];
                destImg = jpeg_handler.readImage(baseImgFile.getAbsolutePath());
                //COMPARACION
                destImg = matchFinder(baseImg, destImg, nTiles, seekRange, quality, matches);
                jpeg_handler.writeImage(destImg, outPath);
            }

        }

    }

    public BufferedImage matchFinder(BufferedImage baseImg, BufferedImage destImg, int nTiles, int seekRange, int quality, MatchWriter matches) {
        BufferedImage newDest = destImg;
        for (int x = 0; x < destImg.getWidth(); x += nTiles) {
            for (int y = 0; y < destImg.getHeight(); y += nTiles) {
                newDest = cellMatching(baseImg, destImg, x, y, nTiles, seekRange, quality, matches);
            }
        }
        return newDest;
    }

    public BufferedImage cellMatching(BufferedImage baseImg, BufferedImage destImg, int Xcoord, int Ycoord, int nTiles, int seekRange, int quality, MatchWriter matches) {
        boolean matchFound = false;
        BufferedImage newDest = destImg;

        int centerX = Xcoord;
        int centerY = Ycoord;
        int w = Xcoord + nTiles;
        int h = Ycoord + nTiles;

        //Subdivision Tessela
        BufferedImage tesselaDes = destImg.getSubimage(centerX, centerY, w, h);
        BufferedImage tesselaBase = baseImg.getSubimage(centerX, centerY, w, h);

        int range = 1;

        if (tessleComparator(tesselaBase, tesselaDes, quality)) {
            //TODO aplicar average a la tessela
            matchFound = true;
        }
        boolean outOfRange = false;

        while (!matchFound || !outOfRange) {

            //TODO Search Algorithm
            int x = centerX-range;
            int y = centerX-range;
            int width = range*2+1;
            int height = range*2+1;

            for(int wit=0; wit<width;wit++){
                x = x+wit;
                w = x+nTiles;
                if(x>=0 && x<destImg.getWidth() && w>=0 && w<destImg.getWidth()){
                    for(int hit=0; hit<height;hit++){
                        y = y+hit;
                        h = y+nTiles;
                        if(y>=0 && y<destImg.getHeight() && h>=0 && h<destImg.getHeight()){
                            //generar subimagen
                            tesselaBase = baseImg.getSubimage(x, y, w, h);
                            //comparar las dos subimagenes
                            if (tessleComparator(tesselaBase, tesselaDes, quality)) {
                                matchFound = true;
                                break;
                            }
                        }
                    }

                }
                if(matchFound){
                    break;
                }
            }
            if (range == seekRange) {
                outOfRange = true;
            }
            range++;
        }
        if(matchFound){
           applyAverage(newDest, Xcoord, Ycoord, nTiles);
        }
        return newDest;
    }

    public boolean tessleComparator(BufferedImage baseTessle, BufferedImage destTessle, int quality){
        boolean isMatch =false;
        //TODO: Compare subimages
        return isMatch;
    }

    public BufferedImage applyAverage(BufferedImage destImg, int xCoord, int yCoord, int nTiles){
        //TODO: Average on subimage
        int numPixels = nTiles*nTiles;
        BufferedImage newDest = destImg;

        return newDest;
    }




}
