package GameStudio.Game.Pexeso.Core;

import java.util.Random;

import GameStudio.Service.score.ScoreService;
import GameStudio.Service.score.ScoreServiceFile;

public class Field {
	private int rowCount;
	private int columnCount;
	private Tile[][] tiles;
	private int movesCount;
	private int solvedTiles = 0;
	private int r1 = -1;
	private int c1 = -1;
	private int r2 = -1;
	private int c2 = -1;

	public Field(GameDifficulty gd) {
		this.rowCount = gd.getRowCount();
		this.columnCount = gd.getColumnCount();
		tiles = new Tile[rowCount][columnCount];
		generateValues();
		movesCount = 0;
	}

	public int getRowCount() {
		return rowCount;
	}

	public int getColumnCount() {
		return columnCount;
	}

	public Tile getTile(int row, int column) {
		return tiles[row][column];
	}

	private void generateValues() {

		int numberOfReamingSymbols = (rowCount * columnCount) / 2;
		Random random = new Random();
		while (numberOfReamingSymbols != 0) {
			int row1 = random.nextInt(rowCount);
			int row2 = random.nextInt(rowCount);
			int column1 = random.nextInt(columnCount);
			int column2 = random.nextInt(columnCount);
			if (!(row1 == row2 && column1 == column2)) {
				if (tiles[row1][column1] == null && tiles[row2][column2] == null) {
					tiles[row1][column1] = new Tile(numberOfReamingSymbols);
					tiles[row2][column2] = new Tile(numberOfReamingSymbols);
					numberOfReamingSymbols--;

				}
			}
		}

	}

	public void openTile(int r, int c) {
		if (getTile(r, c).getState().equals(TileState.CLOSED)) {
			getTile(r, c).setState(TileState.OPENED);

			if (isSecondTileOpened()) {
				getTile(r1, c1).setState(TileState.CLOSED);
				getTile(r2, c2).setState(TileState.CLOSED);
				r1 = r;
				c1 = c;
				r2 = -1;
				c2 = -1;
			} else if (isFirstTileOpened()) {
				r2 = r;
				c2 = c;
				Tile tile1 = getTile(r1, c1);
				Tile tile2 = getTile(r2, c2);
				if (tile1.getValue() == tile2.getValue()) {
					getTile(r1, c1).setState(TileState.SOLVED);
					getTile(r2, c2).setState(TileState.SOLVED);
					solvedTiles += 2;
					resetOpenTilePositions();

				}
			} else {
				r1 = r;
				c1 = c;

			}
		} 	
	}

	private boolean isFirstTileOpened() {
		return r1 != -1 && c1 != -1;
	}

	private boolean isSecondTileOpened() {
		return r2 != -1 && c2 != -1;
	}

	private void resetOpenTilePositions() {
		r1 = -1;
		c1 = -1;
		r2 = -1;
		c2 = -1;

	}

	public boolean isSolved() {
		return solvedTiles == rowCount * columnCount;

	}

	public int getScore() {
		return rowCount * columnCount - movesCount;
	}

}
