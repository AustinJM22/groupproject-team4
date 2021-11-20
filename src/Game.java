import java.awt.Color;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.Timer;

import acm.graphics.GLabel;
import acm.graphics.GObject;
import acm.graphics.GOval;
import acm.graphics.GRect;
import acm.program.GraphicsProgram;
import acm.util.RandomGenerator;

public class Game extends GraphicsProgram implements ActionListener{
	private ArrayList <enemyship> enemies = new ArrayList<enemyship>();
	public static final int WINDOW_HEIGHT = 600;
	public static final int WINDOW_WIDTH = 800;
	public static final int SIZE = 25;
	//private static final int SPEED = 5;
	enemyship enemyShip;
	enemyship bossShip;
	PlayerShip playerShip;
	
	
	public void init() {
		setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
	}
	
	public void run() {
		//addEnemies();
		//moveEnemies();
		addBoss();
		moveBoss();
		makePlayerShip();
		//playerShip.addKeyListeners();
	}
	
	//Function Adds Two Rows of Enemies to An arraylist
	private void addEnemies() {
		for(int i = SIZE; i < 800/*WINDOW_WIDTH-SIZE/2*/; i += 50) {
			enemyShip = new enemyship(shipType.ENEMYSHIP, i, 25, this);
			enemies.add(enemyShip);
		}
		for(int i = SIZE+150; i < 650; i+=50) {
			enemyShip = new enemyship(shipType.ENEMYSHIP, i, 75, this);
			enemies.add(enemyShip);
		}
	}
	//Function Looks into the Arraylist and places the enemies onto the screen
	private void moveEnemies() {
		for(enemyship e:enemies) {
			e.makeEnemy();
		}
	}
	private void addBoss() {
		bossShip = new enemyship(shipType.BOSSSHIP, 20, 50, this);
	}
	private void moveBoss() {
		bossShip.makeBoss();
	}
	
	//Calls the PlayerShip class to create and add playerShip onto the screen
	private void makePlayerShip() {
		playerShip = new PlayerShip(this);
		playerShip.makePlayerShip();
		addKeyListeners();
	}
	
	//KeyListeners used to move playerShip using WASD
	@Override
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		
		if (key == e.VK_W) {
			playerShip.move(1);
		} else if(key == e.VK_A) {
			playerShip.move(2);
		} else if(key == e.VK_S) {
			playerShip.move(3);
		} else if (key == e.VK_D) {
			playerShip.move(4);
		} else if (key == e.VK_SPACE) {
			playerShip.move(5);
		}
	}
	
	//Test Test
	public static void main(String[] args) {
		new Game().start();
	}
}
