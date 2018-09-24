package GameStudio.Game.TicTacToe.core;

import java.util.Arrays;
import java.util.Random;

import org.springframework.web.servlet.config.TilesConfigurerBeanDefinitionParser;

public class Field {

	private Tile[][] tiles;
	private int rowCount;
	private int columnCount;
	private boolean player1Turn = true;
	private boolean isSolved = false;
	private int pocitadloColumn = 1;
	private int pocitadloRow = 1;
	private int numberOfMarked = 0;

	public Field() {
		this.rowCount = 3;
		this.columnCount = 3;
		tiles = new Tile[rowCount][columnCount];
		fill();
	}

	private void fill() {
		for (int i = 0; i < tiles.length; i++) {
			for (int j = 0; j < tiles.length; j++) {
				tiles[i][j] = new Tile(0);
			}
		}

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

	public void play(int r, int c) {
		if (player1Turn) {
			if (tiles[r][c].getValue() == 0) {
				tiles[r][c].setValue(1);
				numberOfMarked++;
				player1Turn = false;
				check(r, c);
			}
		} else {
			if (tiles[r][c].getValue() == 0) {
				tiles[r][c].setValue(2);
				numberOfMarked++;
				check(r, c);
				player1Turn = true;
			}
		}
	}

	public void playPC(int r, int c) {
		if (tiles[r][c].getValue() == 0) {
			tiles[r][c].setValue(1);
			numberOfMarked++;
			player1Turn = false;
		}
			if (!check(r, c)) {
				if (numberOfMarked != 9) {
					generateTurn();
					numberOfMarked++;
					player1Turn = true;			
				}
			}
		
	}

	public void generateTurn() {
		Random random = new Random();
		boolean tileIsEmpty = false;
		while (!tileIsEmpty) {
			System.out.println("hladam");
			int r = random.nextInt(3);
			int c = random.nextInt(3);
			if (tiles[r][c].getValue() == 0) {
				tileIsEmpty = true;
				tiles[r][c].setValue(2);
				check(r, c);
			}
		}
	}

	private boolean zLdP() {
		return (tiles[0][0].getValue() == tiles[1][1].getValue()) && (tiles[1][1].getValue() == tiles[2][2].getValue());
	}

	private boolean zPdL() {
		return (tiles[0][2].getValue() == tiles[1][1].getValue()) && (tiles[1][1].getValue() == tiles[2][0].getValue());
	}

	private boolean check(int r, int c) {
		if (tiles[1][1].getValue() != 0) {
			if (zLdP() || zPdL()) {
				isSolved = true;
				
				return true;
			}
		}
		pocitadloColumn = 1;
		pocitadloRow = 1;
		for (int i = 0; i < tiles.length - 1; i++) {
			if (tiles[r][i].getValue() == tiles[r][i + 1].getValue()) {
				pocitadloColumn++;
			} else {
				pocitadloColumn = 1;
			}
			if (tiles[i][c].getValue() == tiles[i + 1][c].getValue()) {
				pocitadloRow++;
			} else {
				pocitadloRow = 1;
			}
		}
		if (pocitadloColumn == 3 || pocitadloRow == 3) {
			
			isSolved = true;
			pocitadloColumn = 1;
			pocitadloRow = 1;
			return true;
		}
		return false;
	}

	public boolean isSolved() {
		return isSolved;
	}

	public boolean whoWin() {
		return player1Turn;
	}

	public boolean isDraw() {
		return numberOfMarked == 9;
	}


}