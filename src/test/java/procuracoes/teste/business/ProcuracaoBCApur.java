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

import procuracoes.business.ProcuracaoBC;
import procuracoes.dao.MongoDAO;
import procuracoes.dao.QueueDAO;
import procuracoes.model.Ambiente;
import procuracoes.model.Log;
import procuracoes.model.Procuracao;
import procuracoes.model.RequisicaoProcuracao;
import procuracoes.test.util.Factories;

/*
 * TODO: 1- ajustar os testes para não utilizar gravação em banco.
 *               2- criar testes de integração para gravar no banco
 * 
 * */
//@RunWith(MockitoJUnitRunner.class)
public class ProcuracaoBCApur {

	Logger logger;
	private static final Marker PROCURACAO_MARKER = MarkerManager
			.getMarker("PROCURACAO");
	private static final Marker BUSCA_MARKER = MarkerManager
			.getMarker("BUSCA_MARKER")
			.setParents(PROCURACAO_MARKER);
	private static final Marker CANCELA_MARKER = MarkerManager
			.getMarker("CANCELA_MARKER")
			.setParents(PROCURACAO_MARKER);
	int envNumber = 0;

	@Before
	public void init() {
		System.setProperty("configurationFile", "log4j2-test.xml");
		logger = LogManager.getLogger(ProcuracaoBCApur.class.getName());

		envNumber = Ambiente.HOMOLOGACAO.valorAmbiente;

	}

//	@Test
	public void testProcuracaPorTitularSucesso() {
		ProcuracaoBC bc = ProcuracaoBC.getInstance();
		Gson json = new Gson();
		ArrayList<String> titular = new ArrayList<>();
		titular.add("68281455500");
//		titular.add("09333243000116");
//		titular.add("98717731968");
//		titular.add("04446960830");
//		titular.add("13471754000171");
//		titular.add("13648033000194");
//		titular.add("03344500000139");
//		titular.add("49057961000121");
//		titular.add("28543319000182");
//		titular.add("03150167000127");
//		titular.add("09433001000102");
//		titular.add("07841928000148");
//		titular.add("01858933000187");
//		titular.add("25118717000163");
		ArrayList<Procuracao> procs = null;
		try {
			bc.setAmbiente(envNumber);
			for (String string : titular) {

				procs = bc.getProcuracoesPorTitular(string);

				for (Procuracao procuracao : procs) {
					// logger.info(json.toJson(procuracao));
					logger.info("ISN: "
							+ procuracao.getIsn());
					logger.info("UA Atendimento: " + procuracao
							.getCdUaAtendimento());
					logger.info("Situação: " + procuracao
							.getSituacao());
					logger.info("cod controle: "
							+ procuracao.getNumeroControle());
				}
			}
			Assert.assertNotNull(procs);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	
//	 @Test
	public void obterProcuracaoCodControleSucesso() {
		ProcuracaoBC bc = ProcuracaoBC.getInstance();
		Gson json = new Gson();
		ArrayList<String> codControle = new ArrayList<>();
		ArrayList<Procuracao> procs = new ArrayList<>();
		codControle.add("A649C0677F8C94232440");
		
		try {
			bc.setAmbiente(envNumber);

			for (String cdControl : codControle) {
				procs = bc.obterProcuracaoCodControle(
						cdControl);
				System.out.println(procs);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
//	 @Test
	public void obterLogCodControleSucesso() {
		ProcuracaoBC bc = ProcuracaoBC.getInstance();
		Gson json = new Gson();
		ArrayList<String> codControle = new ArrayList<>();
		ArrayList<Log> logs = new ArrayList<>();
		codControle.add("A649C0677F8C94232440");
		
		try {
			bc.setAmbiente(envNumber);

			for (String cdControl : codControle) {
				logs = bc.getLogProcuracaoFromCodControle(
						cdControl);
				for (Log log : logs) {
					logger.info(json.toJson(log));
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

//	 @Test
	public void obterLogUsuarioSucesso() {
		ProcuracaoBC bc = ProcuracaoBC.getInstance();
		Gson json = new Gson();
		
		ArrayList<Log> logs = new ArrayList<>();
		String cpf = "71891730800";
		try {
			bc.setAmbiente(envNumber);
			logs = bc.getLogProcuracaoFromUsuario(cpf);
			for (Log log : logs) {
				logger.info(json.toJson(log));
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

//	 @Test
	public void obterLogPorIsn() {
		ProcuracaoBC bc = ProcuracaoBC.getInstance();
		Gson json = new Gson();
		
		ArrayList<Log> logs = new ArrayList<>();
		ArrayList<String> isnLog = new ArrayList<>();
		isnLog.add("30647669");
		isnLog.add("30647670");
		isnLog.add("30685709");
		
		
		try {
			bc.setAmbiente(envNumber);
			
			for (String isn : isnLog) {
				logs = bc.listaLogPorISN(isn);
				for (Log log : logs) {
					logger.info(json.toJson(log));
				}				
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}	
	
	
//	 @Test
	public void obterProcuracaoIsnSucesso() {
		ProcuracaoBC bc = ProcuracaoBC.getInstance();
		Gson json = new Gson();
		ArrayList<String> isnProcuracao = new ArrayList<>();
		isnProcuracao.add("16215442");		
		try {
			bc.setAmbiente(envNumber);
			for (String isn : isnProcuracao) {
				Procuracao p = bc.getProcuracoesPorIsn(isn);
				logger.info(json.toJson(p));

			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

//	 @Test
	public void deleteProcuracaoIsnSucesso() {
		ProcuracaoBC bc = ProcuracaoBC.getInstance();
		Gson json = new Gson();
		ArrayList<String> isnProcuracao = new ArrayList<>();
		isnProcuracao.add("0015712958");
		
		try {
			bc.setAmbiente(envNumber);
			for (String isn : isnProcuracao) {
				logger.info("Deletando procuracao de ISN: " + isn);
				bc.deleteProcuracoesPorIsn(isn);
				
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

//	 @Test
	public void deleteLogIsnSucesso() {
		ProcuracaoBC bc = ProcuracaoBC.getInstance();
		Gson json = new Gson();
		ArrayList<String> isnLog = new ArrayList<>();
		
		
		
		isnLog.add("0028922495");
		isnLog.add("0028941725");
		isnLog.add("0029496284");
		
		
		try {
			bc.setAmbiente(envNumber);

			for (String isn : isnLog) {
				logger.info("Deletando INS: " + isn);
				bc.deleteLogPorIsn(isn);
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

//	 @Test
	public void testProcuracaoPorOutorgadoSucesso() {
		
		/*
		 * 	16313363000117	Baixado	Incorporação	2054	931.524.778-72	2031200
                        21133657000105	Baixado	Incorporação	2062	936.351.848-53	2949299
                        15229396000110	Baixado	Incorporação	2054	017.630.678-15	2031200
                        17156514000133	Baixado	Incorporação	2046	192.959.956-00	6421200
                        11826476000100	Baixado	Incorporação	2054	472.032.001-59	2423702
		 * 
		 * */
		
		ProcuracaoBC bc = ProcuracaoBC.getInstance();
		
		try {
			
			bc.setAmbiente(envNumber);
			ArrayList<Procuracao> procs = bc.getProcuracoesPorProcurador("68281455500");
			Gson json = new Gson();

			for (Procuracao procuracao : procs) {
				logger.info(json.toJson(procuracao));
				gravaNaFila(json.toJson(procuracao));
				gravaNoBanco(procuracao);
			}
			gravaNaFila("SHUTDOWN");
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	 private void gravaNoBanco(Procuracao reg) {
		 MongoDAO mongodao = MongoDAO.getInstance();
		 mongodao.insertProcuracao(reg);
	 }
	 
	 private void gravaNaFila(String msg) throws Exception {
		 QueueDAO q = new QueueDAO("serpro.queue.34624.procuracoes.procuracao");
		 q.produce(msg);
	 }

	/*
	 * Para verificação do erro, peço verificar se o CPF 44600844904 tem
	 * procuração eletrônica (perfil "eSocial - Grupo Acesso WEB") para o
	 * CNPJ 28702346000150.
	 * 
	 */
//	 @Test
	public void testProcuracaoTitularProcuradorSucesso() {		 
		ProcuracaoBC bc = ProcuracaoBC.getInstance();
		ArrayList<String> outorgantes = new ArrayList<>();
		// String procurador = " 09068656000110";
		String procurador = "21898278920";
		outorgantes.add("23152273000157");
		// outorgantes.add("11316164000149");
		// outorgantes.add("01250090000131");
		// outorgantes.add("10593824000176");
		ArrayList<Procuracao> procs = null;
		Gson json = new Gson();

		try {
			logger.info("Buscando procuração");
			bc.setAmbiente(envNumber);
			for (String outorgante : outorgantes) {
//				logger.info("Titular: " + outorgante);
//				logger.info("Procurador: " + procurador);
				procs = bc.getProcuracaoTitularProcurador(
						outorgante, procurador);
				for (Procuracao procuracao : procs) {
					logger.info(json.toJson(procuracao));
					
					
				}
			}
			Assert.assertNotNull(procs);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// @Test
	public void obterProcuracaoCPFforaDaBase() {
		ProcuracaoBC bc = ProcuracaoBC.getInstance();
		try {
			bc.setAmbiente(envNumber);
			ArrayList<Procuracao> procs = bc
					.getProcuracoesPorTitular("00000000");
			Assert.assertNotNull(procs);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// @Test
	public void cancelarProcuracaoSucesso() {
		ProcuracaoBC bc = ProcuracaoBC.getInstance();
		try {
			bc.setAmbiente(envNumber);
			String isn = "0000017497";
			String cpfCancelador = "00000000191";

			bc.cancelarProcuracao(isn, cpfCancelador);
			Procuracao p = bc.getProcuracoesPorIsn(isn);
			logger.info("Data cancelamento: "
					+ p.getDataCancelamento());
			Assert.assertNotEquals("00000000",
					p.getDataCancelamento());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	 @Test
	public void criarProcuracaoLoteSucesso() {
		try {
			ProcuracaoBC bc = ProcuracaoBC.getInstance();
			bc.setAmbiente(envNumber);
			ArrayList<String> listProcuradores = new ArrayList<>();
			ArrayList<String> listTitulares = new ArrayList<>();

			String procurador = "34036776215";
			listTitulares.add("00080011705");
			listTitulares.add("00080012434");
			listTitulares.add("00080013082	");
			listTitulares.add("04940445000102");
			listTitulares.add("04940446000157");
			listTitulares.add("00000674000277");

			
			for (String titular : listTitulares) {
				bc.criarProcuracao(titular, procurador, "00001",
						"20190320", "20190720");
			}

			// for (String p : listProcs) {
			// bc.criarProcuracao(titular, p, "88888",
			// "20180801", "20180801");
			// }

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// @Test
	public void criarProcuracaoLotePorIsnSucesso() {
		try {
			ProcuracaoBC bc = ProcuracaoBC.getInstance();
			bc.setAmbiente(envNumber);
			ArrayList<String> listProcs = new ArrayList<>();
			ArrayList<Procuracao> listProcuracoes = new ArrayList<>();
			listProcs.add("0000017316");
			listProcs.add("0000017502");
			listProcs.add("0000017472");

			// Montando a massa com base em procurações escolhidas
			// (inativas) na
			// base
			Procuracao procuracao = null;
			for (String isn : listProcs) {
				procuracao = bc.getProcuracoesPorIsn(isn);
				listProcuracoes.add(procuracao);
			}

			for (Procuracao p : listProcuracoes) {
				System.out.println(p);
				bc.criarProcuracao(p.getNiTitular(),
						p.getNiProcurador(), "00001",
						"20180516", "20180517");
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	// @Test
	public void criarProcuracaoLoteFromJson() {
		Gson j = new Gson();
		ProcuracaoBC bc = ProcuracaoBC.getInstance();
		Factories<RequisicaoProcuracao> f = new Factories<>();
		ArrayList<RequisicaoProcuracao> req = f.getLoteProcuracoes(1);

		for (RequisicaoProcuracao requisicaoProcuracao : req) {
			bc.criarProcuracaoEmLote(requisicaoProcuracao);
		}
	}

}
