package com.example.demo.utils;




import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.server.Session;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.util.UUID;

/**
 * @author Chu
 * @create 2022-03-17-19:44
 */
public class UploadUtils {


   @Value("${server.port}")
   private static int serverPort;
   public static String upload(MultipartFile photo, HttpSession session) throws IOException {
      InetAddress inetAddress = InetAddress.getLocalHost();
      //获取上传的文件的文件名
      String fileName = null;
      try {
         fileName = photo.getOriginalFilename();
      } catch (Exception e) {
         return null;
      }
      //处理文件重名问题
      String hzName = fileName.substring(fileName.lastIndexOf("."));
      fileName = UUID.randomUUID().toString() + hzName;
      //获取服务器中photo目录的路径
      String photoPath = "/home/photo";
      File file = new File(photoPath);
      if (!file.exists()) {
         file.mkdir();
      }
      String finalPath = photoPath + File.separator + fileName;
      //实现上传功能
      photo.transferTo(new File(finalPath));
      return "http://"+inetAddress.getHostAddress() +":"+UploadUtils.serverPort;

   }
}
