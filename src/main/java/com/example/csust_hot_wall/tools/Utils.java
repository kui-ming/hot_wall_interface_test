package com.example.csust_hot_wall.tools;

import org.springframework.util.DigestUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Calendar;

public class Utils {
    public static String QXM = "jdcookie1998sjw";
    public static String SBM = "望苍茫大地谁主沉浮";
    public static String[] gods = new String[]{"太阳","月亮","北欧战神","奥丁","托尔","婚姻和生育女神","农神"};


    public static String ObfuscatedEncryption(Calendar c){
        String z = "";
        int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);
        dayOfWeek = c.getFirstDayOfWeek() == Calendar.SUNDAY ? dayOfWeek-1 :dayOfWeek;
        String md5 = c.get(Calendar.DATE) + gods[dayOfWeek] + SBM + c.get(Calendar.HOUR_OF_DAY); //天数加星期加识别码加小时码
        z = DigestUtils.md5DigestAsHex(md5.getBytes());
        return z;
    }

    public static String UserObfuscatedEncryption(Calendar c,String username,String pswd){
        String z = "";
        int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);
        dayOfWeek = c.getFirstDayOfWeek() == Calendar.SUNDAY ? dayOfWeek-1 :dayOfWeek;
        //用户名加天数加星期码加密码(md5s加密)加小时码
        String md5 = username +
                c.get(Calendar.DATE) +
                gods[dayOfWeek] +
                pswd +
                c.get(Calendar.HOUR_OF_DAY);
        z = DigestUtils.md5DigestAsHex(md5.getBytes());
        return z;
    }

    public static boolean CompareEncryption(String nonce){
        boolean isOk = false;
        try {
            String x[] = nonce.split("=");
            Calendar c = Calendar.getInstance();
            isOk = x[1].equals(ObfuscatedEncryption(c));
            if ("D".equals(x[0]) && c.get(Calendar.MINUTE)<=5){ // 开头为D并且当前分钟数小于等于5
            //if ("D".equals(x[0])){
                c.set(Calendar.HOUR_OF_DAY,c.get(Calendar.HOUR_OF_DAY)-1);
                isOk = x[1].equals(ObfuscatedEncryption(c));
            }
        }
        catch (Exception e){ }
        return isOk;
    }

    public static String sessionObfuscatedEncryption(String username,String pswd){

        return DigestUtils.md5DigestAsHex((username+pswd).getBytes());
    }

    /**
     * 依赖springboot中的DigestUtils，根据字符串生成md5密码
     * @param plaintext 明文字符串
     * @return 加密后的字符串
     */
    public static String md5(String plaintext){
        return DigestUtils.md5DigestAsHex(plaintext.getBytes());
    }

    public static String md5(byte[] bytes){
        return DigestUtils.md5DigestAsHex(bytes);
    }


    /**
     * 判断对象中的多个属性是否存在空值，如果有空则返回true，没有则返回false。前提是该对象属性拥有正确getter方法
     * @param o 目标对象
     * @param nameList 字符串数组，表示对象中的属性
     * @return
     */
    public static boolean PropertyIsEmpty(Object o, String ...nameList){
        Field[] fields = o.getClass().getDeclaredFields();  // 获取所有属性
        for (int i = 0; i < nameList.length; i++) { //循环查找属性
            try {
                String frist = nameList[i].substring(0,1);
                if (Character.isLowerCase(nameList[i].substring(1,2).charAt(0))) //如果第二个字符是大写的话首字符小写
                    frist = frist.toUpperCase();
                String name = "get" + frist +nameList[i].substring(1);   // 拼凑该属性的getter方法名
                Method method = o.getClass().getMethod(name.trim(),new Class[]{}); //获取方法
                Object value = method.invoke(o,new Object[]{}); // 执行方法获取值
                if (value == null) return true; // 为空则返回真
            } catch (Exception e) {
                System.out.println(e.toString());
                return true;
            }
        }
        return false;
    }

    /**
     * 通过HttpServletRequest请求获得请求体数据，防止经过@requestBody注解的加工
     * @param request
     * @return
     */
    public static String getRequestBody(HttpServletRequest request){
        StringBuffer data = new StringBuffer();
        String line;
        try {
            BufferedReader reader = request.getReader();
            while (null != (line = reader.readLine())){
                data.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
            return data.toString();
        }
        return data.toString();
    }
}
