package com.lamp.component.essential.security.verify;


import org.junit.Test;

public class DefaultVerifyTest {
	
	@Test
	public void test(){
		System.out.println(DefaultVerify.verifys("AES",256));
		System.out.println(DefaultVerify.verifys("ARCFOUR", 1024));
		System.out.println(DefaultVerify.verifys("BLOWFISH", 50));
		System.out.println(DefaultVerify.verifys("DES", 56));
		System.out.println(DefaultVerify.verifys("DESede", 168));
		System.out.println(DefaultVerify.verifys("RC2", 34));
		
	}

}
