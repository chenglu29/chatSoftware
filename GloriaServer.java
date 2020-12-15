import java.net.*;
import java.io.*;
import java.util.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
class GloriaServer {
	public static void main(String[] args)throws java.io.IOException {
		EventQueue.invokeLater(new Runnable(){
			public void run(){
				SimpleFrame frame=new SimpleFrame();
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				frame.setVisible(true);

			}
		});
/*	ServerSocket s = new ServerSocket(8088);
	while (true) {
		Socket incoming = s.accept();
		PrintWriter toClient = new PrintWriter(incoming.getOutputStream());
		toClient.println(new Date());
		toClient.flush();
		incoming.close();
		}  */
	}
}

class SimpleFrame extends JFrame
{
	public SimpleFrame(){
		setTitle("Gloria Server");
		setSize(300,500);
		JButton connectButton=new JButton("Connect");
		connectButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent event)
			{
				try {
					ServerSocket s = new ServerSocket(8088);
					Socket ss=s.accept();
					outputStream=ss.getOutputStream();
					inputStream=ss.getInputStream();
					ServerReceive sr=new ServerReceive();
					Thread t=new Thread(sr);
					t.start();
					//new Thread(new ServerReceive()).start();
				}
				catch (NumberFormatException e1) {
					e1.printStackTrace();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
			});
		
		inputLine=new JTextField("",25);	
		textArea=new JTextArea(20,25);
		JPanel p=new JPanel();
		JButton sendButton=new JButton("Send");
		sendButton.addActionListener(new ActionListener(){
		public void actionPerformed(ActionEvent e){	
				sent=inputLine.getText();
				sent=" From server : "+sent+"\n";
				textArea.append(sent);
				PrintWriter toClient=new PrintWriter(outputStream);
				toClient.print(sent);
				toClient.flush();	
			}
		});	
		p.add(connectButton);
		p.add(inputLine);
		p.add(textArea);
		p.add(sendButton);
		add(p);
	}

	public static JTextField inputLine;
	public static JTextArea textArea;
	public ServerSocket s;
	public static OutputStream outputStream;
	public static InputStream inputStream;
	public Socket ss;
	public String sent="";
}

class ServerReceive implements Runnable{
	public void run(){
		while (true){						
			Scanner scan = new Scanner(SimpleFrame.inputStream);
			SimpleFrame.textArea.append(scan.nextLine()+"\n");
		}
	}
}