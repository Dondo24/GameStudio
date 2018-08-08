package GameStudio.Game.MineSweeper.Core;


public class Clue extends Tile{

	private final int value;
	
	public Clue(int value) {
		this.value= value;
	}
	
	
	public int getValue() {
		return value;
	}
}

