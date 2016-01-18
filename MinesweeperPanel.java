import java.awt.event.*;
import java.awt.*;
import javax.swing.*;
import java.util.*;
import java.math.*;

//Board legend:
	//-8 = revealed to touch 8 bombs
	//-7 = revealed to touch 7 bombs
	//-6 = revealed to touch 6 bombs
	//-5 = revealed to touch 5 bombs
	//-4 = revealed to touch 4 bombs
	//-3 = revealed to touch 3 bombs
	//-2 = revealed to touch 2 bombs
	//-1 = revealed to touch 1 bomb
	//0 = no bomb, hidden to user
	//1 = bomb, hidden to user
	//2 = flagged bomb <<<<< This is going to need some adjusting because it needs to retain its bomb status
	//3 = bomb, touched and revealed to user (at game over)
	//4 = blank space, revealed to user (does not touch any bomb)

public class MinesweeperPanel extends Panel implements KeyListener, MouseListener
{
	boolean firstClick = true;

	int w = 32; //30 + 2
	int h = 18; // 16 + 2

	//Dimension (height/width) of each square
	int d = 20;
	int buffer = 500;
	int[][] board = new int[w][h];
	boolean minesVisible = false;

	public MinesweeperPanel()
	{
		addKeyListener(this);
		addMouseListener(this);
		placeMines();
	}

	public void placeMines()
	{
		int bombNumber = 99;

		for (int i = 0; i < bombNumber; i ++)
		{
			Random random = new Random();
			int x = random.nextInt(30) + 1;
			int y = random.nextInt(16) + 1;

			if (board[x][y] == 1)
			{
				i--;
			}
			else
			{
				board[x][y] = 1;
			}
		}
	}

	public int getBombCount(int x, int y)
	{
		int bombCount = 0;

		for (int i = -1; i <= 1; i ++)
		{
			for (int j = -1; j <= 1; j ++)
			{
				if (board[x + i][y + j] == 1)
				{
					bombCount ++;
				}
			}
		}

		return bombCount;
	}

	public void goodClick(int x, int y)
	{
		int bombCount = getBombCount(x, y);

		if (bombCount == 0)
		{
			paintClear(x, y);

			if (x > 1 && x < 30 && y > 1 && y < 16)
			{
				for (int i = -1; i <= 1; i ++)
				{
					for (int j = -1; j <= 1; j ++)
					{
						if (board[x + i][y + j] != 4)
						{
							goodClick(x + i, y + j);
						}
					}
				}
			}

			int i = -1;
			int j = -1;
			int iMax = 1;
			int jMax = 1;

			System.out.println("X: " + x);
			System.out.println("Y: " + y);

			if (x == 1)
			{
				i = 0;
			}

			if (x == 30)
			{
				iMax = 0;
			}

			if (y == 1)
			{
				j = 0;
			}

			if (y == 16)
			{
				jMax = 0;
			}

			System.out.println("i: " + i);
			System.out.println("iMax: " + iMax);
			System.out.println("j: " + j);
			System.out.println("jMax: " + jMax);


			for (int i1 = i; i1 <= iMax; i1 ++)
			{
				for (int j1 = j; j1 <= jMax; j1 ++)
				{
					if (board[x + i1][y + j1] != 4)
					{
						goodClick(x + i1, y + j1);
						System.out.println("Good click " + x + " " + y);
						System.out.println("i: " + i1 + " j: " + j1);
					}
				}
			}
		}

		else if (bombCount == 1)
		{
			paint1(x, y);
		}

		else if (bombCount == 2)
		{
			paint2(x, y);
		}

		else if (bombCount == 3)
		{
			paint3(x, y);
		}

		else if (bombCount == 4)
		{
			paint4(x, y);
		}

		else if (bombCount == 5)
		{
			paint5(x, y);
		}

		else if (bombCount == 6)
		{
			paint6(x, y);
		}

		else if (bombCount == 7)
		{
			paint7(x, y);
		}

		else if (bombCount == 8)
		{
			paint8(x, y);
		}

		repaint();

		//else if one or more bombs touches the spot
			//reveal the number of bombs that are touching the spot
	}

	public void paintClear (int x, int y)
	{
		board[x][y] = 4;
		System.out.println("PAINT CLEAR!");
	}

	public void paint1(int x, int y)
	{
		board[x][y] = -1;
	}
	public void paint2(int x, int y)
	{
		board[x][y] = -2;
	}
	public void paint3(int x, int y)
	{
		board[x][y] = -3;
	}
	public void paint4(int x, int y)
	{
		board[x][y] = -4;
	}
	public void paint5(int x, int y)
	{
		board[x][y] = -5;
	}
	public void paint6(int x, int y)
	{
		board[x][y] = -6;
	}
	public void paint7(int x, int y)
	{
		board[x][y] = -7;
	}
	public void paint8(int x, int y)
	{
		board[x][y] = -8;
	}

	public void clearBoard()
	{
		for (int x = 0; x < w; x ++)
		{
			for (int y = 0; y < h; y ++)
			{
				board[x][y] = 0;
			}
		}
	}

	public void paint (Graphics g)
	{

		//Making random mine distribution on board:
		//Later I should figure out how to randomize mines to make interesting patterns.
		//99 mines on a 16 x 30 board:

		//Creating lines through board.

		g.setColor(Color.BLUE);

		for (int i = 1; i < w; i ++)
		{
			g.drawLine(i*d, d, i*d, (h-1)*d);
		}

		for (int i = 1; i < h; i ++)
		{
			g.drawLine(d, i*d, (w-1)*d, i*d);
		}

		//Paints mines where they are on the board
		if (minesVisible)
		{

			for (int x = 0; x < w; x ++)
			{
				for (int y = 0; y < h; y ++)
				{
					if (board[x][y] == 1)
					{
						g.fillRect(x*d, y*d, d, d);
					}
				}
			}
		}

		for (int i = 1; i < w - 1; i ++)
		{
			for (int j = 1; j < h - 1; j ++)
			{
				int halfd = d/2;
				int quarterd = d/4;

				if (board[i][j] == 4)
				{

					g.setColor(Color.RED);
					g.fillRect(i*d, j*d, d, d);
					g.setColor(Color.BLACK);
					g.drawString("0", i*d + quarterd, (j+1)*d - quarterd);
				}
				if (board[i][j] == -1)
				{

					g.setColor(Color.ORANGE);
					g.fillRect(i*d, j*d, d, d);
					g.setColor(Color.BLACK);
					g.drawString("1", i*d + quarterd, (j+1)*d - quarterd);
				}
				if (board[i][j] == -2)
				{
					g.setColor(Color.GREEN);
					g.fillRect(i*d, j*d, d, d);
					g.setColor(Color.BLACK);
					g.drawString("2", i*d + quarterd, (j+1)*d - quarterd);
				}
				if (board[i][j] == -3)
				{
					g.setColor(Color.YELLOW);
					g.fillRect(i*d, j*d, d, d);
					g.setColor(Color.BLACK);
					g.drawString("3", i*d + quarterd, (j+1)*d - quarterd);
				}
				if (board[i][j] == -4)
				{

					g.setColor(Color.CYAN);
					g.fillRect(i*d, j*d, d, d);
					g.setColor(Color.BLACK);
					g.drawString("4", i*d + quarterd, (j+1)*d - quarterd);
				}
				if (board[i][j] == -5)
				{

					g.setColor(Color.MAGENTA);
					g.fillRect(i*d, j*d, d, d);
					g.setColor(Color.BLACK);
					g.drawString("5", i*d + quarterd, (j+1)*d - quarterd);
				}

				if (board[i][j] == -6)
				{

					g.setColor(Color.PINK);
					g.fillRect(i*d, j*d, d, d);
					g.setColor(Color.BLACK);
					g.drawString("6", i*d + quarterd, (j+1)*d - quarterd);
				}

				if (board[i][j] == -7)
				{

					g.setColor(Color.LIGHT_GRAY);
					g.fillRect(i*d, j*d, d, d);
					g.setColor(Color.BLACK);
					g.drawString("7", i*d + quarterd, (j+1)*d - quarterd);
				}

				if (board[i][j] == -8)
				{

					g.setColor(Color.DARK_GRAY);
					g.fillRect(i*d, j*d, d, d);
					g.setColor(Color.BLACK);
					g.drawString("8", i*d + quarterd, (j+1)*d - quarterd);
				}
			}
		}
	}

	public void mouseClicked (MouseEvent event)
	{

		int xClick = event.getX();
		int yClick = event.getY();
		boolean validFirstClick = false;
		System.out.println("Click. X: " + xClick + " Y: " + yClick);


		int x = xClick/d;
		int y = yClick/d;

		if (firstClick)
		{
			while (!validFirstClick)
			{
				System.out.println("CLEARED");
				clearBoard();
				placeMines();

				if (board[x][y] != 1)
				{
					if (getBombCount(x, y) == 0)
					{
						validFirstClick = true;
					}
				}
			}
		}

		if (board[x][y] == 1)
		{

			//End game
			//Reveal all bombs
			minesVisible = true;
			repaint();
		}

		if (board[x][y] == 0)
		{
			goodClick(x, y);
		}


		firstClick = false;
	}

	public void mouseExited (MouseEvent event)
	{

	}

	public void mouseEntered (MouseEvent event)
	{

	}

	public void mouseReleased (MouseEvent event)
	{

	}

	public void mousePressed (MouseEvent event)
	{

	}

	public void keyTyped (KeyEvent event)
	{

	}

	public void keyPressed (KeyEvent event)
	{

	}

	public void keyReleased (KeyEvent event)
	{

	}
}
