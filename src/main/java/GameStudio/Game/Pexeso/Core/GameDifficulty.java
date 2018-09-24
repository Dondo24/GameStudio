package GameStudio.Game.Pexeso.Core;

public enum GameDifficulty {
EASY(2,2),NORMAL(4,4),HARD(6,6);
	
	
	private final int rowCount;
	private final int columnCount;
	
	public int getColumnCount() {
		return columnCount;
	}
	public int getRowCount() {
		return rowCount;
	}
	
	private GameDifficulty(int rowCount,int columnCount) {
		this.rowCount = rowCount;
		this.columnCount= columnCount;
	}
	
}
