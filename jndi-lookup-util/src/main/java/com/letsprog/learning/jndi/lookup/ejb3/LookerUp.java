package com.letsprog.learning.jndi.lookup.ejb3;

import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class LookerUp {
	private Properties prop = new Properties();
	private String jndiPrefix;

	// Same WAR
	public LookerUp(){
		prop.put(Context.URL_PKG_PREFIXES, "org.jboss.ejb.client.naming");
	}
	public Object findLocalSessionBean(String moduleName, String beanName, String interfaceFullQualifiedName) throws NamingException{
		 
		final Context context = new InitialContext(prop);
		Object object = context.lookup("java:global/"+moduleName+"/"+beanName+"!"+interfaceFullQualifiedName);
		context.close();
 
		return object;
	}

	public Object findSessionBean(String jndiName) throws NamingException{
 
		final Context context = new InitialContext(prop);
		Object object = context.lookup(jndiName);
		context.close();
 
		return object;
	}
}
