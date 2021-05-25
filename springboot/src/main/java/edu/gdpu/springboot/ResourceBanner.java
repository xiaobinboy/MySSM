package edu.gdpu.springboot;

import edu.gdpu.spring.core.util.ResourceUtils;
import edu.gdpu.spring.core.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author mazebin
 * @date 2021年 02月25日 17:07:53
 */
public class ResourceBanner implements Banner {
    private static final Logger logger = LoggerFactory.getLogger(ResourceBanner.class);
    private static final String bannerName = "banner.txt";
@Override
    public void printBanner() {
        InputStream ins = ResourceUtils.loadInClassPath(bannerName);
        try {
            String banner = StringUtils.copyToString(ins);
            logger.info(banner);
        } catch ( IOException e ) {
            e.printStackTrace();
        }

    }
}