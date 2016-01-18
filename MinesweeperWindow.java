import java.awt.*;

public class MinesweeperWindow extends Frame
{
	MinesweeperPanel panel = new MinesweeperPanel() ;

	public MinesweeperWindow( )
	{
		setTitle ("Minesweeper");
		setSize	(700, 700);
		setLocation (100, 100);
		setResizable(true);
		add(panel);
		setVisible (true);
		this.addKeyListener(panel);
	}

}
