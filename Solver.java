import java.io.File;
import java.util.Scanner;
import java.util.Set;
import java.util.ArrayList;
import java.util.List;

public class Solver {
	private static List<String> wordList = new ArrayList<String>();
	static {
		try {
			Scanner scanner = new Scanner(new File("list.txt"));
			while (scanner.hasNextLine()) {
				wordList.add(scanner.nextLine());
			}
		} catch (Exception e) {
			System.out.println(e);
			System.exit(0);
		}
	}
	private Board boardRef;

	public Solver (Board board) {
		boardRef = board;
	}
	public List<Solution> solveHorizontal (Set<String> permutations) {
		List<Solution> horSolutions = new ArrayList<Solution>();
		for (int row = 0; row < 15; row++) {
			Pattern rowPattern = new Pattern(boardRef.getRow(row));
			for (String perm : permutations) {
				List<Pattern> createdPatterns = rowPattern.apply(perm);
				if (createdPatterns.isEmpty()) continue;
				for (Pattern createdPattern : createdPatterns) {
					String createdWord = createdPattern.extractCreatedWord();
					if (!checkWord(createdWord)) continue;

					List<String> causedWords = new ArrayList<String>();
					boolean correct = true;
					List<Modification> modifications = createdPattern.getModifications();
					for (Modification mod : modifications) {
						// Check columns for newly created word.
						Pattern tmpColPattern = new Pattern(boardRef.getCol(mod.getIndex()));
						tmpColPattern.fill(row, mod.getLetter());
						String causedWord = tmpColPattern.extractCreatedWord();
						if (causedWord.length() == 1) continue;
						if (!checkWord(causedWord)) {
							correct = false;
							break;
						}
						causedWords.add(causedWord);
					}
					if (correct) {
						horSolutions.add(new Solution(createdPattern, row, true, causedWords, boardRef));
					}
				}
			}
		}
		return horSolutions;
	}
	public List<Solution> solveVertical (Set<String> permutations) {
		List<Solution> verSolutions = new ArrayList<Solution>();
		for (int col = 0; col < 15; col++) {
			Pattern colPattern = new Pattern(boardRef.getCol(col));
			for (String perm : permutations) {
				List<Pattern> createdPatterns = colPattern.apply(perm);
				if (createdPatterns.isEmpty()) continue;
				for (Pattern createdPattern : createdPatterns) {
					String createdWord = createdPattern.extractCreatedWord();
					if (!checkWord(createdWord)) continue;

					List<String> causedWords = new ArrayList<String>();
					boolean correct = true;
					List<Modification> modifications = createdPattern.getModifications();
					for (Modification mod : modifications) {
						// Check rows for newly created word.
						Pattern tmpRowPattern = new Pattern(boardRef.getRow(mod.getIndex()));
						tmpRowPattern.fill(col, mod.getLetter());
						String causedWord = tmpRowPattern.extractCreatedWord();
						if (causedWord.length() == 1) continue;
						if (!checkWord(causedWord)) {
							correct = false;
							break;
						}
						causedWords.add(causedWord);
					}
					if (correct) {
						verSolutions.add(new Solution(createdPattern, col, false, causedWords, boardRef));
					}
				}
			}
		}
		return verSolutions;
	}
	public static boolean checkWord (String word) {
		for (String legalWord : wordList) {
			if (legalWord.equals(word)) return true;
		}
		return false;
	}
}