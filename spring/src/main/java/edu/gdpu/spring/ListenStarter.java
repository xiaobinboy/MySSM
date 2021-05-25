package edu.gdpu.spring;

import edu.gdpu.spring.AnnotationPostProcessor;
import edu.gdpu.spring.context.ApplicationContext;
import edu.gdpu.spring.core.util.ResourceUtils;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * @author mazebin
 * @date 2021年 05月10日 21:15:02
 */
public class ListenStarter implements ApplicationListener {
   private static List<ApplicationListener> listeners = new ArrayList<>();
    static {

        List<InputStream> inputStreams = ResourceUtils.getFiles("listener");
        for (InputStream inputStream : inputStreams) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            String s = null;

            try {
                while ((s = reader.readLine()) != null) {
                    Class<?> aClass = null;
                    try {
                        aClass = Class.forName(s);
                        ApplicationListener o = (ApplicationListener) aClass.getDeclaredConstructor().newInstance();
                        listeners.add(o);
                    } catch ( ClassNotFoundException e ) {

                        e.printStackTrace();
                    }


                }
            } catch ( Exception e ) {
                e.printStackTrace();
            } finally {
                try {
                    inputStream.close();
                } catch ( Exception e ) {
                    e.printStackTrace();
                }
            }

        }
    }
   @Override
    public void begin() {
       for (ApplicationListener listener : listeners) {
           listener.begin();
       }
    }

    @Override
    public void completeScan() {
        for (ApplicationListener listener : listeners) {
            listener.completeScan();
        }
    }
}