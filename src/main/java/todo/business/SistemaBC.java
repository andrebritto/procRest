package todo.business;

import java.util.ArrayList;

import todo.dao.SistemaDAO;
import todo.model.Sistema;

public class SistemaBC {
	
	static
	protected ArrayList<Sistema> sistemas;
	
	private static SistemaBC instance = null;
	
	protected SistemaBC() {

	}
	
	public static SistemaBC getInstance() {
		if (instance == null) {
			instance = new SistemaBC();
			sistemas = instance.recuperaSistemas();
		}
		return instance;
	}
	
	public ArrayList<Sistema> getSistemas(){
		return instance.sistemas;
	}
	
	private ArrayList<Sistema> recuperaSistemas(){	
	
		
		ArrayList<ArrayList> ret = SistemaDAO.getInstance().getSistemas();		
		Sistema s = new Sistema();
		ArrayList<Sistema> arrSist = new ArrayList<Sistema>();  
		for (ArrayList<String> arrayList : ret) {
			s.setIsn(arrayList.get(0));
			s.setCodigo(arrayList.get(1));
			s.setSigla(arrayList.get(2));
			s.setDescricao(arrayList.get(3));
			s.setTipoAcesso(arrayList.get(4));
			arrSist.add(s);
			System.out.println(s);
			s = new Sistema();			
		}
		
		return arrSist; 
	}
	
	public Sistema  getSistema(String codigo){
		
		Sistema s = new Sistema();		
		if ("88888".equals(codigo)){
			s.setIsn("0");
			s.setCodigo(codigo);
			s.setSigla("Todos os Serviços Existentes");
			s.setDescricao("Todos os serviços existentes e os que vierem a ser disponibilizados no sistema de Procurações Eletrônicas do e-CAC (destinados ao tipo do Outorgante - PF ou PJ), para todos os fins, inclusive confissão de débitos, durante o período de validade da procuração.");
			s.setTipoAcesso("3");
			return s;
		}
		
		ArrayList<String> sistema = new ArrayList<String>();
		sistema = SistemaDAO.getInstance().getSistema(codigo);
		s.setIsn(sistema.get(0));
		s.setCodigo(sistema.get(1));
		s.setSigla(sistema.get(2));
		s.setDescricao(sistema.get(3));
		s.setTipoAcesso(sistema.get(4));
		
		System.out.println(s);
		
		return s;
	}

	
}
