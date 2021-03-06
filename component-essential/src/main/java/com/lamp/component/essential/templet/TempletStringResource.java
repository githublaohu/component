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
package com.lamp.component.essential.templet;

public class TempletStringResource {
	
	 /** template body */
    private String body;
    
    /** encoding */
    private String encoding;

    /** last modified ts */
    private long lastModified;

    /**
     * convenience constructor; sets body to 'body' and sets lastModified to now
     * @param body
     */
    public TempletStringResource(final String body, final String encoding)
    {
        setBody(body);
        setEncoding(encoding);
    }

    /**
     * Sets the template body.
     * @return String containing the template body.
     */
    public String getBody()
    {
        return body;
    }

    /**
     * Returns the modification date of the template.
     * @return Modification date in milliseconds.
     */
    public long getLastModified()
    {
        return lastModified;
    }

    /**
     * Sets a new  value for the template body.
     * @param body New body value
     */
    public void setBody(final String body)
    {
        this.body = body;
        this.lastModified = System.currentTimeMillis();
    }

    /**
     * Changes the last modified parameter.
     * @param lastModified The modification time in millis.
     */
    public void setLastModified(final long lastModified)
    {
        this.lastModified = lastModified;
    }

    /**
     * Returns the encoding of this String resource.
     * 
     * @return The encoding of this String resource.
     */
    public String getEncoding() {
        return this.encoding;
    }

    /**
     * Sets the encoding of this string resource.
     * 
     * @param encoding The new encoding of this resource.
     */
    public void setEncoding(final String encoding)
    {
        this.encoding = encoding;
    }

}
