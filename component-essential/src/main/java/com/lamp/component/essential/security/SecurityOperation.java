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

import java.security.SignatureException ;

public interface SecurityOperation {

	public byte[] encryption( byte[] data ) throws Exception;
	
	public byte[] decryption(byte[] data) throws Exception;
	
	public int encryptionAfterLength( byte[] data );
	
	public int decryptionAfterLength( byte[] data );
	
	public byte[] sign(byte[] data) throws SignatureException;
	
	boolean verify ( byte[] data , byte[] verifyByte ) throws SignatureException ;

	
	public int signAfterLength( byte[] data );
	
	
	public String getAlgorithm();
	
	public Integer getKeyLength();

}
