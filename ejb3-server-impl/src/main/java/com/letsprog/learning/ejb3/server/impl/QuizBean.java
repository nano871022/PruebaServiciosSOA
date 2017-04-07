package com.letsprog.learning.ejb3.server.impl;

import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Remove;
import javax.ejb.Stateless;

import com.letsprog.learning.ejb3.server.api.ILocalQuiz;
import com.letsprog.learning.ejb3.server.api.ILocalQuizQuestionGenerator;
import com.letsprog.learning.ejb3.server.api.IQuiz;
import com.letsprog.learning.ejb3.server.api.IRemoteQuiz;
import com.letsprog.learning.ejb3.server.api.entities.LevelQuestion;
@Stateless
@Remote(IRemoteQuiz.class)
@Local(ILocalQuiz.class)
public class QuizBean implements IQuiz {
	@EJB
	ILocalQuizQuestionGenerator levelQuestionGenerator;
	private String playerName;
	private String askedQuestion;
	private Long expectedAnswer;
	private int score=0;
	private int level=1;
	
	@Override
	public void begin(String userName) {
this.playerName = playerName;
	}

	@Override
	public String generateQuestionAndAnswer() {
		LevelQuestion levelQuestion = levelQuestionGenerator.generateQuestion(level);
		askedQuestion = levelQuestion.getQuestion(); 
		expectedAnswer = levelQuestion.getExpectedAswer();
		return askedQuestion;
		}

	@Override
	public boolean verifyAnswerAndReward(int result) {
		if(expectedAnswer == result){
			score++;
			level++;
			return true;
		}
		return false;
		}
	@Remove
	@Override
	public void end() {
		System.out.println("QuizBean instance will be removed..");
	}

	@Override
	public int getScore() {
		return score;
		}

	@Override
	public String getPlayerName() {
		return playerName;
		}
	public String getAskedQuestion() {
		return askedQuestion;
	}

}
