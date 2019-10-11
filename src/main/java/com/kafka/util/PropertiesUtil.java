package com.kafka.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * 读取配置文件
 */
public class PropertiesUtil {
    private static Properties properties;
    private static InputStream in;

    public static String getProperty(String filePath, String key){
        try {
            in = PropertiesUtil.class.getClassLoader().getResourceAsStream(filePath);
            properties.load(in);
            return properties.getProperty(key);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }finally {
            try {
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
