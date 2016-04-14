package com.server.uno.util;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

import com.server.uno.model.Card;
import com.server.uno.model.Game;
import com.server.uno.model.Player;

public class RulesManager {
	
	private Game game;
	private Player mover;
	private List<Player> playersDeque = new ArrayList<>(game.getPlayers()); // TODO
	private CardsManager cardsManager = new CardsManager(game, this);
	private List<Card> cardsPool = new ArrayList<>();
	
	public RulesManager(Game game) {
		this.game = game;
	}

	public void makeStep(Player player, Card card) throws Exception {
		if(!game.getMover().equals(player)){
			player.addCard(game.getTable().getCardFromDeck()); // TODO punishment
			return;
		}
		cardsManager.putCard(player, card);
		player.removeCard(card);
		if(!cardsPool.isEmpty()) {
//			TODO give next player pool cards
		}
//		TODO
	}

	public List<Card> getCardsPool() {
		return new ArrayList<>(cardsPool);
	}

	public void addCardInToPool(Card card) {
		cardsPool.add(card);
	}
	
}
