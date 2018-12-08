package snakegame;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Board extends JPanel implements KeyListener,ActionListener{
	
	private int[] snakexlength=new int[900]; //max length of snake can be 900
	private int[] snakeylength=new int[900];
	private int snakelength=3;
	private int delay=100;	//used to control speed of snake
	
	private int ax,ay;
	
	private boolean left=false;
	private boolean right=true;
	private boolean up=false;
	private boolean down=false;
	
	private boolean GameOn=true;
	
	private Timer timer;
	private Image apple,dot,head;
	
	public Board() {
		initializeBoard();
	}
	
	public void initializeBoard() {
		addKeyListener(this);
		setBackground(Color.white);
		setFocusable(true);
		setPreferredSize(new Dimension(300,300)); //set size of JPanel
		loadImages();
		initializeGame();
	}
	
	public void initializeGame() {
		//setting the initial coordinates of body of snake
		for (int z = 0; z < snakelength; z++) {
            snakexlength[z] = 50 - z * 10;
            snakeylength[z] = 50;
        }
		spawnApple();
		timer=new Timer(delay,this);
		timer.start();
	}
	
	public void loadImages() {
		ImageIcon a=new ImageIcon("src/snake/apple.png");
		apple=a.getImage();
		ImageIcon d=new ImageIcon("src/snake/dot.png");
		dot=d.getImage();
		ImageIcon h=new ImageIcon("src/snake/head.png");
		head=h.getImage();
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		drawOnCanvas(g);
	}
	
	public void drawOnCanvas(Graphics g) {
		if(GameOn) {
			String s1=Integer.toString(snakelength-3);
			g.setColor(Color.black);
			g.drawString("Score :"+s1, 10, 10);
			g.drawImage(apple,ax,ay,this);
			for(int u=0;u<snakelength;u++) {
				if(u==0) {
					g.drawImage(head,snakexlength[u],snakeylength[u],this);
				}
				else {
					g.drawImage(dot,snakexlength[u],snakeylength[u],this);
				}
			}
			Toolkit.getDefaultToolkit().sync();//synchronizes graphics,helps to make animation smooth
		}
		else {
			gameOver(g);
		}
	}
	//displays game over string
	public void gameOver(Graphics g) {
		String s="Game Over";
		String s1=Integer.toString(snakelength-3);
		g.setColor(Color.black);
		g.drawString(s, 120, 150);
		g.drawString("Score :"+s1, 130, 170);
		g.drawString("Press SPACE to restart", 100, 190);
	}
	
	//checks if snake has eaten the apple
	private void checkApple() {
		if((snakexlength[0]==ax)&&(snakeylength[0]==ay)) {
			snakelength++;
			spawnApple();
		}
	}
	
	//generates apple in a random place in canvas
	private void spawnApple() {
		int j=(int)(Math.random()*29); //constants 29 and 10 are used because size of JPanel is 300x300
		ax=j*10;
		int k=(int)(Math.random()*29);
		ay=k*10;
	}
	//checks if snake has bitten itself
	private void checkCollision() {
		for(int u=1;u<snakelength;u++) {
			if((snakexlength[0]==snakexlength[u]) && (snakeylength[0]==snakeylength[u])){
				GameOn=false;
			}
		}
	}
	//moves the snake, if snake move out-of-bounds from one side than it emerges from other side
	private void move() {
		for(int u=snakelength-1;u>0;u--) {
			snakexlength[u]=snakexlength[u-1];
			snakeylength[u]=snakeylength[u-1];
		}
		if(left) {
			snakexlength[0]-=10;		//size of each dot of snake and apple is 10x10
			if(snakexlength[0]<0) {
				snakexlength[0]=300;
			}
		}
		if(right) {
			snakexlength[0]+=10;
			if(snakexlength[0]>300) {
				snakexlength[0]=0;
			}
		}
		if(up) {
			snakeylength[0]+=10;
			if(snakeylength[0]>300) {
				snakeylength[0]=0;
			}
		}
		if(down) {
			snakeylength[0]-=10;
			if(snakeylength[0]<0) {
				snakeylength[0]=300;
			}
		}
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		if(GameOn) {
			checkApple();
			checkCollision();
			move();
		}
		repaint();
	}
	 @Override
     public void keyPressed(KeyEvent e) {

         int key = e.getKeyCode();

         if ((key == KeyEvent.VK_LEFT) && (!right)) {
             left = true;
             up = false;
             down = false;
         }

         if ((key == KeyEvent.VK_RIGHT) && (!left)) {
             right = true;
             up = false;
             down = false;
         }

         if ((key == KeyEvent.VK_DOWN) && (!down)) { //ideally it should be VK_UP ,but changed because of invert controls found during testing
             up = true;
             right = false;
             left = false;
         }

         if ((key == KeyEvent.VK_UP) && (!up)) {//ideally it should be VK_DOWN ,but changed because of invert controls found during testing
             down = true;
             right = false;
             left = false;
         }
         if((key==KeyEvent.VK_SPACE)) {//is SPACE is pressed game is restarted with initial score
        	 snakelength=3;
        	 GameOn=true;
         }
     }

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
}
