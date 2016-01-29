import java.util.Collections;
import java.util.ArrayList;
import java.util.List;
import java.awt.Font;
import java.awt.BasicStroke;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Graphics;
import javax.swing.JComponent;

public class BoardComponent extends JComponent implements MouseListener, KeyListener {
	private Board boardRef;
	private Kelimezor kelimezorRef;
	private List<Solution> solutions;
	private int cursorRow = 0;
	private int cursorCol = 0;
	private int chosenSolutionIndex = -1;
	public static int cellSize = 35;		

	public BoardComponent (Board board, Kelimezor kelimezor) {
		boardRef = board;
		kelimezorRef = kelimezor;
		solutions = new ArrayList<Solution>();
	}
	public void paintComponent (Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		g2.setColor(Color.BLACK);
		g2.setFont(new Font("Arial", Font.PLAIN, 16));
		for (int row = 0; row < 15; row++) {
			for (int col = 0; col < 15; col++) {
				g2.drawRect(cellSize*col, cellSize*row, cellSize, cellSize);
				Cell cell = boardRef.getCell(row, col);
				if (cell.isOccupied()) {
					g2.setColor(Color.RED);
					g2.drawString(""+cell.getLetter(), 10 + cellSize*col, 20 + cellSize*row);
					g2.setColor(Color.BLACK);
				} else {
					if (cell.getSpecial() != Cell.Special.NONE) {
						g2.setColor(Color.GRAY);
						g2.drawString(cell.getSpecial().toString(), 10 + cellSize*col, 20 + cellSize*row);
						g2.setColor(Color.BLACK);
					}
				}
			}
		}
		g2.setColor(Color.BLUE);
		if (chosenSolutionIndex != -1) {
			Solution solution = solutions.get(chosenSolutionIndex);
			for (Solution.Modif2 mod : solution.getModifications()) {
				g2.drawString(""+mod.getLetter(), 10 + cellSize*mod.getCol(), 20 + cellSize*mod.getRow());
			}
		}
		int solX = cellSize * 15 + 5;
		int solY = 20;
		for (Solution s : solutions) {
			g2.drawString(s.toString(), solX, solY);
			solY += 15;
		}
		g2.setColor(Color.RED);
		g2.setStroke(new BasicStroke(5));
		g2.drawRect(cellSize*cursorCol, cellSize*cursorRow, cellSize, cellSize);
		g2.setStroke(new BasicStroke(1));
	}
	public void mouseClicked (MouseEvent e) {
		Point mousePos = e.getPoint();
		if (mousePos.x > cellSize*15) {
			if (solutions.isEmpty()) return;
			int index = mousePos.y / 16;
			if (index > solutions.size()) return;
			chosenSolutionIndex = index;
			repaint();
			return;
		}
		if (mousePos.y > cellSize*15) return;
		cursorCol = mousePos.x / cellSize;
		cursorRow = mousePos.y / cellSize;
		repaint();
	}
	public void keyPressed (KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_UP) {
			cursorRow--;
			if (cursorRow < 0) cursorRow = 14;
		} else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
			cursorRow++;
			if (cursorRow > 14) cursorRow = 0;
		} else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
			cursorCol--;
			if (cursorCol < 0) cursorCol = 14;
		} else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
			cursorCol++;
			if (cursorCol > 14) cursorCol = 0;
		} else if (e.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
			boardRef.clearLetter(cursorRow, cursorCol);
		} else if (e.getKeyCode() == KeyEvent.VK_ENTER) {
			solutions = kelimezorRef.solve();
			Collections.sort(solutions);
			chosenSolutionIndex = -1;
		}
		repaint();
	}
	public void keyTyped (KeyEvent e) {
		chosenSolutionIndex = -1;
		String allowed = "abcçdefgğhıijklmnoöprsştuüvyz";
		char letter = e.getKeyChar();
		if (allowed.indexOf(letter) == -1) return;
		boardRef.putLetter(cursorRow, cursorCol, letter);
		repaint();
	}

	public void mousePressed (MouseEvent e) {}
	public void mouseReleased (MouseEvent e) {}
	public void mouseEntered (MouseEvent e) {}
	public void mouseExited (MouseEvent e) {}
	public void keyReleased (KeyEvent e) {}
}