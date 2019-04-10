package procuracoes.dao;

public class TestJMS {

	
	
	
	public static void main(String[] args) throws Exception {

		QueueDAO q = new QueueDAO("serpro.queue.34624.procuracoes.procuracao");
//		for(int i=0;i<50;i++) {//			
//			q.produce("From Java: " + Math.random());						
//		}
//		q.produce("SHUTDOWN");
		String msg = "";
		while (true) {
			msg = q.consume();
			System.out.println(msg);
			if("SHUTDOWN".equals(msg)){
				break;
			}
		}
		
	}

}