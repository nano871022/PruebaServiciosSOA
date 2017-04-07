package com.letsprog.learning.ejb3.local.client;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.naming.NamingException;

import com.letsprog.learning.ejb3.server.api.IRemotePlayedQuizzesCounter;
import com.letsprog.learning.ejb3.server.api.IRemoteQuiz;
import com.letsprog.learning.jndi.lookup.ejb3.remote.LookerUp;

@ManagedBean(name = "quizManagedBean")
@SessionScoped
public class QuizManagedBean {
	private String playerName;
	private int score = 0;
	private String question = "";
	private int answer;
	private IRemoteQuiz quizProxy;
//	@EJB(mappedName = "ejb:/ejb3-server-war//PlayedQuizzesCounterBean!com.letsprog.learning.ejb3.server.api.IRemotePlayedQuizzedCounter")
	@EJB(mappedName = "java:global/ejb3-server-war-0.0.1-SNAPSHOT/PlayedQuizzesCounterBean!com.letsprog.learning.ejb3.server.api.IRemotePlayedQuizzesCounter")
	private IRemotePlayedQuizzesCounter playedQuizzesCounterProxy;

	@PostConstruct
	public void setup() {
		System.out.println("Configuracion despues de crear el bean");
	}

	public String start() throws NamingException {
		playedQuizzesCounterProxy.increment();
		if (quizProxy != null) {
			quizProxy.end();
			quizProxy = null;
			System.out.println("Recargando Quiz");
		}
		String ejbServerAdress = "127.0.0.1";
		int ejbServerPort = 9180;
		String earName = "";
		String moduleName = "ejb3-server-war-0.0.1-SNAPSHOT";
		String deploymentDistinctName = "";
		String beanName = "QuizBean";
		String interfaceFullQualifiedName = IRemoteQuiz.class.getName();

		LookerUp wildf10Lookerup = new LookerUp(ejbServerAdress, ejbServerPort);
		quizProxy = (IRemoteQuiz) wildf10Lookerup.findRemoteSessionBean(LookerUp.SessionBeanType.STATELESS, earName,
				moduleName, deploymentDistinctName, beanName, interfaceFullQualifiedName);
		quizProxy.begin(playerName);
		setQuestion(quizProxy.generateQuestionAndAnswer());
		return "quiz.xhtml";
	}

	public String verifyAnswer() {
		if (quizProxy == null) {
			System.out.println("quizProxy esta vacio");
		}
		boolean correct = quizProxy.verifyAnswerAndReward(answer);
		setScore(quizProxy.getScore());

		if (!correct) {
			System.out.println("Error de validacion");
			quizProxy.end();
			quizProxy = null;
			return "end.xhtml";
		} else {
			setQuestion(quizProxy.generateQuestionAndAnswer());
			return "quiz.xhtml";
		}
	}

	public long getPlayedQuizzedNumber() {
		return playedQuizzesCounterProxy.getNumber();
	}

	public void cleanUp() {
		System.out.println("Limpiando antes de destruir el bean");
		if (quizProxy == null) {
			quizProxy.end();
			quizProxy = null;
		}
	}

	public String getPlayerName() {
		return playerName;
	}

	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public int getAnswer() {
		return answer;
	}

	public void setAnswer(int answer) {
		this.answer = answer;
	}
	
}
