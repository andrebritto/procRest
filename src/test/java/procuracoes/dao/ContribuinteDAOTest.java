package procuracoes.dao;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.google.gson.Gson;

import procuracoes.model.Ambiente;


public class ContribuinteDAOTest {

	int envNumber = 0;
	ContribuinteDAO dao; 
	
	Logger logger;
	
	
	@Before
	public void init() {
		System.setProperty("configurationFile", "log4j2-test.xml");
		logger = LogManager.getLogger(ContribuinteDAOTest.class.getName());
		envNumber = Ambiente.HOMOLOGACAO.valorAmbiente;
	}
	
	@Test
	public void getPessoaFisicaSucesso(){
		try {
			Gson json = new Gson();
			dao = ContribuinteDAO.getInstance();
			dao.configuraAmbiente(envNumber);
			String pf =  dao.getPF("27999416215");
			
			System.out.println(pf);
			assertNotNull(pf);
		} catch (Exception e) {
			e.printStackTrace();
			fail("Falha na consulta pessoa Fisica");
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
//	@Test
	public void getPessoaJuridicaSucesso(){
		try {
			dao = ContribuinteDAO.getInstance();
			dao.configuraAmbiente(envNumber);
			String pf =  dao.getPJ("66229717000118");
			assertNotNull(pf);
		} catch (Exception e) {
			fail("Falha na consulta pessoa Fisica");
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
//	@Test
	public void getPessoaFisicaForaDaBase(){
		try {
			dao = ContribuinteDAO.getInstance();
			dao.configuraAmbiente(envNumber);
			String pf =  dao.getPF("00000000");
			assertNotNull(pf);
			
		} catch (Exception e) {
			Assert.assertEquals("Contribuinte não Encontrado na Base", e.getMessage());
			// TODO Auto-generated catch block
			//e.printStackTrace();
		}
	}
		
	
//	@Test
	public void getPessoaJuridicaForaDaBase(){
		try {
			dao = ContribuinteDAO.getInstance();
			dao.configuraAmbiente(envNumber);
			String pf =  dao.getPJ("00000000000000");
			assertNotNull(pf);
			
		} catch (Exception e) {
			Assert.assertEquals("Contribuinte não Encontrado na Base", e.getMessage());
			// TODO Auto-generated catch block
			//e.printStackTrace();
		}
	}
	

}
