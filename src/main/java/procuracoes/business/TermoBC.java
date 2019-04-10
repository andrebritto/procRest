package procuracoes.business;

import procuracoes.dao.TermoDAO;
import procuracoes.model.Ambiente;

public class TermoBC {
	private static TermoDAO dao = null;
	private static TermoBC instance = null;

	public static TermoBC getInstance() {
		if (instance == null) {
			instance = new TermoBC();
			dao = TermoDAO.getInstance();
		}
		return instance;
	}
	
	private void setAmbiente(Ambiente _env){
		dao.setAmbiente(_env);
	}
	
	public void cancelarOpcao(String ni){
		TermoDAO dao = TermoDAO.getInstance();
		dao.cancelaOpcao(ni);
	}		
}
