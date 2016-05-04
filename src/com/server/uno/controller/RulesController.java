package com.server.uno.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Set;

import com.server.uno.model.Bonuses;
import com.server.uno.model.Card;
import com.server.uno.model.Game;
import com.server.uno.model.Player;

public class RulesController {

	private Player prevMover;
	private Player mover;

	private Game game; // TODO remove if it uses only in one place
	private ArrayList<Player> playersDeque;

	private Bonuses bonuses = new Bonuses();

	public RulesController(Game game) {
		this.game = game;
	}

	public void giveNextMoverBonuses(Bonuses bonuses) {
		this.bonuses = bonuses;
		Card topCard = game.getTable().getTopOpenCard();
		Player nextMover = getNextPlayer();

		if (bonuses.isCards) {
			boolean giveCards = true;
			for (Card card : nextMover.getCards()) {
				if (card.getNumber() == topCard.getNumber() && (topCard.getNumber() == 12 || topCard.getNumber() == 14))
					giveCards = false;
			}
			if (giveCards) {
				for (Card card : bonuses.takeCards()) {
					nextMover.addCard(card);
				}
			}
		}
	}

	public void giveThisMoverBonuses(Bonuses bonuses) {
		for (Card card : bonuses.takeCards()) {
			mover.addCard(card);
		}
	}
	
	public void goToNextMover() {
		prevMover = mover;
		int padding  = 1 + bonuses.takeGaps();
		if (mover.getPosition() == playersDeque.size() - padding)
			mover = playersDeque.get(0);
		else if (mover.getPosition() > playersDeque.size() - padding){
			int tempGap = playersDeque.size() - mover.getPosition();
			padding = padding - tempGap;
			mover = playersDeque.get(padding);
		} else
			mover = playersDeque.get(mover.getPosition() + padding);
	}

	public Player getNextPlayer() {
		if (mover.getPosition() == playersDeque.size() - 1)
			return playersDeque.get(0);
		else
			return playersDeque.get(mover.getPosition() + 1);
	}
	
	public void givePlayersDeque(Set<Player> players) {
		playersDeque = new ArrayList<>(players);
		for (int i = 0; i < playersDeque.size(); i++) {
			playersDeque.get(i).setPosition(i);
		}
	}
	
	public void reversePlayersDeque() {
		Collections.reverse(playersDeque);
		for (int i = 0; i < playersDeque.size(); i++) {
			playersDeque.get(i).setPosition(i);
		}
	}
	
	public int countPoints() {
		int topScore = 0;
		for (Player player : playersDeque) {
			for (Card card : player.getCards()) {
				if(card.getNumber() < 10) 
					player.addPoints(card.getNumber());;
				switch (card.getNumber()) {
				case 10 :
				case 11 :
				case 12 : 
					player.addPoints(20);
					break;
				case 13 :
				case 14 :
					player.addPoints(50);
					break;
				}
			}
			if(player.getScore() > topScore)
				topScore = player.getScore();
		}
		return topScore;
	}

	public Player getPrevMover() {
		return prevMover;
	}

	public void setPrevMover(Player prevMover) {
		if (prevMover == null)
			throw new NullPointerException();
		this.prevMover = prevMover;
	}

	public Player getMover() {
		return mover;
	}

	public void setMover(Player mover) {
		if (mover == null)
			throw new NullPointerException();
		this.mover = mover;
	}

	public ArrayList<Player> getPlayersDeque() {
		return new ArrayList<>(playersDeque);
	}

	public void setPlayersDeque(ArrayList<Player> players) { // TODO remove
		playersDeque = players;
	}
}
