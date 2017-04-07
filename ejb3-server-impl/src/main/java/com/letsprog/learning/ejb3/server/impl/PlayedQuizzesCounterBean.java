package com.letsprog.learning.ejb3.server.impl;

import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Singleton;

import com.letsprog.learning.ejb3.server.api.ILocalPlayedQuizzesCounter;
import com.letsprog.learning.ejb3.server.api.IPlayedQuizzesCounter;
import com.letsprog.learning.ejb3.server.api.IRemotePlayedQuizzesCounter;
@Singleton
@Local(ILocalPlayedQuizzesCounter.class)
@Remote(IRemotePlayedQuizzesCounter.class)
public class PlayedQuizzesCounterBean implements IPlayedQuizzesCounter {
	long playedQuizzesNumber = 0;
	@Override
	public void increment() {
		playedQuizzesNumber++;
	}

	@Override
	public long getNumber() {
		return playedQuizzesNumber;
		}

}
