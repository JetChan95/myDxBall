package myDxBall;

import java.util.Random;

public class Block extends PhysicalBody implements CollisionalBody{

	private Ball ball;
	private Map map;
	private int dropValue = 2;//0-100,���Ƶ��ߵ����ʣ���ֵԽ�ߵ�����Խ�ߣ�0���޵��䣻100���ض�����
	public Block(Map map, Ball ball, int width, int height, Vector2D position) {
		super(width, height, position);
		this.map = map;
		this.ball = ball;
	}

	public Block(int r, Vector2D position) {
		super(r, position);
	}
	
	public void destroy() {
		this.map.destroy(this);
	}
	
	public void drop() {
		this.map.add(new Drop(1, DropFunc.doubleSpeed, this.map, 20, 20, this.getPosition()));
	}

	@Override
	public void runPerTick() {
		if(this.isPhysicalBodyIn(this.ball)) {
			this.onCollision();
		}

	}

	@Override
	public void onCollision() {
		System.out.printf("������ײ%d,%d\n",this.getPosition().getX(),this.getPosition().getY());
		// TODO Auto-generated method stub 
		Vector2D newDir = this.ball.getDir();
		int top = this.getPosition().getY()-this.getHeight()/2;
		int bottom = top+this.getHeight();
		int left = this.getPosition().getX()-this.getWidth()/2;
		int right = left + this.getWidth();
		int ballX = this.ball.getPosition().getX();
		int ballY = this.ball.getPosition().getY();
		int r = this.ball.getR();
		if(left  <= ballX && right>=ballX) {
			System.out.printf("���������·�\n");
			newDir.setY(-newDir.getY());
		}
		if(top <= ballY && bottom >= ballY) {
			System.out.printf("���������ҷ�\n");
			newDir.setX(-newDir.getX());
		}
		this.ball.setDir(newDir);
		if(new Random().nextInt(100)<=this.dropValue ) {
			System.out.println("************************************************");
			this.drop();
		}
		this.destroy();
		
	}

}
