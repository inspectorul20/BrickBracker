import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import java.util.Vector;

public class Game implements MouseListener{
	
	private static Vector<Brick> brickTable= new Vector<Brick>();
	private static Ballsgame[] ballTable;
	private static Arrow a1;
	private static GameArena g1;
	private static int ballsRadius = 10;
		
	public Game(){		
		g1 = new GameArena(500,500);
	    createBricks();
	    createBallsgame();
	    createArrow();
		g1.getPanel().addMouseListener(this);
		move();
		
	}
	
	 public void mouseClicked(MouseEvent e) {
		int mouseX = e.getX();
		int mouseY = e.getY(); 
		System.out.println("X is " + mouseX + " Y is " + mouseY);
    }


	private static void createBricks(){
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

	public static void createBallsgame(){
	
	ballTable = new Ballsgame[1];
			
		for (int i = 0; i < ballTable.length; i++){			
					ballTable[i] = new Ballsgame(250,485,ballsRadius, "YELLOW"); 								
	                ballTable[i].addToGameArena(g1);
		
	
		}


	}

	public static void createArrow(){
	
		a1 = new Arrow(250,475, 250, 450,5, "WHITE", g1);

	}
	
	public void mousePressed(MouseEvent e) {
      }

    public void mouseReleased(MouseEvent e) {
    }

    public void mouseEntered(MouseEvent e) {
    }

    public void mouseExited(MouseEvent e) {
    }

	public void move(){		
		while (true){
			
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
			g1.update();	    
		}
	}
}
