import java.awt.Color;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.Timer;

import acm.graphics.GLabel;
import acm.graphics.GObject;
import acm.graphics.GOval;
import acm.graphics.GRect;
import acm.program.GraphicsProgram;
import acm.util.RandomGenerator;

public class enemyship extends GraphicsProgram implements ActionListener{
	//private ArrayList <GRect> enemies;
	private Timer movement;
	private RandomGenerator rgen;
	
	public static final int SIZE = 25;
	public static final int SPEED = 2;
	public static final int MS = 50;
	public static final int WINDOW_HEIGHT = 600;
	public static final int WINDOW_WIDTH = 800;
	private int numTimes = -1;
	public Boolean right = true;
	private int x, y;	
	private GRect enemy;
	
	public enemyship(int x, int y) { //location of the ship
		this.x=x;
		this.y=y;
	}
	
	public void run() {
		rgen = RandomGenerator.getInstance();
		movement = new Timer(MS, this);
		enemy = new GRect(x-SIZE/2, y, SIZE, SIZE);
		enemy.setColor(new Color(0,255, 0));
		enemy.setFilled(true);
		add(enemy);
		movement.start();
		addMouseListeners();
	}
	
	public void actionPerformed(ActionEvent e) {
		numTimes ++;
		moveEnemy();
	}
	/*
	private void addAnEnemy() {
			GRect e = makeEnemy();
			add(e);
			//moveEnemy(e);
	}
	
	public GRect makeEnemy() {
		GRect temp = new GRect(x-SIZE/2, y, SIZE, SIZE);
		temp.setColor(new Color(0,255,0));
		temp.setFilled(true);
		return temp;
	}
	*/
	
	
	private void moveEnemy() {
		if(numTimes == 5) {
			if(right) {
				right = false;
				numTimes =0;
			}
			else if(!right) {
				right = true;
				numTimes = 0;
			}
		}
		if(right) {
			enemy.move(2, 0);		
		}
		if(!right) {
			enemy.move(-2, 0);
		}
	}
	
	public void init() {
		setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
	}
	
	public static void main(String args[]) {
		new enemyship(20,50).start();
		
	}
}
//add kill enemy function
//change arraylist to single and make arraylist in main
