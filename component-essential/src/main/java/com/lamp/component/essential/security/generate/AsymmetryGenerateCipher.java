/*
 *Copyright (c) [Year] [name of copyright holder]
 *[Software Name] is licensed under Mulan PubL v2.
 *You can use this software according to the terms and conditions of the Mulan PubL v2.
 *You may obtain a copy of Mulan PubL v2 at:
 *         http://license.coscl.org.cn/MulanPubL-2.0
 *THIS SOFTWARE IS PROVIDED ON AN "AS IS" BASIS, WITHOUT WARRANTIES OF ANY KIND,
 *EITHER EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO NON-INFRINGEMENT,
 *MERCHANTABILITY OR FIT FOR A PARTICULAR PURPOSE.
 *See the Mulan PubL v2 for more details.
 */
package com.lamp.component.essential.security.generate;

import java.security.InvalidKeyException ;
import java.security.Key;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;

/**
 * 
 * 
 * 执行类
 * 操作类
 * 
 * 把所有的 非对称加解密的 算法测试完成
 * @author yuki
 *
 */
public class AsymmetryGenerateCipher extends AbstractGenerateCipher implements GenerateSignature {

	
	public AsymmetryGenerateCipher(String algorithm , byte[] publicKeyByte , byte[] privateKeyByte) throws NoSuchAlgorithmException, InvalidKeySpecException, NoSuchPaddingException, InvalidKeyException{
		super( algorithm );
		KeyFactory keyFactory = getKeyFactory();
		X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(publicKeyByte);
		Key publicKey = keyFactory.generatePublic(x509KeySpec);
		setPublicCipher(  getCipher(Cipher.DECRYPT_MODE , publicKey));

		PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(privateKeyByte);  
	    Key privateKey = keyFactory.generatePrivate(pkcs8KeySpec); 
	    setPrivateCipher(  getCipher(Cipher.ENCRYPT_MODE , privateKey) );

	    
	}
	
	public AsymmetryGenerateCipher(String algorithm ,String sign, byte[] publicKeyByte , byte[] privateKeyByte) throws NoSuchAlgorithmException, InvalidKeySpecException, NoSuchPaddingException, InvalidKeyException{
		super( algorithm , sign );
		KeyFactory keyFactory = getKeyFactory();
		X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(publicKeyByte);
		Key publicKey = keyFactory.generatePublic(x509KeySpec);
		setPublicCipher(  getCipher(Cipher.DECRYPT_MODE , publicKey));
		
		PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(privateKeyByte);  
	    Key privateKey = keyFactory.generatePrivate(pkcs8KeySpec); 
	    setPrivateCipher(  getCipher(Cipher.ENCRYPT_MODE , privateKey) );
	    
	    
	    setSignSignature( getSignature( GenerateSignature.SIGN ,  privateKey ) );
	    setVerifySignature( getSignature( GenerateSignature.VERIFY , publicKey ) );
	}
 
	
	
}
