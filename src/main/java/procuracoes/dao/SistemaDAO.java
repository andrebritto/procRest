package procuracoes.dao;

import java.util.ArrayList;
import java.util.HashMap;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.MarkerManager;

import procuracoes.model.Ambiente;
import procuracoes.model.Sistema;
import br.gov.serpro.util.adabas.SqladaUtil;

public class SistemaDAO {

	static Logger logger = LogManager.getLogger(SistemaDAO.class.getName());
	
	private static final Marker SISTEMA_MARKER = MarkerManager.getMarker("SISTEMA");	
	private static final Marker BUSCA_MARKER = MarkerManager.getMarker("SELECT_MARKER").setParents(SISTEMA_MARKER);
	private static final Marker INSERT_MARKER = MarkerManager.getMarker("INSERT_MARKER").setParents(SISTEMA_MARKER);
	private static final Marker DELETE_MARKER = MarkerManager.getMarker("DELETE_MARKER").setParents(SISTEMA_MARKER);
	
	private static SistemaDAO instance = null;

	protected SistemaDAO() {

	}

	static 
	GPDAO gpdao;

	public static SistemaDAO getInstance() {
		if (instance == null) {
			instance = new SistemaDAO();
		}
		gpdao = GPDAO.getInstance();
		return instance;
	}
	
	public void configuraAmbiente(Object _ambiente) throws Exception{		
		logger.info(_ambiente);
		if (_ambiente instanceof Integer){
			gpdao.configuraAmbiente((int)_ambiente);
		}else if(_ambiente instanceof Ambiente){
			gpdao.configuraAmbiente((Ambiente)_ambiente);
		}else{
			logger.error("Erro na configuração do ambiente: " +  _ambiente);
			throw new Exception("Erro de Ambiente");
		}
	}

	public ArrayList<Sistema> getSistemas() {
		HashMap<String, String> map = new HashMap<>();
		ArrayList<Sistema> retArr = new ArrayList<Sistema>();
		String selectQuery = "";
		try {
			gpdao = GPDAO.getInstance();
			selectQuery = "SELECT ISN AA AB AC AE FROM" + gpdao.getFileSistema() + "WHERE AA>0 ORDER AA";
			logger.debug(BUSCA_MARKER,"Query: " + selectQuery);
			retArr = gpdao.execQuery(selectQuery, Sistema.class);
			
		} catch (Exception e) {
			logger.error(BUSCA_MARKER,"Erro ao buscar sistemas: " + selectQuery);
			e.printStackTrace();
		}
				
		return retArr;
	}

	public Sistema getSistema(String codigo) throws Exception {
			Sistema ret = null;	
			gpdao = GPDAO.getInstance();
			ArrayList<HashMap> retArr = gpdao.execQuery("SELECT ISN AA AB AC AE FROM"+ gpdao.getFileSistema() + "WHERE AA=" + codigo);
			if (retArr.isEmpty()){
				throw new Exception("Nenhum Sistema Encontrado.");
			}
				
			ret = (Sistema) SqladaUtil.hashToObject(Sistema.class, retArr.get(0)) ;

	
		return ret;
	}

	public void insertSistema(Sistema sistema) throws Exception {
			String insertQuery="";
			gpdao = GPDAO.getInstance();			
			insertQuery = "INSERT " + gpdao.getFileSistema() + " AA=" + sistema.getCodigo()+ " AB='" + sistema.getSigla() + "' AC='" + sistema.getDescricao() + "' AE=" + sistema.getTipoPessoa();
			System.out.println(insertQuery);
			gpdao.execInsert(insertQuery);			
		
	}

	public void updateSistema(Sistema sistema) {
		int conexao = -1;
		try {
			gpdao = GPDAO.getInstance();			
			
			String lockQuery = "SELECT AA AB AC AE FROM" + gpdao.getFileSistema() + "WHERE ISN = " + sistema.getIsn() +  " LOCK";
			System.out.println("lockQuery: " + lockQuery);
			conexao = gpdao.executeLock(lockQuery);
			String updateQuery= "UPDATE" + gpdao.getFileSistema() + "AB='" + sistema.getSigla() + "' AC='" + sistema.getDescricao() + "' AE='" + sistema.getTipoPessoa() + "' WHERE ISN " + sistema.getIsn();	

			System.out.println(updateQuery);
			gpdao.execUpdate(updateQuery, conexao);			
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	public void deleteSistema(String isn) {
		try {
			String query="";
			gpdao = GPDAO.getInstance();			
			query = "DELETE " + gpdao.getFileSistema() + "ISN " + isn;
			System.out.println(query);
			gpdao.execDelete(query);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	
	
	public static void main(String[] args) {
		
		SistemaDAO dao = SistemaDAO.getInstance();
		try {
			dao.configuraAmbiente(Ambiente.DESENVOLVIMENTO.valorAmbiente);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		ArrayList<Sistema> sistemas = dao.getSistemas(); 
		
//		ArrayList<String> registro = new ArrayList<String>();
//		Sistema ultimoSistema = null;
//		Sistema sis = new Sistema();
				

//		ultimoSistema = new Sistema(registro);
//		System.out.println(ultimoSistema);
//		int cd = Integer.parseInt(ultimoSistema.getCodigo()) + 1;
//		
//		sis.setCodigo(StringUtils.leftPad(String.valueOf(cd), 5, '0'));
//		sis.setSigla("SISTESTE");
//		sis.setDescricao("SISTESTE - TESTE INCLUSÃO DE SISTEMA - terceira");
//		sis.setTipoAcesso("1");
//		SistemaDAO.getInstance().insertSistema(sis);
//		
//		for (ArrayList<String> sistema : sistemas) {
//			System.out.println(sistema);		
//			 
//		}
//		SistemaDAO.getInstance().deleteSistema("108");
//		
		for (Sistema sistema : sistemas) {
			System.out.println(sistema);		
			
		}		
		
		//sistemas.get(sistemas.lastIndexOf(sistemas));
		
		
		
	}
}
