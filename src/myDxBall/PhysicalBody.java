package myDxBall;

public abstract class PhysicalBody implements Base{
	protected int width;
	protected int height;
	protected Vector2D position;
	protected Shape shape;
	

	public PhysicalBody(int width, int height, Vector2D position) {
		/**
		 * 构造矩形PysicalBody
		 */
		super();
		this.width = width;
		this.height = height;
		this.position = position;
		this.shape = Shape.rectangle;
	}
	
	public PhysicalBody(int r, Vector2D position) {
		/**
		 * 构造圆形PysicalBody
		 */
		super();
		this.width = r;
		this.height = r;
		this.position = position;
		this.shape = Shape.circular;
	}
	
	public int getR() {
		return width/2;
	}

	public void setR(int r) {
		this.width = r*2;
		this.height = r*2;
	}
	
	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public Vector2D getPosition() {
		return position;
	}


	public void setPosition(Vector2D position) {
		this.position = position;
	}


	public Shape getShape() {
		return shape;
	}

	public boolean isPhysicalBodyIn(PhysicalBody body) {
	/**
	 * 碰撞检测
	 */
		switch(body.getShape()) {
		case circular:
			return isCircularIn(body);
		case rectangle:
			return isRectangleIn(body);
		default:
			return false;
		}
	}
	
	private boolean isCircularIn(PhysicalBody cir) {
		switch(this.getShape()) {
		case circular:
			//圆和圆的碰撞
			return false;
		case rectangle:
			//圆和矩形的碰撞
			return isCirInRec(cir, this);
		default:
			return false;
		}
	}
	
	private boolean isRectangleIn(PhysicalBody rec) {
		switch(this.getShape()) {
		case circular:
			//矩形和圆的碰撞
			return isCirInRec(this, rec);
		case rectangle:
			//矩形和矩形的碰撞
			return isRecInRec(this, rec);
		default:
			return false;
		}
	}
	
	private boolean isRecInRec(PhysicalBody A, PhysicalBody B) {
		/*
		 * 矩形和矩形碰撞检测*/
		//测距：矩形中心距离
		Vector2D distance = A.position.distanceOf(B.position);
		if(distance.getX()<=(A.width+B.width)/2
				&& distance.getY()<=(A.height+B.height)/2) {
			return true;
		}
		return false;
	}
	
	private boolean isCirInRec(PhysicalBody cir, PhysicalBody rec) {
		/*
		 * 圆和矩形碰撞检测*/
		int r = cir.getR();
		int x = Math.abs(cir.getPosition().getX() - rec.getPosition().getX());
		int y = Math.abs(cir.getPosition().getY() - rec.getPosition().getY());
		int minX = Math.min(Math.abs(rec.getPosition().getX()+rec.getWidth()/2-cir.getPosition().getX()), 
				Math.abs(rec.getPosition().getX()-rec.getWidth()/2-cir.getPosition().getX()));
		int minY = Math.min(Math.abs(rec.getPosition().getY()+rec.getHeight()/2-cir.getPosition().getY()), 
				Math.abs(rec.getPosition().getY()-rec.getHeight()/2-cir.getPosition().getY()));
		int dy = Math.abs(y-this.getHeight()/2);
		if((x-r)<rec.getWidth()/2 && (y-r)<rec.getHeight()/2) {
			return true;
		}
		if(Math.pow(minX, 2)+Math.pow(minY, 2)<=Math.pow(cir.getR(), 2)) {
			return true;
		}
		return false;
	}
	
}
