package com.lamp.component.essential.security.verify;

import java.security.Key ;

import org.junit.Test ;

import com.lamp.component.essential.security.DefaultSecurityOperation;
import com.lamp.component.essential.security.SecurityEntity;
import com.lamp.component.essential.security.SecurityEntity.SecurityEntityBuilder;
import com.lamp.component.essential.security.generate.KeyPairCreate;

public class SecurityOperationTest {

	byte[] data = "测试怎么样测试怎么样测试怎么样测试怎么样测试怎么样测试怎么样测试怎么样测试怎么样测试怎么样测试怎么样".getBytes( );
	
	
	@Test
	public void test() throws Exception{
		String asymmetric = "RSA";
		KeyPairCreate<Key> keyCreate = new KeyPairCreate<>( asymmetric , 1024 );
		SecurityEntityBuilder se = SecurityEntity.create( );
		se.setAlgorithm( "RSA" );
		se.setSignName( "SHA1WithRSA" );
		se.setPrivateKey( keyCreate.getPrivateKey( ).getEncoded( )	 );
		se.setPublicKey( keyCreate.getPublicKey( ).getEncoded( )	 );
		DefaultSecurityOperation operation = new DefaultSecurityOperation( se.builder( ) );
		byte[] by = operation.encryption( data );
		byte[] deby = operation.decryption( by );
		
		System.out.println( new String(data).equals( new String(deby) ) ) ;
		
	}
}
