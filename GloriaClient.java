import java.net.*;
import java.io.*;
import java.util.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
class GloriaClient {
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
		setTitle("Gloria Client");
		setSize(300,500);
		JButton connectButton=new JButton("Connect");
		connectButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent event)
			{
				try {
				//	ServerSocket s = new ServerSocket(8088);
					s=new Socket(InetAddress.getLocalHost(),8088);
					inputStream=s.getInputStream();			   
					outputStream=s.getOutputStream();	
					new Thread(new ClientReceive()).start();
					sent=" From Client: Connetction sucessful"+"\n";
					textArea.append(sent);
					PrintWriter toServer=new PrintWriter(outputStream);
					toServer.print(sent);
					toServer.flush();

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
				sent=" From Client : "+sent+"\n";
				textArea.append(sent);
				PrintWriter toServer=new PrintWriter(outputStream);
				toServer.print(sent);
				toServer.flush();
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
	public Socket s;
	public static OutputStream outputStream;
	public static InputStream inputStream;
	public Socket ss;
	public String sent="";
}
class ClientReceive implements Runnable{
	public void run(){
		while (true){			
			Scanner scan = new Scanner(SimpleFrame.inputStream);
		    SimpleFrame.textArea.append(scan.nextLine()+"\n");
		    }
		}
}