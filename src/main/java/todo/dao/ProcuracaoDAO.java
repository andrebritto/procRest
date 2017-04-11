package todo.dao;

import java.util.ArrayList;

import javax.inject.Inject;
public class ProcuracaoDAO {
	
	private static ProcuracaoDAO instance = null;

	protected ProcuracaoDAO() {

	}
	
	public static ProcuracaoDAO getInstance() {
		if (instance == null) {
			instance = new ProcuracaoDAO();
		}
		return instance;
	}
	
	@Inject
	GPDAO gpdao;
	
	
	
	
	/*
	 * SELECT ISN AA AC AE AG AI AK001 AK002 AK003 AK004 AK005 AK006 AK007 AK008
	 * AK009 AK010 AK011 AK012 AK013 AK014 AK015 AK016 AK017 AK018 AK019 AK020
	 * AK021 AK022 AK023 AK024 AK025 AK026 AK027 AK028 AK029 AK030 AK031 FROM
	 * 147.185 WHERE
	 * S4=(NR-CPF-NR-CPF-CNPJ-PROCURADOR_1,NR-CPF-CNPJ-TITULAR_1,DT
	 * -INICIO-VIGENCIA_1,HR-INICIO-VIGENCIA_1) TO
	 * S4=(NR-CPF-NR-CPF-CNPJ-PROCURADOR_2
	 * ,NR-CPF-CNPJ-TITULAR_2,DT-INICIO-VIGENCIA_2,HR-INICIO-VIGENCIA_2)
	 */
	public ArrayList<String> getProcuracao(String ni_titular,
			String ni_procurador) {
		ArrayList<String> retorno = new ArrayList<String>();
		ArrayList<ArrayList> retArr = new ArrayList<ArrayList>();
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT ISN AA AC AE AG AI ");
		sb.append("AK001 AK002 AK003 AK004 AK005 AK006 AK007 AK008 AK009 AK010 ");
		sb.append("AK011 AK012 AK013 AK014 AK015 AK016 AK017 AK018 AK019 AK020 AK021 AK022 AK023 AK024 ");
		sb.append("AK025 AK026 AK027 AK028 AK029 AK030 AK031 ");
		sb.append("FROM 047.185 ");
		sb.append("WHERE S4=(" + ni_procurador + "," + ni_titular
				+ ",00000000,000000) ");
		sb.append("TO S4=("+ ni_procurador + "," + ni_titular
				+ ",99999999,999999)");
		try {
			gpdao = GPDAO.getInstance();
			retArr = gpdao.execQuery(sb.toString());
			
			for (ArrayList<String> procs : retArr) {
				for (String proc : procs) {
					System.out.println("->" + proc);				
				}
				
			
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return retorno;
	}




	/*
	 * 	Codigo de controle		procurador			vigencia
	 * 	9A2FA.0DE9F.CDFD5.AF1E8	190.078.093-34	30/12/2016 - 01/01/2017
		AEF2D.41A4E.D4009.C1EE3	190.078.093-34	16/02/2017 - 16/02/2018
	 * */
	
}
