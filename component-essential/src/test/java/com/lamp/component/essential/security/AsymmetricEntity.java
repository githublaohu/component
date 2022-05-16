package com.lamp.component.essential.security;

import java.security.Key;
import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Base64;

import javax.crypto.Cipher;


public class AsymmetricEntity {
	//加密方式 对称加密 OR 非对称加密
	private  String encryptName;
	
	//秘钥长度
	private  int keySize;

	//私钥长度
	private  int privateLength;

	//公钥长度
	private  int publicLength;

	//数字签名
	private  String signatureAlgorithm;

	private  byte[] sign;

	private  byte[] signDate;
	
	//公钥
	private  byte[] publicKey;
	
	//私钥
	private  byte[] privateKey;

	/**
	 * @param by
	 * @param encryptName 加密方式
	 * @param flag 是否需要读取文件
 	 * @param signatureAlgorithm 数字签名
	 */
	public AsymmetricEntity(byte[] by , String encryptName,boolean flag,String signatureAlgorithm){
		if(!flag){
			AysmmetricFile af = new AysmmetricFile();
			Key key = af.toSymmetrySpec(by, encryptName);
			by = key.getEncoded();
		}
		this.encryptName = encryptName;
		this.publicKey = by;
		this.signatureAlgorithm = signatureAlgorithm;
	}


	public AsymmetricEntity(String publicFile ,String type ,String privateFile, String fillStyle,String pwd, String keyAlias){
		AysmmetricFile af = new AysmmetricFile();
		PrivateKey privateKey = af.getTPFXPrivateKey(privateFile, fillStyle, pwd, keyAlias);
		if(keyAlias == null)
			keyAlias = privateKey.getAlgorithm();

		PublicKey publicKey =  af.getCERPublicKey(publicFile, type);
		into(keyAlias, 0, null, null, null, publicKey.getEncoded(), privateKey.getEncoded());
	}

	public AsymmetricEntity(String encryptName, int keySize ){
		this(encryptName, keySize, null, null, null);
	}

	public AsymmetricEntity(String encryptName, int keySize ,String signatureAlgorithm ,byte[] sign ,byte[] signDate){
		this(encryptName, keySize, signatureAlgorithm, sign, signDate, null, null);
	}

	public AsymmetricEntity(String encryptName , String	 publicKey,String privateKey){
		this(encryptName, publicKey.getBytes(), privateKey.getBytes());
	}

	public AsymmetricEntity(String encryptName , byte[] publicKey,byte[] privateKey){
		this(encryptName, 0 , null, null, null, publicKey, privateKey);
	}

	public AsymmetricEntity(String encryptName, int keySize ,String signatureAlgorithm ,byte[] sign ,byte[] signDate,byte[] publicKey,byte[] privateKey){
		into(encryptName, keySize, signatureAlgorithm, sign, signDate, publicKey, privateKey);
	}
	/**
	 * 只能生成非对称加密的密钥。非对称加密的秘钥最短为512
	 * @param encryptName 加密方式
	 * @param keySize 秘钥长度，公钥不为空则为公钥长度，否则为私钥长度
	 * @param signatureAlgorithm 数字签名
	 * @param sign
	 * @param signDate
	 * @param publicKey
	 * @param privateKey
	 */
	private void into(String encryptName, int keySize ,String signatureAlgorithm ,byte[] sign ,byte[] signDate,byte[] publicKey,byte[] privateKey){
		this.encryptName = encryptName;
		if(keySize == 0 && privateKey != null && publicKey != null){
			keySize = publicKey == null ? privateKey.length : publicKey.length;				
		}
		this.keySize = keySize;
		//数字签名
		this.signatureAlgorithm = signatureAlgorithm;
		this.sign = sign;
		this.signDate = signDate;
		
		if(privateKey == null && publicKey == null ){
			try {
				KeyPair keyPair = AysmmetricOperate.createKey(encryptName, keySize);
				privateKey = keyPair.getPrivate().getEncoded();

				publicKey  = keyPair.getPublic().getEncoded();

			} catch (NoSuchAlgorithmException e) {
				e.printStackTrace();
			}
		}
		//RSA 因子分解
		if("RSA".equals(encryptName) ){
			int capacity = 64;
			int i = 0;
			if(signatureAlgorithm == "RSA/ECB/PKCS1Padding"){
				i =11;
			}
			if(privateKey != null ){
				int initialCapacity = privateKey.length;
		        while (capacity < initialCapacity)
		            capacity <<= 1;
		        this.privateLength = capacity >> 3;
		        this.publicLength  = this.privateLength - i;
			}else if(publicKey != null){
				int initialCapacity = publicKey.length;
		        while (capacity < initialCapacity)
		            capacity <<= 1;
		        this.privateLength = capacity >> 1;
		        this.publicLength  = this.privateLength - i;
			}
			if(this.publicLength == 0  && this.privateLength == 0){

			}
		}
		
		this.privateKey = privateKey;
		this.publicKey = publicKey;
	}

	public  byte[] decryptByPrivateKey(byte[] data) throws Exception{
		return AysmmetricOperate.decryptByPrivateKey(data, this.privateKey, encryptName, privateLength);

	}

	public  String decryptByPrivateKeyString(byte[] data) throws Exception{
		return AysmmetricOperate.decryptByPrivateKeyString(data, this.privateKey, encryptName, privateLength);

	}

	public  byte[] encryptByPrivateKey(byte[] data) throws Exception{
		return AysmmetricOperate.encryptByPrivateKey(data, this.privateKey, encryptName, privateLength);

	}

	public  String encryptByPrivateKeyString(byte[] data) throws Exception{
		return AysmmetricOperate.encryptByPrivateKeyString(data, this.privateKey, encryptName, privateLength);

	}

	public  byte[] decryptByPublicKey(byte[] data) throws Exception{
		return AysmmetricOperate.decryptByPublicKey(data, this.publicKey, encryptName, publicLength);

	}

	public  String decryptByPublicKeyString(byte[] data) throws Exception{
		return AysmmetricOperate.decryptByPublicKeyString(data, this.publicKey, encryptName, publicLength);

	}

	public  byte[] encryptByPublicKey(byte[] data) throws Exception{
		return AysmmetricOperate.encryptByPublicKey(data, this.publicKey, encryptName, publicLength);

	}

	public  String encryptByPublicKeyString(byte[] data) throws Exception{
		return AysmmetricOperate.encryptByPublicKeyString(data, this.publicKey, encryptName, publicLength);
	}


	public byte[] symmetry(byte[] data) throws Exception{
		return AysmmetricOperate.symmetry(publicKey, signatureAlgorithm, data, encryptName,Cipher.ENCRYPT_MODE);
	}

	public byte[] symmetryUnbind(byte[] data) throws Exception{
		return AysmmetricOperate.symmetry(publicKey, signatureAlgorithm, data, encryptName,Cipher.DECRYPT_MODE);
	}

	public String getPublicKeyString(){
		return  Base64.getEncoder().encodeToString(publicKey);
	}

	public String getPrivateKeyString(){
		
		return  Base64.getEncoder().encodeToString(privateKey);
	}

	public String getEncryptName() {
		return encryptName;
	}

	public void setEncryptName(String encryptName) {
		this.encryptName = encryptName;
	}

	public int getKeySize() {
		return keySize;
	}

	public void setKeySize(int keySize) {
		this.keySize = keySize;
	}

	public String getSignatureAlgorithm() {
		return signatureAlgorithm;
	}

	public void setSignatureAlgorithm(String signatureAlgorithm) {
		this.signatureAlgorithm = signatureAlgorithm;
	}

	public byte[] getSign() {
		return sign;
	}

	public void setSign(byte[] sign) {
		this.sign = sign;
	}

	public byte[] getSignDate() {
		return signDate;
	}

	public void setSignDate(byte[] signDate) {
		this.signDate = signDate;
	}

	public byte[] getPublicKey() {
		return publicKey;
	}

	public void setPublicKey(byte[] publicKey) {
		this.publicKey = publicKey;
	}

	public byte[] getPrivateKey() {
		return privateKey;
	}

	public void setPrivateKey(byte[] privateKey) {
		this.privateKey = privateKey;
	}
}
