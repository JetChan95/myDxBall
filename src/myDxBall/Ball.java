package myDxBall;

public class Ball extends PhysicalBody{
	
	private Map map;
	private int speed = 1;
	private Vector2D dir = new Vector2D(0, 0);
	
	public Ball(Map map, int r, Vector2D position) {
		super(r, position);
		this.map = map;
		
	}

	public static void main(String[] args) {
		
	}

	
	public int getSpeed() {
		return speed;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
	}

	public void setDir(Vector2D dir) {
		this.dir = dir;
	}
	
	public Vector2D getDir(){
		return this.dir;
	}
	
	public void move() {
		if(this.speed != 0) {
			this.getPosition().add(this.dir.mult(speed));
		}
	}
	
	public void check() {
		if(this.getPosition().getY() > this.map.bottom)
			this.map.gameOver();
	}
	@Override
	public void runPerTick() {
		this.move();
		this.check();
		
	}


}

