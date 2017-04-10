package todo.dao;

import java.util.ArrayList;
import javax.inject.Inject;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
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

	

	public ArrayList<String> getSistemas() {
		ArrayList<String> retArr = new ArrayList<String>();

		try {
			gpdao = GPDAO.getInstance();
			retArr = gpdao.execQuery("SELECT AA AB AC AE FROM 047.182 WHERE AA>0 ORDER AA");

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return retArr;
	}
	
	
	/*
	 * 	Codigo de controle		procurador			vigencia
	 * 	9A2FA.0DE9F.CDFD5.AF1E8	190.078.093-34	30/12/2016 - 01/01/2017
		AEF2D.41A4E.D4009.C1EE3	190.078.093-34	16/02/2017 - 16/02/2018
	 * */
	
}
