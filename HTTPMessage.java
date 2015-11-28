public class HTTPMessage {

	public final static String SUPPORTED_HTTP_VERSION = "HTTP/1.1";
	public final static String SEPARATOR = " ";
	
	// protocole utilisŽ (1.1 est sa version)
    protected String protocol;

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }
}
