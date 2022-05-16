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
package com.lamp.component.essential.security;

import java.security.InvalidKeyException ;
import java.security.NoSuchAlgorithmException ;
import java.security.Signature ;
import java.security.SignatureException ;
import java.security.spec.InvalidKeySpecException ;

import javax.crypto.NoSuchPaddingException ;

import com.lamp.component.essential.security.cipherstream.CipherStream;
import com.lamp.component.essential.security.generate.AsymmetryGenerateCipher;
import com.lamp.component.essential.security.generate.GenerateCipher;
import com.lamp.component.essential.security.generate.SymmetricGenerateCipher;

public class DefaultSecurityOperation implements SecurityOperation {

	private SecurityEntity securityEntity;
	
	private CipherStream cipherStream;
	
	private GenerateCipher generateCipher;
	
	
	public DefaultSecurityOperation(SecurityEntity securityEntity) throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeySpecException {
		this.securityEntity = securityEntity;
		this.cipherStream   = securityEntity.getCipherStream( );
		if(securityEntity.isSymmetric( )){
			this.generateCipher = new SymmetricGenerateCipher( securityEntity.getAlgorithm( ) , securityEntity.getEncryption( ) );
		}else{
			this.generateCipher = new AsymmetryGenerateCipher( securityEntity.getAlgorithm( ) , securityEntity.getSignName( ) , securityEntity.getDecryption( ) , securityEntity.getEncryption( ) );
		}
	}
	
	@Override
	public byte[] encryption ( byte[] data ) throws Exception {
		return cipherStream.getByte( generateCipher.getPrivateCipher( ) , data , securityEntity.getEncryptionCipherLength( ) , securityEntity.isSymmetric( ) ) ;
	}

	@Override
	public byte[] decryption ( byte[] data ) throws Exception {
		return cipherStream.getByte( generateCipher.getPublicCipher( ) , data , securityEntity.getDecryptionCipherLength( ) , securityEntity.isSymmetric( ) ) ;
	}

	@Override
	public int encryptionAfterLength ( byte[] data ) {
		return 0 ;
	}

	@Override
	public int decryptionAfterLength ( byte[] data ) {
		return 0 ;
	}

	@Override
	public byte[] sign ( byte[] data ) throws SignatureException {
		Signature st = generateCipher.getSign( );
		st.update( data );
		return st.sign( );
	}

	@Override
	public boolean verify ( byte[] data , byte[] verifyByte) throws SignatureException {
		Signature verify = generateCipher.getVerify( );
		verify.update( data );;
		return verify.verify( verifyByte );
	}

	@Override
	public int signAfterLength ( byte[] data ) {
		// TODO 自动生成的方法存根
		return 0 ;
	}

	@Override
	public String getAlgorithm ( ) {
		return securityEntity.getAlgorithm( ) ;
	}

	@Override
	public Integer getKeyLength ( ) {
		return securityEntity.getLength( ) ;
	}

}
