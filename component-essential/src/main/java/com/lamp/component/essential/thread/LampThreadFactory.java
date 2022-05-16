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
package com.lamp.component.essential.thread;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

public class LampThreadFactory implements ThreadFactory{

	private final static String SEQARATOR = "_";
	
	
	private AtomicInteger threadIndex = new AtomicInteger(0);
	
	private final String name;
	
	private final String separator;
	
	
	public LampThreadFactory(String name){
		this(name , SEQARATOR);
	}
	
	public LampThreadFactory(String name , String separator){
		this.name = name;
		this.separator = separator;
		
	}

    @Override
    public Thread newThread(Runnable r) {
        return new Thread(r, name + separator+this.threadIndex.incrementAndGet());
    }

	

}
