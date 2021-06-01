package myDxBall;

public class Drop extends PhysicalBody implements CollisionalBody {

	private DropFunc df = null; 
	private Map map;
	private int fallSpeed = 1;
	
	/**
	 * 构造方法：根据实例化一个Drop对象
	 * @param fallSpeed	下落速度
	 * @param df	道具功能
	 * @param map	所属地图对象
	 * @param width		宽度
	 * @param height	高度
	 * @param position		位置
	 */
	public Drop(int fallSpeed, DropFunc df ,Map map ,int width, int height, Vector2D position) {
		super(width, height, position);
		this.map = map;
		this.df = df;
		this.fallSpeed = fallSpeed;
	}

	
	/**
	 * 每一游戏Tick被调用执行
	 */
	@Override
	public void runPerTick() {
		// TODO Auto-generated method stub
		this.fall();
		this.check();

	}

	
	/**
	 * 在与控制板发生碰触时被调用
	 */
	@Override
	public void onCollision() {
		// TODO Auto-generated method stub
		this.effect();
		this.destroy();

	}
	
	
	/**
	 * 放置检测代码，对游戏对象需要关注的内容进行检测
	 * eg.掉落道具需要检测是否被收集和是否掉出游戏界面
	 * 		当与控制板发生碰撞时，道具被收集，道具生效并摧毁
	 * 		当掉出游戏界面时，道具摧毁
	 */
	public void check() {
		if(this.getPosition().getY() > this.map.bottom)
			this.destroy();
		if(this.isPhysicalBodyIn(this.map.controller))
			this.onCollision();
	}
	
	/**
	 * 通知map对象，对自己进行摧毁
	 */
	private void destroy() {
		// TODO Auto-generated method stub
		this.map.destroy(this);
	}

	/**
	 * 根据掉落速度fallSpeed移动
	 */
	private void fall() {
		this.setPosition(new Vector2D(this.getPosition().getX(),
				this.getPosition().getY()+this.fallSpeed));
	}
	
	/**
	 * 道具生效：根据功能df，对游戏产生相应效果
	 */
	public void effect() {
		switch(df) {
		case doubleSpeed:
			if(this.map.ball.getSpeed()<5)
				this.map.ball.setSpeed(this.map.ball.getSpeed()*2);
			break;
		case halfSpeed:
			if(this.map.ball.getSpeed()>1)
				this.map.ball.setSpeed(this.map.ball.getSpeed()/2);
			break;
		}
	}

}
