package procuracoes.business;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import procuracoes.dao.AutenticacaoSqlAda;


public class AutenticacaoBC {

	static Logger logger = LoggerFactory.getLogger(AutenticacaoBC.class);
	
	protected AutenticacaoBC() {

	}
	
	public void autenticar(String cpf, String pwd) throws Exception {
		AutenticacaoSqlAda dao = AutenticacaoSqlAda.getInstance();
		boolean retorno = dao.autenticar(cpf, pwd);		 
	}
	
	
	public static void main(String[] args) {
		String cpf = "00012345678";
		String pwd = "op321654";
		
		try {
			if (AutenticacaoSqlAda.getInstance().autenticar(cpf, pwd)){
				logger.info("Antenticado");				
			}else{
				logger.info("Não Antenticado");
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
