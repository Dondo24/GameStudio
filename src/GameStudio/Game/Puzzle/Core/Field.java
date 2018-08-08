package GameStudio.Game.Puzzle.Core;

import java.util.Random;

public class Field {
	private Tile[][] tiles;
	private int rowCount;
	private int columnCount;
	private int movesCount;

	public Field(GameDifficulty gd) {
		this.rowCount = gd.getRowCount();
		this.columnCount = gd.getColumnCount();
		tiles = new Tile[rowCount][columnCount];
		generateAndShuffle();
		movesCount = 0;
	}

	public int getRowOfTile(int value) {
		int row = 0;
		for (int i = 0; i < tiles.length; i++) {
			for (int j = 0; j < tiles.length; j++) {
				Tile tile = tiles[i][j];
				if (tile.getValue() != 0) {
					if (tile.getValue() == value) {
						row = i;
						return row;
					}
				}
			}
		}

		return row;
	}

	public int getColumnOfTile(int value) {
		int column = 0;
		for (int i = 0; i < tiles.length; i++) {
			for (int j = 0; j < tiles.length; j++) {
				Tile tile = tiles[i][j];
				if (tile.getValue() != 0) {
					if (tile.getValue() == value) {
						column = j;
						return column;
					}
				}
			}
		}
		return column;
	}

	public int getColumnCount() {
		return columnCount;
	}

	public int getRowCount() {
		return rowCount;
	}

	public Tile getTile(int row, int column) {
		return tiles[row][column];
	}

	public void generateAndShuffle() {
		generate();
		shuffle();
	}

	private void generate() {

		int value = 1;
		for (int i = 0; i < tiles.length; i++) {
			for (int j = 0; j < tiles.length; j++) {
				tiles[i][j] = new Tile(value);
				value++;
			}
		}
		tiles[rowCount - 1][rowCount - 1].setValue(0);

	}

	private void shuffle() {
		int numberOfShuffles = rowCount * columnCount * 4;
		Random random = new Random();
		while (!(numberOfShuffles == 0)) {
			int row = random.nextInt(rowCount);
			int column = random.nextInt(columnCount);
			Tile tile = tiles[row][column];
			if (tile.getValue() != 0) {
				if (neighborIsBlank(row, column)) {
					move(row, column);
					numberOfShuffles--;
				}
			}
		}
		if (isSolved()) {
			shuffle();
		}

	}

	public boolean move(int row, int column) {

		if (isBlank(row - 1, column)) {
			changeTiles(row, column, row - 1, column);
			movesCount++;
			return true;
		}
		if (isBlank(row + 1, column)) {
			changeTiles(row, column, row + 1, column);
			movesCount++;
			return true;
		}
		if (isBlank(row, column - 1)) {
			changeTiles(row, column, row, column - 1);
			movesCount++;
			return true;
		}
		if (isBlank(row, column + 1)) {
			changeTiles(row, column, row, column + 1);
			movesCount++;
			return true;
		}
		return false;
	}

	private void changeTiles(int rowNumber, int columnNumber, int rowBlank, int columnBlank) {
		tiles[rowBlank][columnBlank].setValue(tiles[rowNumber][columnNumber].getValue());
		tiles[rowNumber][columnNumber].setValue(0);

	}

	private boolean isBlank(int row, int column) {
		return validIndex(row, column) && tiles[row][column].getValue() == 0;
	}

	private boolean validIndex(int row, int column) {
		return row >= 0 && row < rowCount && column >= 0 && column < columnCount;
	}

	public boolean neighborIsBlank(int row, int column) {
		return isBlank(row - 1, column) || isBlank(row + 1, column) || isBlank(row, column - 1)
				|| isBlank(row, column + 1);
	}

	public boolean isSolved() {
		Tile tile = tiles[rowCount - 1][columnCount - 1];
		if (tile.getValue() != 0)
			return false;
		int value = 1;
		for (int i = 0; i < tiles.length; i++) {
			for (int j = 0; j < tiles[0].length; j++) {
				if (i == tiles.length - 1 && j == tiles[0].length - 1) {
					return true;
				}
				tile = tiles[i][j];

				if (!(tile.getValue() == value)) {
					return false;
				}
				value++;

			}
		}
		return true;
	}

	public int getScore() {
		return rowCount * columnCount * 100 - movesCount;
	}

}
