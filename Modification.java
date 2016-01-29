public class Modification {
	private int index;
	private char letter;

	public Modification (int index, char letter) {
		this.index = index;
		this.letter = letter;
	}
	public int getIndex () {
		return index;
	}
	public char getLetter () {
		return letter;
	}
}