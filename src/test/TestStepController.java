package test;

import org.junit.Before;
import org.junit.Test;

import com.server.uno.model.Game;
import com.server.uno.model.Player;

public class TestStepController {

	public Game game;
	
	@Before
	public void fillingGame() {
		game = new Game();
		game.addPlayer(new Player("11", "Test_ONE"));
		game.addPlayer(new Player("22", "Test_TWO"));
	}

	@Test
	public void testMakeFirstStep() {
		
	}
	
	@Test
	public void testMakeStep() {
		
	}
}
