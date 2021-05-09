import java.awt.*;
import java.util.*;
import java.lang.Math;

public class Mijinco{
	int age;
	int size;
	Point position;
	Image img;
	Mijinco(int x,int y,Image image,int gap){
		Calendar cale = new GregorianCalendar();
		age=cale.get(cale.DATE)-gap;
		if(age<0)
			age += 30;
		size=10+(int)(10.0*java.lang.Math.sqrt(age));
		position = new Point(x,y);
		this.img = image;
	}
	Mijinco(int x,int y,Image image){
		this(x,y,image,0);
	}
	void Jump(){
		double c = java.lang.Math.random();
		if(c>0.5)
			this.position.translate(size,-size);
		else
			this.position.translate(-size,-size);
		
	}		
	void Sink(){
		this.position.y+= size/3;
	}
	void move(){
		double c = java.lang.Math.random();
		if(c<0.25)
			this.Jump();
		else
			this.Sink();
	}
	public String toString(){
		StringBuffer out = new StringBuffer();
		out.append("MIJINCO\n");
		out.append("age :");
		out.append(this.age);
		out.append("\n");
		out.append("size :");
		out.append(this.size);
		out.append("\n");
		out.append("position :");
		out.append(this.position);
		out.append("\n");
		return out.toString();
	}
}


