package com.epam.esm.dao.connection;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class DbPropertyManager {
    private DbPropertyManager() {
    }

    public static String getProperty(String key) {
        Properties property = new Properties();
        String conf = null;
        try {
            InputStream inputStream = DbPropertyManager.class.getClassLoader().getResourceAsStream("dbconfig.properties");
            property.load(inputStream);
            conf = property.getProperty(key);
        } catch (IOException e) {

        }
        return conf;
    }
}
