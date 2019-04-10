package procuracoes.dao;

import javax.jms.JMSException;

import org.apache.commons.lang3.StringUtils;

import queue.Consumer;
import queue.Producer;

public class QueueDAO {

	private String fila="";
	
	public QueueDAO(String queue) throws JMSException {
		if (StringUtils.isEmpty(queue)) throw new JMSException("Fila não pode ser vazia");
		this.fila = queue;
	}
	
	public String consume() {
		Consumer leitor = new Consumer(this.fila);		
		return leitor.consume();
	}

	public void produce(String msg) {
		Producer gravador =  new Producer(this.fila);
		gravador.produce(msg);
	}
	
}
