package Test;
import myDxBall.*;
public class TestGame {

	public static void main(String args[]) {
		Map map = new Map();
		map.initGame();
		map.initUI();
		map.run();
	}
}
