import java.io.IOException;
import java.net.*;

public class WebServer implements Runnable {

	public final static String HTTP_FILES_FOLDER = "./";
	
	private boolean stop;
	private int port;
	
	ServerSocket serversocket;
	
	public WebServer(int port) {
		this.stop = true;
		this.port = port;
	}

	@Override
	public void run() {
		stop = false;
		
		try {		
			serversocket = new ServerSocket(this.port);
			
			System.out.println("Listening on port " + this.port);
			
			while (stop == false) {
				Socket socket = serversocket.accept();
				RequestProcessor requestprocessor = new RequestProcessor(socket);
				requestprocessor.process();
			}
		} catch (IOException e) {
			System.err.println(e.getMessage());
		}
	}
	
	public void startServer() {
		Thread thread = new Thread(this);
		thread.start();
	}
	
	public void stopServer() {
		stop = true;
		try {
			serversocket.close();
		} catch (IOException e) {
			System.err.println(e.getMessage());
		}
	}
	
	public static void main(String[] args) {
		WebServer server = new WebServer(8080);
		server.startServer();
	}
}
