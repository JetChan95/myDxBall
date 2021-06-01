package myDxBall;

public class Wall extends PhysicalBody implements CollisionalBody{

	private Ball ball;
	
	public Wall(Ball ball, int width, int height, Vector2D position) {
		super(width, height, position);
		this.ball = ball;
	}

	@Override
	public void runPerTick() {
		if(this.isPhysicalBodyIn(this.ball)) {
			this.onCollision();
		}
	}

	@Override
	public void onCollision() {
		// TODO Auto-generated method stub
		System.out.printf("发生碰撞%d,%d\n",this.getPosition().getX(),this.getPosition().getY());
		Vector2D newDir = this.ball.getDir();
		int top = this.getPosition().getY()-this.getHeight()/2;
		int bottom = top+this.getHeight();
		int left = this.getPosition().getX()-this.getWidth()/2;
		int right = left + this.getWidth();
		int ballX = this.ball.getPosition().getX();
		int ballY = this.ball.getPosition().getY();
		int r = this.ball.getR();
		if(left  <= ballX && right>=ballX) {
			System.out.printf("球体在上下方\n");
			newDir.setY(-newDir.getY());
		}
		if(top <= ballY && bottom >= ballY) {
			System.out.printf("球体在左右方\n");
			newDir.setX(-newDir.getX());
		}
		this.ball.setDir(newDir);
	}

}
