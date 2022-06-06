package FileManagement;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.util.Enumeration;
import java.util.Enumeration;

import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;


public class ZipHandler {
    public ZipHandler(){

    }

    //read and unzip the file
    public void readZip(String inPath, String outPath) {

        try{
            FileInputStream fis = new FileInputStream(inPath);
            ZipInputStream zis = new ZipInputStream(fis);
            File outDirectoryFolder = new File(outPath);

            if(!outDirectoryFolder.exists()){
                outDirectoryFolder.mkdir();
            }

            byte[] buffer = new byte[1024];

            ZipEntry temp;
            while( (temp=zis.getNextEntry()) != null){
                //System.out.println(temp.getName());
                String filePath = outPath + File.separator + temp.getName();
                //System.out.println(filePath);
                if(!temp.isDirectory()){
                    FileOutputStream fos = new FileOutputStream(filePath);
                    int len;
                    while( (len = zis.read(buffer)) > 0 ){
                        fos.write(buffer,0,len);
                    }
                    fos.close();
                } else{
                    File dir = new File(filePath);
                    dir.mkdir();
                }
                zis.closeEntry();
            }

            System.out.println("Reading ZipFile: correct");
            zis.close(); //close entry and inputStream
            //zis.closeEntry();

        }catch (IOException error){
            System.out.println("Error reading ZipFile: " + error);
        }

    }

    //function that creates a zip file, given an input file path to read from, and a given file path to create the zip
    public void writeZip(String inPath, String outPath){

        try{

            File inputFile = new File(inPath);
            File[] file_allPaths = inputFile.listFiles();
            if (file_allPaths.length == 0){
                throw new IllegalArgumentException("This file is empty " + inputFile.getAbsolutePath());
            }

            FileOutputStream fos = new FileOutputStream(outPath);
            ZipOutputStream zipOut = new ZipOutputStream(fos);

            for (File temp : file_allPaths) { //for each loop
                if(!temp.isDirectory()){
                    FileInputStream fis = new FileInputStream(temp);
                    ZipEntry zipEntry = new ZipEntry(temp.getName());
                    zipOut.putNextEntry(zipEntry);

                    byte[] buffer = new byte[1024];
                    int len;
                    while((len = fis.read(buffer)) >= 0) {
                        zipOut.write(buffer, 0, len);
                    }
                    fis.close();

                }else{
                    File dir = new File(temp.getName());
                    dir.mkdir();
                }
            }
            System.out.println("Creating ZipFile: correct");
            zipOut.close();
            fos.close();

        }catch (IOException error){
            System.out.println("Error creating ZipFile: " + error);
        }

    }

    //create a copy file with JPEG images
    public void copy_file_as_jpeg(String inPath, String outPath){

        File outDirectoryFolder = new File(outPath);

        if(!outDirectoryFolder.exists()){
            outDirectoryFolder.mkdir();
        }

        File inputFile = new File(inPath);
        File[] file_allPaths = inputFile.listFiles();
        if (file_allPaths.length == 0){
            throw new IllegalArgumentException("This file is empty " + inputFile.getAbsolutePath());
        }

        for (File temp : file_allPaths) { //for each loop
            if(!temp.isDirectory()){
                JPEG_Handler jpeg_handler = new JPEG_Handler();
                jpeg_handler.writeImage(jpeg_handler.png_to_jpeg(jpeg_handler.readImage(temp.getAbsolutePath())),
                        outPath + File.separator + temp.getName().substring(0,temp.getName().length()-4) + ".jpeg");


            }else{
                File dir = new File(temp.getName());
                dir.mkdir();

            }
        }
        System.out.println("Creating Folder with JPEG: correct");


    }
}
