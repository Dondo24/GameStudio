package GameStudio.Game.MineSweeper.Core;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import GameStudio.Game.MineSweeper.Core.*;


public class Field {

	private final int rowCount;
	private final int columnCount;
	private final int mineCount;
	private final Tile[][] tiles;
	private GameState state = GameState.PLAYING;
	private Long startTime;
	

	public Field(GameDifficulty gd) {
		startTime = System.currentTimeMillis();
		this.rowCount = gd.getRowCount();
		this.columnCount = gd.getColumnCount();
		this.mineCount = gd.getMinesCount();
		tiles = new Tile[rowCount][columnCount];
	

		generate();
	}

	public void generate() {
		generateMines();
		fillWithClues();

	}
	



	private void generateMines() {
	
		Random random = new Random();
		int minesToSet = mineCount;
		while (minesToSet > 0) {
			int row = random.nextInt(rowCount);
			int column = random.nextInt(columnCount);
			if ((tiles[row][column] == null)) {
				tiles[row][column] = new Mine();
				
				minesToSet--;
			}
		}
		
	}

	public boolean isSolved() {
		int numberOfOpened = 0;
		for (int i = 0; i < rowCount; i++) {
			for (int j = 0; j < columnCount; j++) {
				if (tiles[i][j].getState().equals(TileState.OPENED)) {
					numberOfOpened++;
				}
			}
		}
		return rowCount * columnCount - mineCount == numberOfOpened;

	}

	private int sumAround(int row, int column) {
		int sum = 0;

		if (isMine(row - 1, column - 1))
			sum++;

		if (isMine(row - 1, column))
			sum++;

		if (isMine(row - 1, column + 1))
			sum++;

		if (isMine(row, column - 1))
			sum++;

		if (isMine(row, column + 1))
			sum++;

		if (isMine(row + 1, column - 1))
			sum++;

		if (isMine(row + 1, column))
			sum++;

		if (isMine(row + 1, column + 1))
			sum++;

		return sum;
	}

	public boolean validIndex(int row, int column) {
		return row >= 0 && row < rowCount && column >= 0 && column < columnCount;
	}

	public boolean isMine(int row, int column) {
		return validIndex(row, column) && getTile(row, column) instanceof Mine;
	}

	private void fillWithClues() {
		for (int i = 0; i < tiles.length; i++) {
			for (int j = 0; j < tiles[0].length; j++) {
				if (!(tiles[i][j] instanceof Mine)) {
					tiles[i][j] = new Clue(sumAround(i, j));
				}
			}
		}

	}
	public void openTile(int row,int column) {
		openTile(row, column,true);
	}

	public void openTile(int row, int column,boolean save) {
		Tile tile = tiles[row][column];
		if ((tile.getState().equals(TileState.CLOSED))) {
			tile.setState(TileState.OPENED);
			
			if (tile instanceof Mine) {
				state = GameState.FAILED;
				return;
			}
			if (tile instanceof Clue && ((Clue) tile).getValue() == 0) {
				openAround(row, column);
			}
			if (isSolved()) {
				state = GameState.SOLVED;
			}
		}

	}

	public void markTile(int row, int column) {
		Tile tile = getTile(row, column);
		if (tile.getState().equals(TileState.CLOSED)) {
			tile.setState(TileState.MARKED);
			
		} else {
			if (tile.getState().equals(TileState.MARKED)) {
				tile.setState(TileState.CLOSED);
				
			}
		}

	}

	public int getRowCount() {
		return rowCount;
	}

	public int getColumnCount() {
		return columnCount;
	}

	public int getMineCount() {
		return mineCount;
	}

	public GameState getState() {
		return state;
	}

	public void setState(GameState state) {
		this.state = state;
	}

	public Tile getTile(int row, int column) {
		return tiles[row][column];
	}

	public void openAround(int row, int column) {
		if (validIndex(row - 1, column - 1))
			openTile(row - 1, column - 1,false);

		if (validIndex(row - 1, column))
			openTile(row - 1, column,false);

		if (validIndex(row - 1, column + 1))
			openTile(row - 1, column + 1,false);

		if (validIndex(row, column - 1))
			openTile(row, column - 1,false);

		if (validIndex(row, column + 1))
			openTile(row, column + 1,false);

		if (validIndex(row + 1, column - 1))
			openTile(row + 1, column - 1,false);

		if (validIndex(row + 1, column))
			openTile(row + 1, column,false);

		if (validIndex(row + 1, column + 1))
			openTile(row + 1, column + 1,false);

	}

	public int getScore() {
		return (int) (mineCount * 100 - (startTime - System.currentTimeMillis()) / 1000);
	}

}
