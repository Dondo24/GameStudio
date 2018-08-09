package GameStudio.Game.Puzzle.Core;
public enum GameDifficulty {
	EASY(2, 2), NORMAL(3, 3), HARD(4, 4), EXTREME(10, 10);

	private final int rowCount;

	private final int columnCount;

	public int getRowCount() {
		return rowCount;
	}

	public int getColumnCount() {
		return columnCount;
	}

	private GameDifficulty(int rowCount, int columnCount) {
		this.rowCount = rowCount;
		this.columnCount = columnCount;
	}
}
