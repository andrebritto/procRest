package queue;

import java.io.IOException;
import java.net.UnknownHostException;

import org.apache.activemq.transport.stomp.Stomp.Headers.Subscribe;
import org.apache.activemq.transport.stomp.StompConnection;
import org.apache.activemq.transport.stomp.StompFrame;

public class Consumer {

	String fila="";
	
	public Consumer(String queue) {
		this.fila = queue;
	}
	
	public String consume() {
		 
		StompConnection connection = new StompConnection();
		StompFrame message = null;
		String ret = "";
		long waitTimeOut = 10000;
		try {
			connection.open("localhost", 61613);
			connection.connect("admin", "password");			
			connection.subscribe("event", Subscribe.AckModeValues.CLIENT);
			message = connection.receive(waitTimeOut);
			connection.ack(message);
			connection.disconnect();
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
		         
		return message.getBody();
	}
	
	
}
