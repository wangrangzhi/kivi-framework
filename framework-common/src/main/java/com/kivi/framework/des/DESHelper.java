package com.kivi.framework.des;

import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import  javax.crypto.spec.DESedeKeySpec;
import javax.crypto.spec.IvParameterSpec;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;
import org.apache.http.util.ByteArrayBuffer;

public class DESHelper {
	private static final String DES = "DES";
	private static final String TRIPLE_DES = "DESede";
	private static final String DEFAULT_ENCODING = "UTF-8";
	/** 加密算法 */
	private final static String ALGORITHM = "DES/CBC/PKCS5Padding";
	
	private final static String ALGORITHM_3DES = "DESede/CBC/PKCS5Padding";
	
	/**
	 * 生成符合DES要求的密钥, 长度为64位(8字节).
	 */
	public static byte[] generateDesKey() {
		try {
			KeyGenerator keyGenerator = KeyGenerator.getInstance(DES);
			SecretKey secretKey = keyGenerator.generateKey();
			return secretKey.getEncoded();
		} catch (GeneralSecurityException e) {
			throw convertRuntimeException(e);
		}
	}
	
	
	/**
	 * 使用DES加密原始字符串, 返回Hex编码的结果.
	 * 
	 * @param input 原始输入字符串
	 * @param keyBytes 符合DES要求的密钥
	 */
	public static String desEncryptToHex(String input, byte[] keyBytes) {
		byte[] encryptResult = null;
		try {
			encryptResult = des(input.getBytes(DEFAULT_ENCODING), keyBytes, Cipher.ENCRYPT_MODE);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return hexEncode(encryptResult);
	}
	
	/**
	 * 使用DES解密Hex编码的加密字符串, 返回原始字符串.
	 * 
	 * @param input Hex编码的加密字符串
	 * @param keyBytes 符合DES要求的密钥
	 */
	public static String desDecryptFromHex(String input, byte[] keyBytes) {
		byte[] decryptResult = des(hexDecode(input), keyBytes, Cipher.DECRYPT_MODE);
		String decryptString = null;
		try {
			decryptString = new String(decryptResult, DEFAULT_ENCODING);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return decryptString;
	}
	
	/**
	 * Hex编码, byte[]->String.
	 */
	public static String hexEncode(byte[] input) {
		return Hex.encodeHexString(input);
	}

	/**
	 * Hex解码, String->byte[].
	 */
	public static byte[] hexDecode(String input) {
		try {
			return Hex.decodeHex(input.toCharArray());
		} catch (DecoderException e) {
			throw new IllegalStateException("Hex Decoder exception", e);
		}
	}
	
	/**
	 * !!!重要提示，本函数代码不允许修改！！
	 * 利用密钥因子生成3des密钥，算法：
	 * 第一步，输入种子A和种子B：由两个独立的人各输入一个16位数（或少于16位），分别作为SeedA和SeedB；
	 * 第二步，计算种子C：SeedC=SeedA◎SeedB；
	 * 第三步，密钥种子的初始化：
	 * ·KEYINIT=常量
	 * ·Seed=DES-1（DES（DES-1（KEYINIT，SeedC）,SeedB），SeedA）
	 * ·设K0=Seed
	 * 第四步，密钥种子的生成：
	 * ·K1=DES-1（DES（DES-1（K0,SeedC），SeedB），SeedA）K3
	 * ·MK=K0+K1，MK即为生成的原始密钥
	 * @param seed1
	 * @param seed2
	 * @return
	 */
	public static byte[] generate3DesKey(String seed1,String seed2) {
		try {
			byte[] seedA = encodeSeed(seed1) ;
			byte[] seedB = encodeSeed(seed2);
			
			ByteArrayBuffer buff = new ByteArrayBuffer(seedA.length);
			for( int i =0 ; i< seedA.length; i++ ){
				byte b = (byte)(seedA[i] ^ seedB[i]);
				buff.append(b);
			}
			byte[] seedC = buff.toByteArray() ;
			buff.clear();
			
			byte[] key0={0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00};
			ByteArrayBuffer keybuff = new ByteArrayBuffer(8);
			byte[] data = desECB(seedC, key0, Cipher.DECRYPT_MODE) ;
			for( int i =0 ; i< 8; i++ ){
				byte b = (byte)(data[i] ^ data[8+i]);
				keybuff.append(b);
			}
			key0 = keybuff.toByteArray() ;
			
			data = desECB(seedB, key0, Cipher.ENCRYPT_MODE) ;
			for( int i =0 ; i< 8; i++ ){
				byte b = (byte)(data[i] ^ data[8+i]);
				keybuff.append(b);
			}
			key0 = keybuff.toByteArray() ;
			
			data = desECB(seedA, key0, Cipher.DECRYPT_MODE) ;
			for( int i =0 ; i< 8; i++ ){
				byte b = (byte)(data[i] ^ data[8+i]);
				keybuff.append(b);
			}
			key0 = keybuff.toByteArray() ;
			
			data=desECB(seedC, key0, Cipher.DECRYPT_MODE) ;
			for( int i =0 ; i< 8; i++ ){
				byte b = (byte)(data[i] ^ data[8+i]);
				keybuff.append(b);
			}
			byte[] key1 = keybuff.toByteArray() ;
			data = desECB(seedB, key1, Cipher.ENCRYPT_MODE) ;
			for( int i =0 ; i< 8; i++ ){
				byte b = (byte)(data[i] ^ data[8+i]);
				keybuff.append(b);
			}
			key1 = keybuff.toByteArray() ;
			data = desECB(seedA, key1, Cipher.DECRYPT_MODE) ;
			for( int i =0 ; i< 8; i++ ){
				byte b = (byte)(data[i] ^ data[8+i]);
				keybuff.append(b);
			}
			key1 = keybuff.toByteArray() ;
			
			ByteArrayBuffer mkbuff = new ByteArrayBuffer(24);
			mkbuff.append(key0,0,8);
			mkbuff.append(key1,0,8);
			mkbuff.append(key0,0,8);
			byte[] mk = mkbuff.toByteArray() ;
			
			DESedeKeySpec desKeySpec = new DESedeKeySpec(mk);
			SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(TRIPLE_DES);
			//密钥
			SecretKey secretKey = keyFactory.generateSecret(desKeySpec);
			return secretKey.getEncoded();
		}
		 catch (GeneralSecurityException e) {
				throw convertRuntimeException(e);
			}
	}
	
	private static byte[] encodeSeed( String seed ){
		byte[] iv={0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00};
		int seed_len = 16;
		int len = seed.length() ;
		int mod = len%seed_len ;
		int seed_size = len + (mod == 0?0:(seed_len-mod)) ;
		ByteArrayBuffer buff = new ByteArrayBuffer(seed_size);
		buff.append(seed.getBytes(),0,len);
		if(mod >0){
			buff.append(iv,0,seed_len-mod);
		}
		
		if((buff.length()/seed_len)%2 > 0)
			buff.append( iv,0,seed_len);
		
		byte[] seed_bytes = buff.toByteArray();
		byte[] result = {0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00};
		for( int i = 0 ; i< seed_bytes.length/seed_len;i++){
			for( int j = 0 ; j< seed_len;j++){
				result[j] =  (byte)(result[j] ^ seed_bytes[i*seed_len+j]);
			}
		}
		
		//System.out.println( DESHelper.hexEncode(result) ); 
		
		return result ;
	}
	
	/**
	 * 3des加密
	 * @param input
	 * @param keyBytes
	 * @return
	 */
	public static String encrypt3desToHex(String input, byte[] keyBytes) {
		byte[] encryptResult = null;
		try {
			encryptResult = tripleDes(input.getBytes(DEFAULT_ENCODING), keyBytes, Cipher.ENCRYPT_MODE);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return hexEncode(encryptResult);
	}
	
	
	/**
	 * 使用DES解密Hex编码的加密字符串, 返回原始字符串.
	 * 
	 * @param input Hex编码的加密字符串
	 * @param keyBytes 符合DES要求的密钥
	 */
	public static String decrypt3desFromHex(String input, byte[] keyBytes) {
		byte[] decryptResult = tripleDes(hexDecode(input), keyBytes, Cipher.DECRYPT_MODE);
		String decryptString = null;
		try {
			decryptString = new String(decryptResult, DEFAULT_ENCODING);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return decryptString;
	}
	
	
	/**
	 * !!!重要提示，本函数代码不允许修改！！
	 * 对3Des密钥进行分散的算法DK（DKLDKR）。其算法如下[3]：
	 * 推导DK左半部分DKL的方法是：
	 * ·将分散数据的最右16个数字作为输入数据；
	 * ·将MK作为加密密钥；
	 * ·用MK对输入数据进行3DES运算。
	 * 推导DK右半部分DKR的方法：
	 * ·将分散数据的最右16个数字求反，作为输入数据；
	 * ·将MK作为加密密钥；
	 * ·用MK对输入数据进行3DES运算。
	 */
	public static byte[] disperse3desKey(String factor, byte[] keyBytes) {
		byte[] iv={0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00};
		int len = factor.length() ;
		int mod = len%8 ;
		int factor_size = len + (mod == 0?0:(8-mod)) ;
		ByteArrayBuffer buff = new ByteArrayBuffer(factor_size);
		buff.append(factor.getBytes(),0,len);
		if(mod >0){
			buff.append(iv,0,8-mod);
		}
		
		if((buff.length()/8)%2 > 0)
			buff.append( iv,0,8);
		
		byte[] factor_bytes = buff.toByteArray();
		byte[] key_init = {0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00};
		for( int i = 0 ; i< factor_bytes.length/8;i++){
			for( int j =0; j< 8; j++)
			key_init[j] =  (byte)(key_init[j] ^ factor_bytes[i*8+j]);
		}
		
		byte[] keyl = tripleDesECB(key_init, keyBytes, Cipher.ENCRYPT_MODE) ;
		
		for( int i = 0 ; i< key_init.length;i++){
			key_init[i] = (byte)~key_init[i] ;
		}
		byte[] keyr = tripleDesECB(key_init, keyBytes, Cipher.ENCRYPT_MODE) ;
		
		ByteArrayBuffer result = new ByteArrayBuffer(16);
		result.append(keyl,0,8);
		result.append(keyr,0,8);
		result.append(keyl,0,8);
		return result.toByteArray();
	}
	
	/**
	 * 使用DES加密或解密无编码的原始字节数组, 返回无编码的字节数组结果.
	 * 通用的java .net php
	 * @param inputBytes 原始字节数组
	 * @param keyBytes 符合DES要求的密钥
	 * @param mode Cipher.ENCRYPT_MODE 或 Cipher.DECRYPT_MODE
	 */
	private static byte[] des(byte[] inputBytes, byte[] keyBytes, int mode) {
		try {
			DESKeySpec desKeySpec = new DESKeySpec(keyBytes);
			SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(DES);
			//密钥
			SecretKey secretKey = keyFactory.generateSecret(desKeySpec);
			// 偏移量
			IvParameterSpec iv = new IvParameterSpec(keyBytes);
			Cipher cipher = Cipher.getInstance(ALGORITHM);
			cipher.init(mode, secretKey, iv);
			return cipher.doFinal(inputBytes);
		} catch (GeneralSecurityException e) {
			throw convertRuntimeException(e);
		}
	}
	
	private static byte[] desECB(byte[] inputBytes, byte[] keyBytes, int mode) {
		try {
			DESKeySpec desKeySpec = new DESKeySpec(keyBytes);
			SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(DES);
			//密钥
			SecretKey secretKey = keyFactory.generateSecret(desKeySpec);
			// 偏移量
			Cipher cipher = Cipher.getInstance("DES/ECB/NoPadding");
			cipher.init(mode, secretKey);
			return cipher.doFinal(inputBytes);
		} catch (GeneralSecurityException e) {
			throw convertRuntimeException(e);
		}
	}
	
	
	
	
	private static byte[] tripleDes(byte[] inputBytes, byte[] keyBytes, int mode) {
		try {
			 DESedeKeySpec  desKeySpec = new  DESedeKeySpec(keyBytes);
			SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(TRIPLE_DES);
			//密钥
			SecretKey secretKey = keyFactory.generateSecret(desKeySpec);
			// 偏移量
			ByteArrayBuffer ivbuff = new ByteArrayBuffer(8);
			ivbuff.append(keyBytes,0,8);
			IvParameterSpec iv = new IvParameterSpec(ivbuff.toByteArray());
			Cipher cipher = Cipher.getInstance(ALGORITHM_3DES);
			cipher.init(mode, secretKey, iv);
			return cipher.doFinal(inputBytes);
		} catch (GeneralSecurityException e) {
			throw convertRuntimeException(e);
		}
	}
	
	private static byte[] tripleDesECB(byte[] inputBytes, byte[] keyBytes, int mode) {
		try {
			 DESedeKeySpec  desKeySpec = new  DESedeKeySpec(keyBytes);
			SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(TRIPLE_DES);
			//密钥
			SecretKey secretKey = keyFactory.generateSecret(desKeySpec);
			// 偏移量
			ByteArrayBuffer ivbuff = new ByteArrayBuffer(8);
			ivbuff.append(keyBytes,0,8);
			IvParameterSpec iv = new IvParameterSpec(ivbuff.toByteArray());
			Cipher cipher = Cipher.getInstance("DESede/CBC/NoPadding");
			cipher.init(mode, secretKey, iv);
			return cipher.doFinal(inputBytes);
		} catch (GeneralSecurityException e) {
			throw convertRuntimeException(e);
		}
	}
	
	
	

	
	
	private static IllegalStateException convertRuntimeException(GeneralSecurityException e) {
		return new IllegalStateException("Security exception", e);
	}
}
