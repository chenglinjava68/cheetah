package org.cheetah.common.utils;

import org.apache.commons.beanutils.BeanUtils;

import java.security.MessageDigest;
import java.util.*;

/**
 * MD5工具类
 *
 * @author Max
 * @email pdemok@163.com
 * @date 2015/3/4
 */
public class MD5Util {

    private final static String[] hexDigits = {"0", "1", "2", "3", "4", "5", "6", "7",
            "8", "9", "a", "b", "c", "d", "e", "f"};

    /**
     * 转换字节数组为16进制字串
     *
     * @param b 字节数组
     * @return 16进制字串
     */
    public static String byteArrayToHexString(byte[] b) {
        StringBuilder resultSb = new StringBuilder();
        for (byte aB : b) {
            resultSb.append(byteToHexString(aB));
        }
        return resultSb.toString();
    }

    /**
     * 转换byte到16进制
     *
     * @param b 要转换的byte
     * @return 16进制格式
     */
    private static String byteToHexString(byte b) {
        int n = b;
        if (n < 0) {
            n = 256 + n;
        }
        int d1 = n / 16;
        int d2 = n % 16;
        return hexDigits[d1] + hexDigits[d2];
    }

    /**
     * MD5编码
     *
     * @param origin 原始字符串
     * @return 经过MD5加密之后的结果
     */
    public static String MD5Encode(String origin, String charsetname) {
        String resultString = null;
        try {
            resultString = new String(origin);
            MessageDigest md = MessageDigest.getInstance("MD5");
            if (charsetname == null || "".equals(charsetname))
                resultString = byteArrayToHexString(md.digest(resultString
                        .getBytes()));
            else
                resultString = byteArrayToHexString(md.digest(resultString
                        .getBytes(charsetname)));
        } catch (Exception exception) {
            exception.printStackTrace();
            return null;
        }
        return resultString;
    }

    public static String createMD5Sign(SortedMap<String, String> signParams) throws Exception {
        StringBuffer sb = new StringBuffer();
        Set es = signParams.entrySet();
        Iterator it = es.iterator();
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            String k = (String) entry.getKey();
            String v = (String) entry.getValue();
            if (StringUtils.isNotBlank(v)) {//空的不加进去
                sb.append(k + "=" + v + "&");//要采用URLENCODER的原始值！
            }
        }
        String params = sb.substring(0, sb.lastIndexOf("&"));
        return MD5Util.MD5Encode(params, "UTF-8").toUpperCase();
    }

    /**
     * 创建带商户密钥的签名MD5
     *
     * @param signParams 实现SortedMAp接口的参数对象
     * @param key        商户支付密钥，此字段不参与排序
     * @return
     * @throws Exception
     */
    public static String createMD5SignWithKey(Map<String, Object> signParams, String key) throws Exception {
        StringBuffer sb = new StringBuffer();
        Set es = signParams.entrySet();
        Iterator it = es.iterator();
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            String k = (String) entry.getKey();
            String v = (String) entry.getValue();
            if (StringUtils.isNotBlank(v)) {//空的不加进去
                sb.append(k + "=" + v + "&");//要采用URLENCODER的原始值！
            }
        }
        String params;
        if (StringUtils.isNotBlank(key)) {
            params = sb.append("key=" + key).toString();//最后拼上商户支付密钥，不参与排序
        } else {
            params = sb.substring(0, sb.lastIndexOf("&"));
        }
        System.out.println("sign:");
        System.out.println(params);
        return MD5Util.MD5Encode(params, "UTF-8").toUpperCase();
    }

    /**
     * 验证签名
     *
     * @param params
     * @param exclude 排除
     * @return
     */
    public static boolean verifySign(Map<String, Object> params, List<String> exclude, String partner_key) {
        Map<String, Object> temp = new TreeMap<>(params);
        for (String name : exclude)
            temp.remove(name);
        try {
            String sign = MD5Util.createMD5SignWithKey(temp, partner_key);
            return params.get("sign").equals(sign);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 复制bean属性到SortedMap，只接受String属性
     *
     * @param bean
     * @return
     */
    public static SortedMap BeanToSortedMap(Object bean) {
        SortedMap<String, String> param = new TreeMap<String, String>();
        try {
            Map<String, String> map = BeanUtils.describe(bean);
            if (map.containsKey("class")) {//除去class属性
                map.remove("class");
            }
            param.putAll(map);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return param;
    }

    public static void main(String[] args) throws Exception {
        SortedMap<String, String> signParams = new TreeMap<String, String>();
        signParams.put("appid", "appid");
        signParams.put("noncestr", "noncestr");
        signParams.put("package", "packageValue");
        signParams.put("timestamp", "timestamp");
        signParams.put("appkey", "APP_KEY");
        String str = MD5Util.createMD5Sign(signParams);
        System.out.println(str);
    }
}
