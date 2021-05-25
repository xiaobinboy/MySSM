package edu.gdpu.spring.core.util;

import java.io.IOException;
import java.util.Properties;

/**
 * @author mazebin
 * @date 2021年 02月26日 00:11:07
 * properties工具类
 */
public class PropertiesLoaderUtils {
    private static final String SUFFIX = ".properties";

    public PropertiesLoaderUtils() {
    }

    public static Properties load(String fileName) {

        if (fileName.endsWith(SUFFIX)) {
            return doLoad(fileName, true);
        } else {
            return doLoad(fileName + SUFFIX, true);
        }

    }

    public static Properties load(String fileName, boolean inClassPath) {
        if (fileName.endsWith(SUFFIX) && fileName != null) {
            if (inClassPath) {
                return doLoad(fileName, true);
            } else {
                return doLoad(fileName, false);
            }
        } else {
            if (inClassPath) {
                return doLoad(fileName + SUFFIX, true);
            } else {
                return doLoad(fileName + SUFFIX, false);
            }
        }

    }

    public static Properties doLoad(String fileName, boolean inClassPath) {
        Properties properties = new Properties();
        if (inClassPath) {
            try {
                properties.load(ResourceUtils.loadInClassPath(fileName));
            } catch ( IOException e ) {
                e.printStackTrace();
            }
        }
        return properties;
    }
}