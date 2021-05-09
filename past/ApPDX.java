/*This is program to send merody to PSH(H") for applet
 */

import java.lang.Math;
import java.applet.*;
import java.awt.*;
import java.awt.event.*;
import java.net.*;
import java.io.*;
import java.util.*;

public class ApPDX extends Applet {

    static final int DEFAULT_PORT = 25;
    static final String CRLF = "\r\n";
    BufferedReader reply = null;
    PrintStream send = null;
    Socket sock = null;

    TextField TO;
    TextField TITLE;
    TextArea MESSAGE;
    java.awt.List MELODY;
    TextArea STATUS;
    public void init() {
	super.init();
	GridBagConstraints gc = new GridBagConstraints();
	GridBagLayout gl = new GridBagLayout();
	setLayout(gl);
	resize(500,400);
	setBackground(Color.pink);
	gc.fill=gc.NONE;
	gc.gridx=0;gc.gridy=0;
	Label label_TO = new Label("TO:");
	gl.setConstraints(label_TO,gc);add(label_TO);
	gc.gridx=1;gc.gridy=0;
	TO = new TextField(40);
	TO.setText("@pdx.ne.jp");
	gl.setConstraints(TO,gc);add(TO);
	gc.gridx=0;gc.gridy=1;
	Label label_TITLE = new Label("TITLE:");
	gl.setConstraints(label_TITLE,gc);add(label_TITLE);
	gc.gridx=1;gc.gridy=1;
	TITLE = new TextField(40);
	TITLE.setText("");
	gl.setConstraints(TITLE,gc);add(TITLE);
	gc.gridx=0;gc.gridy=2;
	Label label_MESSAGE = new Label("MESSAGE:");
	gl.setConstraints(label_MESSAGE,gc);add(label_MESSAGE);
	gc.gridx=1;gc.gridy=2;
	MESSAGE = new TextArea(4,40);
	MESSAGE.setText("Write here!");
	gl.setConstraints(MESSAGE,gc);add(MESSAGE);
	gc.gridx=0;gc.gridy=3;
	Label label_MELODY = new Label("MELODY:");
	gl.setConstraints(label_MELODY,gc);add(label_MELODY);
	gc.gridx=1;gc.gridy=3;
	MELODY = new java.awt.List(5,false);
	MELODY.add("1. ひだまりの詩");
	MELODY.add("2. 幸福論");
	MELODY.add("3. 虹になりたい");
	MELODY.add("4. 主よ人の喜びよ");
	MELODY.add("5. 缶コーヒーBOSS");
	MELODY.add("6. ラジオ体操第1");
	gl.setConstraints(MELODY,gc);add(MELODY);
	gc.gridx=0;gc.gridy=10;
	Button SEND=new Button("SEND");
	SEND.addActionListener(new SEND_ActionListener());
	gl.setConstraints(SEND,gc);add(SEND);
	gc.gridx=1;gc.gridy=10;
	Button CLEAR=new Button("CLEAR");
	CLEAR.addActionListener(new CLEAR_ActionListener());
	gl.setConstraints(CLEAR,gc);add(CLEAR);
	gc.gridy=11;gc.gridwidth=2;
	STATUS = new TextArea(4,40);
	gl.setConstraints(STATUS,gc);add(STATUS);
    }
    public void paint(Graphics g) {
    }
    
    static String Index2Name(int index){
	switch(index){
	case(0):return hidamarinouta;
	case(1):return koufukuron;
	case(2):return nijininaritai;
	case(3):return shuyohitonoyorokobiyo;
	case(4):return BOSS;
	case(5):return radiotaisou;
	}
	return null;
    }
    
    public void sendMessage(String receiver, String title,
			    String message, int als_index )
	throws IOException, ProtocolException {
	try {
	    STATUS.append(getCodeBase().getHost());
	    sock = new Socket(getCodeBase().getHost(), DEFAULT_PORT);
	    reply = new BufferedReader(new
		InputStreamReader(sock.getInputStream()));
	    send = new PrintStream(sock.getOutputStream());
	}catch (Exception e) {
	    try {
		System.out.println(e);
		if (sock != null) {
		    sock.close();
		}
	    }
	    catch (IOException ex) {}
	}
	String rstr = reply.readLine();
	InetAddress local;
	
	GregorianCalendar calen = new GregorianCalendar();
	String boundary = new String();
	boundary += calen.get(calen.YEAR);
	if(calen.get(calen.MONTH)<10)
	    boundary += "0";
	boundary += calen.get(calen.MONTH);
	if(calen.get(calen.DATE)<10)
	    boundary += "0";
	boundary += calen.get(calen.DATE);
	if(calen.get(calen.HOUR_OF_DAY)<10)
	    boundary += "0";
	boundary += calen.get(calen.HOUR_OF_DAY);
	if(calen.get(calen.MINUTE)<10)
	    boundary += "0";
	boundary += calen.get(calen.MINUTE);
	
	String alsData = Index2Name(als_index);
	
	try {
	    String host = InetAddress.getLocalHost().getHostName();
	    send.print("HELO " + host + CRLF);
	    STATUS.append("send> HELO " + host + CRLF);
	    send.flush();
	    rstr = reply.readLine();
	    STATUS.append("reply> " + rstr + CRLF);
	    if (!rstr.startsWith("250")) {
		throw new ProtocolException(rstr);
	    }
	    send.print("MAIL FROM: hirotate@f2.dion.ne.jp"+CRLF);
	    STATUS.append("send> MAIL FROM: hirotate@f2.dion.ne.jp"+CRLF);
	    send.flush();
	    rstr = reply.readLine();
	    STATUS.append("reply> " + rstr + CRLF);
	    if (!rstr.startsWith("250")) {
		throw new ProtocolException(rstr);
	    }
	    send.print("RCPT TO: "+receiver+CRLF);
	    STATUS.append("send> RCPT TO: "+receiver+CRLF);
	    send.flush();
	    rstr = reply.readLine();
	    STATUS.append("reply> " + rstr + CRLF);
	    if (!rstr.startsWith("250")) {
		throw new ProtocolException(rstr);
	    }
	    send.print("DATA"+CRLF);
	    STATUS.append("send> DATA"+CRLF);
	    send.flush();
	    rstr = reply.readLine();
	    STATUS.append("reply> " + rstr + CRLF);
	    if (!rstr.startsWith("354")) {
		throw new ProtocolException(rstr);
	    }
	    send.print("To: " + receiver+CRLF);
	    send.print("From: hirotate@f2.dion.ne.jp"+CRLF);
	    send.print("Subject: " + title+CRLF);
	    send.print("Content-Type: Multipart/Mixed;boundary=\""+boundary+"\""+CRLF);
	    send.print(CRLF);
	    send.print("--"+boundary+CRLF);
	    send.print("Content-Type: text/plain; charset=iso-2022-jp"+CRLF);
	    send.print("Content-Transfer-Encoding: 7bit"+CRLF);
	    send.print(CRLF);
	    send.print(message+CRLF);
	    send.print(CRLF);
	    send.print("--"+boundary+CRLF);
	    send.print("Content-Type: Audio/X-Alpha5; Name=\"melo0001.als\""+CRLF);
	    send.print("Content-Transfer-Encoding: Base64"+CRLF);
	    send.print(CRLF);
	    send.print(alsData.substring(0,alsData.lastIndexOf("=")+1)+CRLF);
	    send.print(CRLF);
	    send.print("--"+boundary+"--"+CRLF);
	    send.print(".");
	    send.print(CRLF);
	    send.flush();
	    rstr = reply.readLine();
	    if (!rstr.startsWith("250")) {
		throw new ProtocolException(rstr);
	    }
	    send.print("QUIT"+CRLF);
	    STATUS.append("send> QUIT"+CRLF);
	    send.flush();
	    sock.close();
	}
	catch (IOException e) {
	    sock.close();
	}
    }
    protected class SEND_ActionListener implements ActionListener{
	public void actionPerformed(ActionEvent e){
	    STATUS.append("Action!!\n");
	    try{
		sendMessage(TO.getText(), TITLE.getText(),
			    MESSAGE.getText(), MELODY.getSelectedIndex());
	    }catch(Exception ex){
		STATUS.append(ex.toString());
		System.out.println(ex);
	    }
	    init();
	}
    }
    protected class CLEAR_ActionListener implements ActionListener{
	public void actionPerformed(ActionEvent e){
	    init();
	}
    }
    
    final static String hidamarinouta
	="4fCC0IK+gtyC6ILMjo398XTGP8M/xj/GPsY/xkHCP8Y+xjzGOsYzwzzGPMY6xjjGN8Y1wjfEAMY/wz/GP8Y+xj/GQcI/xj7GPMY6xjPDPMY8xjrGOMY3xjXAN8M6xjrGOMY3xjXGN8MzxjPGM8Y1xjfGM8M8xjzGOsY4xjfGNcI3xADGAMY6wzrGOsY4xjfGNcY3wzPGM8QzxDfDNcY1wzDGNcMzyTLJM8IywzfGN8M4xjjGOsY/xj7GP8Q+xkPCP8Y/xD3GP8Q8xDzEPMJDwzrGOsM/xjfGOsY4xjjGN8QzxDXDOsY6wz/GN8Y6xjjGOMY3xDPENcM3xjrCP6HGOsY4xjjGN8QzxDXAM8AA/w==";
    
    final static String koufukuron
="4fCNS5WfmF/98XLJOMk6yTzGPck8yTrGOMk6yTzGQMk8yTrFOMlByUHJP8k8yTrJOMk4yTXJOMk6xjzGOsk4yTrJPMY9yTzJOsY4yTrJPMZAyTzJOsU4yUHJQck/yTzJOsk4yTjJNck4yTrJPMk8xjrJOMk6yTzGPck8yTrGOMk6yTzGQMk8yTrFOMlByUHJP8k8yTrJOMk4yTXJOMk6xjzGOsk4yTrJPMY9yTzJOsY4yTrJPMZAyTzJOsY4yUHJQclByT/JPMk6yTjGOMk6yTjGPMk6w8k4yTjJOMk6yTjGPMk6yTjEOP8=";
    
    final static String nijininaritai
	="4fCT+ILJgsiC6IK9gqL98WrENcQ3xjnJOcQ5yTnGN8k0xDDJMMY1xjTGNcY5xjrGOcY1xjfGOck5xDnJOcY3yTTFMMkyyTTJNck0wzLEAMY1xjfGOck5xDnJOcY3yTTEMMkwxjXGNMY1xjnGOsY5xjXGN8Y5yTnEOck5xjfJPMU8yTrJOck6yTnBNcQAxDXGNck10DTGAMYwxjLGMMYuyS3QLcQAxDXGNck50DfGAMkwyTDGMsY0xjXJN9A5xADGNck0xTXJOdA3xgDJMsk0xjXGNMYyyTDQLcUAyTDGMMYtxi3JMMU0yTfHN8c1xzTJNNHJMsUAxTDGLcYtyTChxDTJN8c3xzXHNMk0yTLBMv8=";
    
    final static String shuyohitonoyorokobiyo
	="4fCO5YLmkGyCzJZdgt2CzP3xeMYAxkbGSMZKxk3GS8ZLxk/GTcZNxlLGUcZSxk3GSsZGxkjGSsZLxk3GT8ZNxkvGSsZIxkrGRsZFxkbGSMZBxkXGSMZLxkrGSMZKxkbGSMZKxk3GS8ZLxk/GTcZNxlLGUcZSxk3GSsZGxkjGSsZDxk3GS8ZKxkjGRsZBxkbGRcZGxkrGTcZSxk3GSsZGxkrGTcFKw0vBTcNNwUvDSsZIxkHGQ8ZFxkjGRsZIxkvGSsZLxkjGRcZBxkXGSMZLxkrGSMFKw0vBTcNKxkjGSsZLw0rDSMZGxkrGSMZKxk3GS8ZLxk/GTcZNxlLGUaHGUsZNxkrGRsZIxkrGS8ZNxk/GTcZLxkrGSMZKxkbGRcZGxkjGQcZFxkjGS8ZKxkjGSsZGxkjGSsZNxkvGS8ZPxk3GTcZSxlHGUsZNxkrGRsZIxkrGQ8ZNxkvGSsZIxkbGQcZGxkXARv8=";
    
    final static String BOSS
	="4fCKyoNSgVuDcYFbgsxCT1NTKDQp/fGNxDLEN8Y3xjXGN8Y5xjrGPMQ6xjrGOcY6xjnGN8Y1wjfEAMk+yT/JPsk8wD7/=";
    
    final static String radiotaisou
	="4fCDiYNXg0mRzJGAkeaI6v3xgcRBxUXJRsZIxkHGRcZIxEbFSslMxk3GRsZKxk3GSMZNxkrGSMZGxkXGQcZDxkXGQcZDxkDEQclByUXJSMJN/w==";
    
}




