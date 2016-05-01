package com.server.uno.controller;

import java.util.ArrayList;
import java.util.List;
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

	public RulesController(Game game) {
		this.game = game;
	}

	public void giveNextMoverBonuses(Bonuses bonuses) {
		Card topCard = game.getTable().getTopOpenCard();
		Player nextMover = getNextMover();
		
		if (bonuses.isCards) {
			boolean giveCards = true;
			for (Card card : nextMover.getCards()) {
				 if (card.getNumber() == topCard.getNumber() 
						 && (topCard.getNumber() == 12 || topCard.getNumber() == 14))
					 giveCards = false;
			}
			if(giveCards) {
				for (Card card : bonuses.takeCards()) {
					nextMover.addCard(card);
				}
			}
		}
		if (bonuses.isGaps) {
			
		}
	}

	public void goToNextMover() {
		if (prevMover.getPosition() == playersDeque.size() - 1)
			mover = playersDeque.get(0);
		else
			mover = playersDeque.get(prevMover.getPosition() + 1);
	}

	public Player getNextMover() {
		if (prevMover.getPosition() == playersDeque.size() - 1)
			return playersDeque.get(0);
		else
			return playersDeque.get(prevMover.getPosition() + 1);
	}

	public void endMoverMove() {
		prevMover = mover;
		mover = Game.ADMIN;
	}

	public void givePlayersDeque(Set<Player> players) {
		playersDeque = new ArrayList<>(players);
		for (int i = 0; i < playersDeque.size(); i++) {
			playersDeque.get(i).setPosition(i);
			System.out.println(playersDeque.get(i));
		}
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
