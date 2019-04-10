package procuracoes.teste.business;

import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.MarkerManager;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.google.gson.Gson;

import procuracoes.business.SistemaBC;
import procuracoes.dao.MongoDAO;
import procuracoes.model.Ambiente;
import procuracoes.model.Sistema;
import procuracoes.test.util.Factories;

public class SistemaBCApur {

	Logger logger;
	
	private static final Marker SISTEMA_MARKER = MarkerManager.getMarker("PROCREST");
	private static final Marker BUSCA_MARKER = MarkerManager.getMarker("PROCREST_").setParents(SISTEMA_MARKER);
	private static final Marker DELETE_MARKER = MarkerManager.getMarker("DELETE_MARKER").setParents(SISTEMA_MARKER);

	int envNumber = 0;

	@Before
	public void init() {
		System.setProperty("configurationFile", "log4j2-test.xml");
		logger = LogManager.getLogger(SistemaBCApur.class.getName());
		envNumber = Ambiente.DESENVOLVIMENTO.valorAmbiente;	
		
	}

	SistemaBC bc;

//	 @Test
	public void getSistemaSucessoJson() {
		bc = SistemaBC.getInstance();
		try {
			String sistemaJson = "{\"codigo\":\"00193\",\"sigla\":\"DARF-PUC            \",\"descricao\":\"Ajustar Documentos de Arrecadação numerados.\",\"tipoPessoa\":\"2\"}";
			
			Gson json = new Gson();
			Sistema sistemaParam = json.fromJson(sistemaJson, Sistema.class);
			bc.setAmbiente(envNumber);
			Sistema sistema = bc.getSistema(sistemaParam.getCodigo());
			logger.info(BUSCA_MARKER,sistema);
			Assert.assertNotNull(sistema);
		} catch (Exception e) {
			logger.error(BUSCA_MARKER, e.getMessage());
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	 
	 
//	 @Test
	public void getSistemaSucesso() {
		bc = SistemaBC.getInstance();
		try {
			bc.setAmbiente(envNumber);
			Sistema sistema = bc.getSistema("00075");
			logger.info(BUSCA_MARKER,sistema);
			Assert.assertNotNull(sistema);
		} catch (Exception e) {
			logger.error(BUSCA_MARKER, e.getMessage());
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

//	 @Test
	public void deleteSistemaSucesso() {
		bc = SistemaBC.getInstance();
		try {
			bc.setAmbiente(envNumber);
			Sistema sistema = bc.getSistema("00089");
			bc.deleteSistema(sistema);
			logger.info(DELETE_MARKER,sistema);

		} catch (Exception e) {
			logger.error(DELETE_MARKER,e.getMessage());
			e.printStackTrace();
		}
	}

	
//	 @Test
	public void updateSistema() {
		bc = SistemaBC.getInstance();
//		{"isn":"0000000136","codigo":"00129","sigla":"REINF-ESPECIAL      ","descricao":"REINF-Especial                                                                                      ","tipoPessoa":"3"}
//		{"isn":"0000000135","codigo":"00130","sigla":"REINF-ROTINAS       ","descricao":"REINF-Rotinas                                                                                       ","tipoPessoa":"3"}
//		{"isn":"0000000134","codigo":"00131","sigla":"REINF-RETORNO       ","descricao":"REINF-Retorno                                                                                       ","tipoPessoa":"3"}
//		{"isn":"0000000119","codigo":"00195","sigla":"EFD-Reinf-Geral     ","descricao":"EFD - Reinf - Geral                                                                                   ","tipoPessoa":"3"}
		try {
			Gson json = new Gson();
			bc.setAmbiente(envNumber);
			
			Sistema resultado = new Sistema();
//			{"isn":"0000000114","codigo":"00190","sigla":"CAEPF-CAD           ","descricao":"CAEPF- Cadastro de Atividade Econômica da Pessoa Física                                             ","tipoPessoa":"2"}
			String codigo = "00138";
//			String sigla = "EFD-Reinf-Geral";
//			String descricao = "EFD - Reinf - Geral";
			String tipoPessoa = "3";
			
			resultado = bc.getSistema(codigo);
			logger.info("Sistema antes: " + json.toJson(resultado, Sistema.class));
//			resultado.setDescricao(descricao);
//			resultado.setSigla(sigla);
			resultado.setTipoPessoa(tipoPessoa);
			logger.info("Sistema depois: " + json.toJson(resultado, Sistema.class));
			bc.updateSistema(resultado);			

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// @Test
	public void insertSistemaCodigoJaExistente() {
		bc = SistemaBC.getInstance();
		try {
			Gson json = new Gson();
			bc.setAmbiente(envNumber);
			Factories<Sistema> f = new Factories<>();

			String codigo = "00189";
			String descricao = "Sistema Já cadastrado";
			String tipoPessoa = "3";
			String sigla = "PRECADIN";

			Sistema entrada = new Sistema();
			entrada.setCodigo(codigo);
			entrada.setDescricao(descricao);
			entrada.setSigla(sigla);
			entrada.setTipoPessoa(tipoPessoa);
			bc.insertSistema(entrada);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	
//	 @Test
	public void insertSistemaSucesso() {
		bc = SistemaBC.getInstance();
		try {
			bc.setAmbiente(envNumber);
//			sisJson ="{\"codigo\":\"00190\",\"sigla\":\"CAEPF-CAD            \",\"descricao\":\"CAEPF- Cadastro de Atividade Econômica da Pessoa Física\",\"tipoPessoa\":\"2\"}";
//			sisJson ="{\"codigo\":\"00191\",\"sigla\":\"SERO-AFERI            \",\"descricao\":\"Acessar o SERO – Serviço Eletrônico de Aferição de Obras\",\"tipoPessoa\":\"3\"}";
//			sisJson ="{\"codigo\":\"00192\",\"sigla\":\"SISOBRA            \",\"descricao\":\"Acessar o SisobraPref – Sistema de Alvarás e Habite-se\",\"tipoPessoa\":\"3\"}";
//			sisJson ="{\"codigo\":\"00193\",\"sigla\":\"DARF-PUC            \",\"descricao\":\"Ajustar Documentos de Arrecadação numerados.\",\"tipoPessoa\":\"2\"}";
			//{"isn":"0000000091","codigo":"00089","sigla":"CAEPF               ","descricao":"CAEPF - Cadastro de Atividades Econômicas da Pessoa Física                                          ","tipoPessoa":"1"}
//			{"isn":"0000000119","codigo":"00195","sigla":"EFD-Reinf-Geral     ","descricao":"EFD - Reinf - Geral                                                                                   ","tipoPessoa":"3"}

			Gson json = new Gson();
			Sistema entrada = new Sistema();
			String codigo = "00189";
			String sigla = "RETGPS";
			String descricao = "Retificação de GPS.";
			String tipoPessoa = "2";	
			entrada.setCodigo(codigo);
			entrada.setDescricao(descricao);
			entrada.setSigla(sigla);
			entrada.setTipoPessoa(tipoPessoa);
			Sistema saida = bc.insertSistema(entrada);
			logger.info("Resultado: " +  json.toJson(saida, Sistema.class));
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void getSistemasSucesso() {
		bc = SistemaBC.getInstance();
		MongoDAO mongodao = MongoDAO.getInstance();
		try {
			Gson json = new Gson();
			bc.setAmbiente(envNumber);
			ArrayList<Sistema> sistemas = bc.getSistemas();
			for (Sistema sistema : sistemas) {
				logger.info(BUSCA_MARKER,json.toJson(sistema));
				//mongodao.insertSistema(sistema);
			}
			Assert.assertNotNull(sistemas);
		} catch (Exception e) {
			logger.error(BUSCA_MARKER,e.getMessage());
			e.printStackTrace();
		}
	}
	
}
