package Parser;

import com.beust.jcommander.IParameterValidator;
import com.beust.jcommander.ParameterException;

import java.nio.file.Files;
import java.nio.file.LinkOption;
import  java.nio.file.Paths;
import  java.nio.file.Path;

public class FileParamaterValidator implements IParameterValidator {

    //Name of parameter and value (path to file) checks if it exists.
    @Override
    public void validate(String name, String value) throws ParameterException {
        Path pathToZipDir = Paths.get(value);
        if(!exists(pathToZipDir)){
            String message = (value + " does not exist");
            throw  new ParameterException(message);
        }
        if(!Files.isRegularFile(pathToZipDir, LinkOption.NOFOLLOW_LINKS)){
            String message = (value + " Is not a file");
            throw  new ParameterException(message);
        }
    }

    private Boolean exists(Path path){
        return (Files.exists(path, LinkOption.NOFOLLOW_LINKS));
    }
}
