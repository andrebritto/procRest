package procuracoes.business;

import java.util.ArrayList;
import java.util.Iterator;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.MarkerManager;

import procuracoes.dao.SistemaDAO;
import procuracoes.model.Sistema;


public class SistemaBC {
	
	static protected ArrayList<Sistema> sistemas;
	
	static Logger logger = LogManager.getLogger(SistemaBC.class.getName());
	private static final Marker SISTEMA_MARKER = MarkerManager.getMarker("SISTEMA");	
	private static final Marker BUSCA_MARKER = MarkerManager.getMarker("SELECT_MARKER").setParents(SISTEMA_MARKER);
	private static final Marker INSERT_MARKER = MarkerManager.getMarker("INSERT_MARKER").setParents(SISTEMA_MARKER);
	private static final Marker DELETE_MARKER = MarkerManager.getMarker("DELETE_MARKER").setParents(SISTEMA_MARKER);

	static SistemaDAO  dao = null;
	
	private static SistemaBC instance = null;

	protected SistemaBC() {

	}

	public static SistemaBC getInstance() {		
		if (instance == null) {
			instance = new SistemaBC();			
		}
		 dao = SistemaDAO.getInstance();
		return instance;
	}
	
	public void setAmbiente(Object _ambiente) throws Exception {
		dao.configuraAmbiente(_ambiente);
	}

	public ArrayList<Sistema> getSistemas() {
		if (sistemas == null){
			carregaSistemas();
		}
		return SistemaBC.sistemas;
	}

	private void carregaSistemas() {

		ArrayList<Sistema> ret = SistemaDAO.getInstance().getSistemas();
		sistemas = new ArrayList<Sistema>();
		for (Sistema sistema : ret) {
//			logger.info(BUSCA_MARKER,sistema);
			sistemas.add(sistema);

		}
	}

	public Sistema getSistema(String codigo) {

		Sistema s = new Sistema();

		if ("88888".equals(codigo)) {

			s.setIsn("0");
			s.setCodigo(codigo);
			s.setSigla("Todos os Serviços Existentes");
			s.setDescricao("Todos os serviços existentes e os que vierem a ser disponibilizados no sistema de Procurações Eletrônicas do e-CAC (destinados ao tipo do Outorgante - PF ou PJ), para todos os fins, inclusive confissão de débitos, durante o período de validade da procuração.");
			s.setTipoPessoa("3");
//			logger.info(BUSCA_MARKER,s);
			return s;
		}
		Iterator<Sistema> it = getSistemas().iterator();

		while (it.hasNext()) {
			s = (Sistema) it.next();
			if (s.getCodigo().equals(codigo)) {
//				logger.info(BUSCA_MARKER,s);
				return s;
			}

		}
		return null;
	}

	private Sistema getUltimoSistema() {
		Sistema ultimoSistema = null;
		for (Sistema sistema : sistemas) {
			ultimoSistema = sistema;
		}
		logger.info(BUSCA_MARKER,ultimoSistema);
		return ultimoSistema;
	}

	public void updateSistema(Sistema sistema) {
		SistemaDAO.getInstance().updateSistema(sistema);
	}

	public Sistema insertSistema(Sistema sis) throws Exception {

		validaEntrada(sis);
		
		if (getSistema(sis.getCodigo())!=null){
			throw (new Exception (sis.getCodigo() + " já cadastrado"));
		}
		
		if (StringUtils.EMPTY.equals(sis.getCodigo())){			
			Sistema ultimoSistema = getUltimoSistema();
			int cd = Integer.parseInt(ultimoSistema.getCodigo()) + 1;
			sis.setCodigo(StringUtils.leftPad(String.valueOf(cd), 5, '0'));			
		}				

		logger.info(INSERT_MARKER,sis);
		SistemaDAO.getInstance().insertSistema(sis);

		return getSistema(sis.getCodigo());
	}

	public void deleteSistema(Sistema sis) {
		SistemaDAO.getInstance().deleteSistema(sis.getIsn());
	}

	private void validaEntrada(Sistema sis) throws Exception {

		if (sis == null)
			throw new Exception("Sistema inválido");
		if (StringUtils.isEmpty(sis.getDescricao())
				|| StringUtils.isEmpty(sis.getDescricao().trim()))
			throw new Exception("Sistema inválido - Descrição invalida");
		if (StringUtils.isEmpty(sis.getSigla())
				|| StringUtils.isEmpty(sis.getSigla().trim()))
			throw new Exception("Sistema inválido - sigla invalida");
		if (StringUtils.isEmpty(sis.getTipoPessoa())
				|| !sis.getTipoPessoa().matches("^[1-3]$"))
			throw new Exception("Sistema inválido - tipo de acesso invalido: "
					+ sis.getTipoPessoa());

	}

	
		
	

		// bc.getSistemas();
		// Sistema sis = new Sistema();
		// ArrayList<Sistema> sistemas = new ArrayList<Sistema>();
		//
		// sis.setSigla("SISTESTE5");
		// sis.setDescricao("Sistema Teste");
		// sis.setTipoAcesso("1");
		// sistemas.add(sis);

		// sis.setIsn("0000000102");
		// bc.deleteSistema(sis);

		// bc.insertSistema(sis);

		// try {
		// bc.validaEntrada(sis);
		// System.out.println("Sistema valido");
		// } catch (Exception e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
		// Sistema s = null;
		// for (Sistema sistema : bc.recuperaSistemas()) {
		// //if(sistema.getSigla().contains("eSocial")){
		// if(sistema.getCodigo().contains("142")){
		// System.out.println(sistema);
		// s = sistema;
		// }
		// }
		// s.setTipoAcesso("3");
		// System.out.println(s);
		// bc.updateSistema(s);



}
