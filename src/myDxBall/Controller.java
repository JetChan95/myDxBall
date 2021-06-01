package myDxBall;

public class Controller extends PhysicalBody implements CollisionalBody {

	private int dir = 0;
	private int speed = 3;
	public boolean isFire = false;
	private Map map;

	public Controller(Map map, int width, int height, Vector2D position) {
		super(width, height, position);
		// TODO Auto-generated constructor stub
		this.map = map;
	}

	@Override
	public void runPerTick() {
		// TODO Auto-generated method stub
		this.move();
		if(this.isPhysicalBodyIn(this.map.ball)) {
			this.onCollision();
		}

	}

	private void move() {
		// TODO Auto-generated method stub
		Vector2D np = new Vector2D(this.position.getX()+this.speed *this.dir, this.position.getY());
		if(np.getX()> (this.width/2+this.map.left) 
				&& np.getX() < (this.map.right - this.width/2))	
			this.position = np;
		
		//球未发射，随着控制板移动
		if(!this.isFire) {
			this.map.ball.setPosition(new Vector2D(this.position.getX(), this.position.getY()-(this.height+this.map.ball.height)/2));
		}
	}

	public void setDir(int dir) {
		if(dir > 0)
			this.dir = 1;
		else if(dir < 0)
			this.dir = -1;
		else
			this.dir = 0;
	}

	@Override
	public void onCollision() {
		// TODO Auto-generated method stub
		Vector2D oldDir = this.map.ball.getDir();
		Vector2D newDir;
		if (Math.abs(this.dir + oldDir.getX()) >= Math.abs(oldDir.getX()))
			newDir = oldDir.getXReverse();
		else
			newDir = oldDir.getReverse();
		this.map.ball.setDir(newDir);

	}

	public int getDir() {
		// TODO Auto-generated method stub
		return this.dir;
	}

	public boolean isFire() {
		// TODO Auto-generated method stub
		return this.isFire;
	}
	
	public void fire() {
		//给球设定初始速度和方向
		this.map.ball.setSpeed(1);
		this.map.ball.setDir(new Vector2D(-1, -3));
		this.isFire = true;
	}

}
