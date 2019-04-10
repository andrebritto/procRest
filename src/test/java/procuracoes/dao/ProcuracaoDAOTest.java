package procuracoes.dao;

import java.util.ArrayList;
import java.util.LinkedList;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import procuracoes.dao.ProcuracaoDAO;
import procuracoes.model.Ambiente;
import procuracoes.model.Procuracao;
import procuracoes.model.Sistema;
import procuracoes.test.util.Factories;


public class ProcuracaoDAOTest  {

	int envNumber = 0;
	ProcuracaoDAO dao; 
	
	@Before
	public void init() {
		envNumber = Ambiente.DESENVOLVIMENTO.valorAmbiente;
		dao = ProcuracaoDAO.getInstance();		
	}
	
	//@Test
	public void getProcuracaoSucesso(){
		try {
			dao.configuraAmbiente(envNumber);
			ArrayList<Procuracao> procs = dao.getProcuracoesPorTitular("00000000");
			
			Assert.assertNotNull(procs);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
//	@Test
	public void criarProcuracaoSucesso(){
		try {
			dao.configuraAmbiente(envNumber);
			Procuracao procuracao = new Procuracao();
			Factories<Procuracao> fact = new Factories<Procuracao>();
			procuracao = fact.getProcuracoes(1).get(0);
			procuracao.setDataInicioVigencia("20180516");
			procuracao.setDataFimVigencia("20180517");
			System.out.println(procuracao);
			StringBuilder strSis = new StringBuilder();
			for (Sistema sistema : procuracao.getSistemas()) {
				System.out.println(sistema.getCodigo());
				strSis.append(sistema.getCodigo());
			}
			System.out.println(strSis.toString());
			procuracao.setStrSistemas(strSis.toString());
			dao.criarProcuracao(procuracao);
			ArrayList<Procuracao> procs = dao.getProcuracoesPorTitular(procuracao.getNiTitular());
			Assert.assertNotNull(procs);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
		
		@Test
		public void criarProcuracaoLoteSucesso(){
			try {
				dao.configuraAmbiente(envNumber);
				ArrayList<String> listProcs = new ArrayList<>();
				ArrayList<Procuracao> listProcuracoes = new ArrayList<>();
				listProcs.add("06105181868");
				listProcs.add("19007809334");
				listProcs.add("27203670791");
				
				//LinkedList l = new LinkedList<>(listProcs);				
				Procuracao procuracao = null;
				LinkedList l = null;
				for (String ni_procurador : listProcs) {
					l = new LinkedList<Procuracao>(dao.getProcuracoesPorProcurador(ni_procurador));
					listProcuracoes.add((Procuracao)l.getLast());
				}				
				
				for (Procuracao p : listProcuracoes) {
					System.out.println(p);
				}
				
				

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
	}
	
}
