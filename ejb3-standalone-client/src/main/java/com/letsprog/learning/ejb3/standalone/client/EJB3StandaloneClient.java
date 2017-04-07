package com.letsprog.learning.ejb3.standalone.client;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Hashtable;

import javax.naming.Context;
import javax.naming.InitialContext;

import com.letsprog.learning.ejb3.server.api.IRemoteQuiz;

public class EJB3StandaloneClient {
	public static void main(String[] args) throws Exception {
		 
		// Defining JNDI properties - (!) Other properties are set in jboss-ejb-client.proerties
		final Hashtable<String, String> jndiProperties = new Hashtable<String, String>();
		jndiProperties.put(Context.URL_PKG_PREFIXES, "org.jboss.ejb.client.naming");
		
		// Setting JNDI Context
		final Context context = new InitialContext(jndiProperties);
		
		// Using the JNDI name of the EJB following this pattern :
		// "ejb:{EarName}/{ModuleName}/{DeploymentName}/{EJBClassName}!{EJBInterfaceFullyQualifiedName}?{Stateful}"
		IRemoteQuiz remoteQuiz = (IRemoteQuiz) context.lookup("ejb:/ejb3-server-war-0.0.1-SNAPSHOT//QuizBean!com.letsprog.learning.ejb3.server.api.IRemoteQuiz");
		remoteQuiz.begin("Farah");
		String question = remoteQuiz.generateQuestionAndAnswer();
		System.out.println("Question : "+question);
 
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String answer = br.readLine();
		
		Boolean answerIsCorrect = remoteQuiz.verifyAnswerAndReward(Integer.parseInt(answer));
 
		if(!answerIsCorrect){
			System.out.println("The answer is wrong. Your score is : "+remoteQuiz.getScore()+". "
					+ "Have a better luck next time, "+remoteQuiz.getPlayerName());
		} else {
			System.out.println("The answer is correct. Your score is : "+remoteQuiz.getScore()+". "
					+ "Very well done, "+remoteQuiz.getPlayerName());			
		}
 
		remoteQuiz.end();
	}
}
