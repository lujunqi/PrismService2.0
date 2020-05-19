package com.prism.weixin;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

public class SignUtil {
//	http://www.9-9h.com/prism/wx

	public static void main(String[] args) {
//		nonce==358428252
//				signature==35bcb4a77de79a408a3a37fe328a3dba3ed1a9b6
//				echostr==5797405101790298103
//				timestamp==1578464183
//				nonce==355218438
				System.out.println(checkSignature("35bcb4a77de79a408a3a37fe328a3dba3ed1a9b6","1578464183","355218438"));
	}
	private static String TOKEN ="132456sdsfdfgfdghhf";
    public static boolean checkSignature(String signature, String timestamp, String nonce) {
        // 对token、timestamp、和nonce按字典排序.
        String[] paramArr = new String[] {TOKEN, timestamp, nonce};
        Arrays.sort(paramArr);

        // 将排序后的结果拼接成一个字符串.
        String content  = paramArr[0].concat(paramArr[1]).concat(paramArr[2]);

        String ciphertext = null;
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-1");
            // 对拼接后的字符串进行sha1加密.
            byte[] digest = md.digest(content.toString().getBytes());
            ciphertext = byteToStr(digest);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        // 将sha1加密后的字符串与signature进行对比.
        return ciphertext != null ? ciphertext.equals(signature.toUpperCase()) : false;
    }

    /**
     * 将字节数组转换为十六进制字符串.
     * @param byteArray
     * @return
     */
    private static String byteToStr(byte[] byteArray) {
        String strDigest = "";
        for (int i = 0; i < byteArray.length; i++) {
            strDigest += byteToHexStr(byteArray[i]);
        }
        return strDigest;
    }

    /**
     * 将字节转换为十六进制字符串.
     * @param mByte
     * @return
     */
    private static String byteToHexStr(byte mByte) {
        char[] Digit = { '0', '1' , '2', '3', '4' , '5', '6', '7' , '8', '9', 'A' , 'B', 'C', 'D' , 'E', 'F'};
        char[] tempArr = new char[2];
        tempArr[0] = Digit[(mByte >>> 4) & 0X0F];
        tempArr[1] = Digit[mByte & 0X0F];

        String s = new String(tempArr);
        return s;
    }

}
