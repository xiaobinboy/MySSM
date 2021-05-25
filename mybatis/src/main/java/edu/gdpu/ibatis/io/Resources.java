package edu.gdpu.ibatis.io;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author mazebin
 * @date 2021年 03月24日 08:48:32
 */
public class Resources {
    private static ClassLoaderWrapper wrapper;
    public InputStream getResourcesAsStream(String resource) throws IOException{
        return   this.getResourceAsStream((ClassLoader)null,resource);
    }
    public InputStream getResourceAsStream(ClassLoader loader, String resource) throws IOException {
      InputStream in = wrapper.getReourcesAsStream(resource);
      if(in == null){
          throw  new IOException("输入的文件有异常"+resource);
      }else{
          return in;
      }
    }
}