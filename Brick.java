public class Brick{
	
	private Rectangle r1;
	private Integer hitNumbers;
	private Text txt;
	private boolean isDestroyed = false;
	private GameArena ga;
	
	public Brick(double x, double y, int number,GameArena gameArena){
		hitNumbers = number;
		r1 = new Rectangle(x,y,50,50,getColour(number));
		txt = new Text(hitNumbers.toString(),x,y,20, "WHITE");
		ga = gameArena;
		addToGameArena();
	}	
	
	public boolean getIsDestroyed(){
		return isDestroyed;
	}

	
	private String getColour(int value){
		
		String col=null;
		
		
		if (value <= 40 && value >= 31) {
			 col = "BLUE";}
		
		else if (value <= 30  && value >= 21){
			col = "GREEN";
			}
		
		else if (value <= 20 && value >= 11){
			col = "YELLOW";
			}
		
		else{
			col = "RED";
			}
		
		return col;		
		
		}
		
	
	

	
	private void addToGameArena(){
	ga.addRectangle(r1);
	ga.addText(txt);
	}
	
	public Rectangle getBrickRectangle(){
		return r1;
	}
	
	public void hit(){
		//when a ball is hit, the number of hit decreases
		hitNumbers--;
		txt.setText(hitNumbers.toString());
		r1.setColour(getColour(hitNumbers));
		if(hitNumbers==0){
			isDestroyed=true;
			removeFromGameArena();
		}
	}
	
	private void removeFromGameArena(){
		ga.removeRectangle(r1);
		ga.removeText(txt);
	}
}
