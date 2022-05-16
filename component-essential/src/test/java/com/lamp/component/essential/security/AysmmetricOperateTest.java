package com.lamp.component.essential.security;

import org.junit.Test;

public class AysmmetricOperateTest {
	
	@Test
	public void generator() throws Exception{
		String encryptName = "RSA";//加密的算法
		AsymmetricEntity asy = new AsymmetricEntity(encryptName,1024,null,null,null,null,null);
		String privateKey = asy.getPrivateKeyString();
		String publickey  = asy.getPublicKeyString();
		byte[] data = asy.encryptByPublicKey("非对称加密".getBytes());
		System.out.println(new String(data));
		
		
		AsymmetricEntity asyOne = new AsymmetricEntity(encryptName ,asy.getPublicKey( ) , asy.getPrivateKey( )  );
		String str = asyOne.decryptByPrivateKeyString( data );
		System.out.println( str ) ;
	}

	
}
