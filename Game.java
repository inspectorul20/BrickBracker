import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseEvent;
import java.util.Vector;
import java.awt.PointerInfo;
import java.awt.Point;
import java.awt.MouseInfo;

public class Game implements MouseListener, MouseMotionListener{
	
	private  Vector<Brick> brickTable= new Vector<Brick>();
	private  Ballsgame[] ballTable;
	private GameArena g1;
	private int ballsRadius = 10;
	private Arrow a1;
    private double arrowStartX = 250, arrowStartY = 475, arrowRadius= 25, arrowInitAngle = -Math.PI/2;
	private double angle;
	private boolean roundStarted = false;
	private double mouseX=arrowStartX, mouseY=arrowStartY;
		
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
		brickTable.add(new Brick(25,25,30,g1)); 			
	   
		brickTable.add(new Brick(120,60,20,g1)); 			
	   
		brickTable.add(new Brick(250,90,10,g1)); 			
	   
/*			
		for (int col = 0; col < 10; col++){			
				for (int row = 0; row < 3; row++){			
					brickTable.add(new Brick(25+50*col,25+50*row,30,g1)); 			
			}
			
		}*/
	}

	public void createBallsgame(){
	
	ballTable = new Ballsgame[3];
			
		for (int i = 0; i < ballTable.length; i++){			
		    ballTable[i] = new Ballsgame(250,475,ballsRadius, "YELLOW"); 
			ballTable[i].addToGameArena(g1);				
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
		//shoot the ball when the space is hit
		for(int i = 0; i< ballTable.length; i++){
			ballTable[i].setXSpeed(2 * Math.cos(angle));
			ballTable[i].setYSpeed(2 * Math.sin(angle));
		}
		
		a1.removeArrow(g1);
		
		roundStarted=true;
    }
	
	public void move(){		
		while (true){
			if(!roundStarted)
				moveArrow();
			else{
				for(int i = 0; i < ballTable.length ; i++){
					
					//check side collision
					if(ballTable[i].getXPosition() + ballsRadius >= g1.getArenaWidth() || ballTable[i].getXPosition() - ballsRadius <= 0)				
						ballTable[i].setXSpeed(-ballTable[i].getXSpeed());
					
					if(ballTable[i].getYPosition() - ballsRadius <= 0)
						ballTable[i].setYSpeed(-ballTable[i].getYSpeed());	

					if(ballTable[i].getYPosition() + ballsRadius >= g1.getArenaHeight())
						ballTable[i].setYSpeed(-ballTable[i].getYSpeed());					
					
					//check bricks collision
					int brickNumber = brickTable.size();
					for(int j = 0; j < brickNumber; j++){
						double brickX = brickTable.elementAt(j).getBrickRectangle().getXPosition();
						double brickY = brickTable.elementAt(j).getBrickRectangle().getYPosition();
						double brickHalfWidth = brickTable.elementAt(j).getBrickRectangle().getWidth()/2;
						double brickHalfHeight = brickTable.elementAt(j).getBrickRectangle().getHeight()/2;
						
						//left side
						if (ballTable[i].getXPosition() + ballsRadius >= brickX-brickHalfWidth
							&& ballTable[i].getXPosition() + ballsRadius < brickX-(brickHalfWidth*2/3)
							&& ballTable[i].getYPosition() + ballsRadius > brickY-brickHalfHeight
							&& ballTable[i].getYPosition() - ballsRadius < brickY+brickHalfHeight){
								System.out.println("Left of #"+j+" ball at "+ballTable[i].getXPosition()+", "+ballTable[i].getYPosition());
								ballTable[i].setXSpeed(-ballTable[i].getXSpeed());
								brickTable.elementAt(j).hit();
							}
						
						//bottom side
						else if (ballTable[i].getYPosition() - ballsRadius <= brickY+brickHalfHeight
							&& ballTable[i].getYPosition() - ballsRadius > brickY+(brickHalfHeight*2/3)
							&& ballTable[i].getXPosition() + ballsRadius > brickX-brickHalfWidth
							&& ballTable[i].getXPosition() - ballsRadius < brickX+brickHalfWidth){
								System.out.println("Bottom of #"+j+" ball at "+ballTable[i].getXPosition()+", "+ballTable[i].getYPosition());
								ballTable[i].setYSpeed(-ballTable[i].getYSpeed());	
								brickTable.elementAt(j).hit();								
							}
						
						//right side
						else if (ballTable[i].getXPosition() - ballsRadius <= brickX+brickHalfWidth
							&& ballTable[i].getXPosition() - ballsRadius > brickX+(brickHalfWidth*2/3)
							&& ballTable[i].getYPosition() + ballsRadius > brickY-brickHalfHeight
							&& ballTable[i].getYPosition() - ballsRadius < brickY+brickHalfHeight){
								System.out.println("Right of #"+j+" ball at "+ballTable[i].getXPosition()+", "+ballTable[i].getYPosition());
								ballTable[i].setXSpeed(-ballTable[i].getXSpeed());
								brickTable.elementAt(j).hit();
							}
						
						//top side
						else if (ballTable[i].getYPosition() + ballsRadius >= brickY-brickHalfHeight
							&& ballTable[i].getYPosition() - ballsRadius < brickY-(brickHalfHeight*2/3)
							&& ballTable[i].getXPosition() + ballsRadius > brickX-brickHalfWidth
							&& ballTable[i].getXPosition() - ballsRadius < brickX+brickHalfWidth){
								System.out.println("Top of #"+j+" ball at "+ballTable[i].getXPosition()+", "+ballTable[i].getYPosition());
								ballTable[i].setYSpeed(-ballTable[i].getYSpeed());	
								brickTable.elementAt(j).hit();						
							}
						if(brickTable.elementAt(j).getIsDestroyed()){
									brickTable.removeElementAt(j);
									j--;
									brickNumber--;
						}
					}				
					ballTable[i].setXPosition(ballTable[i].getXPosition() + ballTable[i].getXSpeed());
					ballTable[i].setYPosition(ballTable[i].getYPosition() + ballTable[i].getYSpeed());
					
				
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
		    if(mouseY>arrowStartY)
				angle = 0;
		}
		else if(mouseX<250){
			angle = -Math.PI+Math.atan((arrowStartY-mouseY)/(arrowStartX-mouseX));
			if(mouseY>arrowStartY)
				angle = -Math.PI;
		}
		a1.setEnd(arrowStartX+arrowRadius*Math.cos(angle),arrowStartY+arrowRadius*Math.sin(angle));
	}
}
