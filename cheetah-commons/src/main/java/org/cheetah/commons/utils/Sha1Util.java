package org.cheetah.commons.utils;

import java.security.MessageDigest;
import java.util.*;

/**
 * sha1工具类
 * @author Max
 * @email pdemok@163.com
 * @date 2015/3/4
 */
public class Sha1Util {
    private static final String base = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

    public static void main(String[] args) throws Exception {
        SortedMap<String, String> signParams = new TreeMap<String, String>();
        signParams.put("appid", "appid");
        signParams.put("noncestr", "noncestr");
        signParams.put("package", "packageValue");
        signParams.put("timestamp", "timestamp");
        signParams.put("appkey", "APP_KEY");
        String str = Sha1Util.createSHA1Sign(signParams);
        System.out.println(str);
    }

    public static String getNonceStr() {
        return MD5Util.MD5Encode(getRandomStr(), "UTF-8");
    }

    // 随机生成16位字符串
    static String getRandomStr() {

        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < 32; i++) {
            int number = random.nextInt(base.length());
            sb.append(base.charAt(number));
        }
        return sb.toString();
    }

    //创建签名SHA1
    public static String createSHA1Sign(SortedMap<String, String> signParams) throws Exception {
        StringBuffer sb = new StringBuffer();
        Set es = signParams.entrySet();
        Iterator it = es.iterator();
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            String k = (String) entry.getKey();
            String v = (String) entry.getValue();
            sb.append(k + "=" + v + "&");
            //要采用URLENCODER的原始值！
        }
        String params = sb.substring(0, sb.lastIndexOf("&"));
        return getSha1(params);
    }

    //Sha1签名
    public static String getSha1(String str) {
        if (str == null || str.length() == 0) {
            return null;
        }

        try {
            // SHA1签名生成
            MessageDigest md = MessageDigest.getInstance("SHA-1");
            md.update(str.getBytes());
            byte[] digest = md.digest();

            StringBuffer hexstr = new StringBuffer();
            String shaHex = "";
            for (int i = 0; i < digest.length; i++) {
                shaHex = Integer.toHexString(digest[i] & 0xFF);
                if (shaHex.length() < 2) {
                    hexstr.append(0);
                }
                hexstr.append(shaHex);
            }
            return hexstr.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
