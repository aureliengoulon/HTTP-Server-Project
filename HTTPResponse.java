import java.util.ArrayList;
import java.util.Iterator;
import java.io.*;

public class HTTPResponse extends HTTPMessage {

	public final static String OK_CODE = "200";
	public final static String NOTFOUND_CODE = "404";
	public final static String OK_MESSAGE = "OK";
	public final static String NOTFOUND_MESSAGE = "Not found";
	
	private ArrayList<String> content;
    private String code;
    private String message;
    
    public ArrayList<String> getContent() {
        return content;
    }

    public void addContent(String line) {
        this.content.add(line);
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void writeResponse(OutputStream os) {
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(os));
        
        String header = super.protocol + HTTPMessage.SEPARATOR + this.code + HTTPMessage.SEPARATOR + this.message;
        
        try {
        	bw.write(header);
        	bw.newLine();
        	bw.newLine();
        	
        	Iterator<String> iterator = content.iterator();
        	
        	while (iterator.hasNext()) {
        		String line = iterator.next();
        		if (line != null) {
        			bw.write(line);
        			bw.newLine();
        		}
        	}
        	
        	bw.newLine();bw.flush();
        } catch (IOException e) {
        	System.err.println(e.getMessage());
        }
    }

    public void display() {
    	System.out.println(super.protocol + HTTPMessage.SEPARATOR + this.code + HTTPMessage.SEPARATOR + this.message);
    }
}
