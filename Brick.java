public class Brick{
	
	private Rectangle r1;
	private Integer hitNumbers;
	private Text txt;
	
	public Brick(double x, double y, int number){
		hitNumbers = number;
		r1 = new Rectangle(x,y,50,50,getColour(number));
		txt = new Text(hitNumbers.toString(),x,y,20, "WHITE");
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
	
	public void addToGameArena(GameArena ga){
	ga.addRectangle(r1);
	ga.addText(txt);
	}
	
	public Rectangle getBrickRectangle(){
		return r1;
	}
	
	
}
