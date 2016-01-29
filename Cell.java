public class Cell {
	public enum Special {
		NONE, H2, H3, K2, K3, STAR;
		@Override
		public String toString () {
			switch(this) {
				case H2:
					return "H2";
				case H3:
					return "H3";
				case K2:
					return "K2";
				case K3:
					return "K3";
				case STAR:
					return ":)";
				default:
					return "  ";
			}
		}
	}
	private char letter = ' ';
	private boolean occupied = false;
	private Special special = Special.NONE;

	public void setLetter (char letter) {
		this.letter = Character.toLowerCase(letter);
		occupied = true;
	}
	public void clear () {
		letter = ' ';
		occupied = false;
	}
	public void setSpecial (Special special) {
		this.special = special;
	}
	public char getLetter () {
		return letter;
	}
	public Special getSpecial () {
		return special;
	}
	public boolean isOccupied () {
		return occupied;
	}
}