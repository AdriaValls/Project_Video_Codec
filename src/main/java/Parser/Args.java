package Parser;

import com.beust.jcommander.Parameter;

public class Args {

    @Parameter(names = { "-i", "--input" },required = true, validateWith = FileParamaterValidator.class, description = "Path to file.zip")
    private String zipPath;

    @Parameter(names = { "-o", "--output" }, description = "Path to output file")
    private String outputName;

    //TODO: check funcitionality of encode and decode params
    @Parameter(names = { "-e", "--encode" },help = true, description = "Encode mode")
    private boolean encode;

    @Parameter(names = { "-d", "--decode" },help = true, description = "Decode mode")
    private boolean decode;

    @Parameter(names = "--fps",help = true, description = "Frames per second on the reproduction")
    private int fps = 24;

    //TODO: Do actual tessle input
    @Parameter(names = "--nTiles",help = true, description = "Indicate if we want to apply filter to the images")
    private int nTiles = 8;

    @Parameter(names = "--seekRange",help = true, description = "Maximum sliding of tessles")
    private int seekRange = 8;


    @Parameter(names = "--GOP",help = true, description = "Number of images between two base images")
    private int GOP = 10;

    @Parameter(names = "--quality",help = true, description = "Quality factor for tessle coincidence")
    private int quality = 2;


    @Parameter(names = { "--batch", "-b" },help = true, description = "Batch mode")
    private boolean batch;

    //TODO: filter parameters

    @Parameter(names = { "--help", "-h" },help = true, description = "Display help information")
    private boolean help;

    public boolean isHelp(){
        return help;
    }
    public boolean isEncode(){
        return encode;
    }
    public boolean isDecode(){
        return decode;
    }
    public boolean isBatch(){
        return decode;
    }

    public String getZipPath(){return zipPath;}
    public String getOutputName(){return outputName;}

    public int getFps(){return fps;}
    public int getnTiles(){return nTiles;}
    public int getSeekRange(){return seekRange;}
    public int getGOP(){return GOP;}
    public int getQuality(){return quality;}


}