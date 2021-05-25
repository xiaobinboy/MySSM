package edu.gdpu.spring.core.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * @author mazebin
 * @date 2021年 02月25日 17:31:36
 * 加载文件内容工具类·
 */
public class StringUtils {
    public static String copyToString(InputStream ins) throws IOException {
        if (ins == null) {
            return "";
        } else {
            StringBuilder builder = new StringBuilder(4096);
            InputStreamReader reader = new InputStreamReader(ins);
            char[] buffer = new char[4096];
            int charsRead;
            while ((charsRead = reader.read(buffer)) != -1) {
                builder.append(buffer, 0, charsRead);

            }
            return builder.toString();
        }
    }

}