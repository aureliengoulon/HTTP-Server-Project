import java.io.*;

public class HTTPRequest extends HTTPMessage {

	public final static String GET_TYPE = "GET";
	
	private String file;
    private String type;
    
    public String getFile() {
        return file;
    }
    public void setFile(String file) {
        this.file = file;
    }
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
    
    public static HTTPRequest buildRequest(InputStream is) {
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        String line = null;
        HTTPRequest hr = new HTTPRequest();
        try {
            line = br.readLine();
            
            // \\s représente un espace
            // une requete est composée de trois String
            String[] requestComponents = line.split("\\s");
            
            if (requestComponents.length == 3) {
	            hr.setFile(requestComponents[1]);
	            hr.setType(requestComponents[0]);
	            hr.setProtocol(requestComponents[2]);
            } else {
            	System.err.println("Malformed request.");
            }
            
            boolean endRequest = false;
            
            while (endRequest == false) {
                line = br.readLine();
                if (line.equals("")) {
                	endRequest = true;
                }
            }
            
            return hr;
        } catch (IOException e){
            e.printStackTrace();
            return null;
        }
    }
    
    public void display() {
    	System.out.println(this.type + HTTPMessage.SEPARATOR + this.file + HTTPMessage.SEPARATOR + super.protocol);
    }
}
