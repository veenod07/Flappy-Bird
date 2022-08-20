import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.ArrayList;

public class Bird extends Rectangle {


	private static final long serialVersionUID = 1L;
	private int spd = 4;
	public boolean isPressed = false;
	private ArrayList<Rectangle> tubes;

	public Bird(int x, int y, ArrayList<Rectangle> tubes) {
		setBounds(x, y, 32, 32);
		this.tubes = tubes;
	}
	
	public void update() {
		
		if(isPressed) {
			y -= spd;
		}else
		{
			y += spd;
		}
		
		for(int i = 0; i < tubes.size(); i++) {
			if(this.intersects(tubes.get(i))) {
				//restart the game
				Flappy.room = new Room(80);
				tubes = Flappy.room.tubes;
				y = Flappy.HEIGHT / 2;
				Flappy.score = 0;
				break;
			}
		}
		if(y >= Flappy.HEIGHT) {
			//restart the game
			Flappy.room = new Room(80);
			tubes = Flappy.room.tubes;
			y = Flappy.HEIGHT / 2;
			Flappy.score = 0;
		}
	}
	
	public void render(Graphics g) {
		g.setColor(Color.green);
		g.fillOval(x, y, width, height);
	}
}
