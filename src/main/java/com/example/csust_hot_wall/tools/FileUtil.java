package com.example.csust_hot_wall.tools;


import sun.misc.BASE64Decoder;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class FileUtil {

    public static void uploadFile(byte[] file,String filePath,String fileName){
        File targetFile = new File(filePath);
        targetFile.setWritable(true,true); // 在linux下设置权限
        if (!targetFile.exists()){
            targetFile.mkdirs(); // 创建文件目录
        }
        try {
            FileOutputStream out = new FileOutputStream(filePath + fileName);
            out.write(file);
            out.flush();
            out.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 图片Base64编码转为byte数组
     * @param str
     * @return
     */
    public static byte[] Base64ToByte(String str){
        byte[] bytes = new byte[0];
        String[] baseStr = str.split(",");
        if (baseStr.length<2) return bytes;
        BASE64Decoder base64Decoder = new BASE64Decoder();
        try {
            bytes = base64Decoder.decodeBuffer(baseStr[1]);
            for (int i = 0; i < bytes.length; i++) {
                if (bytes[i] < 0) bytes[i] += 256;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bytes;
    }


}
