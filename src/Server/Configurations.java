package Server;

import java.io.*;
import java.util.Properties;

public class Configurations {

    private static Configurations instance=null;
    private static Properties prop;

    /**
     * singleton for the configuration instance
     */
    public static Configurations getInstance()
    {
        if (instance == null)
        {
            instance = new Configurations();
        }
        return instance;
    }

    private Configurations() { // private constructor
        prop = new Properties();
    }

    /**
     * @param name - property key
     * @return returns property value
     */
    public String getProperty(String name) {
        if (prop.isEmpty()) {
            File configFile = new File("resources/config.properties");
            if (configFile.exists()) {
                try (InputStream input = new FileInputStream(configFile)) {
                    prop.load(input);
                } catch (IOException e) {
                    System.err.println("Warning: Failed to load config.properties. Using defaults.");
                }
            } else {
                System.out.println("Info: config.properties not found. Using default configuration.");
            }
        }

        return prop.getProperty(name); // Will return null if not found
    }


    /**
     * @param key - property key
     * @param value - property value
     * sets a new property
     */
    public void setProperty(String key, String value) {
        try (OutputStream output = new FileOutputStream("resources/config.properties")) {
            prop.setProperty(key, value);
            prop.store(output,"");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}