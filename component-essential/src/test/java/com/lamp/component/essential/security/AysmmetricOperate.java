package com.lamp.component.essential.security;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec ;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.RSAPrivateKeySpec;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;

public class AysmmetricOperate {

	private static byte[] cipherOperate( byte[] data, Key key, KeyFactory keyFactory,int init,int length,boolean b) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, IOException{
		return cipherOperate(data, key, init, length, b, keyFactory.getAlgorithm());
	}

	private static byte[] cipherOperate( byte[] data, Key key ,int init,int length,boolean b,String algorithm) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, IOException{
		Cipher cipher = Cipher.getInstance(algorithm);
		cipher.init(init, key);
		if(length != 0){
			int dataLength = data.length;
			if(dataLength > length ){
				byte[][] d = splitArray(data, length);
				int dLeng = d.length;
				ByteArrayOutputStream  bos = new ByteArrayOutputStream(dLeng*length);
				int i = 0;
				for( ; ;){
					bos.write(cipher.doFinal(d[i]));
					i++;
					if(i == dLeng){
						break;
					}
				}
				return bos.toByteArray();
			}else{
				return cipher.doFinal(data);
			}

		}
		return cipher.doFinal(data);
	}

	private static KeyFactory getKeyFactory(String encryptName) throws NoSuchAlgorithmException{
		return KeyFactory.getInstance(encryptName);
	}

	private static PublicKey publicKeyOperate(byte[] key ,KeyFactory keyFactory) throws InvalidKeySpecException, NoSuchAlgorithmException{
		X509EncodedKeySpec es = new X509EncodedKeySpec(key);
		return keyFactory.generatePublic(es);
	}

	private static PrivateKey privateKeyOperate(byte[] key  ,KeyFactory keyFactory) throws InvalidKeySpecException, NoSuchAlgorithmException{
		PKCS8EncodedKeySpec es = new PKCS8EncodedKeySpec(key);
		return keyFactory.generatePrivate(es);
	}

	private static byte[] edPrivateKey(byte[] data, byte[] key,String encryptName,int init,int length,boolean b)throws Exception {
		KeyFactory keyFactory = getKeyFactory(encryptName);
		PrivateKey privateKey = privateKeyOperate(key, keyFactory);
		return cipherOperate(data, privateKey, keyFactory, init, length,b);
	}

	private static byte[] edPublicKey(byte[] data, byte[] key,String encryptName,int init,int length,boolean b)throws Exception {
		KeyFactory keyFactory = getKeyFactory(encryptName);
		PublicKey  publicKey = publicKeyOperate(key, keyFactory);
		return cipherOperate(data, publicKey, keyFactory, init, length,b);
	}
	
	//
	public static byte[] decryptByPrivateKey(byte[] data, byte[] key,String encryptName,int length) throws Exception{
		return edPrivateKey(data, key, encryptName, Cipher.DECRYPT_MODE, length,true);

	}
	
	public static String decryptByPrivateKeyString(byte[] data, byte[] key,String encryptName,int length) throws Exception{
		byte[] b = edPrivateKey(data, key, encryptName, Cipher.DECRYPT_MODE, length,true);
		return new String(b);

	}
	//对称加密
	public static byte[] encryptByPrivateKey(byte[] data, byte[] key,String encryptName,int length) throws Exception{
		return edPrivateKey(data, key, encryptName, Cipher.ENCRYPT_MODE, length,false);

	}
	//对称加密，使用私钥加密
	public static String encryptByPrivateKeyString(byte[] data, byte[] key,String encryptName,int length) throws Exception{
		byte[] b = edPublicKey(data, key, encryptName, Cipher.ENCRYPT_MODE, length,false);
		return new String(b);

	}

	public static byte[] decryptByPublicKey(byte[] data, byte[] key,String encryptName,int length) throws Exception{
		return edPublicKey(data, key, encryptName, Cipher.DECRYPT_MODE, length,true);

	}

	public static String decryptByPublicKeyString(byte[] data, byte[] key,String encryptName,int length) throws Exception{
		byte[] b = edPublicKey(data, key, encryptName, Cipher.DECRYPT_MODE, length,true);
		return new String(b);
	}

	public static byte[] encryptByPublicKey(byte[] data, byte[] key,String encryptName,int length) throws Exception{
		return edPublicKey(data, key, encryptName, Cipher.ENCRYPT_MODE, length,false);

	}

	public static String encryptByPublicKeyString(byte[] data, byte[] key,String encryptName,int length) throws Exception{
		byte[] b = edPublicKey(data, key, encryptName, Cipher.ENCRYPT_MODE, length,false);
		return new String(b);

	}

	//密钥生成工厂
	public static KeyPair createKey(String encryptName , int size ) throws NoSuchAlgorithmException{
		KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance(encryptName);
		keyPairGen.initialize(size);
		
		return keyPairGen.generateKeyPair();
	}
	public static RSAPrivateKey getPrivateKey(String modulus, String exponent) {
		try {
			BigInteger b1 = new BigInteger(modulus);
			BigInteger b2 = new BigInteger(exponent);
			KeyFactory keyFactory = KeyFactory.getInstance("RSA");
			RSAPrivateKeySpec keySpec = new RSAPrivateKeySpec(b1, b2);
			return (RSAPrivateKey) keyFactory.generatePrivate(keySpec);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static byte[] symmetry(byte[] key , String signatureAlgorithm,byte[] data,String algorithm,int in) throws Exception{
		return cipherOperate(data, toKey(key, algorithm), in, 0, true, signatureAlgorithm);
	}

	//key生成工厂
	public static Key toKey(byte[] key , String algorithm ) throws Exception{
		//实例化DES密钥材料
		KeySpec dks = new DESedeKeySpec(key);
		//实例化秘密密钥工厂
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(algorithm);
		//生成秘密密钥
		return keyFactory.generateSecret(dks);
	}


	public static byte[][] splitArray(byte[] data,int len){
		int x = data.length / len;
		int y = data.length % len;
		int z = 0;
		if(y!=0){
			z = 1;
		}
		byte[][] arrays = new byte[x+z][];
		byte[] arr;
		for(int i=0; i<x+z; i++){
			arr = new byte[len];
			if(i==x+z-1 && y!=0){
				System.arraycopy(data, i*len, arr, 0, y);
			}else{
				System.arraycopy(data, i*len, arr, 0, len);
			}
			arrays[i] = arr;
		}
		return arrays;
	}

}
