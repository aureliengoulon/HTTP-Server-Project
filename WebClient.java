import java.io.*;
import java.net.*;

/** Client réseau capable d'envoyer une requête http à un serveur web
* et d'en afficher la réponse dans une console
*/
public class WebClient {
	
    private String server;
    private int port;
    private Socket socket;
    
    public WebClient(String server, int port) {
        this.server = server;
        this.port = port;
    }
    
    public void connect() throws IOException{
        InetAddress ia = InetAddress.getByName(server);
        socket = new Socket(ia,port);
        System.out.println(socket);
    }
    
    public void sendRequest(String request) throws IOException {
        OutputStream os = socket.getOutputStream();
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(os));
        bw.write(request);
        bw.flush();
    }
    
    public void printResponse() throws IOException {
        InputStream is = socket.getInputStream();
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        String line = null;
        do {
            line = br.readLine();
            System.out.println(line);
        } while (line != null);
        br.close();
    }
    
    public void close() throws IOException{
        socket.close();
        System.out.println("Connection closed");
    }
    
    public static void main(String[] args) {
        // TODO Auto-generated method stub
        WebClient client = new WebClient("google.com", 80);
        
        try {
        	client.connect();
        	client.sendRequest("GET / HTTP 1.1 \n\n");
        	client.printResponse();
            Thread.sleep(500);
            client.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
