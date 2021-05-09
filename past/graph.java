import java.lang.Math;
import java.applet.*;
import java.awt.*;

public class graph extends Applet {
	int N = 380;
	double size=30.0;
	Point r[] = new Point[N];
	static Point origin = new Point(200,100);
	TextArea Xtxt;
	TextArea Ytxt;
	Button SHOW;
	Button CLEAR;
	Button BIG;
	Button SMALL;
	Button UP;
	Button DOWN;
	Button LEFT;
	Button RIGHT;
  Graphics graph ;
	String enterXtxt;
	String enterYtxt;
  Image img ;
	Color dp = new Color(0xcc3333);
	Color dw = new Color(0xeeeeee);
	public void init() {
		super.init();
		this.setLayout(null);
		resize(400,350);
    setBackground(Color.pink);
		Xtxt = new TextArea(100,2);
		Xtxt.setText("X-input");
    add(Xtxt);
    Xtxt.reshape(10,200,380,40);
		Ytxt = new TextArea(100,2);
		Ytxt.setText("Y-input");
    add(Ytxt);
    Ytxt.reshape(10,245,380,40);
    SHOW=new Button("SHOW");
		add(SHOW);
    SHOW.reshape(150,290,50,20);
		CLEAR=new Button("CLEAR");
    add(CLEAR);
		CLEAR.reshape(150,320,50,20);
    BIG=new Button("BIG");
		add(BIG);
		BIG.reshape(220,290,50,20);
    SMALL=new Button("SMALL");
		add(SMALL);
		SMALL.reshape(220,320,50,20);
    UP=new Button("UP");
		add(UP);
    UP.reshape(315,290,50,17);
		DOWN=new Button("DOWN");
    add(DOWN);
		DOWN.reshape(315,324,50,17);
    LEFT=new Button("LEFT");
		add(LEFT);
    LEFT.reshape(290,307,50,17);
		RIGHT=new Button("RIGHT");
    add(RIGHT);
		RIGHT.reshape(340,307,50,17);
		repaint();
 	}
	public void paint(Graphics g) {
		if(graph==null){
			img=createImage(400,350) ;
			graph=img.getGraphics() ;
		}
		g.drawImage(img,0,0,this) ;
		graph.setColor(Color.black);
		graph.fillRect(10,10,380,180);
		graph.setColor(Color.white);
		graph.drawLine(10,100,389,100);
		graph.drawLine(200,10,200,190);
		graph.setColor(dw);
		graph.drawLine(391,191,391,10);
		graph.drawLine(391,191,10,191);
		graph.setColor(dp);
		graph.drawLine(8,8,391,8);
		graph.drawLine(8,9,8,191);
	}
    public boolean handleEvent(Event event){
      if(event.id==Event.ACTION_EVENT&&event.target==SHOW){
				clickedSHOWBtn();
        return true;
      }
			if(event.id==Event.ACTION_EVENT&&event.target==CLEAR){
        clickedCLEARBtn();
        return true;
      }
			if(event.id==Event.ACTION_EVENT&&event.target==BIG){
        clickedBIGBtn();
        return true;
      }				
			if(event.id==Event.ACTION_EVENT&&event.target==SMALL){
        clickedSMALLBtn();
        return true;
      }				
			if(event.id==Event.ACTION_EVENT&&event.target==UP){
        clickedUPBtn();
        return true;
      }				
			if(event.id==Event.ACTION_EVENT&&event.target==DOWN){
        clickedDOWNBtn();
        return true;
      }				
			if(event.id==Event.ACTION_EVENT&&event.target==LEFT){
        clickedLEFTBtn();
        return true;
      }				
			if(event.id==Event.ACTION_EVENT&&event.target==RIGHT){
        clickedRIGHTBtn();
        return true;
      }				
      return super.handleEvent(event);
    }

    public void clickedSHOWBtn(){
      enterXtxt=Xtxt.getText();
			enterYtxt=Ytxt.getText();
			repaint();
      Node xroot = MakeTree(enterXtxt.toCharArray());
			Node yroot = MakeTree(enterYtxt.toCharArray());
			for(int i=0;i<N;i++){
				r[i]=new Point((int)(size*CalTree(xroot,(double)(i-N/2)/size))+origin.x,
											 -(int)(size*CalTree(yroot,(double)(i-N/2)/size))+origin.y);
			}
			graph.setColor(Color.yellow);
			for(int i=0;i<N-1;i++){
				if(r[i].x<390&&r[i].x>10&&r[i].y<190&&r[i].y>10&&
					 r[i+1].x<390&&r[i+1].x>10&&r[i+1].y<190&&r[i+1].y>10)
					graph.drawLine(r[i].x,r[i].y,r[i+1].x,r[i+1].y);
			}
		}
    public void clickedCLEARBtn(){
			size=30.0;
			origin.x=200;
			origin.y=100;
			enterXtxt="";
			enterYtxt="";
			repaint();
    }
    public void clickedBIGBtn(){
			size*=1.5;
			repaint();
			clickedSHOWBtn();
    }
    public void clickedSMALLBtn(){
			size*=0.67;
			repaint();
			clickedSHOWBtn();
    }
    public void clickedUPBtn(){
			origin.y-=15;
			repaint();
			clickedSHOWBtn();
    }
    public void clickedDOWNBtn(){
			origin.y+=15;
			repaint();
			clickedSHOWBtn();
    }
    public void clickedLEFTBtn(){
			origin.x-=15;
			repaint();
			clickedSHOWBtn();
    }
    public void clickedRIGHTBtn(){
			origin.x+=15;
			repaint();
			clickedSHOWBtn();
    }
	
  static double CalTree(Node root,double t){
	  double out =0.0;
	  double r=0.0;
	  double l=0.0;
		double child[] = new double[10];
	  if(root==null)return 0;
	  if(root.NodeName.equals("t")){
		  return t;
	  }
	  if(root.NodeName.equals("-t")){
		  return -t;
	  }
		if(root.NodeName.equals("PI")){
			return Math.PI;
		}
	  try{
		  return (double)Integer.parseInt(root.NodeName);
	  }catch(java.lang.NumberFormatException e){}
		for(int i=0;i<root.children.length;i++)
			child[i]=CalTree(root.children[i],t);
		if(root.NodeName.equals("+")){
			double o=0;
			for(int i=0;i<child.length;i++){
				if(child[i]==0)return o;
				o += child[i];
			}
		}
		if(root.NodeName.equals("-")){
			double o=child[0];
			for(int i=1;i<child.length;i++){
				if(child[i]==0)return o;
				o-= child[i];
			}
		}
		if(root.NodeName.equals("*")){
			double o=1.0;
			for(int i=0;i<child.length;i++){
				if(child[i]==0)return o;
				o*=child[i];
			}
		}
	  if(root.NodeName.equals("/")){
			double o=child[0];
			for(int i=1;i<child.length;i++){
				if(child[i]==0.0)return o;
				o/=child[i];
			}
		}
	  if(root.NodeName.equals("sin"))return Math.sin(child[0]);
	  if(root.NodeName.equals("cos"))return Math.cos(child[0]);
	  if(root.NodeName.equals("tan"))return Math.tan(child[0]);
	  if(root.NodeName.equals("exp"))return Math.exp(child[0]);
		if(root.NodeName.equals("ln")){
			if(l<=0)return -10000;
			else return Math.log(l);
		}
		if(root.NodeName.equals("abs"))return Math.abs(child[0]);
		if(root.NodeName.equals("sqrt")){
			if(l<=0)return -10000;
			else return Math.sqrt(l);
		}
	  else return 0.0;
  }
  static Node MakeTree(char input[]){
    String Name = "";
	  Node out=new Node(Name);
    String temp="";
    int i=0;
    int j=0;
		int cn=0;//cn:children number
    int PTC = 0;//PTC:patenthesis count
		while(input[i]!='('){
			out.NodeName += input[i];
			if((input[i]==')')||(input[i]==',')||(i+1==input.length)){
				return out;
			}	
			i++;
		}
    i++;
		while(true){
			j=0;temp="";
			while(((input[i]!=',')&&(input[i]!=')'))||(PTC>0)){
				temp+=input[i];
				if(input[i]=='(')PTC++;
				if(input[i]==')')PTC--;
				i++;j++;
			}
			out.children[cn]=MakeTree(temp.toCharArray());
			if(input[i]==')')break;
			cn++;
			i++;
		}
    return out;
  }
}
class Node{
  String NodeName;
  Node[] children;
  Node(String N){NodeName=N;children=new Node[10];}
}

	