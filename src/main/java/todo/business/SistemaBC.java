package todo.business;

import java.util.ArrayList;

import todo.dao.SistemaDAO;
import todo.model.Sistema;

public class SistemaBC {
	
	
	
	
	private static SistemaBC instance = null;
	
	protected SistemaBC() {

	}
	
	public static SistemaBC getInstance() {
		if (instance == null) {
			instance = new SistemaBC();
		}
		return instance;
	}
	
	public ArrayList<Sistema> getSistemas(){	
	
		
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
		ArrayList<String> sistema = new ArrayList<String>();
		sistema = SistemaDAO.getInstance().getSistema("00162");
		s.setIsn(sistema.get(0));
		s.setCodigo(sistema.get(1));
		s.setSigla(sistema.get(2));
		s.setDescricao(sistema.get(3));
		s.setTipoAcesso(sistema.get(4));
		
		System.out.println(s);
		
		return s;
	}

	
}
