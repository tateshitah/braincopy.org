/*This is program to send merody to PSH(H") for Frame.
 */

import java.lang.Math;
import java.awt.*;
import java.awt.event.*;
import java.net.*;
import java.io.*;
import java.util.*;

public class FPDX extends Frame implements WindowListener{

	static final int DEFAULT_PORT = 25;
	static final String CRLF = "\r\n";
	BufferedReader reply = null;
	PrintStream send = null;
	Socket sock = null;
	TextField SERVER;
	TextField TO;
	TextField FROM;
	TextField TITLE;
	TextArea MESSAGE;
	TextField MELODY;
	TextArea STATUS;
	
	public static void main(String s[]){
		Frame MyFrame = new FPDX();
	}

	FPDX() {
		addWindowListener(this);
		GridBagConstraints gc = new GridBagConstraints();
		GridBagLayout gl = new GridBagLayout();
		setLayout(gl);
		setSize(500,400);
		setBackground(Color.pink);
		gc.fill=gc.NONE;
		gc.gridx=0;gc.gridy=0;
		Label label_SERVER = new Label("SERVER:");
		gl.setConstraints(label_SERVER,gc);add(label_SERVER);
		gc.gridx=1;gc.gridy=0;
		SERVER = new TextField(40);
		SERVER.setText("mail server");
		gl.setConstraints(SERVER,gc);add(SERVER);
		gc.gridx=0;gc.gridy=1;
		Label label_TO = new Label("TO:");
		gl.setConstraints(label_TO,gc);add(label_TO);
		gc.gridx=1;gc.gridy=1;
		TO = new TextField(40);
		TO.setText("@pdx.ne.jp");
		gl.setConstraints(TO,gc);add(TO);
		gc.gridx=0;gc.gridy=2;
		Label label_FROM = new Label("FROM:");
		gl.setConstraints(label_FROM,gc);add(label_FROM);
		gc.gridx=1;gc.gridy=2;
		FROM = new TextField(40);
		FROM.setText("@");
		gl.setConstraints(FROM,gc);add(FROM);
		gc.gridx=0;gc.gridy=3;
		Label label_TITLE = new Label("TITLE:");
		gl.setConstraints(label_TITLE,gc);add(label_TITLE);
		gc.gridx=1;gc.gridy=3;
		TITLE = new TextField(40);
		TITLE.setText("");
		gl.setConstraints(TITLE,gc);add(TITLE);
		gc.gridx=0;gc.gridy=4;
		Label label_MESSAGE = new Label("MESSAGE:");
		gl.setConstraints(label_MESSAGE,gc);add(label_MESSAGE);
		gc.gridx=1;gc.gridy=4;
		MESSAGE = new TextArea(4,40);
		MESSAGE.setText("Write here!");
		gl.setConstraints(MESSAGE,gc);add(MESSAGE);
		gc.gridx=0;gc.gridy=5;
		Label label_MELODY = new Label("MELODY:");
		gl.setConstraints(label_MELODY,gc);add(label_MELODY);
		gc.gridx=1;gc.gridy=5;
		MELODY = new TextField(40);
		MELODY.setText(".als");
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
		setVisible(true);
	}

	public void windowClosing(WindowEvent event) {
		System.exit(0);
	}
	public void windowClosed(WindowEvent event) {}
	public void windowOpened(WindowEvent event) {}
	public void windowIconified(WindowEvent event) {}
	public void windowDeiconified(WindowEvent event) {}
	public void windowActivated(WindowEvent event) {}
	public void windowDeactivated(WindowEvent event) {}

	public void sendMessage(String server, String sender,
							String receiver, String title,
							String message, String alsFile)
		throws IOException, ProtocolException {
		InetAddress IP = null;
		try{
			IP = InetAddress.getByName(server);
		}catch(UnknownHostException e){
			STATUS.append("UnknownHostException was caught , so I'll try to get Host by myself.\n");
			IP = InetAddress.getLocalHost();
		}
		try {
			sock = new Socket(IP, DEFAULT_PORT);
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

		//make boundary
		
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

		//read als file
		
		String alsData = new String();
		try{
			FileInputStream inStream = new FileInputStream(alsFile);
			BufferedInputStream bufIn = new BufferedInputStream( inStream );
			byte[] b = new byte[64];
			while(bufIn.read(b)!=-1){
				String tmp = new String(b);
				alsData+=tmp;
			}
		}catch(IOException ioerr){
			System.out.println( ioerr.toString() );
		}
		
		
		try {
			send.print("HELO " + server +CRLF);
			STATUS.append("send> HELO " + server + CRLF);
			send.flush();
			rstr = reply.readLine();
			STATUS.append("reply> " + rstr + CRLF);
			if (!rstr.startsWith("250")) {
				throw new ProtocolException(rstr);
			}
			send.print("MAIL FROM: "+ sender + CRLF);
			STATUS.append("send> MAIL FROM: "+ sender +CRLF);
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
			send.print("From: "+ sender +CRLF);
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
				sendMessage(SERVER.getText(), FROM.getText(),
							TO.getText(), TITLE.getText(),
							MESSAGE.getText(), MELODY.getText());
			}catch(Exception ex){
				STATUS.append(ex.toString());
				System.out.println(ex);
			}
		}
	}
	protected class CLEAR_ActionListener implements ActionListener{
		public void actionPerformed(ActionEvent e){
			TITLE.setText("");
			MESSAGE.setText("");
		}
	}
	
}




