package edu.gdpu.spring.postProcessor.facory;

import edu.gdpu.spring.AnnotationPostProcessor;
import edu.gdpu.spring.core.util.ResourceUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.lang.annotation.Annotation;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author mazebin
 * @date 2021年 02月28日 21:03:23
 * 产生注解后置处理器的工厂
 * beanpostProcessor和autowritedPostProcessor需要二次强转
 * 加载postprocessor文件
 */
public class AnnotationPostProcessorFactory {
    private static final String postProcessorName = "postprocessor";
    private static final Logger LOGGER = LoggerFactory.getLogger(AnnotationPostProcessorFactory.class);

    private static Map<String, AnnotationPostProcessor> postProcessorMap = new ConcurrentHashMap<>();
    private static long startTime;
    private static long endTime;
    private static long continueTime;

    /**
     * 这种方法也可以生成对象，所以有点懵逼了，是效率区别？
     */
    static {
        startTime = System.currentTimeMillis();
        List<InputStream> inputStreams = ResourceUtils.getFiles(postProcessorName);
        for (InputStream inputStream : inputStreams) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            String s = null;

            try {
                while ((s = reader.readLine()) != null) {
                    Class<?> aClass = null;
                    try {
                        aClass = Class.forName(s);
                    } catch ( ClassNotFoundException e ) {

                        e.printStackTrace();
                    }
                    AnnotationPostProcessor o = (AnnotationPostProcessor) aClass.getDeclaredConstructor().newInstance();
                    String[] split = s.split("\\.");
                    if (!postProcessorMap.containsKey(split[split.length - 1])) {
                        postProcessorMap.put(split[split.length - 1], o);
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
        endTime = System.currentTimeMillis();
        continueTime = endTime - startTime;
       // LOGGER.info("读取文件,初始化annotationPostProcessor的时间为：" + continueTime);

    }


    public static AnnotationPostProcessor getPostProcessor(Annotation annotation) {
        String key = annotation.annotationType().getSimpleName() + "PostProcessor";
        return postProcessorMap.containsKey(key) ? postProcessorMap.get(key) : null;
    }

    public static AnnotationPostProcessor getPostProcessor(String name) {
        return postProcessorMap.containsKey(name) ? postProcessorMap.get(name) : null;
    }

}