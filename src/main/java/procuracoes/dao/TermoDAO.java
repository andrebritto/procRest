package procuracoes.dao;

import java.util.ArrayList;
import java.util.List;

import procuracoes.business.ContribuinteBC;
import procuracoes.model.Ambiente;

import br.gov.serpro.jada.JAdaException;

public class TermoDAO {

	private static TermoDAO instance = null;
	private static GPDAO gp = null;
	private Ambiente env = Ambiente.DESENVOLVIMENTO;

	protected TermoDAO() {

	}

	public void setAmbiente(Ambiente _env) {
		if (env == null) {
			env = Ambiente.DESENVOLVIMENTO;
		} else {
			env = _env;
		}
		gp.configuraAmbiente(env);
	}

	public static TermoDAO getInstance() {
		if (instance == null) {
			instance = new TermoDAO();
			gp = GPDAO.getInstance();
		}
		return instance;
	}

	public void getOpcao() {

//		gp.configuraAmbiente(env);

		ArrayList<String> arl = new ArrayList<String>();
		arl.add("29339895000175");

		String comando = "";
		String ret = "";

		try {
			for (String cnpj : arl) {
				comando = "EXEC O96026SN K34790  O34790012" + cnpj;
				ret = gp.execRotina(comando, "");
				System.out.println("Retorno: " + ret);

			}
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

	}

	public void getOpcaoPj(String cnpj) throws Exception {

//		gp.configuraAmbiente(env);

		String comando = "";
		String ret = "";

		comando = "EXEC O96026SN K34790  O34790012" + cnpj;
		ret = gp.execRotina(comando, "");
		System.out.println("Retorno: " + ret);

	}

	public void cancelaOpcao(String ni) {

	}

	public void opcaoDteParaPJ() {

		ArrayList<String> arl = new ArrayList<String>();

		final String ACAO_OPTAR = "1";
		final String ACAO_CANCELAR = "2";
//		JadaFactory jada = JadaFactory.getInstance();
		String cpfRespLegal = "";

		String comando = "";
		String strRetorno = "";

		ContribuinteBC bc = ContribuinteBC.getInstance();

//		arl.add("29338819000145");
//		arl.add("29340182000121");
//		arl.add("29339155000139");
//		arl.add("29338812000123");
		
		arl.add("29337459000167");
		arl.add("29339565000180");
		arl.add("29337464000170");
		arl.add("29338294000148");
		arl.add("29337633000171");
		arl.add("29337595000157");
		arl.add("29338786000133");
		
//		arl.add("29339203000199");
//		arl.add("29338038000150");
//		arl.add("29340547000118");
//		arl.add("29338755000182");
//		arl.add("29339382000164");
//		arl.add("29340172000196");
//		arl.add("29338494000109");
//		arl.add("29339832000119");
//		arl.add("29337723000162");
//		arl.add("29338628000183");
//		arl.add("29339012000127");
//		arl.add("29339050000180");
//		arl.add("29338549000172");
//		arl.add("29337393000105");
//		arl.add("29338638000119");
//		arl.add("29337997000151");
//		arl.add("29338184000186");
//		arl.add("29338235000170");
//		arl.add("29338020000159");
//		arl.add("29339837000141");
//		arl.add("29339228000192");
//		arl.add("29338963000181");
//		arl.add("29337894000191");
//		arl.add("29338467000128");
//		arl.add("29339895000175");
//		arl.add("00776764000173"); // Erro

		for (String cnpjOptante : arl) {
			try {
				bc.getPJ(cnpjOptante);
				cpfRespLegal = "00197750168"; // bc.getCpfResponsavelLegal(); - 40376516615

				comando = "K34790  O34790B2" + ACAO_CANCELAR + "00000000000" + cnpjOptante + cpfRespLegal
						+ "123.123.123.12301C319                                                                Autoridade Certificadora ACSERPRORFBv3                                21    125694";
				strRetorno = gp.execRotina(comando, "SET PARAMETER ML=32000");
				System.out.println("retorno: [" + cnpjOptante + "] " + strRetorno);

			} catch (JAdaException e) {
				System.err.println("NI: " + cnpjOptante);
				System.err.println(e.getMessage());
			} catch (Exception e) {
				System.err.println("NI: " + cnpjOptante);
				System.err.println(e.getMessage());
			}

		}

	}

	public static void main(String[] args) {
		TermoDAO dao = TermoDAO.getInstance();
		dao.setAmbiente(Ambiente.HOMOLOGACAO);
		dao.opcaoDteParaPJ();
		try {
//			dao.getOpcaoPj("29339895000175");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
