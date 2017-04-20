package todo.dao;

import java.util.ArrayList;

import javax.inject.Inject;
public class SistemaDAO {
	
	private static SistemaDAO instance = null;

	protected SistemaDAO() {

	}
	
	@Inject
	GPDAO gpdao;
	
	
	public static SistemaDAO getInstance() {		
		if (instance == null) {
			instance = new SistemaDAO();
		}
		return instance;
	}
	

	public ArrayList<ArrayList> getSistemas() {
		ArrayList<ArrayList> retArr = new ArrayList<ArrayList>();

		try {
			gpdao = GPDAO.getInstance();
			retArr = gpdao.execQuery("SELECT ISN AA AB AC AE FROM 047.182 WHERE AA>0 ORDER AA");
			
//			
//			for (ArrayList<String> procs : retArr) {
//				for (String proc : procs) {
//					System.out.println("->" + proc);				
//				}
//				
//			
//			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return retArr;
	}
	
	public ArrayList<String> getSistema(String codigo) {
		ArrayList<String> ret = new ArrayList<String>(); 

		try {
			gpdao = GPDAO.getInstance();
			ArrayList<ArrayList> retArr = gpdao.execQuery("SELECT ISN AA AB AC AE FROM 047.182 WHERE AA=" + codigo);			
			ret = retArr.get(0);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ret;
	}
	
//	public static void main(String[] args) {
//		ArrayList<ArrayList> sistemas = SistemaDAO.getInstance().getSistemas();
//	}
	/*
	 * 	Codigo de controle		procurador			vigencia
	 * 	9A2FA.0DE9F.CDFD5.AF1E8	190.078.093-34	30/12/2016 - 01/01/2017
		AEF2D.41A4E.D4009.C1EE3	190.078.093-34	16/02/2017 - 16/02/2018
	 * */
	
}
