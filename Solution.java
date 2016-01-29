import java.util.ArrayList;
import java.util.List;

public class Solution implements Comparable<Solution> {
	public class Modif2 {
		private int row;
		private int col;
		private char letter;

		public Modif2(int row, int col, char letter) {
			this.row = row;
			this.col = col;
			this.letter = letter;
		}
		public int getRow () {
			return row;
		}
		public int getCol () {
			return col;
		}
		public char getLetter () {
			return letter;
		}
	}

	private List<Modif2> modifications;
	private List<String> causedWords;
	private String word;
	private int points = 0;

	public Solution (Pattern pattern, int secondIndex, boolean isRow, List<String> causedWords, Board board) {
		this.causedWords = causedWords;
		modifications = new ArrayList<Modif2>();
		word = pattern.extractCreatedWord();
		for (Modification mod : pattern.getModifications()) {
			if (isRow) {
				modifications.add(new Modif2(secondIndex, mod.getIndex(), mod.getLetter()));
			} else {
				modifications.add(new Modif2(mod.getIndex(), secondIndex, mod.getLetter()));
			}
		}
		points = calculatePoints(board);
	}
	public int calculatePoints (Board board) {
		int points = 0;
		int wordMult = 1;
		int star = 0;
		StringBuilder oldWord = new StringBuilder(getWord());
		for (Solution.Modif2 mod : getModifications()) {
			int letterPoint = Board.pointLookup.get(mod.getLetter());
			Cell cell = board.getCell(mod.getRow(), mod.getCol());
			oldWord.deleteCharAt(oldWord.indexOf(""+mod.getLetter()));

			if (cell.getSpecial() == Cell.Special.H2) {
				letterPoint *= 2;
			} else if (cell.getSpecial() == Cell.Special.H3) {
				letterPoint *= 3;
			} else if (cell.getSpecial() == Cell.Special.K2) {
				wordMult *= 2;
			} else if (cell.getSpecial() == Cell.Special.K3) {
				wordMult *= 3;
			} else if (cell.getSpecial() == Cell.Special.STAR) {
				star = 25;
			}
			points += letterPoint;
		}
		points = points * wordMult + star;
		for (String causedWord : getCausedWords()) {
			for (int c = 0; c < causedWord.length(); c++) {
				points += Board.pointLookup.get(causedWord.charAt(c));
			}
		}
		for (int c = 0; c < oldWord.length(); c++) {
			points += Board.pointLookup.get(oldWord.charAt(c));
		}
		return points;
	}
	@Override
	public int compareTo (Solution b) {
		Integer p = (Integer) b.getPoints();
		return p.compareTo(points);
	}
	public String getWord () {
		return word;
	}
	public int getPoints () {
		return points;
	}
	public List<Modif2> getModifications () {
		return modifications;
	}
	public List<String> getCausedWords () {
		return causedWords;
	}
	@Override
	public String toString () {
		return word + ": " + points;
	}
}