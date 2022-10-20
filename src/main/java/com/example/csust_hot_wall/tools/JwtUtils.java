package com.example.csust_hot_wall.tools;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.Map;


/**
 * 需要jjwt依赖：jjwt-api、jjwt-jackson、jjwt-impl 0.11.2，groupId=io.jsonwebtoken
 * JWT由三部分组成:
 *      header标头：记录了token的类型和加密算法的josn，要转成base64字符串
 *      payload有效负载：记录了用户信息的json，要转成base64字符串
 *      signatur签名算法：head和payload的json格式，转为base64字符串后，再加密得到的
 */
public class JwtUtils {

    private static String secretKey = "931ff8fa0c0d108e79e741c40c29344e";   // 签名秘钥
    // 默认签名算法
    private static SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
    // 默认的JWT创建者
    private static String iss = "kuiming";
    //默认的JWT面向的使用者
    private static String sub = "everyone";
    // 默认30分钟超时
    private static int second = 60 * 30;

    //获取通过签名获取密钥
    public static SecretKey getSecretKey(){
        return Keys.hmacShaKeyFor(secretKey.getBytes());
    }

    /**
     *  创建一个JWT令牌
     * @param issuer 令牌的签发者
     * @param subject 令牌面向的接收者，通常是用户ID
     * @param second 令牌的超时时间，以秒为单位
     * @param claims 存储自定义的一些信息，通常是用户权限和角色信息
     * @return
     */
    public static String createJWT(String issuer,String subject,Integer second,Map<String,Object> claims){
        JwtBuilder jwtBuilder = Jwts.builder();
        jwtBuilder.setIssuedAt(new Date()); //创建jwt的时间
        if (issuer != null) jwtBuilder.setIssuer(issuer);
        if (subject != null) jwtBuilder.setSubject(subject);
        if (claims != null) jwtBuilder.addClaims(claims); //设置时间必须在claims后面，否则无效
        if (second != null) jwtBuilder.setExpiration(new Date(System.currentTimeMillis() + 1000 * second));
        else jwtBuilder.setExpiration(new Date(System.currentTimeMillis() + 1000 * JwtUtils.second));
        return  jwtBuilder.signWith(getSecretKey()).compact();
    }

    public static String createJWT(String subject,Map<String,Object> claims,Integer second){
        return createJWT(iss,subject,second,claims);
    }

    public static String createJWT(Map<String,Object> payload,Integer second){
        return createJWT(iss,sub,second,payload);
    }

    public static String createJWT(){
        return createJWT(iss,sub,second,null);
    }

    /**
     *  检查令牌是否正确，是否超时
     * @param jwt 加密的令牌码
     * @return
     */
    public static Boolean checkJWT(String jwt){
        boolean result = false;
        try{
            Jwts.parserBuilder()
                    .setSigningKey(getSecretKey())
                    .build()
                    .parseClaimsJws(jwt);
            result = true;
        }catch (JwtException e){
            e.printStackTrace();
            result = false;
        }
        finally {
            return result;
        }
    }

    /**
     * 解析JWT令牌
     * @param jwt 字符串形式令牌
     * @return
     */
    public static Jws parserJws(String jwt){
        Jws<Claims> jws = null;
        try {
            jws = Jwts.parserBuilder()
                    .setSigningKey(getSecretKey())
                    .build()
                    .parseClaimsJws(jwt);
        }catch (JwtException e){}
        return jws;
    }

    /**
     * 解析令牌并获取令牌头部信息
     * @param jwt   令牌的字符串形式
     * @return 集合形式的数据对象
     */
    public static Map<String,Object> getHeader(String jwt){
        Jws jws = parserJws(jwt);
        if (jws == null){
            return null;
        }
        return jws.getHeader();
    }

    /**
     * 解析令牌并获取令牌的payload信息
     * @param jwt 令牌的字符串形式
     * @return 集合形式的数据对象
     */
    public static Map<String,Object> getPayload(String jwt){
        Jws jws = parserJws(jwt);
        if (jws == null){
            return null;
        }
        return (Map<String, Object>) jws.getBody();
    }

}
