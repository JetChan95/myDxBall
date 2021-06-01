package myDxBall;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

import javax.swing.*;
public class Map implements KeyListener{
    
	private JFrame frame;
	private Mypanel panel;
	private ArrayList<Wall> walls = new ArrayList<Wall>();
	private ArrayList<Block> blocks = new ArrayList<Block>();
	private ArrayList<Drop> drops = new ArrayList<Drop>();
	public Ball ball;
	public Controller controller;
	public ArrayList<PhysicalBody> gameBody = new ArrayList<PhysicalBody>();
	private ArrayList<PhysicalBody> destroyList = new ArrayList<PhysicalBody>();
	private ArrayList<PhysicalBody> addList = new ArrayList<PhysicalBody>();
	public int top,bottom,left,right;

	private boolean gameOver = false;
	
	
	public static void main(String args[]) {
		Map m = new Map();
		m.initGame();
		m.initUI();
		m.run();
	}
	
	/**
	 * 初始化游戏，生成游戏对象
	 */
	public void initGame() {
		this.top = 0;
		this.bottom = 550;
		this.left = 0;
		this.right = 1000;
		//生成球
		this.ball = new Ball(this, 20, new Vector2D(512, 500));
		this.ball.setDir(new Vector2D(1, -3));
		this.ball.setSpeed(0);
		//将ball对象加入游戏对象管理清单
		this.gameBody.add(ball);
		
		//生成砖块
		int blockX = 40;
		int blockY = 20;
		for (int i=1; i<25;i++) {
			for(int j=1; j<20;j++) {
				this.blocks.add(new Block(this, this.ball, 38, 18, new Vector2D(i*blockX, j*blockY)));
			}
		}
		//将所有block对象加入游戏对象管理清单
		this.gameBody.addAll(blocks);
		
		//生成墙体
		//顶墙
		this.walls.add(new Wall(this.ball, 1024, 10, new Vector2D(512, 5)));
		//左墙
		this.walls.add(new Wall(this.ball, 10, 600, new Vector2D(5, 300)));
		//右墙
		this.walls.add(new Wall(this.ball, 10, 600, new Vector2D(995, 300)));
		this.gameBody.addAll(walls);
		
		//生成控制板
		this.controller = new Controller(this, 50, 10, new Vector2D(512, 550));
		this.gameBody.add(controller);
		
		
	}
	
	/**
	 * 初始化游戏窗口
	 */
	public void initUI() {
		
		// 创建 JFrame 实例
        this.frame = new JFrame("myDxBall");
        this.frame.setResizable(false);
        this.frame.setLocationRelativeTo(null);
        
        // Setting the width and height of frame
        int windowWidth = 1016; //窗口宽
        int windowHeight = 600; //窗口高
        this.frame.setSize(windowWidth, windowHeight);
        
        //窗口居中
        Toolkit kit = Toolkit.getDefaultToolkit(); //定义工具包
        Dimension screenSize = kit.getScreenSize(); //获取屏幕的尺寸
        int screenWidth = screenSize.width; //获取屏幕的宽
        int screenHeight = screenSize.height; //获取屏幕的高
        this.frame.setLocation(screenWidth / 2 - windowWidth / 2, screenHeight / 2 - windowHeight / 2);
        
        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // 创建并添加游戏界面面板至JFrame
        this.panel = new Mypanel();  
        this.frame.add(this.panel);
        
        //设置可见
        this.frame.setVisible(true);
	}
	
    /**
     * 启动游戏
     */
    public void run() {    
        this.frame.addKeyListener(this);
        //主控循环
        while(!this.gameOver) {
    	
        	
        	//处理游戏对象的生成
        	this.gameBody.addAll(addList);
        	this.addList.clear();
        	//处理游戏对象的destroy
        	this.gameBody.removeAll(destroyList);
        	this.destroyList.clear();
        	
        	//每个游戏对象调用runPerTick()函数
        	for(PhysicalBody body:this.gameBody) {
        		body.runPerTick();
        	}
        	
        	//将所有游戏对象绘制出来
        	this.panel.setGameBodyList(this.gameBody);
        	this.panel.repaint();
        	
        	
        	
        	//短暂休眠
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            

        }

    }

    public void gameOver() {
    	this.gameOver= true;
    }
    
    static class Mypanel extends JPanel{
    	/**
		 * 绘制游戏对象
		 */
		private static final long serialVersionUID = 1L;
		
		//存储需要绘制的游戏对象
		ArrayList<PhysicalBody> gameBodyList = new ArrayList<PhysicalBody>();
    	
		//设置需要绘制的游戏对象
    	public void setGameBodyList(ArrayList<PhysicalBody> gameBodyList) {
    		this.gameBodyList = gameBodyList;
    	}
    	
        @Override
        public void paint(Graphics g) {
            super.paint(g);
            //设置画笔颜色
            g.setColor(Color.BLUE);
            //设置画笔大小
            g.setFont(new Font(null, 0,50));
            //循环画出所有游戏对象
            for(PhysicalBody body:gameBodyList) {
            	int x;
            	int y;
            	switch(body.getShape()) {
            	case rectangle:
            		if(body instanceof Block)
            			g.setColor(Color.CYAN);
            		else if(body instanceof Wall)
            			g.setColor(Color.RED);
            		else if(body instanceof Drop) 
            			g.setColor(Color.ORANGE);
            		x = body.getPosition().getX()-body.getWidth()/2;
            		y = body.getPosition().getY()-body.getHeight()/2;
            		g.fillRect(x, y, body.getWidth(), body.getHeight());
            		break;
            	case circular:
            		x = body.getPosition().getX()-body.getR();
            		y = body.getPosition().getY()-body.getR();
            		g.fillOval(x, y, body.getWidth(), body.getHeight());
            		break;
            	default:
            		break;
            	}

            }

        }
    }
    //游戏对象调用Map的destroy方法，通知Map摧毁参数body所描述的游戏对象
    public void destroy(PhysicalBody body) {
    	this.destroyList.add(body);
    }
    
    //游戏对象调用Map的add方法，通知Map添加参数body所描述的游戏对象
    public void add(PhysicalBody body) {
    	this.addList.add(body);
    }

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		switch(e.getKeyCode()) {
		case KeyEvent.VK_LEFT:
			this.controller.setDir(-1);
			break;
		case KeyEvent.VK_RIGHT:
			this.controller.setDir(1);
			break;
		case KeyEvent.VK_SPACE:
			if(!this.controller.isFire())
				this.controller.fire();
			break;
		default:
			break;
		}
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		switch(e.getKeyCode()) {
		case KeyEvent.VK_LEFT:
			if(this.controller.getDir() == -1)
				this.controller.setDir(0);
			break;
		case KeyEvent.VK_RIGHT:
			if(this.controller.getDir() == 1)
				this.controller.setDir(0);
			break;
		default:
			break;
		}
	}

}