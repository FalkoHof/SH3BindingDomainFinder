package falko.patternfinder;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ScriptProperties {

    private static ScriptProperties instance = new ScriptProperties();
    private Properties props;

    public ScriptProperties(){
        try {
            props = new Properties();
            InputStream inputStream = getClass().getResourceAsStream("PatternFinder.properties");
            props.load(inputStream);
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static ScriptProperties getInstance() {
        return instance;
    }
    
    public int getNumberOfThreads(){
    	return Integer.parseInt(props.getProperty("numberOfThreads"));
    }
    
    
    public int getNumberOfRandomSets(){
    	return Integer.parseInt(props.getProperty("numberOfRandomSets"));
    }    
}
