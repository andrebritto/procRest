package procuracoes.dao;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.MarkerManager;

import procuracoes.business.ContribuinteBC;
import procuracoes.model.Ambiente;
import br.gov.serpro.jada.JAdaConexao;
import br.gov.serpro.jada.JAdaException;
import br.gov.serpro.jada.JAdaResultado;

import br.gov.serpro.sqladaj.SQLAdaJ;
import br.gov.serpro.util.adabas.SqladaUtil;

public class GPDAO<T> {

	static Logger logger = LogManager.getLogger(GPDAO.class.getName());

	private static final Marker GPDAO_MARKER = MarkerManager
			.getMarker("GPDAO");
	private static final Marker CONFIGURACAO_MARKER = MarkerManager
			.getMarker("CONFIGURACAO_MARKER").setParents(
					GPDAO_MARKER);
	private static final Marker BUSCA_MARKER = MarkerManager.getMarker(
			"SELECT_MARKER").setParents(GPDAO_MARKER);
	private static final Marker INSERT_MARKER = MarkerManager.getMarker(
			"INSERT_MARKER").setParents(GPDAO_MARKER);
	private static final Marker DELETE_MARKER = MarkerManager.getMarker(
			"DELETE_MARKER").setParents(GPDAO_MARKER);
	private static final Marker UPDATE_MARKER = MarkerManager.getMarker(
			"UPDATE_MARKER").setParents(GPDAO_MARKER);

	protected static SQLAdaJ sqlada = null;

	protected Ambiente env = null;
	private String fileLog = null;
	private String fileSistema = null;
	private String filePreprocuracao = null;
	private String fileProcuracao = null;
	private String fileTermo = null;

	/**
	 * Deve ser chamada pelas classes derivadas para executar a rotina
	 * 
	 * @param logon
	 *                biblioteca a ser chamada
	 * @param nome
	 *                nome da rotina a ser chamada
	 * @param parametros
	 *                parametros que irão ser enviados para a rotina.
	 * @return o retorno da rotina na forma ret[0] = Código do retorno,
	 *         ret[1] = Retorno da Rotina.
	 * @throws Exception
	 * @throws AmbienteException
	 *                 se houver falha no ambiente como comunicação com o
	 *                 Grande Porte.
	 * @throws AplicacaoException
	 *                 se houver um erro retorno da rotina.
	 */

	private static GPDAO instance = null;

	protected GPDAO() {
	}

	public static GPDAO getInstance() {
		if (instance == null) {
			instance = new GPDAO();
			sqlada = new SQLAdaJ();
		}
		return instance;
	}

	public void configuraAmbiente(int valor) {
		// logger.info("cdAmnbiente {}" + valor);
		logger.debug(CONFIGURACAO_MARKER, "Valor rerente ao ambiente: "
				+ valor);
		Ambiente amb = null;
		switch (valor) {

		case 1:
			amb = Ambiente.DESENVOLVIMENTO;
			break;
		case 2:
			amb = Ambiente.HOMOLOGACAO;
			break;

		case 3:
			amb = Ambiente.PRODUCAO;
			break;
		default:
			amb = Ambiente.DESENVOLVIMENTO;
			break;
		}
		logger.debug(CONFIGURACAO_MARKER, "Ambiente configurado : "
				+ amb.name());
		configurarBanco(amb);
		this.env = amb;

	}

	public void configuraAmbiente(Ambiente _env) {
		configurarBanco(_env);
		this.env = _env;
	}

	public boolean autentica(String usr, String snh) throws Exception {
		int iCon = 0;
		boolean ret = false;

		iCon = sqlada.SAConnect(usr, snh, "DES-FISAO-ESA",
				"10.3.9.1:3001");
		fecharConexao(iCon);

		if (iCon >= 0) {
			ret = true;
		}

		return ret;
	}

	protected String execRotina(Marker marker, String comando, String param)
			throws Exception {
		return execRotina(comando, param);
	}

	protected String execRotina(String comando, String param)
			throws Exception {

		int codigoConexao = -1;
		codigoConexao = abrirConexao();
		String retorno = "";
		int valorRetornoComando = -1;

		if (StringUtils.isNotBlank(param)) {
			valorRetornoComando = sqlada.SACommand(codigoConexao,
					param);
			if (valorRetornoComando < 0) {
				throw (new Exception(sqlada.strException));
			}
		}

		sqlada.SACommand(codigoConexao, "EXEC O96026SN " + comando);		
		
		if (StringUtils.isEmpty(sqlada.strArea) || sqlada.strArea.contains("ERRO")) {
			throw (new Exception(sqlada.strArea));
		}
		
		
		valorRetornoComando = Integer.parseInt(sqlada.strArea
				.substring(0, 1));
		logger.debug("Comando: " + "EXEC O96026SN " + comando);
		logger.debug("sqlada.strArea: " + sqlada.strArea);
		logger.debug("valorRetornoComando: " + valorRetornoComando);

		if (valorRetornoComando != 0) {
			fecharConexao(codigoConexao);
			throw (new Exception(sqlada.strArea));
		} else {
			retorno = StringUtils.trim(sqlada.strArea);

		}
		fecharConexao(codigoConexao);
		return retorno;
	}
		

	protected void execUpdate(String comando, int conexao) throws Exception {
		logger.debug(UPDATE_MARKER, "Atualizando o sistema...");
		execOperacao(comando, conexao);
	}

	protected int executeLock(String comando) throws Exception {
		int codigoConexao = -1;
		codigoConexao = abrirConexao();
		int valorRetornoComando = sqlada.SACommand(codigoConexao,
				comando);
		if (valorRetornoComando < 0) {
			System.out.println("qlada.strException: "
					+ sqlada.strMsgErro);
			fecharConexao(codigoConexao);
			throw (new Exception(sqlada.strException));
		}
		return codigoConexao;

	}

	protected void execInsert(String comando) throws Exception {
		execOperacao(comando);
	}

	public void execDelete(String comando) throws Exception {
		execRotina(comando, "");

	}
	
	public void execBusca(String comando) throws Exception {
		execRotina(comando, "");

	}
	
	public ArrayList<T> execBusca(String comando, String param, Class<T> obj) throws Exception {		
		return null;

	}

	protected void execOperacao(String comando) throws Exception {
		int codigoConexao = abrirConexao();
		execOperacao(comando, codigoConexao);
	}

	protected void execOperacao(String comando, int codigoConexao)
			throws Exception {

		int valorRetornoComando = sqlada.SACommand(codigoConexao,
				"SET CONVERT ON");
		if (valorRetornoComando < 0) {
			logger.error("Erro ao efetuar SET CONVERT ON");
			fecharConexao(codigoConexao);
			throw (new Exception(sqlada.strException));
		} else {
			valorRetornoComando = sqlada.SACommand(codigoConexao,
					comando);
			if (valorRetornoComando < 0) {
				valorRetornoComando = sqlada.SACommand(
						codigoConexao, "ROLLBACK");
				logger.error("Efetuado rollback");
				fecharConexao(codigoConexao);
				throw (new Exception(sqlada.strException));
			} else {
				valorRetornoComando = sqlada.SACommand(
						codigoConexao, "COMMIT");
				if (valorRetornoComando < 0) {
					logger.error("Erro ao efetuar commit");
					fecharConexao(codigoConexao);
					throw (new Exception(
							sqlada.strException));
				}
				fecharConexao(codigoConexao);
			}

		}

	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	protected ArrayList<HashMap> exec_(String comando) throws Exception {
		ArrayList<HashMap> ret = new ArrayList<HashMap>();
		String strArea = "";
		logger.debug("executando...");
		JAdaResultado jr = JadaFactory.getInstance().executarQuery(
				comando);
		logger.debug("LayOut: " + jr.getLayout());
//		logger.info("Registro: " + jr.getRegistro());
//		logger.info("qtds: " + jr.getQtdRegistros());
//		jr.obterProximo();
		
		String[] str =  jr.getRegistro().split(",");
		ArrayList<String> tams = new ArrayList<>();
		int j=0;
		for(int i=1;i<str.length;i++){

			if((i%2)!=0){
				tams.add(str[i]);
			}
			
		}
		
		
		try{			
			do{
				logger.debug("Registro: " + jr.getRegistro());
				logger.debug(getEntity(jr.getRegistro(), tams));
			}while (jr.obterProximo());

		}catch (Exception e) {
				//e.printStackTrace();
		}

		return ret;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	protected ArrayList<HashMap> exec(String comando) throws Exception {
		logger.debug("Executando a query ...: " + comando);
		ArrayList<HashMap> ret = new ArrayList<HashMap>();
		String strArea = "";
		int codigoConexao = -1;
		String param = comando.substring("SELECT".length(),
				comando.indexOf("FROM"));
		codigoConexao = abrirConexao();
		int valorRetornoComando = sqlada.SACommand(codigoConexao,
				comando);
		
		strArea = sqlada.strArea.replace(",U", "#U");
		strArea = strArea.replace(",A", "#A");
		strArea = StringUtils.trim(strArea);

		String[] arrStrArea = strArea.split(",");

		ArrayList<String> arrLengthArea = new ArrayList<String>();

		for (String string : arrStrArea) {
			if (string.contains("#")) {
				arrLengthArea.add(string.split("#")[0]);
			}
		}
		if (valorRetornoComando < 0) {
			logger.error("Comando: " + comando + ". Erro Sqlada: "
					+ sqlada.strArea);
			throw new Exception("Comando: " + comando
					+ ". Erro Sqlada: "
					+ sqlada.strMsgErro);
		}		
		
		int codErroNext = 0;
		String registro = "";
		HashMap<String, String> map = new HashMap<>();

		for (int i = 0; i < valorRetornoComando; i++) {
			codErroNext = sqlada.SAGetNext(codigoConexao);
			// TODO: colocar o getLine dentro do getEntity
			logger.debug("codErroNext: " + codErroNext);

			if (codErroNext < 0) {
				fecharConexao(codigoConexao);
				throw (new Exception(sqlada.strMsgErro));
//				logger.error("Mensagem de erro: " + sqlada.strMsgErro);
			} else {
				registro = getEntity(sqlada.strArea, arrLengthArea);
				map = SqladaUtil.selectToHash(param, registro);
				strArea = StringUtils.trim(sqlada.strArea);
				ret.add(map);
			}
		}

		fecharConexao(codigoConexao);
		return ret;
	}
	
	

	private static String getLine(List entrada) {
		StringBuilder linha = new StringBuilder();
		String retorno = "";

		for (Object l : entrada.toArray()) {
			linha.append((String) l).append(",");
		}
		retorno = linha.replace(linha.lastIndexOf(","), linha.length(),
				"").toString();
		return retorno;
	}

	private String getEntity(String entrada, ArrayList<String> tams)
			throws Exception {
		int passo = 0;
		int tamanho = 0;
		String campo = "";
		List<String> ret = new ArrayList<>();
		logger.debug("Registro Adabas: " + entrada);
		if (StringUtils.isEmpty(entrada))
			throw new Exception("erro no registro adabas");
		try {

			for (String tam : tams) {
				tamanho = Integer.parseInt(tam);
				campo = entrada.substring(passo,
						passo + tamanho)
						.replace("+", "")
						.replace(",", ".");
				if (!campo.equals("00000")) {
					ret.add(campo);
				}
				passo = passo + tamanho;
			}
		} catch (Exception ex) {
			logger.error("Erro ao extrair dados da consulta: ",
					ex.getMessage());
			ex.printStackTrace();
		}

		return getLine(ret);
	}

	/**
	 * Abre a conexão com o MainFrame
	 * 
	 * @return código da conexão retornado
	 * @throws AmbienteException
	 */

	private void configurarBanco(Ambiente env) {
		if (env == null) {
			env = Ambiente.DESENVOLVIMENTO;
		}

		logger.debug(CONFIGURACAO_MARKER, "Rodando em : " + env.name());

		switch (env) {
		case DESENVOLVIMENTO:
			fileSistema = " 047.182 ";
			fileLog = " 047.183 ";
			filePreprocuracao = "047.335";
			fileProcuracao = " 047.185 ";

			break;
		case HOMOLOGACAO:
			fileSistema = " 197.182 ";
			fileLog = " 197.183 ";
			filePreprocuracao = "197.335 ";
			fileProcuracao = " 197.185 ";
			
			fileTermo = " 197.185 ";

			break;
		case PRODUCAO:
			fileSistema = " 147.182 ";
			fileLog = " 147.183 ";
			filePreprocuracao = "147.335";
			fileProcuracao = " 147.185 ";

			break;

		default:
			break;
		}
	}

	protected int abrirConexao() {
		int iCon = 0;
		logger.debug("Abrindo conexão");
		try {

			switch (env) {
			// Pra delete
			// case DESENVOLVIMENTO:
			//
			// iCon = sqlada.SAConnect("68281455500", "",
			// "DES-FISAO-ESA", "10.3.9.1:3001");
			// break;
			case DESENVOLVIMENTO:
				// DES-FISAO-ESA -> para deletar sistema
				iCon = sqlada.SAConnect("00000000191",
						"PR0CURAC0ES34624",
						"PROCURACOES-D",
						"10.3.9.1:3001");
				break;
			case HOMOLOGACAO:
				iCon = sqlada.SAConnect("00000000191",
						"PR0CURAC0ES34624",
						"PROCURACOES-H",
						"10.3.10.15:3004"); // Procurações
				// iCon = sqlada.SAConnect("00000000191",
				// "opcaotermosrf",
				// "TERMO          TERMO     ",
				// "10.3.10.15:3004"); // DTE
				// iCon = sqlada.SAConnect("68281455500",
				// "qw456123",
				// "DES-FISAO-ESA", "10.3.10.15:3004"); // DTE

				break;
			case PRODUCAO:
				iCon = sqlada.SAConnect("00000000191",
						"JANSPE34624TRL",
						"PROCURACOES", "10.3.10.3:3012");
				break;
			default:
				iCon = sqlada.SAConnect("00000000191",
						"PR0CURAC0ES34624",
						"PROCURACOES-D",
						"10.3.9.1:3001");
				break;
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if (iCon < 0) {
			System.out.println("Error[Abrindo conexão]: "
					+ sqlada.strMsgErro);
		}

		int iQtd = sqlada.SACommand(iCon, "SET PARAMETER ML=32000");

		if (iQtd < 0) {
			System.out.println("error[Setando PARAMETER]: "
					+ sqlada.strMsgErro);
		}

		iQtd = sqlada.SACommand(iCon, "SET CONVERT ON");

		if (iQtd < 0) {
			System.out.println("error[Setando CONVERT ON]: "
					+ sqlada.strMsgErro);
		}

		return iCon;

	}

	/**
	 * Fecha a conexão com o MainFrame
	 * 
	 * @param conexaoGrandePorte
	 *                código da conexão retornado pelo método abrirConexao
	 */
	protected void fecharConexao(int conexaoGrandePorte)
			throws IOException, Exception {
		logger.debug("Fechando conexão");
		if (conexaoGrandePorte >= 0) {
			sqlada.SADisconnect(conexaoGrandePorte);
		}

	}

	public int getNext(int conexaoGrandePorte) {
		return sqlada.SAGetNext(conexaoGrandePorte);
		
	}
	
	
	public ArrayList<T> execQuery(String qry, Class<T> obj)
			throws Exception {
		ArrayList<HashMap> retArr = (ArrayList<HashMap>) execQuery(qry);
		ArrayList<T> retorno = new ArrayList<T>();

		for (HashMap hash : retArr) {
			retorno.add((T) SqladaUtil.hashToObject(obj, hash));

		}
		return retorno;
	}

	@SuppressWarnings("rawtypes")
	public ArrayList<HashMap> execQuery(String qry) throws Exception {
		return exec(qry);
	}

	public void opcaoDte() {

		ArrayList<String> arl = new ArrayList<String>();
		GPDAO gp = GPDAO.getInstance();
		gp.configuraAmbiente(Ambiente.HOMOLOGACAO);
		final String ACAO_OPTAR = "1";
		final String ACAO_CANCELAR = "2";
		// JadaFactory jada = JadaFactory.getInstance();
		String cpfRespLegal = "";

		String comando = "";
		String strRetorno = "";

		ContribuinteBC bc = ContribuinteBC.getInstance();
		// Meus testes em Homologação :
		// Certificado ->
		// /grupos-DESDR/DE509/CertificadosDigitais/Certificados_Verificados/115481_06044691000101.p12
		// arl.add("06044691000101");

		// Certificado ->
		// /grupos-DESDR/DE509/CertificadosDigitais/Certificados_Verificados/115480_05300543000148.p12
		// arl.add("05300543000148");

		/*
		 * arl.add("00712779000178"); arl.add("00718424000196");
		 * arl.add("00718752000192"); arl.add("00720771000153");
		 * arl.add("00722202000147"); arl.add("00729027000110");
		 * arl.add("00731673000111"); arl.add("00735015000106");
		 * arl.add("00737920000197"); arl.add("00740208000147");
		 * arl.add("00745064000111"); arl.add("00749691000120");
		 * arl.add("00749777000153"); arl.add("00752961000152");
		 * arl.add("00760054000155"); arl.add("00760069000113");
		 * arl.add("00762472000181"); arl.add("00766374000112");
		 * arl.add("00779448000155"); arl.add("00791940000146");
		 * arl.add("00085144000198"); arl.add("00085696000104");
		 * arl.add("00086231000160"); arl.add("00092237000140");
		 * arl.add("00093104000198"); arl.add("84649136000117");
		 * arl.add("11101266000147"); arl.add("10728291000192");
		 * arl.add("03948146000151"); arl.add("00193975000183");
		 * arl.add("00163788000157"); arl.add("00124492000127");
		 * arl.add("00111578000115"); arl.add("07831043000168");
		 * arl.add("11406965000103"); arl.add("15126437000143");
		 * arl.add("04527335000113"); arl.add("24933830000130");
		 * arl.add("03797526000132"); arl.add("26461699000180");
		 * arl.add("14372148000161"); arl.add("07237373000120");
		 * arl.add("05914650000166"); arl.add("33435231000187");
		 */
		arl.add("00720669000158"); // Erro
		arl.add("00776764000173"); // Erro
		arl.add("00786301000192"); // Erro
		arl.add("00195224000104"); // Erro
		arl.add("00164406000100"); // Erro
		arl.add("00336701000104"); // Erro
		arl.add("16404287000155"); // Erro

		for (String cnpjOptante : arl) {
			try {
				bc.getPJ(cnpjOptante);
				cpfRespLegal = "40376516615"; // bc.getCpfResponsavelLegal();
				// System.out.println("Rsp legal: " +
				// cpfRespLegal);
				comando = "K34790  O34790B2"
						+ ACAO_OPTAR
						+ "00000000000"
						+ cnpjOptante
						+ cpfRespLegal
						// +
						// "123.123.123.12335:30:31:33:30:35:30:38:31:39:31:37:33:31:35:32                       Autoridade Certificadora ACSERPRORFBv3                                21    125694";
						+ "123.123.123.12301C319                                                                Autoridade Certificadora ACSERPRORFBv3                                21    125694";
				// jada.executarComando(comando);
				strRetorno = gp.execRotina(comando,
						"SET PARAMETER ML=32000");
				System.out.println("retorno: [" + cnpjOptante
						+ "] " + strRetorno);

			} catch (JAdaException e) {
				System.err.println("NI: " + cnpjOptante);
				System.err.println(e.getMessage());
			} catch (Exception e) {
				System.err.println("NI: " + cnpjOptante);
				System.err.println(e.getMessage());
			}

		}

	}

	// public static void main_(String[] args) {
	// GPDAO gp = GPDAO.getInstance();
	// gp.setAmbiente(Ambiente.HOMOLOGACAO);
	//
	// ArrayList<String> arl = new ArrayList<String>();
	// // arl.add("05300543000148");
	// arl.add("06044691000101");
	//
	// /*
	// * Des - 25 Hom - 175 Prod - 178
	// */
	//
	// String query = "";
	// try {
	// for (String cnpj : arl) {
	// query = "SELECT ISN AA AB AC AD AE FROM 175.056 WHERE AE = "
	// + cnpj;
	// System.out.println("====================================");
	// @SuppressWarnings("rawtypes")
	// ArrayList<ArrayList> ret = gp.execQuery(query);
	// for (ArrayList<String> saida : ret) {
	// System.out.println("ISN: " + saida.get(0));
	// System.out.println("CPF: " + saida.get(4));
	// System.out.println("CNPJ: " + saida.get(5));
	// System.out.println("Data: " + saida.get(1));
	// System.out.println("Hora: " + saida.get(2));
	// if ("1".equals(saida.get(3))) {
	// System.out.println("Optante");
	// } else if ("2".equals(saida.get(3))) {
	// System.out.println("Não Optante");
	// } else {
	// System.out.println("Opção inválida");
	// }
	// System.out
	// .println("----------------------------------------------");
	// }
	// }
	// } catch (Exception e1) {
	// // TODO Auto-generated catch block
	// e1.printStackTrace();
	// }
	//
	// }

	public String getFileLog() {
		return fileLog;
	}

	public void setFileLog(String fileLog) {
		this.fileLog = fileLog;
	}

	public String getFileSistema() {
		// System.out.println("pegando: " + fileSistema);
		return fileSistema;
	}

	public void setFileSistema(String fileSistema) {
		// System.out.println("setando : " + fileSistema);
		this.fileSistema = fileSistema;
	}

	public String getFilePreprocuracao() {
		return filePreprocuracao;
	}

	public void setFilePreprocuracao(String filePreprocuracao) {
		this.filePreprocuracao = filePreprocuracao;
	}

	public String getFileProcuracao() {
		return fileProcuracao;
	}

	public void setFileProcuracao(String fileProcuracao) {
		this.fileProcuracao = fileProcuracao;
	}
	
	public static void main(String[] args) {
		GPDAO gp = GPDAO.getInstance();
		gp.env = Ambiente.HOMOLOGACAO;
		
		int codigoConexao = -1;
		codigoConexao = gp.abrirConexao();
		String retorno = "";
		int valorRetornoComando = -1;
		
		String comando = "K34624  O34624AF0FF7ECC6BABF21EEC4AFC";

		sqlada.SACommand(codigoConexao, "EXEC O96026SN " + comando);
				
		valorRetornoComando = Integer.parseInt(sqlada.strArea.substring(0, 1));
		
		System.out.println("valorRetornoComando: " + valorRetornoComando);
		System.out.println("sqlada: " + sqlada.strArea);
		sqlada.SAGetNext(codigoConexao);		
		System.out.println("sqlada: " + sqlada.strArea);
		sqlada.SAGetNext(codigoConexao);		
		System.out.println("sqlada: " + sqlada.strArea);
		
		if (valorRetornoComando != 0) {
			try {
				gp.fecharConexao(codigoConexao);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}			
		} else {
			retorno = StringUtils.trim(sqlada.strArea);
			
			do {
				System.out.println("Retorno: " + retorno);
				valorRetornoComando = sqlada.SACommand(codigoConexao, "EXEC O96026SN " + "PROXIMO#");	
				System.out.println("valorRetornoComando: " + valorRetornoComando);
				System.out.println("msg: " + sqlada.strArea);
			}while(!(valorRetornoComando== 0));

		}
		try {
			gp.fecharConexao(codigoConexao);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
	}

}
