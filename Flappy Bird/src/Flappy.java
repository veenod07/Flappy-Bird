import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;

import javax.swing.JFrame;

public class Flappy extends Canvas  implements Runnable, KeyListener {
	

	private static final long serialVersionUID = 1L;
	public static final int WIDTH = 640, HEIGHT = 480;
	private boolean running = false;
	private Thread thread;
	
	public static double score = 0;
	
	public static Room room;
	public Bird bird;
	
	public Flappy() {
		
		Dimension d = new Dimension(Flappy.WIDTH, Flappy.HEIGHT);
		setPreferredSize(d);
		addKeyListener(this);
		this.setFocusable(true);
		room = new Room(80);
		bird = new Bird(20, Flappy.HEIGHT/2, room.tubes);
	}
	
	public synchronized void start() {
		if(running) return;
		running = true;
		thread = new Thread(this);
		thread.start();
		
	}
	
	public synchronized void stop() {
		
		if(!running) return;
		running = false;
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		 
		
	}
	
	public static void main(String[] args) {
		JFrame frame = new JFrame("Flappy Bird");
		Flappy flappy = new Flappy();
		frame.add(flappy);
		frame.setResizable(false);
		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		
		flappy.start();
		
		
		
		
	}

	@Override
	public void run() {
		int fps = 0;
		double timer = System.currentTimeMillis();
		long lastTime = System.nanoTime();
		double ns = 1000000000 / 60;
		double delta = 0;
		
		while(running)
		{
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			
			while(delta >= 1) {
				update();
				render();
				fps++;
				delta--;
				
			}
			
			if(System.currentTimeMillis() - timer >= 1000)
			{
				System.out.println("FPS: "+fps);
				fps = 0;
				timer += 1000;
			}
		}
		stop();
		
	}

	private void render() {
		
		BufferStrategy bs = getBufferStrategy();
		if(bs == null)
		{
			createBufferStrategy(3);
			return;
			
		}
		
		Graphics g = bs.getDrawGraphics();
		
		g.setColor(Color.black);
		g.fillRect(0,  0,  Flappy.WIDTH, Flappy.HEIGHT);
		room.render(g);
		bird.render(g);
		g.setColor(Color.white);
		g.setFont(new Font(Font.DIALOG,Font.BOLD,19));
		g.drawString("SCORE: " + (int)score, 10, 20);
		g.dispose();
		bs.show();
		
	}

	private void update() {
		room.update();
		bird.update();
		
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_UP)
			bird.isPressed = true;
		
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_UP)
			bird.isPressed = false;
		
	}
}
