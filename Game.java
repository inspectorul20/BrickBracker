import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseEvent;
import java.util.Vector;
import java.util.Random;
import java.awt.PointerInfo;
import java.awt.Point;
import java.awt.MouseInfo;

public class Game implements MouseListener, MouseMotionListener{
	
	private int maxRoundPerGame = 10;
	private int currentRound = 1;
	
	private GameArena g1;
	private Vector<Brick> brickTable= new Vector<Brick>();
	private double bricksHeight = 50;
	private int brickRandChance = 5;//indicate there is 1 change on that value NOT to get a brick
	private Vector<Ballsgame> ballTable;
	private int ballNumber = 5;
	private int ballOnScreen = ballNumber;
	private int ballsRadius = 10;
	private int ballShootCounter = 0, ballShooterPeriod = 50;
	private Arrow a1;
    private double arrowStartX = 250, arrowStartY = 475, arrowRadius= 25, arrowInitAngle = -Math.PI/2;
	private double angle;
	private boolean roundStarted = false;
	private double mouseX=arrowStartX, mouseY=arrowStartY;
	private boolean stopGame=false;
		
	public Game(){		
		g1 = new GameArena(500,500);
	    createBricks();
	    createBallsgame();
	    createArrow();
		g1.getPanel().addMouseListener(this);
		g1.getPanel().addMouseMotionListener(this);
		move();
		
	}
	
	private void createBricks(){
		/*brickTable.add(new Brick(25,25,30,g1)); 	
		brickTable.add(new Brick(120,60,20,g1)); 	
		brickTable.add(new Brick(250,90,10,g1)); 
		*/
		Random rand = new Random();
		for (int col = 0; col < 10; col++){			
				for (int row = 0; row < 3; row++){	
				    if(rand.nextInt(brickRandChance)>0)
						brickTable.add(new Brick(25+50*col,-75+50*row,40-row*10,g1)); 			
			}			
		}
	}

	public void createBallsgame(){
	
	ballTable = new Vector<Ballsgame>();
			
		for (int i = 0; i < ballNumber; i++){			
			ballTable.add(new Ballsgame(250,475,ballsRadius, "YELLOW")); 
			ballTable.elementAt(i).addToGameArena(g1);				
		}
	}

	public void createArrow(){
	
		a1 = new Arrow(arrowStartX,arrowStartY, arrowStartX, arrowStartY-arrowRadius,5, "WHITE", g1);

	}
	
	public void mousePressed(MouseEvent e) {
      }

    public void mouseReleased(MouseEvent e) {
    }

    public void mouseEntered(MouseEvent e) {
		mouseX = e.getX();
		mouseY = e.getY();
    }

    public void mouseExited(MouseEvent e) {
    }
	
	public void mouseMoved(MouseEvent e) {
		mouseX = e.getX();
		mouseY = e.getY();
    }

    public void mouseDragged(MouseEvent e) {
    }

	public void mouseClicked(MouseEvent e) {
		if(!roundStarted){
			//shoot the ball when the space is hit
			for(int i = 0; i< ballTable.size(); i++){
				ballTable.elementAt(i).setXSpeed(5 * Math.cos(angle));
				ballTable.elementAt(i).setYSpeed(5 * Math.sin(angle));
			}
			
			a1.removeArrow(g1);
			
			roundStarted=true;
		}
	}
	
	public void move(){		
		while (!stopGame){
			if(!roundStarted)
				moveArrow();
			else{
				if(ballShootCounter <ballShooterPeriod*ballNumber-1)
					ballShootCounter++;
				for(int i = 0; i <1+ballShootCounter/ballShooterPeriod; i++){
					if(!ballTable.elementAt(i).getIsDestroyed()){
					//check side collision
						if(ballTable.elementAt(i).getXPosition() + ballsRadius >= g1.getArenaWidth() || ballTable.elementAt(i).getXPosition() - ballsRadius <= 0)				
							ballTable.elementAt(i).setXSpeed(-ballTable.elementAt(i).getXSpeed());
						
						if(ballTable.elementAt(i).getYPosition() - ballsRadius <= 0)
							ballTable.elementAt(i).setYSpeed(-ballTable.elementAt(i).getYSpeed());	
						
						//check bricks collision
						int brickNumber = brickTable.size();
						for(int j = 0; j < brickNumber; j++){
							double brickX = brickTable.elementAt(j).getBrickRectangle().getXPosition();
							double brickY = brickTable.elementAt(j).getBrickRectangle().getYPosition();
							double brickHalfWidth = bricksHeight/2;
							double brickHalfHeight = bricksHeight/2;

							//bottom side
							if (ballTable.elementAt(i).getYPosition() - ballsRadius <= brickY+brickHalfHeight
								&& ballTable.elementAt(i).getYPosition() - ballsRadius > brickY+(brickHalfHeight*2/3)
								&& ballTable.elementAt(i).getXPosition() + ballsRadius > brickX-brickHalfWidth
								&& ballTable.elementAt(i).getXPosition() - ballsRadius < brickX+brickHalfWidth){
									//System.out.println("Bottom of #"+j+" ball at "+ballTable.elementAt(i).getXPosition()+", "+ballTable.elementAt(i).getYPosition());
									ballTable.elementAt(i).setYSpeed(-ballTable.elementAt(i).getYSpeed());	
									brickTable.elementAt(j).hit();	
									ballTable.elementAt(i).setYPosition(brickY+brickHalfHeight+ballsRadius);
								}
							//left side
							else if (ballTable.elementAt(i).getXPosition() + ballsRadius >= brickX-brickHalfWidth
								&& ballTable.elementAt(i).getXPosition() + ballsRadius < brickX-(brickHalfWidth*2/3)
								&& ballTable.elementAt(i).getYPosition() + ballsRadius > brickY-brickHalfHeight
								&& ballTable.elementAt(i).getYPosition() - ballsRadius < brickY+brickHalfHeight){
									//System.out.println("Left of #"+j+" ball at "+ballTable.elementAt(i).getXPosition()+", "+ballTable.elementAt(i).getYPosition());
									ballTable.elementAt(i).setXSpeed(-ballTable.elementAt(i).getXSpeed());
									brickTable.elementAt(j).hit();
									ballTable.elementAt(i).setXPosition(brickX-brickHalfHeight-ballsRadius);
								}
							
							//right side
							else if (ballTable.elementAt(i).getXPosition() - ballsRadius <= brickX+brickHalfWidth
								&& ballTable.elementAt(i).getXPosition() - ballsRadius > brickX+(brickHalfWidth*2/3)
								&& ballTable.elementAt(i).getYPosition() + ballsRadius > brickY-brickHalfHeight
								&& ballTable.elementAt(i).getYPosition() - ballsRadius < brickY+brickHalfHeight){
									//System.out.println("Right of #"+j+" ball at "+ballTable.elementAt(i).getXPosition()+", "+ballTable.elementAt(i).getYPosition());
									ballTable.elementAt(i).setXSpeed(-ballTable.elementAt(i).getXSpeed());
									brickTable.elementAt(j).hit();
									ballTable.elementAt(i).setXPosition(brickX+brickHalfHeight+ballsRadius);
								}
							
							//top side
							else if (ballTable.elementAt(i).getYPosition() + ballsRadius >= brickY-brickHalfHeight
								&& ballTable.elementAt(i).getYPosition() - ballsRadius < brickY-(brickHalfHeight*2/3)
								&& ballTable.elementAt(i).getXPosition() + ballsRadius > brickX-brickHalfWidth
								&& ballTable.elementAt(i).getXPosition() - ballsRadius < brickX+brickHalfWidth){
									//System.out.println("Top of #"+j+" ball at "+ballTable.elementAt(i).getXPosition()+", "+ballTable.elementAt(i).getYPosition());
									ballTable.elementAt(i).setYSpeed(-ballTable.elementAt(i).getYSpeed());	
									brickTable.elementAt(j).hit();
									ballTable.elementAt(i).setYPosition(brickY-brickHalfHeight-ballsRadius);					
								}
							if(brickTable.elementAt(j).getIsDestroyed()){
										brickTable.removeElementAt(j);
										j--;
										brickNumber--;
										if(brickNumber==0){
											setGameWon();											
										}
							}
						}				
						ballTable.elementAt(i).setXPosition(ballTable.elementAt(i).getXPosition() + ballTable.elementAt(i).getXSpeed());
						ballTable.elementAt(i).setYPosition(ballTable.elementAt(i).getYPosition() + ballTable.elementAt(i).getYSpeed());
						if(ballTable.elementAt(i).getYPosition()-ballsRadius > g1.getArenaHeight()){
							ballTable.elementAt(i).setIsDestroyed(true);
							ballOnScreen--;
							if(ballOnScreen==0){
								if(currentRound<maxRoundPerGame)
									newRound();
								else 
									//if we arrive here we lost
									setGameOver();
							}														
						}
					}
				}
			}
			g1.update();	    
		}
	}
	
	public void moveArrow(){
		//update the arrow when in the direction of the mouse
		angle = -Math.PI/2;
		if(mouseX>250){
			angle = Math.atan((arrowStartY-mouseY)/(arrowStartX-mouseX));
		    if(mouseY>arrowStartY || angle == 0)
				angle = 0.01;
		}
		else if(mouseX<250){
			angle = -Math.PI+Math.atan((arrowStartY-mouseY)/(arrowStartX-mouseX));
			if(mouseY>arrowStartY || angle == -Math.PI)
				angle = -Math.PI+0.01;
		}
		a1.setEnd(arrowStartX+arrowRadius*Math.cos(angle),arrowStartY+arrowRadius*Math.sin(angle));
	}
	
	private void newRound(){
		currentRound++;
		for(int i=0;i<brickTable.size();i++){
			brickTable.elementAt(i).setYPosition(brickTable.elementAt(i).getYPosition()+bricksHeight);
			if((brickTable.elementAt(i).getYPosition() + bricksHeight/2) >= g1.getArenaHeight()){
				setGameOver();
				return;

			}
		}	
		ballOnScreen=ballNumber;
		for(int i=0; i<ballNumber;i++){
			//reset the balls
			ballTable.elementAt(i).setIsDestroyed(false);
			ballTable.elementAt(i).setXPosition(arrowStartX);
			ballTable.elementAt(i).setYPosition(arrowStartY);			
		}
		ballShootCounter=0;
		roundStarted=false;
		angle = -Math.PI/2;
		moveArrow();
		a1.addArrow(g1);
		
			
	}
	
	private void setGameOver(){
		//clear the game arena
		stopGame=true;
		ballTable.clear();
		for(Brick brick : brickTable){
			brick.removeFromGameArena();
		}
		brickTable.clear();
		g1.addText(new Text("GAME OVER",100,250,50, "RED"));		
	}
	
	private void setGameWon(){
		//clear the game arena
		stopGame=true;
		for(Brick brick : brickTable){
			brick.removeFromGameArena();
		}
		for(Ballsgame ball : ballTable){
			ball.removeFromGameArena(g1);
		}
		brickTable.clear();
		g1.addText(new Text("YOU WON !",100,250,50, "GREEN"));		
	}
}
