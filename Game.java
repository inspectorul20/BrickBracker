import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;

public class Game implements MouseListener{
	
	private static Brick[] brickTable;
	private static Ballsgame[] ballTable;
	private static Arrow a1;
	private static GameArena g1;
	private static int ballsRadius = 5;
		
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
		
		brickTable = new Brick[30];
		
		int i = 0;
		
		for (int col = 0; col < 10; col++){			
				for (int row = 0; row < 3; row++){			
					brickTable[i] = new Brick(25+50*col,25+50*row,30); 			
	                brickTable[i].addToGameArena(g1);
			}
			
		}
	}

	public static void createBallsgame(){
	
	ballTable = new Ballsgame[1];
			
		for (int i = 0; i < ballTable.length; i++){			
					ballTable[i] = new Ballsgame(250,485,2*ballsRadius, "YELLOW"); 			
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
				//check bricks collision
				for(int j = 0; j < brickTable.length; j++){
					System.out.println("j="+j);
					System.out.println("left");
					//left side
					if (ballTable[i].getXPosition() + ballsRadius >= brickTable[j].getBrickRectangle().getXPosition()
						&& ballTable[i].getXPosition() + ballsRadius < brickTable[j].getBrickRectangle().getXPosition()+(brickTable[j].getBrickRectangle().getWidth()/3)
						&& ballTable[i].getYPosition() + ballsRadius > brickTable[j].getBrickRectangle().getYPosition()-(brickTable[j].getBrickRectangle().getHeight())
						&& ballTable[i].getYPosition() - ballsRadius < brickTable[j].getBrickRectangle().getYPosition()+(brickTable[j].getBrickRectangle().getHeight()))
						ballTable[i].setXSpeed(-ballTable[i].getXSpeed());		
				    //bottom side
				    System.out.println("botto;");
				    if (ballTable[i].getYPosition() + ballsRadius <= brickTable[j].getBrickRectangle().getYPosition()
						&& ballTable[i].getYPosition() + ballsRadius > brickTable[j].getBrickRectangle().getYPosition()+(brickTable[j].getBrickRectangle().getHeight()/3)
						&& ballTable[i].getXPosition() + ballsRadius > brickTable[j].getBrickRectangle().getXPosition()-(brickTable[j].getBrickRectangle().getWidth())
						&& ballTable[i].getXPosition() - ballsRadius < brickTable[j].getBrickRectangle().getXPosition()+(brickTable[j].getBrickRectangle().getWidth()))
						ballTable[i].setYSpeed(-ballTable[i].getYSpeed());			
						
				}
				ballTable[i].setXPosition(ballTable[i].getXPosition() + ballTable[i].getXSpeed());
				
				ballTable[i].setYPosition(ballTable[i].getYPosition() + ballTable[i].getYSpeed());
			
			}
			g1.update();	    
		}
	}
}
