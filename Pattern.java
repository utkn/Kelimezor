import java.util.List;
import java.util.ArrayList;

public class Pattern {
	private StringBuilder pattern;
	private List<Modification> modifications;

	public Pattern (Cell[] cellArray) {
		pattern = new StringBuilder(cellArray.length);
		modifications = new ArrayList<Modification>();

		for (int i = 0; i < cellArray.length; i++) {
			if (cellArray[i].isOccupied()) {
				pattern.append(cellArray[i].getLetter());
			} else {
				pattern.append('_');
			}
		}
	}
	public Pattern (String preMadePattern) {
		pattern = new StringBuilder(preMadePattern);
		modifications = new ArrayList<Modification>();
	}
	public void fill (int index, char letter) {
		modifications.add(new Modification(index, letter));
		pattern.setCharAt(index, letter);
	}
	public List<Pattern> apply (String piece) {
		List<Integer> slotIndexes = new ArrayList<Integer>();
		for (int i = 0; i < pattern.length(); i++) {
			if (!isFilled(i)) {
				slotIndexes.add(i);
			}
		}
		List<Pattern> newPatterns = new ArrayList<Pattern>();
		if (piece.length() > slotIndexes.size()) return newPatterns; // Return an empty list.

		for (int i = 0; i < slotIndexes.size(); i++) {
			int slot = slotIndexes.get(i);
			if (slot + piece.length() > pattern.length()) {
				break; // No need to check for next slots. Its too big already.
			}
			Pattern tmp = new Pattern(pattern.toString());
			for (int j = 0; j < piece.length(); j++) {
				if (i+j > slotIndexes.size() - 1) break;
				int currSlot = slotIndexes.get(i+j);
				tmp.fill(currSlot, piece.charAt(j));
			}
			if (tmp.wordCount() > wordCount()) {
				continue;
			}
			newPatterns.add(tmp);
		}
		return newPatterns;
	}
	public char getLetter (int index) {
		return pattern.charAt(index);
	}
	public boolean isFilled (int index) {
		return (pattern.charAt(index) != '_');
	}
	public List<Modification> getModifications () {
		return modifications;
	}
	public String extractCreatedWord () {
		if (modifications.isEmpty()) return "";
		return extractWord(modifications.get(0).getIndex());
	}
	public String extractWord (int index) {
		if (!isFilled(index)) return "";
		StringBuilder word = new StringBuilder(""+getLetter(index));
		int begin = index - 1;
		while (begin >= 0 && isFilled(begin)) {
			word.insert(0, getLetter(begin));
			begin--;
		}
		int end = index + 1;
		while (end <= 14 && isFilled(end)) {
			word.append(getLetter(end));
			end++;
		}
		return word.toString();
	}
	public int wordCount () {
		String[] wordPieces = pattern.toString().split("_");
		int count = 0;
		for (String word : wordPieces) {
			if (!word.isEmpty()) count++;
		}
		return count;
	}
	@Override
	public String toString () {
		return pattern.toString();
	}
}