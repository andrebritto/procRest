package procuracoes.teste.business;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.MarkerManager;
import org.junit.Before;
import org.junit.Test;

import com.google.gson.Gson;

import procuracoes.business.ProcuracaoBC;
import procuracoes.model.Ambiente;
import procuracoes.model.Log;
import procuracoes.model.Procuracao;

public class LogApur {

	Logger logger;
	private static final Marker PROCURACAO_MARKER = MarkerManager.getMarker("PROCURACAO");
	private static final Marker BUSCA_MARKER = MarkerManager.getMarker("BUSCA_MARKER").setParents(PROCURACAO_MARKER);
	private static final Marker CANCELA_MARKER = MarkerManager.getMarker("CANCELA_MARKER")
			.setParents(PROCURACAO_MARKER);
	int envNumber = 0;

	@Before
	public void init() {
		System.setProperty("configurationFile", "log4j2-test.xml");
		logger = LogManager.getLogger(LogApur.class.getName());

		envNumber = Ambiente.PRODUCAO.valorAmbiente;

	}

	// Apuração para apagar registros repetidos
	// @Test
	public void deleteProcuracaoIsnSucesso() {
		ProcuracaoBC bc = ProcuracaoBC.getInstance();
		Gson json = new Gson();
		try {
			bc.setAmbiente(envNumber);
			String isn = "0007509402";
			// String isn = "0007400887";
			logger.info("Deletando ISN: " + isn);
			bc.deleteProcuracoesPorIsn(isn);
			Procuracao p = bc.getProcuracoesPorIsn(isn);
			logger.info(json.toJson(p));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

//	@Test
	public void listaLogIsnSucesso() {
		ProcuracaoBC bc = ProcuracaoBC.getInstance();
		ArrayList<Log> ret = new ArrayList<>();
		Gson json = new Gson();
		BufferedReader reader = null;

		String linha = "";

		try {
			// 25969633 20180405 ?T'
			reader = new BufferedReader(new FileReader("/home/68281455500/git/procRest/ISNd1.txt"));
			try {

				while ((linha = reader.readLine()) != null) {
					bc.setAmbiente(envNumber);
					ret = bc.listaLogPorISN(linha);
					for (Log log : ret) {
						logger.info(json.toJson(log, Log.class));
					}

				}

			} catch (Exception ex) {
				ex.printStackTrace();
			}

			reader.close();

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

//	@Test
	public void getLogIsnSucesso() {
		ProcuracaoBC bc = ProcuracaoBC.getInstance();
		ArrayList<Log> ret = new ArrayList<>();
		Gson json = new Gson();

		String linha = "0026251826";

		// 25969633 20180405 ?T'

		try {
			bc.setAmbiente(envNumber);
			ret = bc.listaLogPorISN(linha);
			for (Log log : ret) {
				logger.info(json.toJson(log, Log.class));
			}

		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}

//	@Test
	public void getLogDataPorPeriodoSucesso() {
		ProcuracaoBC bc = ProcuracaoBC.getInstance();
		ArrayList<Log> ret = new ArrayList<>();
		Gson json = new Gson();

		String ano = "2018";
		String date = "";

		// 25969633 20180405 ?T'

		try {
			bc.setAmbiente(envNumber);
			for (int m = 4; m <= 7; m++)
				for (int d = 1; d <= 31; d++) {
					if (m < 10) {
						date = ano + "0" + m;
					} else {
						date = ano + m;
					}
					if (d < 10) {
						date = date + "0" + d;
					} else {
						date = date + d;
					}
					ret = bc.getLogProcuracaoFromDate(date);
					for (Log log : ret) {
						if (log.getNomeOperacao().contains("CANCELARPROCRFB")
								&& StringUtils.isEmpty(log.getCodigoUa().trim()))
							logger.info(json.toJson(log, Log.class));
//						logger.info("Operação: " + log.getNomeOperacao() + ", " + log.getNomeOperacao().contains("CANCELARPROCRFB"));
						// logger.info("UA: " + log.getCodigoUa().trim() + ", " +
						// StringUtils.isEmpty(log.getCodigoUa().trim()));
					}
					date = "";
				}

		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	@Test
	public void deleteLogIsnLendoDeArquivoSucesso() {
		ProcuracaoBC bc = ProcuracaoBC.getInstance();
		ArrayList<Log> ret = new ArrayList<>();
		Gson json = new Gson();
		BufferedReader reader = null;
		String linha = "";
		try {
			reader = new BufferedReader(new FileReader("/home/68281455500/git/procRest/isns.txt"));
			try {

				while ((linha = reader.readLine()) != null) {
					bc.setAmbiente(envNumber);
					logger.info("Deletando INS: " + linha);
					bc.deleteLogPorIsn(linha);
				}

			} catch (Exception ex) {
				ex.printStackTrace();
			}

			reader.close();

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

//	@Test
	public void getLogDataSucesso() {
		ProcuracaoBC bc = ProcuracaoBC.getInstance();
		ArrayList<Log> ret = new ArrayList<>();
		Gson json = new Gson();
		try {
			bc.setAmbiente(envNumber);

			ret = bc.getLogProcuracaoFromDate("20180606");
			for (Log log : ret) {
				logger.info(json.toJson(log, Log.class));
			}

		} catch (Exception ex) {
//			ex.getMessage();
			System.out.println("Erro");
		}
	}

//	 @Test
	public void deleteLogIsnSucesso() {
		ProcuracaoBC bc = ProcuracaoBC.getInstance();
		Gson json = new Gson();
		try {
			bc.setAmbiente(envNumber);
//			String isn = "0026523183";
			String is = "0026611944";
//			String[] col= {"000027708742"};

			logger.info("Deletando INS: " + is);
			bc.deleteLogPorIsn(is);

//			for (String is : col) {
//				logger.info("Deletando INS: " + is);
//				bc.deleteLogPorIsn(is);
//			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
