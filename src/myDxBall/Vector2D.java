package myDxBall;

public class Vector2D {
	private int x;
	private int y;
	
	Vector2D(){
		this.x = 0;
		this.y = 0;
	}
	
	Vector2D(int x, int y){
		this.x = x;
		this.y = y;
	}
	
	public int getX() {
		return x;
	}
	
	public void setX(int x) {
		this.x = x;
	}
	
	public int getY() {
		return y;
	}
	
	public void setY(int y) {
		this.y = y;
	}
	
	public Vector2D distanceOf(Vector2D vector) {
		Vector2D distance = new Vector2D();
		distance.setX(Math.abs(vector.getX()-this.getX()));
		distance.setY(Math.abs(vector.getY()-this.getY()));
		return distance;
	}
	
	public void add(Vector2D vector) {
		this.x += vector.x;
		this.y += vector.y;
	}
	
	public Vector2D mult(int mul) {
		return(new Vector2D(this.x*mul, this.y*mul));
	}
	
	public Vector2D getXReverse() {
		return(new Vector2D(this.x, this.y*(-1)));
	}
	
	public Vector2D getYReverse() {
		return(new Vector2D(this.x*(-1), this.y));
	}
	
	public Vector2D getReverse() {
		return(new Vector2D(this.x*(-1), this.y*(-1)));
	}
	
	
}
