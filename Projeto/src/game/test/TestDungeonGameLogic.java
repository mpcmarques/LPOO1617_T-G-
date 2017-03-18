package game.test;

import static org.junit.Assert.*;

import org.junit.Test;

import game.logic.*;

public class TestDungeonGameLogic {
	
	char[][] map = {{'X','X','X','X','X'},
					{'X','H',' ','G','X'},
					{'I',' ',' ','X','X'},
					{'I','k',' ','X','X'},
					{'X','X','X','X','X'}
	};
	
	@Test
	public void testMoveHeroIntoFreeCell(){
		GameMap gameMap = new GameMap(map);
		Game game = new Game(gameMap);
		assertEquals(new Coordinate2d(1,1), game.getGameMap().getHero().getCoordinate());
	}
	
	@Test
	public void testHeroIsCapturedByGuard(){
		GameMap gameMap = new GameMap(map);
		Game game = new Game(gameMap);
		assertFalse(game.isGameOver());
		game.getGameMap().updateGame("d");
		assertTrue(game.isGameOver());
		assertEquals(EndStatus.DEFEAT, game.getEndStatus());
	}
}
