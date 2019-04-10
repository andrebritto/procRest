package procuracoes.dao;

import java.util.ArrayList;

import org.apache.logging.log4j.Logger;

import procuracoes.exception.ConfigException;
import procuracoes.exception.PFException;
import procuracoes.exception.PJException;
import procuracoes.model.Ambiente;


public class ContribuinteDAO {

	private static ContribuinteDAO instance = null;

	protected ContribuinteDAO() {

	}

	public static ContribuinteDAO getInstance() {
		if (instance == null) {
			instance = new ContribuinteDAO();
		}
		return instance;
	}

	Logger logger;

	GPDAO gpdao = GPDAO.getInstance();

	public void configuraAmbiente(Object _ambiente) throws ConfigException {
		// logger.info("Configurando Ambiente {}"+ _ambiente );
		
		if (_ambiente instanceof Integer) {
			gpdao.configuraAmbiente((int) _ambiente);
		} else if (_ambiente instanceof Ambiente) {
			gpdao.configuraAmbiente((Ambiente) _ambiente);
		} else {
			throw new ConfigException("Erro de Ambiente");
		}
	}

	// K03122 O03122AF - PF

	// K34624 O34624PJ - PJ

	public String getPF(String cpf) throws Exception {				 
		return this.getContribuinte("K03122  O03122AF" + cpf);
	}

	public String getPJ(String cnpj) throws Exception {
		return this.getContribuinte("K34624  O34624PJ" + cnpj);
	}

	public String getContribuinte(String query) throws Exception {
		String ret = "";

		gpdao = GPDAO.getInstance();
		ret = gpdao.execRotina(query, "");		
		if (!ret.subSequence(0, 2).equals("00")) {			
			throw new Exception(ret);
		}		 
		return ret;
	}

	public String getMatriz(String cnpj) throws PJException {
		gpdao = GPDAO.getInstance();
		String matriz = "";
		String codRet = "";
		String retorno = "";
		try {
			boolean temMais = true;
			while (temMais) {
				if (cnpj.equals(matriz)) {
					temMais = false;
				} else {
					retorno = gpdao.execRotina("K34624  O34624TT" + cnpj, "");
					matriz  = retorno.split(" ")[1];
					codRet  = retorno.substring(0, 2);
					cnpj = matriz;
					if (!"00".equals(codRet)) throw new PJException(matriz);
					
				}

			}

		} catch (Exception e) {
			throw (new PJException("Falha ao buscar Matriz"));
		}
		return matriz;
	}
	
	
	public static void main(String[] args) {
		ContribuinteDAO dao = ContribuinteDAO.getInstance();
		try {
//			15.209.117/0003-19
//			15209117000742
//			15.209.117/0009-04 *
			
			dao.configuraAmbiente(Ambiente.DESENVOLVIMENTO);
			System.out.println(dao.getMatriz("13558218"));
			
		} catch (ConfigException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (PJException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
