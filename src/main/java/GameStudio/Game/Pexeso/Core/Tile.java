package GameStudio.Game.Pexeso.Core;


public class Tile {
	private int value;
 private TileState state = TileState.CLOSED;
	 
	 public Tile(int value) {
		 this.value= value;
	 }
	 public TileState getState() {
		return state;
	}
	 
	  void setState(TileState state) {
		this.state = state;
	}

	  public int getValue() {
		return value;
	}
}


