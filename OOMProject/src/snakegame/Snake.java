package snakegame;

import java.awt.EventQueue;

import javax.swing.JFrame;

public class Snake extends JFrame{
	
	public Snake() {
		initUI();
	}
	
	public void initUI() {
		add(new Board());
		setResizable(false); 
		pack(); //set window size according to requirement
		setTitle("Snake2D");
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		EventQueue.invokeLater(()->{
			new Snake().setVisible(true);
		});
	}

}
