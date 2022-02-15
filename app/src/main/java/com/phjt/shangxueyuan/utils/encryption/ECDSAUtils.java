package com.phjt.shangxueyuan.utils.encryption;

/**
 * @author: hjk
 * date: 2019/10/24 17:31
 * company: 七钻智鑫
 * description: 描述
 */

import android.content.Context;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.phjt.shangxueyuan.ContentsEncryptJni;
import com.phsxy.utils.StringUtils;

import java.io.IOException;
import java.math.BigInteger;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.interfaces.ECPrivateKey;
import java.security.interfaces.ECPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

import okhttp3.RequestBody;
import okio.Buffer;

/**
 * @author: hjk
 * date: 2019/10/24 14:05
 * company: 七钻智鑫
 * description: 描述
 */
public class ECDSAUtils {

    public static final String ENCYPT_STR = "ENCYPT";

    /**
     * 加密
     *
     * @param json 请求参数JSON串
     * @return
     */
    public static String sign(String json) {
        try {
            //1.初始化密钥
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("EC");
            keyPairGenerator.initialize(256);
            KeyPair keyPair = keyPairGenerator.generateKeyPair();
            ECPrivateKey ecPrivateKey = (ECPrivateKey) keyPair.getPrivate();
            ECPublicKey ecPublicKey = (ECPublicKey) keyPair.getPublic();

            Integer.parseInt(ecPublicKey.toString(), 16);


            //2.执行签名
            PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(ecPrivateKey.getEncoded());
            KeyFactory keyFactory = KeyFactory.getInstance("EC");
            PrivateKey privateKey = keyFactory.generatePrivate(pkcs8EncodedKeySpec);
            Signature signature = Signature.getInstance("SHA1withECDSA");
            signature.initSign(privateKey);
            signature.update(json.getBytes());
            byte[] res = signature.sign();

            // 验证签名
            X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(ecPublicKey.getEncoded());
            keyFactory = KeyFactory.getInstance("EC");
            PublicKey publicKey = keyFactory.generatePublic(x509EncodedKeySpec);
            signature = Signature.getInstance("SHA1withECDSA");
            signature.initVerify(publicKey);


            return encode(res);

        } catch (Exception e) {
            e.printStackTrace();
            return "";

        }
    }

    public static String md5(String json) {
        return stringToMd5(stringToMd5(json));
    }

    public static String getTime() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date ;
        try {
            date = simpleDateFormat.parse(stampToDate(System.currentTimeMillis()));
            long ts = date.getTime() / 1000;
            return String.valueOf(ts);
        } catch (ParseException e) {
            return null;
        }
    }

    /**
     * 将时间戳转换为时间
     */
    public static String stampToDate(long timeMillis) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone("GMT+08"));
        Date date = new Date(timeMillis);
        return simpleDateFormat.format(date);
    }

    public static String requestBodyToString(RequestBody requestBody) {
        try {
            if (requestBody != null) {
                Buffer buffer = new Buffer();
                requestBody.writeTo(buffer);
                return buffer.readUtf8();
            } else {
                return "";
            }
        } catch (IOException e) {
            return "";
        }
    }


    /**
     * 字符串转MD5
     *
     * @param plainText
     * @return
     */
    public static String stringToMd5(String plainText) {
        byte[] secretBytes ;
        try {
            secretBytes = MessageDigest.getInstance("md5").digest(
                    plainText.getBytes());
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("没有这个md5算法！");
        }
        String md5code = new BigInteger(1, secretBytes).toString(16);
        for (int i = 0; i < 32 - md5code.length(); i++) {
            md5code = "0" + md5code;
        }
        return md5code;
    }


    /**
     * 表单参数转json格式字符串
     *
     * @param requests 表单参数
     * @return
     */
    public static String requestToGsonString(Context context, String requests) {
        try {
            if (!StringUtils.isEmpty(requests)) {
                String[] split = requests.split("&");
                Map<String, String> param = new HashMap<>(10);
                param.put("mac", PhoneInfoUtil.getMac(context));
                String isRoot = SecurityCheckUtil.isRoot() ? "1" : "0";
                param.put("isRoot", isRoot);
                for (String request : split) {
                    String[] split1 = request.split("=");
                    //处理有参数value是""的情况,防止字符数组超过长度
                    if (split1.length == 2) {
                        param.put(split1[0], split1[1]);
                    } else if (split1.length == 1) {
                        param.put(split1[0], "");
                    }
                }
                return new Gson().toJson(param);

            } else {
                return "";
            }
        } catch (Exception e) {
            return "";
        }

    }


    public static String encrypt(Context context, RequestBody body) {
        try {
            String requestStr = ECDSAUtils.requestToGsonString(context, ECDSAUtils.requestBodyToString(body));
            if (TextUtils.isEmpty(requestStr)) {
                //没有参数，不需要加密
                return "";
            }
            byte[] data = new byte[(requestStr.getBytes().length + 30) * 2];
            int[] datalen = new int[1];
            int resultCode = ContentsEncryptJni.contentsEncrypt(requestStr.getBytes(), requestStr.getBytes().length, data, datalen);
            if (resultCode == 0) {
                byte[] resultData = new byte[datalen[0]];
                System.arraycopy(data, 0, resultData, 0, datalen[0]);
                return new String(resultData);
            } else {
                return ENCYPT_STR + requestStr;
            }
        } catch (Exception e) {
            return "";
        }
    }


    /**
     * 加密
     *
     * @return 加密后的参数
     */
    public static String encrypt(String requestStr) {
        try {
            if (TextUtils.isEmpty(requestStr)) {
                //没有参数，不需要加密
                return "";
            }
            byte[] data = new byte[(requestStr.getBytes().length + 30) * 2];
            int[] datalen = new int[1];
            int resultCode = ContentsEncryptJni.contentsEncrypt(requestStr.getBytes(), requestStr.getBytes().length, data, datalen);
            if (resultCode == 0) {
                byte[] resultData = new byte[datalen[0]];
                System.arraycopy(data, 0, resultData, 0, datalen[0]);
                return new String(resultData);
            }
        } catch (Exception e) {
            return "";
        }
        return "";
    }


    static private final int BASELENGTH = 128;
    static private final int LOOKUPLENGTH = 16;
    static final private byte[] HEX_NUMBER_TABLE = new byte[BASELENGTH];
    static final private char[] LOOK_UP_HEX_ALPHABET = new char[LOOKUPLENGTH];


    static {
        for (int i = 0; i < BASELENGTH; i++) {
            HEX_NUMBER_TABLE[i] = -1;
        }
        for (int i = '9'; i >= '0'; i--) {
            HEX_NUMBER_TABLE[i] = (byte) (i - '0');
        }
        for (int i = 'F'; i >= 'A'; i--) {
            HEX_NUMBER_TABLE[i] = (byte) (i - 'A' + 10);
        }
        for (int i = 'f'; i >= 'a'; i--) {
            HEX_NUMBER_TABLE[i] = (byte) (i - 'a' + 10);
        }

        for (int i = 0; i < 10; i++) {
            LOOK_UP_HEX_ALPHABET[i] = (char) ('0' + i);
        }
        for (int i = 10; i <= 15; i++) {
            LOOK_UP_HEX_ALPHABET[i] = (char) ('A' + i - 10);
        }
    }

    /**
     * Encode a byte array to hex string
     *
     * @param binaryData array of byte to encode
     * @return return encoded string
     */
    static public String encode(byte[] binaryData) {
        if (binaryData == null) {
            return null;
        }
        int lengthData = binaryData.length;
        int lengthEncode = lengthData * 2;
        char[] encodedData = new char[lengthEncode];
        int temp;
        for (int i = 0; i < lengthData; i++) {
            temp = binaryData[i];
            if (temp < 0) {
                temp += 256;
            }
            encodedData[i * 2] = LOOK_UP_HEX_ALPHABET[temp >> 4];
            encodedData[i * 2 + 1] = LOOK_UP_HEX_ALPHABET[temp & 0xf];
        }
        return new String(encodedData);
    }

    /**
     * Decode hex string to a byte array
     *
     * @param encoded encoded string
     * @return return array of byte to encode
     */
    static public byte[] decode(String encoded) {
        if (encoded == null) {
            return null;
        }
        int lengthData = encoded.length();
        if (lengthData % 2 != 0) {
            return null;
        }

        char[] binaryData = encoded.toCharArray();
        int lengthDecode = lengthData / 2;
        byte[] decodedData = new byte[lengthDecode];
        byte temp1, temp2;
        char tempChar;
        for (int i = 0; i < lengthDecode; i++) {
            tempChar = binaryData[i * 2];
            temp1 = (tempChar < BASELENGTH) ? HEX_NUMBER_TABLE[tempChar] : -1;
            if (temp1 == -1) {
                return null;
            }
            tempChar = binaryData[i * 2 + 1];
            temp2 = (tempChar < BASELENGTH) ? HEX_NUMBER_TABLE[tempChar] : -1;
            if (temp2 == -1) {
                return null;
            }
            decodedData[i] = (byte) ((temp1 << 4) | temp2);
        }
        return decodedData;
    }
}
