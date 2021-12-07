import java.awt.Color;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.ArrayList;

import javax.swing.Timer;

import acm.graphics.GImage;
import acm.graphics.GLabel;
import acm.graphics.GObject;
import acm.graphics.GOval;
import acm.graphics.GRect;
import acm.program.GraphicsProgram;
import acm.util.RandomGenerator;

public class Game extends GraphicsPane implements ActionListener{
    
    File Game = new File("assets/bgm/fight!.wav");
    File menu = new File("assets/bgm/MENU.wav");
    
    private GImage backround;
    private MainApplication program;
    private SettingPane setting;
    private ArrayList <enemyship> enemies = new ArrayList<enemyship>();
    public static final int WINDOW_HEIGHT = 600;
    public static final int WINDOW_WIDTH = 800;
    public static final int SIZE = 25;
    private Timer timer;
    private int count = 20;
    private boolean bossSpawned = false;
    private boolean pause = false;
    private GButton pauseButton;
	private final int BUTTON_SIZE_X = 200;
	private final int BUTTON_SIZE_Y = 100;
	private final int STATIC_ADDER = 50;
	private GButton DEAD;
	private Boolean playerDead = false;
    //private boolean moveSpawnedBoss = false;

    //private static final int SPEED = 5;
	
	Color c = new Color(1f,0f,0f,.2f );
    
    enemyship enemyShip;
    enemyship bossShip;
    PlayerShip playerShip;
    //Hitbox hitbox;
    
    Game(MainApplication program){
        backround = new GImage("assets/sprites/animatedMenu.gif");
        this.program = program;
        //hitbox = new Hitbox();
        timer = new Timer(50, this);
        pauseButton = new GButton("Click on here to continue", program.getWidth()/2-BUTTON_SIZE_X/2, program.getHeight()/2-BUTTON_SIZE_Y/2 - (3*STATIC_ADDER), BUTTON_SIZE_X, BUTTON_SIZE_Y);
        DEAD = new GButton("DEAD", program.getWidth()/2-BUTTON_SIZE_X/2, program.getHeight()/2-BUTTON_SIZE_Y/2 - (3*STATIC_ADDER), BUTTON_SIZE_X, BUTTON_SIZE_Y);
        pauseButton.setFillColor(c);
        pauseButton.setColor(Color.white);
        //hitbox.run();
        //run();
    }    
    
    public void init() {
        program.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
    }
    
    public void run() {
        program.add(backround);
        addEnemies();
        makeEnemies();
        moveEnemies();
        //addBoss();
        //moveBoss();
        makePlayerShip();
        //playerShip.addKeyListeners();
        timer.start();
        //updateAllBullets();      
    }
    
    private void updateAllBullets() {
            for(enemyship e : enemies) {
                    for(Bullet b : e.bullets.bullets) {
                        if( ((playerShip.getPlayerX() < b.returnBulletX()+15) && (b.returnBulletX()-10 < playerShip.getPlayerX() + playerShip.getPlayerWidth()))	
                        && ((playerShip.getPlayerY() < b.returnBulletY()+20) && (b.returnBulletY() < playerShip.getPlayerY()+20 + playerShip.getPlayerHeight()))) {
                            System.out.println("HIT!");
                            b.setXY(20000,20000);
                            playerShip.playerShipHealth.subtractHealth();
                            playerShip.removeHeart();
                            System.out.println(playerShip.playerShipHealth.getHealth());
                            if(playerShip.playerShipHealth.isDestroyed() == true) {
                            	System.out.println("Player is Dead"); //CALL GAME OVER
                            	//program.switchToSome();
                            	playerDead = true;
                            	//JukeBox.STOP();
                            	return;
                            }
                        }        
                    }
            }
            
        	for(Bullet b : playerShip.bullets.bullets) {
        		for(int i = 0; i < enemies.size(); i++) {
            		if( ((enemies.get(i).getEnemyX() < b.returnBulletX()+30) && (b.returnBulletX()-10 < enemies.get(i).getEnemyX() + playerShip.getPlayerWidth()))	
            		 && ((enemies.get(i).getEnemyY() < b.returnBulletY()+20) && (b.returnBulletY() < enemies.get(i).getEnemyY()+20 + playerShip.getPlayerHeight()))) {
            			 System.out.println("HIT!");
            	         b.setXY(20000,20000);
            	         enemies.get(i).killEnemy(program);
            	         enemies.get(i).bullets.clearBullet();
            	         playerShip.playerScore.calculateKilledEnemy(playerShip.playerScore.getScore(), playerShip.playerShipHealth.getHealth());
            	         enemies.remove(enemies.get(i));
            	         }
            		 }
            	}
            	//playerShip.update();

        	if(bossSpawned) { 
                for(Bullet b : bossShip.bullets.bullets) {
                    b.update();
                    if( ((playerShip.getPlayerX() < b.returnBulletX()+15) && (b.returnBulletX()-10 < playerShip.getPlayerX() + playerShip.getPlayerWidth()))	
                    && ((playerShip.getPlayerY() < b.returnBulletY()+20) && (b.returnBulletY() < playerShip.getPlayerY()+20 + playerShip.getPlayerHeight()))) {
                        System.out.println("HIT!");
                        b.setXY(20000,20000);
                        playerShip.playerShipHealth.subtractHealth();
                        playerShip.removeHeart();
                        System.out.println(playerShip.playerShipHealth.getHealth());
                        if(playerShip.playerShipHealth.isDestroyed() == true) {
                        	System.out.println("Player is Dead"); //CALL GAME OVER
                        	//program.switchToSome();
                        	//JukeBox.STOP();
                        	playerDead = true;
                        	return;
                        }
                    }        
                }
        
                for(Bullet b : playerShip.bullets.bullets) {
                	if( ((bossShip.getEnemyX()-30 < b.returnBulletX()) && (b.returnBulletX() < bossShip.getEnemyX()+40 + playerShip.getPlayerWidth()))	
                	&& ((bossShip.getEnemyY() < b.returnBulletY()+20) && (b.returnBulletY() < bossShip.getEnemyY()+20 + playerShip.getPlayerHeight()))) {
                		System.out.println("HIT!");
                		b.setXY(20000,20000);
                		bossShip.subtractBossHealth();
                		playerShip.playerScore.calculateKilledEnemy(playerShip.playerScore.getScore(), playerShip.playerShipHealth.getHealth());
                		System.out.println(bossShip.bossHealth.getHealth());
                		if(bossShip.bossHealth.isDestroyed() == true) {
                        	System.out.println("Boss is Dead"); //CALL GAME OVER
                        	playerShip.playerScore.calculateKilledBoss(playerShip.playerScore.getScore(), playerShip.playerShipHealth.getHealth());
                        	playerDead = true;
                        	//program.switchToSome();
                        	//JukeBox.STOP();
                        }
                	}
                }
    	}
    }
    
    //Function Adds Two Rows of Enemies to An arraylist
    private void addEnemies() {
        if(!bossSpawned) {
        	for(int i = SIZE; i < 750/*WINDOW_WIDTH-SIZE/2*/; i += 50) {
        		enemyShip = new enemyship(shipType.ENEMYSHIP, i, 25, program);
        		enemies.add(enemyShip);
        	}
        
        	for(int i = SIZE+100; i < 650; i+=50) {
        		enemyShip = new enemyship(shipType.ENEMYSHIP, i, 75, program);
        		enemies.add(enemyShip);
        	}
        } else {
        	for(int i = SIZE; i < 750; i+= 50) {
        		enemyShip = new enemyship(shipType.ENEMYSHIP, i, 120, program);
        		enemies.add(enemyShip);
        	}	
        }
    }
    
    private void makeEnemies() {
    	if (!bossSpawned) {
    		for(enemyship e: enemies) {
    			e.makeEnemy();
    		}
    	} else {
    		for(enemyship e: enemies) {
        		e.makeEnemy();
        	}
    	}
    }
    //Function Looks into the Arraylist and places the enemies onto the screen
    private void moveEnemies() {
    	if(!bossSpawned) {
    		for(enemyship e:enemies) {
    			e.numTimes++;
    			e.moveEnemy();
    		}
    	} else {
    		for(enemyship e: enemies) {
        		e.numTimes++;
        		e.moveEnemy();
        	}
    	}
    }
    private void addBoss() {
        bossShip = new enemyship(shipType.BOSSSHIP, 20, 50, program);
        bossShip.makeBoss();
    }
    
    private void moveBoss() {
    	bossShip.numTimes++;
        bossShip.moveEnemy();
    }
    
    @Override
    public void showContents() {
        run();
        //timer.start();
    }
    
    @Override
    public void hideContents() {
    	  for(int i = 0; i < enemies.size(); i++) {
              enemies.get(i).finalize();
          }
        program.removeAll();
        timer.stop();
    }
    
    @Override
    public void mousePressed(MouseEvent e) {
    	GObject obj = program.getElementAt(e.getX(), e.getY());
    	if (obj == pauseButton) {
  		  program.remove(pauseButton);
  		  timer.start();
  		  pause = false;
		}
    }
    
    private void checkEnemiesDead() {
    	if(enemies.size() == 0) {
    		if(bossSpawned == false) {
    			addBoss();
    			bossSpawned = true;
    			addEnemies();
    			makeEnemies();

    			//moveSpawnedBoss = true;
    		}
    	}
    }
    

    //Calls the PlayerShip class to create and add playerShip onto the screen
    private void makePlayerShip() {
        playerShip = new PlayerShip(program);
        playerShip.makePlayerShip();
        //addKeyListeners();
    }
    public void resetGame() {
    	if (enemies.size() != 0) {
    		enemies.clear();
    	}
    	timer.stop();
    	bossSpawned = false;
    	playerDead = false;
    	
    }
    
    //KeyListeners used to move playerShip using WASD
    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        int forward = program.getForward();
        int left = program.getLeft();
        int down = program.getDown();
        int right = program.getRight();
        int shoot = program.getShoot();
        if(!pause) {
        if (key == forward) {
            playerShip.move(1);
        } else if(key == left) {
            playerShip.move(2);
        } else if(key == down) {
            playerShip.move(3);
        } else if (key == right) {
            playerShip.move(4);
        } 
        if (key == shoot) {
        	if(count > 4) {
            playerShip.move(5);
            count = 0;
        		}
        	}
        }
        if(key == KeyEvent.VK_ESCAPE) {
            pause = true;
        	/*JukeBox.STOP();
            program.switchToMenu();
            */
            program.add(pauseButton);
            timer.stop();
        }
        /*if(key == KeyEvent.VK_P) {
        	JukeBox.STOP();
        	program.switchToMenu();
        }*/
    }
    
    //Test Test
    public static void main(String[] args) {
        //new Game().start();
    }


    @Override
    public void actionPerformed(ActionEvent e) {
    	if(!playerDead) {
	        // TODO Auto-generated method stub
	        playerShip.update();
	        updateAllBullets();
	        count++;
	        moveEnemies();
	        for(enemyship enemy : enemies) {
	        	enemy.update();
	        }
	        	
	        if(bossSpawned){
	        	moveBoss();
	        	bossShip.update();
	        }
	        checkEnemiesDead();
    	}
    	else {
    		resetGame();
    		program.switchToSome();
    		//timer.stop();
    	}
    }
}