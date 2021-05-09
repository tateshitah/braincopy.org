import java.applet.Applet;
import java.awt.*;

public class suisou extends Applet implements Runnable{
	MediaTracker mt;
	Thread MyThread = null;
	Mijinco miji = null;
	Mijinco miji2 = null;
	Image img,img2,screen;
	Graphics graph;
	
	public void run(){
		/*
		try{
			mt.waitForID(0);
		}catch(InterruptedException e){
			return;
		}
		*/
		while(true){
			repaint();
		}
	}
	public void start(){
		if(this.MyThread == null){
			this.MyThread = new Thread(this);
			this.MyThread.start();
		}
	}
	public void init(){
		this.resize(400,400);
		this.setBackground(Color.black);
		mt = new MediaTracker(this);
		img = this.getImage(this.getCodeBase(),"miji.gif");
		miji = new Mijinco(100,100,img);
		mt.addImage(img,0);
		img2 = this.getImage(this.getCodeBase(),"miji-l.gif");
		miji2 = new Mijinco(300,300,img2,15);
		mt.addImage(img2,1);
		try{			mt.waitForID(0);  			//イメージのロードが終わるかエラーを受け取るまで待つ		}catch(InterruptedException e){}
		screen = this.createImage(400,400);
		graph = screen.getGraphics();
		graph.setColor(Color.black);
		graph.fillRect(0,0,400,400);	}
	public void paint(Graphics g){
		graph.setColor(new Color(0,0,0));
		if(miji != null){
			//g.setClip(miji.position.x-miji.size/2,
			//		   miji.position.y-miji.size/2,miji.size,miji.size);
			graph.fillRect(miji.position.x-miji.size/2,
					   miji.position.y-miji.size/2,miji.size,miji.size);
			Point now = new Point(miji.position.x,miji.position.y);
			miji.move();
			if((miji.position.x < 0)||
			   (miji.position.x > 400)||
			   (miji.position.y < 0)||
			   (miji.position.y > 400))
				miji.position = now;
			graph.drawImage(img,miji.position.x-miji.size/2,
						miji.position.y-miji.size/2,miji.size,miji.size,
						Color.black,null);
			try{
				Thread.sleep(200);
			}catch(InterruptedException e){}
		}
		if(miji2 != null){
			//g.setClip(miji2.position.x-miji2.size/2,
			//		   miji2.position.y-miji2.size/2,miji2.size,miji2.size);
			graph.fillRect(miji2.position.x-miji2.size/2,
					   miji2.position.y-miji2.size/2,miji2.size,miji2.size);
			Point now = new Point(miji2.position.x,miji2.position.y);
			miji2.move();
			if((miji2.position.x < 0)||
			   (miji2.position.x > 400)||
			   (miji2.position.y < 0)||
			   (miji2.position.y > 400))
				miji2.position = now;
			graph.drawImage(img2,miji2.position.x-miji2.size/2,
						miji2.position.y-miji2.size/2,miji2.size,miji2.size,
						Color.black,null);
			try{
				Thread.sleep(200);
			}catch(InterruptedException e){}
		}
			g.drawImage(screen,0,0,this);
	}
	public void update(Graphics g){
		paint(g);
	}
	
}
