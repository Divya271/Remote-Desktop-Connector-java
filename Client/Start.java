import javax.swing.JOptionPane;
import java.net.Socket;

class Start {
	static String port = "2020";
	public static void main(String[] args) {
		String ip = JOptionPane.showInputDialog("enter ip address");
		new Start().initialize(ip,Integer.parseInt(port));
	}

	public void initialize(String ip, int port) {
		try {
			Socket sc = new Socket(ip,port);
			System.out.println("conneting to the server");
			Authentication frame1 = new Authentication(sc);
			frame1.setSize(300, 80);
			frame1.setLocation(500, 300);
			frame1.setVisible(true); 
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
}