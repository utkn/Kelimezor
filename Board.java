import java.util.Map;
import java.util.HashMap;

public class Board {
	public static final Map<Character,Integer> pointLookup = new HashMap<Character,Integer>();
	static {
		pointLookup.put('a', 1);
    pointLookup.put('b', 3);
    pointLookup.put('c', 4);
    pointLookup.put('ç', 4);
    pointLookup.put('d', 3);
    pointLookup.put('e', 1);
    pointLookup.put('f', 7);
    pointLookup.put('g', 5);
    pointLookup.put('ğ', 8);
    pointLookup.put('h', 5);
    pointLookup.put('ı', 2);
    pointLookup.put('i', 1);
    pointLookup.put('j', 10);
    pointLookup.put('k', 1);
    pointLookup.put('l', 1);
    pointLookup.put('m', 2);
    pointLookup.put('n', 1);
    pointLookup.put('o', 2);
    pointLookup.put('ö', 7);
    pointLookup.put('p', 5);
    pointLookup.put('r', 1);
    pointLookup.put('s', 2);
    pointLookup.put('ş', 4);
    pointLookup.put('t', 1);
    pointLookup.put('u', 2);
    pointLookup.put('ü', 3);
    pointLookup.put('v', 7);
    pointLookup.put('y', 3);
    pointLookup.put('z', 4);
	}
	private Cell[][] grid;

	public Board () {
		grid = new Cell[15][15];
		for (int row = 0; row < 15; row++) {
			for (int col = 0; col < 15; col++) {
				grid[row][col] = new Cell();
			}
		}
		grid[0][2].setSpecial(Cell.Special.K3);
    grid[0][5].setSpecial(Cell.Special.H2);
    grid[0][9].setSpecial(Cell.Special.H2);
    grid[0][12].setSpecial(Cell.Special.K3);
    
    grid[1][1].setSpecial(Cell.Special.H3);
    grid[1][6].setSpecial(Cell.Special.H2);
    grid[1][8].setSpecial(Cell.Special.H2);
    grid[1][13].setSpecial(Cell.Special.H3);
    
    grid[2][0].setSpecial(Cell.Special.K3);
    grid[2][7].setSpecial(Cell.Special.K2);
    grid[2][14].setSpecial(Cell.Special.K3);
    
    grid[3][3].setSpecial(Cell.Special.K2);
    grid[3][11].setSpecial(Cell.Special.K2);
    
    grid[4][4].setSpecial(Cell.Special.H3);
    grid[4][10].setSpecial(Cell.Special.H3);
    
    grid[5][0].setSpecial(Cell.Special.H2);
    grid[5][5].setSpecial(Cell.Special.H2);
    grid[5][9].setSpecial(Cell.Special.H2);
    grid[5][14].setSpecial(Cell.Special.H2);
    
    grid[6][1].setSpecial(Cell.Special.H2);
    grid[6][6].setSpecial(Cell.Special.H2);
    grid[6][8].setSpecial(Cell.Special.H2);
    grid[6][13].setSpecial(Cell.Special.H2);
    
    grid[7][2].setSpecial(Cell.Special.K2);
    grid[7][12].setSpecial(Cell.Special.K2);
    
    for (int row = 8; row < 15; row++) {
      Cell[] mirrorRow = grid[7 - (row - 7)];
      for (int col = 0; col < 15; col++) {
        if (mirrorRow[col].getSpecial() != Cell.Special.NONE) {
          grid[row][col].setSpecial(mirrorRow[col].getSpecial());
        }
      }
    }
	}
	public Cell[] getRow (int row) {
		return grid[row];
	}
	public Cell[] getCol (int col) {
		Cell[] column = new Cell[15];
		for (int row = 0; row < 15; row++) {
			column[row] = grid[row][col];
		}
		return column;
	}
	public Cell getCell (int row, int col) {
		return grid[row][col];
	}
	public void putLetter (int row, int col, char letter) {
		grid[row][col].setLetter(letter);
	}
	public void clearLetter (int row, int col) {
		grid[row][col].clear();
	}
	public void putStar (int row, int col) {
		grid[row][col].setSpecial(Cell.Special.NONE);
	}
	@Override
	public String toString () {
		StringBuilder s = new StringBuilder();
		for (int row = 0; row < 15; row++) {
			s.append('[');
			for (int col = 0; col < 15; col++) {
				s.append("|" + grid[row][col].getLetter() + "|");
			}
			s.append("]\n");
		}
		return s.toString();
	}
}