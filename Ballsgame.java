public class Ballsgame{
	
	private Ball b1;
	private double xSpeed = 2;
	private double ySpeed = -1;
	
	
	public Ballsgame(double x, double y, int size, String colour){

	
	b1 = new Ball(x, y, size, colour);
	
	}	
	
	public void addToGameArena(GameArena ga){
		ga.addBall(b1);
	}

	public void setXPosition(double xPosition){
		b1.setXPosition(xPosition);
	}
	
	public double getXPosition(){
		return b1.getXPosition();
	}
	
	public void setXSpeed(double speed){
		xSpeed = speed;
	}
	
	public double getXSpeed(){
		return xSpeed;
	}
		
	
	public void setYPosition(double yPosition){
		b1.setYPosition(yPosition);
	}
	
	public double getYPosition(){
		return b1.getYPosition();
	}
	
	public void setYSpeed(double speed){
		ySpeed = speed;
	}
	
	public double getYSpeed(){
		return ySpeed;
	}

}
