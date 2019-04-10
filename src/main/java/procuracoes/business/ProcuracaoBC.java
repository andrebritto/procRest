package procuracoes.business;

import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.alfredlibrary.validadores.CNPJ;
import org.alfredlibrary.validadores.CPF;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.MarkerManager;

import procuracoes.dao.ProcuracaoDAO;
import procuracoes.model.Ambiente;
import procuracoes.model.Log;
import procuracoes.model.Procuracao;
import procuracoes.model.RequisicaoProcuracao;
import procuracoes.model.Sistema;

public class ProcuracaoBC {

	static Logger logger = LogManager.getLogger(ProcuracaoBC.class.getName());
	
	private static final Marker PROCURACAO_MARKER = MarkerManager.getMarker("PROCURACAO");	
	private static final Marker BUSCA_MARKER = MarkerManager.getMarker("BUSCA_MARKER").setParents(PROCURACAO_MARKER);
	private static final Marker INSERT_MARKER = MarkerManager.getMarker("INSERT_MARKER").setParents(PROCURACAO_MARKER);
	private static final Marker CANCELA_MARKER = MarkerManager.getMarker("CANCELA_MARKER").setParents(PROCURACAO_MARKER);

	private static ProcuracaoBC instance = null;
	private static String TIPO_PESSOA_FISICA = "1";
	private static String TIPO_PESSOA_JURIDICA = "2";

	protected ProcuracaoBC() {

	}

	private static ProcuracaoDAO dao;

	public static ProcuracaoBC getInstance() {
		if (instance == null) {
			instance = new ProcuracaoBC();
		}
		dao = ProcuracaoDAO.getInstance();
		return instance;
	}

	public void setAmbiente(Object _ambiente) throws Exception {
		dao.configuraAmbiente(_ambiente);
	}

	ContribuinteBC contribuinteBC = null;
	SistemaBC sistemaBC = null;

	@SuppressWarnings("rawtypes")
	public ArrayList<Procuracao> getProcuracaoTitularProcuradorDataHora(
			String ni_titular, String ni_procurador, String data, String hora)
			throws Exception {
		ArrayList<Procuracao> procs = dao
				.getProcuracaoTitularProcuradorDataHora(ni_titular,
						ni_procurador, data, hora);
		// ArrayList<Procuracao> retorno = montarListaProcuracoes(procs);
		ArrayList<Sistema> sistemas = null;
		for (Procuracao proc : procs) {
			proc = setNome(proc);
			sistemas = preencheSistemas(proc);
			proc.setSistemas(sistemas);
			procs.add(proc);			
		}
		return procs;
	}

	public Procuracao getProcuracoesPorIsn(String isn) throws Exception {
		// logger.info("getProcuracoesPorTitular ...");
		Procuracao proc = dao.getProcuracoesPorIsn(isn);
		if (proc == null) {			
			throw new Exception("Procuração Não encontrada");
		}
		//proc = setNome(proc);
		ArrayList<Sistema> sistemas = preencheSistemas(proc);
		proc.setSistemas(sistemas);
		// logger.info("qtd de procurações montadas:{}", procs.size());

		return proc;
	}
	
	public void deleteProcuracoesPorIsn(String isn) throws Exception {
		try{
			isn = StringUtils.leftPad(isn,10,"0");
			logger.info("Apagando... " + isn);
			dao.deleteProcuracoesPorIsn(isn);
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	
	public ArrayList<Procuracao> obterProcuracaoCodControle(String codControle) throws Exception {
		ArrayList<Procuracao> procs =  new ArrayList<>();
		try{
			procs = dao.obterProcuracaoCodControle(codControle);
		}catch(Exception ex){
			ex.printStackTrace();
		}
		
		return procs;
	}

	public ArrayList<Procuracao> getProcuracaoTitularProcurador(
			String ni_titular, String ni_procurador) throws Exception {

		ArrayList<Procuracao> procs = dao.getProcuracaoTitularProcurador(
				ni_titular, ni_procurador);
		ArrayList<Procuracao> procRet = new ArrayList<>();
		// ArrayList<Procuracao> retorno = montarListaProcuracoes(procs);
		ArrayList<Sistema> sistemas = null;
		logger.debug("qtd:" + procs.size());
		for (Procuracao proc : procs) {			
			// proc = setNome(proc);
			sistemas = preencheSistemas(proc);
			proc.setSistemas(sistemas);
			procRet.add(proc);
		}
		return procRet;
	}

	public ArrayList<Procuracao> getProcuracoesPorTitular(String ni_titular)
			throws Exception {
		// logger.info("getProcuracoesPorTitular ...");
		ArrayList<Procuracao> procs = dao.getProcuracoesPorTitular(ni_titular);
		ArrayList<Procuracao> procsRet = new ArrayList<>();
		ArrayList<Sistema> sistemas = null;
		for (Procuracao proc : procs) {
			proc = setNome(proc);
			sistemas = preencheSistemas(proc);
			proc.setSistemas(sistemas);
			procsRet.add(proc);
		}
		// logger.info("qtd de procurações montadas:{}", procs.size());

		return procsRet;
	}

	public ArrayList<Procuracao> getProcuracoesPorProcurador(
			String ni_procurador) throws Exception {
		ArrayList<Procuracao> procs = dao
				.getProcuracoesPorProcurador(ni_procurador);
		ArrayList<Procuracao> procsRet = new ArrayList<>();
		if (procs.size() == 0) {
			throw new Exception("Nehuma Procuração Encontrada");
		}
		ArrayList<Sistema> sistemas = null;
		for (Procuracao proc : procs) {
			// proc = setNome(proc);
			sistemas = preencheSistemas(proc);
			proc.setSistemas(sistemas);
			procsRet.add(proc);
		}

		// ArrayList<Procuracao> retorno = montarListaProcuracoes(procs);
		return procsRet;
	}

	private ArrayList<Sistema> preencheSistemas(Procuracao proc)
			throws IllegalArgumentException, IllegalAccessException {
		ArrayList<Sistema> sistemas = new ArrayList<>();
		sistemaBC = SistemaBC.getInstance();
		Sistema s = null;
		// logger.info("procuração ISN {}", proc.getIsn());
		for (Field metodo : proc.getClass().getDeclaredFields()) {

			if (metodo.getName().contains("sistema")) {
				metodo.setAccessible(true);
				if (metodo.get(proc) != null) {
					// logger.info("AK: " + metodo.getName());
					// logger.info("valor: " + metodo.get(proc).toString());
					s = sistemaBC.getSistema(metodo.get(proc).toString());
					if (s != null) {
						sistemas.add(s);
					}

				}
			}

		}

		return sistemas;
	}

	private Procuracao setNome(Procuracao procuracao) throws Exception {

		contribuinteBC = ContribuinteBC.getInstance();
		
		return contribuinteBC.setNomes(procuracao);
	}

	// void criarProcuracoes(ArrayList<Procuracao> procuracoes) {
	//
	// for (Procuracao procuracao : procuracoes) {
	// ProcuracaoDAO.getInstance().criarProcuracao(procuracao);
	// }
	// }

	public void montaProcuracoes(ArrayList<Procuracao> procuracoes,
			ArrayList<String> niProcuradores, ArrayList<Sistema> sistemas) {

		for (Procuracao procuracao : procuracoes) {
			for (String procurador : niProcuradores) {
				procuracao.setNiProcurador(procurador);
				procuracao.setSistemas(sistemas);
				// criarProcuracoes(procuracoes);
			}
		}
	}

	/*
	 * 
	 * SITUACAO DAS PROCURACOES: "A" => APROVADA = APROVADA OU EXPIRADA (VER
	 * VIGÊNCIA) "R" => REJEITADA "D" => PENDENTE DE APROVAÇÃO "C" => CANCELADA
	 * "P"=> PENDENTE DE ASSINATURA - DESCONSIDERAR NAS CONSULTAS
	 */

	public static boolean vigente(Procuracao procuracao) throws Exception {
		boolean ret = false;
		Date di = new SimpleDateFormat("dd/MM/yyyy").parse(procuracao
				.getDataInicioVigencia());
		Date df = new SimpleDateFormat("dd/MM/yyyy").parse(procuracao
				.getDataFimVigencia());
		Date hoje = new Date();

		// ret = (df.compareTo(hoje) <=0);

		if ((hoje.after(di)) && (hoje.before(df))) {
			ret = true;
		}

		return ret;

	}

	// public void criarProcuracao(Procuracao procuracao) {
	// String titular = StringUtils.rightPad(procuracao.getNiTitular(), 14,
	// ' ');
	// String procurador =
	// StringUtils.rightPad(procuracao.getNiProcurador(),
	// 14, ' ');
	// }

	public void criarProcuracao(String titular, String procurador,
			String stringSistema, String dataInicioVigencia,
			String dataFimVigencia) throws Exception {

		String tipoTitular = "";
		String tipoProcurador = "";
		titular = StringUtils.rightPad(titular, 14, ' ');
		procurador = StringUtils.rightPad(procurador, 14, ' ');

		if (CPF.isValido(titular)) {
			tipoTitular = TIPO_PESSOA_FISICA;
		} else if (CNPJ.isValido(titular)) {
			tipoTitular = TIPO_PESSOA_JURIDICA;
		} else {
			throw new Exception("NI [" + titular + "] titular inválido");
		}

		if (CPF.isValido(procurador)) {
			tipoProcurador = TIPO_PESSOA_FISICA;
		} else if (CNPJ.isValido(procurador)) {
			tipoProcurador = TIPO_PESSOA_JURIDICA;
		} else {
			throw new Exception("NI [" + procurador + "] procurador inválido");
		}

		System.out.println("Tipo Titular: " + tipoTitular);
		System.out.println("Tipo procurador: " + tipoProcurador);

		Procuracao p = new Procuracao();
		p.setNiTitular(titular);
		p.setTipoNiTitular(tipoTitular);
		p.setNiProcurador(procurador);
		p.setTipoNiProcurado(tipoProcurador);
		p.setStrSistemas(stringSistema);
		p.setDataInicioVigencia(dataInicioVigencia);
		p.setDataFimVigencia(dataFimVigencia);
		ProcuracaoDAO dao = ProcuracaoDAO.getInstance();
		dao.criarProcuracao(p);

	}

	public void cancelarProcuracao(String isn, String cpfCancelador)
			throws Exception {
		ProcuracaoDAO dao = ProcuracaoDAO.getInstance();
		dao.cancelarProcuracao(isn, cpfCancelador);
	}

	private void imprimirProcuracões(ArrayList<Procuracao> procs) {

		for (Procuracao procuracao : procs) {
			if (procuracao.isVigente()) {
				System.out
						.println("================================================================================");
				System.out.println("ISN: " + procuracao.getIsn());
				System.out.println("Origem:" + procuracao.getOrigem());
				System.out.println("Numero Controle:"
						+ procuracao.getNumeroControle());
				System.out.println("Procuracao Titular: "
						+ procuracao.getNiTitular() + " - "
						+ procuracao.getNomeTitular());
				System.out.println("Procuracao Procurador: "
						+ procuracao.getNiProcurador() + " - "
						+ procuracao.getNomeProcurador());
				System.out.println("Situação: " + procuracao.getSituacao());
				System.out.println("data Inicio Vigencia: "
						+ procuracao.getDataInicioVigencia());
				System.out.println("data Fim Vigencia: "
						+ procuracao.getDataFimVigencia());
				System.out.println("É vigente: " + procuracao.isVigente());
				System.out.println("data Conferencia: "
						+ procuracao.getDataConferencia());
				System.out.println("Cpf Conferencia: "
						+ procuracao.getCpfConferencia());
				System.out.println("data Aprovação: "
						+ procuracao.getDataAprovacao());
				System.out.println("Cpf Aprovação: "
						+ procuracao.getCpfAprovacao());
				System.out.println("data cancelamento: ["
						+ procuracao.getDataCancelamento() + "]"
						+ procuracao.getHoraCancelamento());
				System.out.println("Cpf cancelamento: "
						+ procuracao.getCpfCancelamento());
				System.out.println("Origem cancelamento: "
						+ procuracao.getOrigemCancelamento());
				for (Sistema sistema : procuracao.getSistemas()) {
					System.out.println("Opção: " + sistema);
				}
			}
		}
	}

	public String montaListaSistemas(ArrayList<Sistema> sistemas) {

		StringBuilder sis = new StringBuilder();
		for (Sistema sistema : sistemas) {
			sis.append(sistema.getCodigo());
		}

		return montaListaSistemas(sis.toString());
	}

	public String montaListaSistemas(String stringSistemas) {

		String x = "";

		System.out.println(stringSistemas.contains("88888"));
		if (stringSistemas.contains("88888")) {
			x = StringUtils.rightPad("88888", 750, "0");
		} else {
			// x = StringUtils.rightPad(stringSistemas, 750, "0");
			x = stringSistemas;
		}
		System.out.println("qtd: " + x.length() / 5);
		return x;
	}

	/*
	 * NI: 80454453000 Versão: v4 NI: 92615112872 Versão: v4 NI: 45803412368
	 * Versão: v5 NI: 27203670791 Versão: v4
	 */

	public void geracaoProcuracaoEmDesenvolvimento() throws Exception {

		ProcuracaoBC bc = ProcuracaoBC.getInstance();

		String titular = "68281455500";
		ArrayList<String> procuradores = new ArrayList<String>();
		// Pocuradores (certificado desenvolvimento
		procuradores.add("05272431758");
		String inicioVigencia = "20180516";
		String fimVigencia = "20180516";
		// procuradores.add("92615112872");
		// procuradores.add("45803412368");
		// procuradores.add("27203670791");
		String sistemas = "00165" + "00166" + "00188";

		for (String procurador : procuradores) {
			bc.criarProcuracao(titular, procurador, sistemas, inicioVigencia,
					fimVigencia);
		}

	}

	public void cancelamentoProcuracaoEmDesenvolvimento() throws Exception {
		ProcuracaoBC.getInstance().cancelarProcuracao("0000017414",
				"68281455500");
	}

	public void verificaOperacaoPorTitular(String niTitular) {
		System.out.println("Verificando procurações");
		ProcuracaoBC bc = ProcuracaoBC.getInstance();
		ArrayList<Procuracao> procs = new ArrayList<Procuracao>();
		try {
			procs = bc.getProcuracoesPorTitular(niTitular);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		bc.imprimirProcuracões(procs);
	}

	public void verificaOperacaoPorProcurador(String niProcurador) {
		System.out.println("Verificando procurações");
		ProcuracaoBC bc = ProcuracaoBC.getInstance();
		ArrayList<Procuracao> procs = new ArrayList<Procuracao>();
		try {
			procs = bc.getProcuracoesPorProcurador(niProcurador);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		bc.imprimirProcuracões(procs);
	}

	public static void main(String[] args) {

		// TODO: Transformar as entradas em JSON.

		ProcuracaoBC bc = null;
		try {
			bc = ProcuracaoBC.getInstance();
			SistemaBC sistemaBc = SistemaBC.getInstance();
			bc.setAmbiente(Ambiente.DESENVOLVIMENTO);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		// bc.geracaoProcuracaoEmDesenvolvimento();
		// bc.cancelamentoProcuracaoEmDesenvolvimento();

		// String titular = "";
		// String procurador="";
		// String sistemas = "";
		// bc.criarProcuracao(titular, procurador, sistemas);
		//
		// bc.verificaOperacaoPorProcurador("05272431758");

		ArrayList<Procuracao> procs = null;
		try {
			System.setProperty("aplicacao.ambiente", "1");

			// procs = bc.getProcuracaoPorTitular("68281455500","27203670791");
			// procs = bc.getProcuracoesPorProcurador("82586004515");
			procs = bc.getProcuracoesPorTitular("68281455500");

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		System.out.println("buscando por procurador " + (procs == null));

		for (Procuracao procuracao : procs) {
			System.out.println("procuracao: " + procuracao);
			// bc.preencheSistemas(procuracao);

		}

	}

	public void criarProcuracaoEmLote(RequisicaoProcuracao proc) {
		// TODO Auto-generated method stub
		for (String niProcurador : proc.getNiProcurador()) {
			for (String sistema : proc.getSistema()) {
				try {
					criarProcuracao(proc.getNiTitular(), niProcurador, sistema, proc.getDataInicioVigencia(), proc.getDataFimVigencia());
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}				
			}			
		}
	}

	public void deleteLogPorIsn(String isn) {
		try{
			logger.info("Apagando... " + isn);			
			isn = StringUtils.leftPad(isn,10,"0");
			if(StringUtils.isEmpty(isn)) {
				throw new Exception("ISN inválido.");
			}
			dao.deleteLogPorIsn(isn.trim());
		}catch(Exception ex){
			ex.printStackTrace();
		}
		
	}

	public ArrayList<Log> listaLogFromDate(String data) {
		ArrayList<Log>  ret = new ArrayList<>();
		try{
			
			ret = dao.getLogProcuracaoFromDate(data);
		}catch(Exception ex){
			ex.printStackTrace();
		}
		
		return ret;
		
	}
	
	
	
	public ArrayList<Procuracao> listaProcuracaoFromPeriodo(String dataInicial, String dataFinal) {
		ArrayList<Procuracao>  ret = new ArrayList<>();
		try{
			
			ret = dao.getProcuracaoFromPeriodo(dataInicial, dataFinal);
		}catch(Exception ex){
			ex.printStackTrace();
		}
		
		return ret;
		
	}
	
	public ArrayList<Log> listaLogFromPeriodo(String dataInicial, String dataFinal) {
		ArrayList<Log>  ret = new ArrayList<>();
		try{
			
			ret = dao.getLogProcuracaoFromPeriodo(dataInicial, dataFinal);
		}catch(Exception ex){
			ex.printStackTrace();
		}
		
		return ret;
		
	}
	
	
	public ArrayList<Log> listaLogPorISN(String isn) {
		ArrayList<Log>  ret = new ArrayList<>();
		try{
			isn = StringUtils.leftPad(isn,10,"0");
			if(StringUtils.isEmpty(isn)) {
				throw new Exception("ISN inválido.");
			}
			ret = dao.getLogPorISN(isn.trim());
		}catch(Exception ex){
			ex.printStackTrace();
		}
		
		return ret;
		
	}
	
	public ArrayList<Log> getLogProcuracaoFromDate(String data) {
		ArrayList<Log>  ret = new ArrayList<>();
		try{
			
			ret = dao.getLogProcuracaoFromDate(data);
		}catch(Exception ex){
			ex.printStackTrace();
		}
		
		return ret;
		
	}

	public ArrayList<Log> getLogProcuracaoFromCodControle(String codControle) {
		ArrayList<Log>  ret = new ArrayList<>();
		try{
			
			ret = dao.getLogProcuracaoFromCodControle(codControle);
		}catch(Exception ex){
			ex.printStackTrace();
		}
		
		return ret;
	}
	
	public ArrayList<Log> getLogProcuracaoFromUsuario(String cpf) {
		ArrayList<Log>  ret = new ArrayList<>();
		try{
			
			ret = dao.getLogProcuracaoFromUsuario(cpf);
		}catch(Exception ex){
			ex.printStackTrace();
		}
		
		return ret;
	}
	
}
