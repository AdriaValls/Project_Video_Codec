package Codec;

import FileManagement.JPEG_Handler;
import FileManagement.MatchWriter;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.PublicKey;
import java.util.HashMap;

public class Encoder {
    private HashMap<Integer, Double> qualityValue;
    /** Encoder Class constructor  */
    public Encoder() {
        /*
        We use this table to make a proportional assesment of the Quality input given by the user.
        Since we use the Euclidean Distance to determine the similarity, and we know how high these distances can be,
        we created these values and assigned it to what we think corresponds to a reasonable quality value.
         */
        qualityValue = new HashMap<>();
        qualityValue.put(10, 1.0);
        qualityValue.put(9, 2.0);
        qualityValue.put(8, 5.0);
        qualityValue.put(7, 8.0);
        qualityValue.put(6, 10.0);
        qualityValue.put(5, 12.0);
        qualityValue.put(4, 15.0);
        qualityValue.put(3, 18.0);
        qualityValue.put(2, 20.0);
        qualityValue.put(1, 25.0);

    }

    /**
     * @param inPath
     */
    public void encode(String inPath, String outPath, int nTiles, int seekRange, int GOP, int quality) {

        System.out.println("Encoding Files :)");

        File dir = new File(outPath); //we create the directory in which we will save all the images
        dir.mkdir();

        JPEG_Handler jpeg_handler = new JPEG_Handler();
        File inputFile = new File(inPath);
        File[] file_allPaths = inputFile.listFiles();

        System.out.println("Estimated time: " + (double) 0.0558 * file_allPaths.length + "s");

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
                //System.out.print("Match file Created");
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
        File destImgFile = file_allPaths[destNum];

        baseImg = jpeg_handler.readImage(baseImgFile.getAbsolutePath());
        jpeg_handler.writeImage(baseImg, outPath + File.separator + "00.jpeg");

        MatchWriter matches = new MatchWriter();
        matches.saveToFile(matchFile);

        String encode_progressBar = new String(new char[file_allPaths.length]).replace('\0', '_');

        while (destNum + 1 < file_allPaths.length) {
            destNum += 1;

            encode_progressBar = encode_progressBar.substring(0, destNum) + "=" + encode_progressBar.substring(destNum+1,encode_progressBar.length());
            System.out.print(encode_progressBar + "\r");

            if (GOPcount == GOP) {
                GOPcount = 0;
                baseNum = destNum;
                //IMG BASE = NEXT IMAGE
                baseImgFile = file_allPaths[baseNum];
                baseImg = jpeg_handler.readImage(baseImgFile.getAbsolutePath());
                //SAVE IMAGE TAL CUAL
                jpeg_handler.writeImage(baseImg, outPath + File.separator + destNum + ".jpeg");
                //SAVE MATCHES
                matches.clearData();
                matches.saveToFile(matchFile);


            } else {
                GOPcount += 1;
                //IMG Dest = NEXT IMAGE
                destImgFile = file_allPaths[destNum];
                destImg = jpeg_handler.readImage(destImgFile.getAbsolutePath());
                //COMPARACION
                //System.out.println("Image num "+destImg);
                destImg = matchFinder(baseImg, destImg, nTiles, seekRange, quality, matches);
                jpeg_handler.writeImage(destImg, outPath + File.separator + destNum + ".jpeg");
                matches.saveToFile(matchFile);
                matches.clearData();
            }

        }

    }

    public BufferedImage matchFinder(BufferedImage baseImg, BufferedImage destImg, int nTiles, int seekRange, int quality, MatchWriter matches) {
        BufferedImage newDest = destImg;
        int cellNum = 0;
        int xCell = 0;
        int yCell = 0;

        for (int y = 0; y*nTiles < destImg.getHeight(); y ++) {
            yCell = y*nTiles;
            for (int x = 0; x*nTiles < destImg.getWidth(); x ++) {
                xCell = x*nTiles;
                //System.out.println("Cell coords: "+xCell+" "+yCell);
                newDest = cellMatching(baseImg, newDest, xCell, yCell, nTiles, seekRange, quality, matches, cellNum);
                cellNum++;
            }
        }
        return newDest;
    }

    public BufferedImage cellMatching(BufferedImage baseImg, BufferedImage destImg, int Xcoord, int Ycoord, int nTiles, int seekRange, int quality, MatchWriter matches, int cellNum) {
        //System.out.println("Entered cell num "+cellNum);
        boolean matchFound = false;
        BufferedImage newDest = destImg;
        int centerX = Xcoord;
        int centerY = Ycoord;
        int w = nTiles;
        int h = nTiles;

        //Subdivision Tessela
        BufferedImage tesselaDes = destImg.getSubimage(centerX, centerY, nTiles, nTiles);
        BufferedImage tesselaBase = baseImg.getSubimage(centerX, centerY, nTiles, nTiles);

        int range = 0;

        if (tessleComparator(tesselaBase, tesselaDes, quality)) {
            matchFound = true;
        }
        boolean outOfRange = false;
        int x = Xcoord;
        int y = Ycoord;
        int test = 0;
        while (!matchFound && !outOfRange) {

            x = centerX-range;
            y = centerY-range;
            int width = range*2+1;
            int height = range*2+1;
            for(int wit=0; wit<width;wit++){
                x = x+wit;
                w = x+nTiles;
                y = centerY-range;
                if(x>=0 && w<destImg.getWidth()){
                    for(int hit=0; hit<height ;hit++){
                        y = y+hit;
                        h = y+nTiles;
                        if(y>=0 && h<destImg.getHeight()){
                            //generar subimagen
                            tesselaBase = baseImg.getSubimage(x, y, nTiles, nTiles);
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
                range++;
            }
            if (range == seekRange) {
                outOfRange = true;
            }

        }
        if(matchFound){
           applyAverage(newDest, Xcoord, Ycoord, nTiles);
           matches.addMatch(cellNum,x,y);
           //System.out.println("Match found in cell "+cellNum);

        }
        return newDest;
    }

    public boolean tessleComparator(BufferedImage baseTessle, BufferedImage destTessle, int quality){

        boolean isMatch =true;
        //get the average color
        int r, g, b; //we will be adding the value to calculate the average
        float diff = 0;
        int count = 0;

        for (int i = 0; i < baseTessle.getWidth(); i++){
            for (int j = 0; j < baseTessle.getHeight(); j++){
                //System.out.println("coordenada i:"+i+" j:"+j);
                Color destPixel = new Color(destTessle.getRGB(i, j));
                Color basePixel = new Color(baseTessle.getRGB(i, j));

                r = destPixel.getRed() - basePixel.getRed();
                g = destPixel.getGreen() - basePixel.getGreen();
                b = destPixel.getBlue() - basePixel.getBlue();
                diff += Math.sqrt(r*r+b*b+g*g)/3;
                count++;

            }
        }
        float distance = diff/count;
        //System.out.println("diff: " + distance);
        if(distance > this.qualityValue.get(quality)){
            isMatch = false;
        }

        return isMatch;
    }

    public BufferedImage applyAverage(BufferedImage destImg, int xCoord, int yCoord, int nTiles){

        BufferedImage newDest = destImg;
        //System.out.println("x: " + xCoord +"|" + "y: " + yCoord);
        //get the average color
        int r, g, b; //we will be adding the value to calculate the average
        r = g = b = 0;
        int count = 0;

        for (int i = xCoord; i < xCoord + nTiles; i++){
            for (int j = yCoord; j < yCoord + nTiles; j++){

                Color pixel = new Color(destImg.getRGB(i, j));
                r = r + pixel.getRed();
                g = g + pixel.getGreen();
                b = b + pixel.getBlue();

                count++;
            }
        }
        //get average of each channel
        r = r / count;
        g = g / count;
        b = b / count;

        Color avgColor = new Color(r, g, b);
        //now that we have the average color, we can apply the color to the new image
        //TODO-check if coords are correct, what is numPixels for?
        for (int x = xCoord; x < xCoord + nTiles; x++){
            for (int y = yCoord; y < yCoord + nTiles; y++){
                newDest.setRGB(x, y, avgColor.getRGB());
            }
        }
        return newDest;
    }




}
