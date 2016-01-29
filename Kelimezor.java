import javax.swing.JOptionPane;
import java.awt.Dimension;
import javax.swing.JFrame;
import java.io.File;
import java.util.List;
import java.util.HashSet;
import java.util.Set;

public class Kelimezor {
	private Board board;
	private Solver solver;
	private JFrame frame;
	private BoardComponent component;

	public Kelimezor () {
		board = new Board();
		solver = new Solver(board);
		component = new BoardComponent(board, this);
		frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(component);
		component.setPreferredSize(new Dimension(600, 600));
		frame.getContentPane().addMouseListener(component);
		frame.getContentPane().addKeyListener(component);
		frame.getContentPane().setFocusable(true);
		frame.requestFocusInWindow();
		frame.pack();
		frame.setVisible(true);
	}
	public List<Solution> solve () {
		String hand = JOptionPane.showInputDialog(frame, "Your hand? (eg. abcd)").toLowerCase();

		Set<String> permutations = new HashSet<String>();
		for (int i = 1; i <= hand.length(); i++) {
			permutations.addAll(Permutator.permutate(i, hand));
		}
		List<Solution> solutions = solver.solveHorizontal(permutations);
		solutions.addAll(solver.solveVertical(permutations));
		return solutions;
	}
}
