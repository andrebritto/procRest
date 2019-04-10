package queue;

import java.io.IOException;
import java.net.UnknownHostException;

import org.apache.activemq.transport.stomp.StompConnection;

public class Producer {
	
	String fila="";
	
	public Producer(String queue) {
		this.fila=queue;
	}
	
	public void produce(String msg) {
		        
        StompConnection connection = new StompConnection();
        try {        	
			connection.open("localhost", 61613);
			connection.connect("admin", "password");
			connection.send("event", msg);
			connection.close();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        
	}
	

}
