import java.awt.BorderLayout; 
import java.beans.PropertyVetoException;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JDesktopPane;
import javax.swing.JInternalFrame;
import java.io.InputStream;
import java.util.zip.*;
import java.net.Socket;
import java.io.IOException;

class CreateFrame extends Thread {
	String width = "", height = "";
	private JFrame frame = new JFrame();
	private JDesktopPane desktop = new JDesktopPane();
	private Socket cSocket = null;
	private JInternalFrame internalFrame = new JInternalFrame("Server Screen", true, true, true);
	private JPanel cPanel = new JPanel();
	public CreateFrame(Socket cSocket, String width, String height) {
		this.width = width;
		this.height = height;
		this.cSocket = cSocket;
		start();
	}
	public void drawUI() {
		frame.add(desktop, BorderLayout.CENTER);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setExtendedState(frame.getExtendedState() | JFrame.MAXIMIZED_BOTH);
		frame.setVisible(true);
		internalFrame.setLayout(new BorderLayout());
		internalFrame.getContentPane().add(cPanel, BorderLayout.CENTER);
		internalFrame.setSize(100, 100);
		desktop.add(internalFrame);

		try {
			internalFrame.setMaximum(true);
		} catch(PropertyVetoException ex) {
			ex.printStackTrace();
		}
		cPanel.setFocusable(true);
		internalFrame.setVisible(true);
	}

	public void run() {
		InputStream in = null;
		drawUI();
		try {
			in = cSocket.getInputStream();
		} catch(IOException e) {
			e.printStackTrace();
		}
		new ReceivingScreen(in, cPanel);
		new SendEvents(cSocket, cPanel, width, height);
	}
}