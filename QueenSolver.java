package nqueens;
import java.awt.*;
import java.awt.event.*;
//import java.util.Scanner;
import javax.swing.*;
import nqueens.Queen;
import nqueens.QueenSolver;
class Queen {
	private int row;//We advance the queen through rows to get the solutions
	private int column;//immutable
	private Queen neighbor;//immutable
	Queen (int c, Queen n) {//initialize
		row = 1;
		column = c;
		neighbor = n;//each queen needs to know only about the queens to her immediate left
		}

	public boolean findSolution() {
/*		To find a solution, a queen simply asks its neighbors if they can attack. 
		If so,then the queen advances herself, if possible (returning failure if she cannot).
		When the neighbors indicate they cannot attack, a solution has been found.*/
		while (neighbor != null && neighbor.canAttack(row, column))
			if (! advance())
				return false;
		return true;
		}

	public boolean advance() {
		/*
		 * The procedure advance divides into two cases. 
		 * If we are not at the end, the queen simply advances the row value by 1. 
		 * Otherwise, she has tried all positions and not found a solution, 
		 * so nothing remains but to ask her neighbor for a new solution and start again from row 1.*/
		if (row < QueenSolver.input) {
			row++;
			return findSolution();
			}
		if (neighbor != null) {
			if (! neighbor.advance())
				return false;
			if (! neighbor.findSolution())
				return false;
			}
		else
			return false;
		row = 1;
		return findSolution();
			
		}

	private boolean canAttack(int testRow, int testColumn) {
		int columnDifference = testColumn - column;
		if ((row == testRow) ||
			(row + columnDifference == testRow) ||
			(row - columnDifference == testRow))
				return true;
		if (neighbor != null)
			return neighbor.canAttack(testRow, testColumn);
		return false;
		}

	public void paint (Graphics g) {
			// first draw neighbor,then draw ourself
		if (neighbor != null)
			neighbor.paint(g);
			// draw a Polygon in shape of a queen crown
			// x, y is upper left corner
		int x = (row - 1) * 50 + 15;
		int y = (column - 1) * 50 + 45;
        Polygon crown = new Polygon();
        crown.addPoint(x, y);
        crown.addPoint(x + 10, y + 10);
        crown.addPoint(x+20, y);
        crown.addPoint(x + 30, y+10);
        crown.addPoint(x+40, y);
        crown.addPoint(x + 30, y + 40);
        crown.addPoint(x+10, y + 40);
        g.drawPolygon(crown);
        g.fillPolygon(crown);
	}
}

@SuppressWarnings("serial")
public class QueenSolver extends JFrame implements ActionListener {
	  public static int input;
	  private Queen lastQueen = null;
      JButton b1,b2;
      JPanel panel = new JPanel();
      public void actionPerformed(ActionEvent e) {
             if(e.getSource()== b1 )
             {
            	lastQueen.advance();//we will find a solution to the entire puzzle by asking the right most queen to find an acceptable solution.
 				repaint();
             }
             else if (e.getSource()== b2 ){
            	 System.exit(0);
             }
      }
 /*     public void actionPerformed(ActionEvent e) {
          if(e.getSource()== b2 )
          {
        	  System.exit(0);
          }
   }*/
	  public static void main(String [] args) {
//		  JTextField field = new JTextField(10);
//		  container.add(field, BorderLayout.SOUTH);
		JFrame frame = new JFrame("Input Dialog Box");
		String name = JOptionPane.showInputDialog(frame, "Enter the size of Board", "InputBox",JOptionPane.PLAIN_MESSAGE);
		input=Integer.parseInt(name);
		
//		System.out.println("Enter the size of the Board: ");
//		Scanner scan = new Scanner(System.in);
//		input=scan.nextInt();//take n as an Input
//		scan.close();
		QueenSolver world = new QueenSolver();
		world.setVisible(true);
		}
	  
	  public QueenSolver() {						
		setTitle(input+" Queens");//sets title of the Window
		setSize(75*input, 75*input);//sets size of the Window
		for (int i = 1; i <= input; i++) {
			lastQueen = new Queen(i, lastQueen);
			lastQueen.findSolution();
		}
//		addMouseListener(new MouseKeeper());
		addWindowListener(new CloseQuit());
//		JButton b1;
		b2 = new JButton("Close");	
		b2.setBounds(150,50*input+70,100,20);
		b2.addActionListener(this);
		b1 = new JButton("Next");	
		b1.setBounds(20,50*input+70,100,20);
		b1.addActionListener(this);
		panel.setLayout(null);
		panel.setBounds(20,50*input+65,300,20);
		panel.add(b1);
		panel.add(b2);
		add(panel);
	}

	  public void paint(Graphics g) {
		  super.paint(g);
		  // draw board
		  for (int i = 0; i <= input; i++) {
			  g.drawLine(50 * i + 10, 40, 50*i + 10, 40+50*input);
			  g.drawLine(10, 50 * i + 40, 10+50*input, 50*i + 40);
		  }
//		  g.drawString("Click Mouse for Next Solution", 20, 50*input+70);
		  // draw queens
		  lastQueen.paint(g);
	  }

		private class CloseQuit extends WindowAdapter {//WindowsAdaptor:This class exists as convenience for creating listener objects.
			public void windowClosing (WindowEvent e) {
				System.exit(0);
			}
		}

//		private class MouseKeeper extends MouseAdapter {
//			public void mousePressed (MouseEvent e) {
//				lastQueen.advance();//we will find a solution to the entire puzzle by asking the right most queen to find an acceptable solution.
//				repaint();
//			}
//		}
}


