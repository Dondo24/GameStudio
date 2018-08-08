package GameStudio.Game.MineSweeper.Core;

public enum GameDifficulty {
	EASY(3, 3,1), NORMAL(5, 5, 4), HARD(9,9,9), EXTREME(9, 9,25);

	private final int rowCount;

	private final int columnCount;
	private final int minesCount;
	
	
	public int getRowCount() {
		return rowCount;
	}

	public int getColumnCount() {
		return columnCount;
	}

	public int getMinesCount() {
		return minesCount;
	}
	private GameDifficulty(int rowCount, int columnCount,int minesCount) {
		this.rowCount = rowCount;
		this.columnCount = columnCount;
		this.minesCount = minesCount;
	}
	
}
