package GameStudio.Game.Pexeso.Core;
import java.util.Random;

import GameStudio.Service.ScoreService;
import GameStudio.Service.ScoreServiceFile;


public class Field {
	private int rowCount;
	private int columnCount;
	private Tile[][] tiles;
	private int movesCount;
	private int solvedTiles = 0;
	
	public Field(int rowCount, int columnCount) {
		this.rowCount = rowCount;
		this.columnCount = columnCount;
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

	public void openTile(int row, int column) {
		Tile tile = tiles[row][column];
		if (tile.getState().equals(TileState.CLOSED)) {
			tile.setState(TileState.OPENED);
			movesCount++;
		}

	}
	public void closeTile(int row, int column) {
		Tile tile = tiles[row][column];
		if (tile.getState().equals(TileState.OPENED)) {
			tile.setState(TileState.CLOSED);
			movesCount--;
		}

	}

	public void checkTiles(int row1, int column1, int row2, int column2) {
		Tile tile1 = tiles[row1][column1];
		Tile tile2 = tiles[row2][column2];
		if(tile1.getState().equals(TileState.OPENED)&&tile2.getState().equals(TileState.OPENED))
		if (tile1.getValue() == tile2.getValue()) {
			tile1.setState(TileState.SOLVED);
			solvedTiles++;
			tile2.setState(TileState.SOLVED);
			solvedTiles++;

		} else {
			tile1.setState(TileState.CLOSED);
			tile2.setState(TileState.CLOSED);
		}
	}

	public boolean isSolved() {
		return solvedTiles == rowCount * columnCount;

	}
	public int getScore() {
		return rowCount * columnCount * 10 - movesCount;
	}


}
