package GameStudio.Game.MineSweeper.Core;

public enum GameDifficulty {
	EASY(12, 12,6), NORMAL(12, 12, 12), HARD(12,12,24), EXTREME(9, 9,25);

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
