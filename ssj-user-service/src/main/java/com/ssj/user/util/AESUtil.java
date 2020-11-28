package com.ssj.user.util;

import java.io.IOException;
import java.io.InputStream;
import java.security.SecureRandom;
import java.util.Properties;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class AESUtil {

    private static final Logger log = LoggerFactory.getLogger(AESUtil.class);
	//定义加密算法，AES
	private static final String Algorithm = "AES";

	private static String key;

    /**
     * 从application.properties(aes.encrypt.key)获取密钥
     * @throws IOException
     */
	private static void getKey() throws IOException {
	    if(StringUtils.isNotBlank(key)) {
	        return;
        }

        InputStream stream = null;
	    try {
            Properties prop = new Properties();
            stream = AESUtil.class.getResourceAsStream("/application.properties");
            prop.load(stream);
            key = prop.getProperty("aes.encrypt.key");
            log.info("aes key:{}", key);
        } catch (Exception e) {
	        log.error("getKey error", e);
	        throw new RuntimeException("e");
        } finally {
	        if(null != stream) {
	            stream.close();
            }
        }
    }

	/**
	 *  数据加密，算法（AES）
	 * @param content 要进行加密的数据
	 * @return 加密后的数据
	 */
    public static String encryptBasedAes(String content) {
        try {
        	if(null == content) {
                log.error("encryptBasedAes参数为空");
        		return null;
        	}

            getKey();

        	//1.构造密钥生成器，指定为AES算法,不区分大小写
            KeyGenerator keygen=KeyGenerator.getInstance(Algorithm);
            //2.根据ecnodeRules规则初始化密钥生成器
            //生成一个128位的随机源,根据传入的字节数组
            keygen.init(128, new SecureRandom(key.getBytes()));
            //3.产生原始对称密钥
            SecretKey original_key=keygen.generateKey();
            //4.获得原始对称密钥的字节数组
            byte [] raw=original_key.getEncoded();
            //5.根据字节数组生成AES密钥
            SecretKey key=new SecretKeySpec(raw, Algorithm);
            //6.根据指定算法AES自成密码器
            Cipher cipher=Cipher.getInstance(Algorithm);
            //7.初始化密码器，第一个参数为加密(Encrypt_mode)或者解密解密(Decrypt_mode)操作，第二个参数为使用的KEY
            cipher.init(Cipher.ENCRYPT_MODE, key);
            //8.获取加密内容的字节数组(这里要设置为utf-8)不然内容中如果有中文和英文混合中文就会解密为乱码
            byte [] byte_encode=content.getBytes("utf-8");
            //9.根据密码器的初始化方式--加密：将数据加密
            byte [] byte_AES=cipher.doFinal(byte_encode);
            //10.将加密后的数据转换为字符串
            //这里用Base64Encoder中会找不到包
            //解决办法：
            //在项目的Build path中先移除JRE System Library，再添加库JRE System Library，重新编译后就一切正常了。
            return new String(new BASE64Encoder().encode(byte_AES));
        } catch (Exception e) {
            log.error("加密错误，错误信息", e);
        }
        return null;
    }

    /**
     * 数据解密，算法（AES）
     * @param cryptData
     * @return
     */
    public static String decryptBasedAes(String cryptData) {
        try {
        	if(null == cryptData) {
                log.error("decryptBasedAes参数为空");
        		return null;
        	}

            getKey();

            //1.构造密钥生成器，指定为AES算法,不区分大小写
            KeyGenerator keygen=KeyGenerator.getInstance(Algorithm);
            //2.根据ecnodeRules规则初始化密钥生成器
            //生成一个128位的随机源,根据传入的字节数组
            keygen.init(128, new SecureRandom(key.getBytes()));
            //3.产生原始对称密钥
            SecretKey original_key=keygen.generateKey();
            //4.获得原始对称密钥的字节数组
            byte [] raw=original_key.getEncoded();
            //5.根据字节数组生成AES密钥
            SecretKey key=new SecretKeySpec(raw, Algorithm);
            //6.根据指定算法AES自成密码器
            Cipher cipher=Cipher.getInstance(Algorithm);
            //7.初始化密码器，第一个参数为加密(Encrypt_mode)或者解密(Decrypt_mode)操作，第二个参数为使用的KEY
            cipher.init(Cipher.DECRYPT_MODE, key);
            //8.将加密并编码后的内容解码成字节数组
            byte [] byte_content= new BASE64Decoder().decodeBuffer(cryptData);
            //9.解密
            byte [] byte_decode=cipher.doFinal(byte_content);
            return new String(byte_decode,"utf-8");
        } catch (Exception e) {
            log.error("解密错误，错误信息", e);
        }
        return null;
    }

    public static void main(String[] a) {
        String text = encryptBasedAes("jiamosuuesknkae892yeihf01813*&*R^anfa110");
        log.info(text);
        log.info(decryptBasedAes(text));
    }
}
