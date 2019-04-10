package procuracoes.dao;

import java.util.ArrayList;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import procuracoes.dao.SistemaDAO;
import procuracoes.model.Ambiente;
import procuracoes.model.Sistema;
import procuracoes.test.util.Factories;


import com.google.gson.Gson;

public class SistemaDAOTest {

	int envNumber = 0;
	SistemaDAO dao; 
	
	@Before
	public void init() {
		envNumber = Ambiente.HOMOLOGACAO.valorAmbiente;
				
	}
	
	@Test
	public void getSistemaSucesso(){
		dao = SistemaDAO.getInstance();
		try {
			dao.configuraAmbiente(envNumber);
			Sistema sistema = dao.getSistema("00028");
			Assert.assertNotNull(sistema);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void getSistemaNaoExistente(){
		dao = SistemaDAO.getInstance();
		try {
			dao.configuraAmbiente(envNumber);
			Sistema sistema = dao.getSistema("00000");
			Assert.assertNull(sistema);
			
		} catch (Exception e) {
			Assert.assertEquals("Nenhum Sistema Encontrado.", e.getMessage());
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void getSistemasSucesso(){
		dao = SistemaDAO.getInstance();
		try {
			Gson json = new Gson();
			dao.configuraAmbiente(envNumber);
			ArrayList<Sistema> sistemas = dao.getSistemas();
			for (Sistema sistema : sistemas) {				
				System.out.println(json.toJson(sistema));
			}
			Assert.assertNotNull(sistemas);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void updateSistemaSucesso(){
		dao = SistemaDAO.getInstance();
		try {
			Gson json = new Gson();
			dao.configuraAmbiente(envNumber);
			Factories<Sistema> f = new Factories<>();
			Sistema sistemaAntes = (Sistema) f.getSistemas(1).get(0);
			Sistema sistemaDepois = (Sistema) f.getSistemas(1).get(0);			
			sistemaDepois.setDescricao(sistemaAntes.getDescricao() + ".");			
			dao.updateSistema(sistemaDepois);	
			//Assert.assertNotEquals(sistemaAntes, sistemaDepois);			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
