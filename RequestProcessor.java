import java.io.*;
import java.net.Socket;

public class RequestProcessor implements Runnable {

	private static long REQUEST_NUMBER = 0;
	
	private Socket socket;
	
	public RequestProcessor(Socket socket) {
		this.socket = socket;
	}
	
	public void process() {
		Thread thread = new Thread(this);
		RequestProcessor.REQUEST_NUMBER++;
		thread.start();
	}

	@Override
	public void run() {
		try {
			System.out.println("Request processor started");
			System.out.println("Request #" + RequestProcessor.REQUEST_NUMBER);
			System.out.println("Client: " + socket.getInetAddress().getHostAddress());
			
			HTTPRequest request = HTTPRequest.buildRequest(socket.getInputStream());
			System.out.println("Request: " + request);
			
			if (request.getProtocol().equals(HTTPMessage.SUPPORTED_HTTP_VERSION)) {
				if (request.getType().equals(HTTPRequest.GET_TYPE)) {
					File file = new File(WebServer.HTTP_FILES_FOLDER + request.getFile());
					
					HTTPResponse response = new HTTPResponse();
					response.setProtocol(HTTPMessage.SUPPORTED_HTTP_VERSION);
					
					if (file.exists()) {
						System.out.println("Found file: " + file);
						a
						response.setCode(HTTPResponse.OK_CODE);
						response.setMessage(HTTPResponse.OK_MESSAGE);
						
						BufferedReader br = new BufferedReader(new FileReader(file));
						
						String line = null;
						
						do {
							line = br.readLine();
							response.addContent(line);
						} while (line != null);
						br.close();
					} else {
						System.out.println("Not found file: " + file);
						
						response.setCode(HTTPResponse.NOTFOUND_CODE);
						response.setMessage(HTTPResponse.NOTFOUND_MESSAGE);
					}
					
					System.out.println("Response: " + response);
					
					response.writeResponse(socket.getOutputStream());
				} else {
					System.out.println("Unsupported request type: " + request.getType());
				}
			} else {
				System.out.println("Unsupported request protocol: " + request.getProtocol());
			}
			
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		System.out.println("Request processor stopped.");
	}
}
