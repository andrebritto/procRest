package procuracoes.dao;

import java.util.ArrayList;
import java.util.HashMap;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.MarkerManager;

import procuracoes.model.Ambiente;
import procuracoes.model.Log;
import procuracoes.model.Procuracao;
import procuracoes.model.Sistema;
import br.gov.serpro.util.adabas.SqladaUtil;

public class ProcuracaoDAO {

	static Logger logger = LogManager.getLogger(ProcuracaoDAO.class
			.getName());

	private static final Marker PROCURACAO_MARKER = MarkerManager
			.getMarker("PROCURACAO");
	private static final Marker BUSCA_MARKER = MarkerManager.getMarker(
			"BUSCA_MARKER").setParents(PROCURACAO_MARKER);
	private static final Marker INSERT_MARKER = MarkerManager.getMarker(
			"INSERT_MARKER").setParents(PROCURACAO_MARKER);
	private static final Marker CANCELA_MARKER = MarkerManager.getMarker(
			"CANCELA_MARKER").setParents(PROCURACAO_MARKER);

	private static ProcuracaoDAO instance = null;

	private static GPDAO gpdao = null;

	protected ProcuracaoDAO() {

	}

	public static ProcuracaoDAO getInstance() {
		if (instance == null) {
			instance = new ProcuracaoDAO();
		}
		gpdao = GPDAO.getInstance();
		return instance;
	}

	// Desenvolvimento.........047
	// Homologação..............197
	// Produção.....................147

	// SELECT ISN AA AC AE AG AI AK001 AK002 AK003 AK004 AK005 AK006 AK007
	// AK008
	// AK009 AK010 AK011 AK012 AK013 AK014 AK015 AK016 AK017 AK018 AK019
	// AK020
	// AK021 AK022 AK023 AK024 AK025 AK026 AK027 AK028 AK029 AK030 AK031
	// FROM
	// 147.185 WHERE
	// S4=(NR-CPF-NR-CPF-CNPJ-PROCURADOR_1,NR-CPF-CNPJ-TITULAR_1,DT
	// -INICIO-VIGENCIA_1,HR-INICIO-VIGENCIA_1) TO
	// S4=(NR-CPF-NR-CPF-CNPJ-PROCURADOR_2
	// ,NR-CPF-CNPJ-TITULAR_2,DT-INICIO-VIGENCIA_2,HR-INICIO-VIGENCIA_2)

	// public ArrayList<ArrayList> getProcuracoesPorTitular(String
	// ni_titular){
	// ArrayList<String> retorno = new ArrayList<String>();
	// ArrayList<ArrayList> retArr = new ArrayList<ArrayList>();
	// StringBuilder sb = new StringBuilder();
	//
	// sb.append("SELECT ISN AA AC AE AG AI AS ");
	// sb.append("AK001 AK002 AK003 AK004 AK005 AK006 AK007 AK008 AK009 AK010 ");
	// sb.append("AK011 AK012 AK013 AK014 AK015 AK016 AK017 AK018 AK019 AK020 AK021 AK022 AK023 AK024 ");
	// sb.append("AK025 AK026 AK027 AK028 AK029 AK030 AK031 ");
	// sb.append("FROM 047.185 ");
	// sb.append("WHERE S1=(" + ni_titular
	// +",\"A\",00000000,000000) TO S1=(" +
	// ni_titular + ",\"A\",99999999,999999)");
	// try {
	// gpdao = GPDAO.getInstance();
	// retArr = gpdao.execQuery(sb.toString());
	//
	//
	// } catch (Exception e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// }
	// return retArr;
	// }

	public void aspectTeste(String arg) {
		System.out.println("Entrada: " + arg);
	}

	public void configuraAmbiente(Object _ambiente) throws Exception {
		// logger.info("Configurando Ambiente {}", _ambiente );
		if (_ambiente instanceof Integer) {
			gpdao.configuraAmbiente((int) _ambiente);
		} else if (_ambiente instanceof Ambiente) {
			gpdao.configuraAmbiente((Ambiente) _ambiente);
		} else {
			throw new Exception("Erro de Ambiente");
		}
	}

	public Procuracao getProcuracoesPorIsn(String isn) {
		Procuracao ret = null;
		StringBuilder sb = new StringBuilder();
		sb.append("WHERE ISN = " + isn);
		logger.debug("Clausula Where: " + sb.toString());
		ArrayList<Procuracao> arrProc = getProcuracao(sb);
		if ((arrProc != null) && (!arrProc.isEmpty())) {
			ret = getProcuracao(sb).get(0);
		}
		return ret;
	}

	
	public void deleteProcuracoesPorIsn(String isn) throws Exception {
		Procuracao ret = null;
		StringBuilder sb = new StringBuilder();
		sb.append("K34624  O34624DP");
		sb.append(isn);
		gpdao.execDelete(sb.toString());
	}
	
	public ArrayList<Procuracao> obterProcuracaoCodControle(String codControle) throws Exception {
//		Procuracao ret = null;
//		StringBuilder sb = new StringBuilder();
//		sb.append("K34624  O34624AF0");
//		sb.append(codControle);		
//		return gpdao.execBusca(sb.toString(),"",Procuracao.class);
		
		StringBuilder sb = new StringBuilder();
		sb.append("WHERE AT = " + codControle);
		logger.debug("Clausula Where: " + sb.toString());

		return getProcuracao(sb);
		
	}
	
	

	public void deleteLogPorIsn(String isn) throws Exception {
		StringBuilder sb = new StringBuilder();
		sb.append("K34624  O34624DL");
		sb.append(isn);
		gpdao.execDelete(sb.toString());
	}

	public ArrayList<Procuracao> getProcuracoesPorTitular(String ni_titular) {
		StringBuilder sb = new StringBuilder();
		sb.append("WHERE S2=(" + ni_titular
				+ ",00000000,000000) TO S2=(" + ni_titular
				+ ",99999999,999999)");
		logger.debug("Clausula Where: " + sb.toString());

		return getProcuracao(sb);
	}

	public ArrayList<Procuracao> getProcuracoesPorProcurador(
			String ni_procurador) {
		StringBuilder sb = new StringBuilder();
		sb.append("WHERE S3=(" + ni_procurador
				+ ",00000000,000000) TO S3=(" + ni_procurador
				+ ",99999999,999999)");

		return getProcuracao(sb);
	}
	
	public ArrayList<Log> getLogProcuracaoFromDate(String data) {		
		return getLogProcuracao("AC =" + data);
	}
		
	
	public ArrayList<Procuracao> getProcuracaoFromPeriodo(String dataInicial, String dataFinal) {	
		StringBuilder sb = new StringBuilder();
		sb.append("WHERE S2 =(00000000000000," + dataInicial + ",000000) TO S2=(99999999999999," + dataFinal  + ",235959)");

		return getProcuracao(sb);
	}
	public ArrayList<Log> getLogProcuracaoFromPeriodo(String dataInicial, String dataFinal) {		
		return getLogProcuracao("AC =(" + dataInicial + ") TO AC=(" + dataFinal + ")");
	}
	
	public ArrayList<Log> getLogPorISN(String isn) {		
		return getLogProcuracao("ISN = " + isn);
	}
	
	public ArrayList<Log> getLogProcuracaoFromCodControle(
			String codControle) {
		return getLogProcuracao("AN = " + codControle);
	}
	
	
	public ArrayList<Log> getLogProcuracaoFromUsuario(String cpf) {
		return getLogProcuracao("AK >= '" + cpf + "   20181000000000'");
	}
	
	
	private ArrayList<Log> getLogProcuracao(String filtro) {
		ArrayList<String> retorno = new ArrayList<String>();
		ArrayList<HashMap> retArr = new ArrayList<HashMap>();
		ArrayList<Log> log = new ArrayList<>();
		Procuracao ret = null;
		StringBuilder sb = new StringBuilder();

		// logger.info("Vai rodar a query no banco {}", gpdao.env);
		
		sb.append("SELECT ISN AC AD AE AF AM AN AA ");
//		sb.append("SELECT ISN AM ");
		sb.append("FROM" + gpdao.getFileLog() );		
		sb.append(" WHERE " + filtro);

		// logger.info("Query: {}", sb.toString());
//		logger.info(BUSCA_MARKER, "Query: " + sb.toString());

		try {
			log = gpdao.execQuery(sb.toString(),
					Log.class);
			logger.debug(BUSCA_MARKER, "quantidade de registros: "
					+ log.size());
		} catch (Exception e) {
			logger.error(BUSCA_MARKER,
					"erro ao buscar procuração: "
							+ e.getMessage());
			e.printStackTrace();
		}
		return log;
	}
	

	public ArrayList<Procuracao> getProcuracao(StringBuilder filtro) {
		ArrayList<String> retorno = new ArrayList<String>();
		ArrayList<HashMap> retArr = new ArrayList<HashMap>();
		ArrayList<Procuracao> procuracoes = new ArrayList<>();
		Procuracao ret = null;
		StringBuilder sb = new StringBuilder();

		// logger.info("Vai rodar a query no banco {}", gpdao.env);

		sb.append("SELECT ISN AA AB AC AD AE AG AI AS AH AP AF AT BA AV AW AX AY AL AZ ");
		sb.append("AK001 AK002 AK003 AK004 AK005 AK006 AK007 AK008 AK009 AK010 ");
		sb.append("AK011 AK012 AK013 AK014 AK015 AK016 AK017 AK018 AK019 AK020 AK021 AK022 AK023 AK024 ");
		sb.append("AK025 AK026 AK027 AK028 AK029 AK030 AK031 ");
		sb.append("FROM" + gpdao.getFileProcuracao());
		sb.append(filtro);

		// logger.info("Query: {}", sb.toString());
		logger.debug(BUSCA_MARKER, "Query: " + sb.toString());

		try {
			procuracoes = gpdao.execQuery(sb.toString(),
					Procuracao.class);
			logger.debug(BUSCA_MARKER, "quantidade de registros: "
					+ procuracoes.size());
		} catch (Exception e) {
			logger.error(BUSCA_MARKER,
					"erro ao buscar procuração: "
							+ e.getMessage());
			e.printStackTrace();
		}
		return procuracoes;
	}

	public ArrayList<Procuracao> getPreProcuracao(StringBuilder filtro) {
		ArrayList<String> retorno = new ArrayList<String>();
		ArrayList<HashMap> retArr = new ArrayList<HashMap>();
		ArrayList<Procuracao> procuracoes = new ArrayList<>();
		StringBuilder sb = new StringBuilder();

		sb.append("SELECT ISN AA AB AC AD AE AF AG AH AI AJ AK ");
		sb.append("AL AM AN AO AP AQ AR AS AT AU AV AW AX AY AZ BA BB BD BE ");
		sb.append("FROM" + gpdao.getFilePreprocuracao());
		sb.append(filtro);
		try {
			retArr = gpdao.execQuery(sb.toString());

			for (HashMap hash : retArr) {
				procuracoes.add((Procuracao) SqladaUtil
						.hashToObject(Sistema.class,
								hash));
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// for (ArrayList arrayList : retArr) {
		// for (Object object : arrayList) {
		// logger.info("campo:{}",object.toString());
		// }
		// }

		return procuracoes;
	}

	public ArrayList<Procuracao> getProcuracaoPorNumeroControle(
			String filtro) {
		StringBuilder sb = new StringBuilder();
		sb.append("WHERE AA=" + filtro);
		return getPreProcuracao(sb);
	}

	public ArrayList<Procuracao> getProcuracaoTitularProcuradorDataHora(
			String ni_titular, String ni_procurador, String data,
			String hora) {
		StringBuilder sb = new StringBuilder();
		sb.append("WHERE S4=(" + ni_procurador + "," + ni_titular
				+ ",19000101,000001) TO S4=(" + ni_procurador
				+ "," + ni_titular + "," + data + "," + hora
				+ ")");
		return getProcuracao(sb);
	}

	public ArrayList<Procuracao> getProcuracaoTitularProcurador(
			String ni_titular, String ni_procurador) {
		StringBuilder sb = new StringBuilder();
		sb.append("WHERE S4=(" + ni_procurador + "," + ni_titular
				+ ",00000000,000000) ");
		sb.append("TO S4=(" + ni_procurador + "," + ni_titular
				+ ",99999999,999999)");
		return getProcuracao(sb);
	}

	public String executarProcuracao(String comando) {
		String retorno = "";
		// comando="EXEC O96026SN K34624  O34624CP000001722110.32.112.221  PFAutoridade Certificadora do SERPRO Final v4                           12B1C5                                                                68281455500   1";
		try {
			retorno = GPDAO.getInstance().execRotina(comando,
					"SET PARAMETER ML=32000");

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return retorno;

	}

	public void criarProcuracao(Procuracao procuracao) throws Exception {

		String comando = "K34624  O34624IP"
				+ procuracao.getNiTitular()
				+ procuracao.getNiProcurador()
				+ procuracao.getTipoNiTitular()
				+ procuracao.getTipoNiProcurador()
				+ "serpro.procuracao@serpro.gov.br                   "
				+ procuracao.getDataFimVigencia()
				+ "10.32.112.221  PFAutoridade Certificadora do SERPRO Final v4                           12B1C5                                                                "
				+ procuracao.getNiTitular() + "116464     "
				+ procuracao.getStrSistemas();

		gpdao = GPDAO.getInstance();
		try {
			String retorno = gpdao.execRotina(comando,"SET PARAMETER ML=32000");
			
			logger.info("Comando: " + comando);
			logger.debug(INSERT_MARKER, "Retorno da criação: " + retorno);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// JadaFactory jada = JadaFactory.getInstance();
		// jada.executarComando(comando);
		// logger.debug(INSERT_MARKER, "Comando " + comando);
	}

	
	public void cancelarProcuracao(String isn, String cpfCancelador)
			throws Exception {
		String comando = "EXEC O96026SN K34624  O34624CP"
				+ isn
				+ "10.32.112.221  PFAutoridade Certificadora do SERPRO Final v4                           12B1C5                                                                "
				+ cpfCancelador + "   1";
		String ret = "";
		logger.debug(CANCELA_MARKER, "Comando " + comando);
		// JadaFactory jada = JadaFactory.getInstance();
		// jada.executarComando(comando);
		// return
		gpdao = GPDAO.getInstance();
		ret = gpdao.execRotina(CANCELA_MARKER, comando, "");
		logger.info("Retorno da rotina: " + ret);

	}

	public static void main(String[] args) {
		ArrayList<Procuracao> procs = null;
		ProcuracaoDAO dao = ProcuracaoDAO.getInstance();

		dao.aspectTeste("casa");

		try {
			dao.configuraAmbiente(Ambiente.PRODUCAO.valorAmbiente);
			procs = dao.getProcuracaoTitularProcurador(
					"55815831000103", "01433194000181");
			System.out.println("buscando");
			for (Procuracao proc : procs) {
				System.out.println("ISN: " + proc.getIsn());
				System.out.println("Procurador: "
						+ proc.getNiProcurador());
				System.out.println("Titular: "
						+ proc.getNiTitular());
				System.out.println("Fim vigencia: "
						+ proc.getDataFimVigencia());
				System.out.println("Cancelamento: "
						+ proc.getDataCancelamento());
				if (proc.getSistemas() != null)
					for (Sistema sistema : proc
							.getSistemas()) {
						System.out.println("Sistema: "
								+ sistema);
					}

			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}



}
