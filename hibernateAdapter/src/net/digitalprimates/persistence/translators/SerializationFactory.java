/**
	Copyright (c) 2008. Digital Primates IT Consulting Group
	http://www.digitalprimates.net
	All rights reserved.
	
	This library is free software; you can redistribute it and/or modify it under the 
	terms of the GNU Lesser General Public License as published by the Free Software 
	Foundation; either version 2.1 of the License.

	This library is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; 
	without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. 
	See the GNU Lesser General Public License for more details.

	
	@author: Mike Nimer
	@ignore
 **/

package net.digitalprimates.persistence.translators;

import javax.servlet.ServletContext;

import net.digitalprimates.persistence.translators.hibernate.HibernateDeserializer;
import net.digitalprimates.persistence.translators.hibernate.HibernateSerializer;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import flex.messaging.FlexContext;

/**
 * Factory to return the right serializer/deserializer for the requests.
 * 
 * @author mike nimer
 */
public class SerializationFactory
{
	public static ISerializer getSerializer(Object source)
	{
		return getSerializer(source,false);
	}
	public static ISerializer getSerializer(Object source,boolean useAggressiveSerialization)
	{
		ServletContext ctx = FlexContext.getServletContext();
		WebApplicationContext springContext = WebApplicationContextUtils.getRequiredWebApplicationContext(ctx);
		ISerializer serializer = (ISerializer) springContext.getBean("hibernateSerializerBean",new Object[]{source,useAggressiveSerialization});
		if (serializer == null)
		{
			throw new RuntimeException("bean named hibernateSerializerBean not found");
		}
		return serializer;
	}


	public static IDeserializer getDeserializer()
	{
		return new HibernateDeserializer();
	}
}
