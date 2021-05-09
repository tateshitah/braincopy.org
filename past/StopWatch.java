/*
 * @(#)StopWatch JFrame version
 *
 * @author HIRO
 * @version 1.01, 15/03/2001
 */


import javax.swing.*;
import java.awt.event.*;
import java.awt.Container;
import javax.swing.border.*;
import java.awt.Font;

class StopWatch extends JFrame{
    JLabel time;
    JButton b1, b2;
    Box box ;
    Container cont;
    Timer timer;
    int second;
    int minute;
    //JTextArea status;
    String s1,s2;

    StopWatch(){
	second =0;
	minute = 0;
	timer = new Timer(1000,new TimerListener());
	this.addWindowListener(new Quit());
	this.setSize(200,200);
	cont = this.getContentPane();
	box = new Box(BoxLayout.Y_AXIS);
	time = new JLabel("00.00",time.CENTER);
	Border border1 = new EtchedBorder(EtchedBorder.RAISED);
	Border border2 = new EmptyBorder(10,20,10,20);
	Border border = new CompoundBorder(border1,border2);
	time.setBorder(border);
	time.setFont(new Font("Dialog",Font.BOLD,30));
	JPanel panel1 = new JPanel();
	panel1.add(time);
	box.add(panel1);
	JPanel panel2 = new JPanel();
	b1 = new JButton("start");
	b1.addActionListener(new Start());
	panel2.add(b1);
	b2 = new JButton("clear");
	b2.addActionListener(new Clear());
	panel2.add(b2);
	//status = new JTextArea(5,20);
	//box.add(status);
	box.add(panel2);
	cont.add(box);
	this.setTitle("HIRO StopWatch");
	this.pack();
	this.setVisible(true);
    }
    public static void main(String[] s){
	Object sw = new StopWatch();
    }
    void showTime(){
	if(minute >=10)
	    s1 = "";
	else 
	    s1 = "0";
	if(second >= 10)
	    s2 = ".";
	else
	    s2 = ".0";
	time.setText(s1+minute + s2 +second );
    }
    protected class Quit extends WindowAdapter implements ActionListener{
	public void windowClosing(WindowEvent even) {
	    System.exit(0);
	}
	public void actionPerformed(ActionEvent even) {
	    System.exit(0);
	}
    }
    protected class Start implements ActionListener{
	public void actionPerformed(ActionEvent even){
	    String b1_label = b1.getText();
	    //status.append( "b1 push! "+ b1_label+"\n");
	    if(b1_label.equals("start")){
		b1.setText("stop!");
		timer.start();
	    }else if(b1_label.equals("stop")){
		b1.setText("start");
		timer.stop();
	    }
	}
    }
    protected class Clear implements ActionListener{
	public void actionPerformed(ActionEvent even){
	    b1.setText("start");
	    timer.stop();
	    second = minute = 0;
	    showTime();
	}
    }
    protected class TimerListener implements ActionListener{
	public void actionPerformed(ActionEvent even){
	    second++;
	    if(second == 60){
		second = 0;
		minute++;
	    }
	    showTime();
	}
    }
}
