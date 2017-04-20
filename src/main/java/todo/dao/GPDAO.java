package todo.dao;

import java.io.IOException;
import java.util.ArrayList;

import org.apache.commons.lang3.StringUtils;

import br.gov.serpro.sqladaj.SQLAdaJ;

public class GPDAO {

	protected final SQLAdaJ sqlada = new SQLAdaJ();

	// protected final MidasSQLAdaJ sqlada = new MidasSQLAdaJ();

	/**
	 * Deve ser chamada pelas classes derivadas para executar a rotina
	 * 
	 * @param logon
	 *            biblioteca a ser chamada
	 * @param nome
	 *            nome da rotina a ser chamada
	 * @param parametros
	 *            parametros que irão ser enviados para a rotina.
	 * @return o retorno da rotina na forma ret[0] = Código do retorno, ret[1] =
	 *         Retorno da Rotina.
	 * @throws Exception
	 * @throws AmbienteException
	 *             se houver falha no ambiente como comunicação com o Grande
	 *             Porte.
	 * @throws AplicacaoException
	 *             se houver um erro retorno da rotina.
	 */
	// protected String[] exec(String logon, String nome, String parametros)
	// throws Exception {
	// String retorno = null, codRetorno = null;
	// String comando = montarComandoGP(logon, nome, parametros);
	// return exec(comando);
	// }

	private static GPDAO instance = null;

	protected GPDAO() {

	}

	public static GPDAO getInstance() {
		if (instance == null) {
			instance = new GPDAO();
		}
		return instance;
	}

	protected ArrayList<ArrayList> exec(String comando) throws Exception {
		int codigoConexao = -1;

		String retorno = null, codRetorno = null;
		ArrayList<String> ret = new ArrayList<String>();
		ArrayList resultado = new ArrayList<String>();
		try {

			codigoConexao = abrirConexao();
			System.out.println("CodConn: " + codigoConexao);

			int valorRetornoComando = sqlada.SACommand(codigoConexao, comando);
			String strArea = "";
//			System.out.println("Retorno: " + sqlada.strArea);
			strArea = sqlada.strArea.replace(",U", "#U");
//			System.out.println(strArea);
			strArea = strArea.replace(",A", "#A");
//			System.out.println(strArea);
			
			strArea = StringUtils.trim(strArea);			
			int strAreaLength = StringUtils.length(strArea);
			
			 
			String[] arrStrArea = strArea.split(",");
			ArrayList<String> arrLengthArea = new ArrayList<String>();
			for (String string : arrStrArea) {
				if(string.contains("#")){								
					arrLengthArea.add(string.split("#")[0]);					
				}
			}
			if (valorRetornoComando < 0) {
				// exibe o código de erro e a mensagem retornadas do grande
				// porte ao executar comando
				System.out.println("error[" + comando + "]" + sqlada.strMsgErro);
			}
			int volta = 0;
			int codErroNext = 0;
			ArrayList registro = new ArrayList<String>();
			
			for (int i = 0; i < valorRetornoComando; i++) {
				codErroNext = sqlada.SAGetNext(codigoConexao);
				registro = getEntity(sqlada.strArea,arrLengthArea);
				resultado.add(registro);
				if (codErroNext < 0) {
					throw (new Exception("Erro ao buscar proximo"));
				} else {
					strArea = StringUtils.trim(sqlada.strArea);
					ret.add(strArea);
				}
			}

			codRetorno = StringUtils.right(strArea, 2);
			retorno = strArea;
			
		} finally {
			fecharConexao(codigoConexao);
		}
		return resultado;
	}

	public ArrayList<String> getEntity(String entrada, ArrayList<String> tams){
		int passo=0;
		int tamanho=0;
		String campo = "";
		ArrayList<String> ret = new ArrayList<String>();
		for (String tam : tams) {
			tamanho = Integer.parseInt(tam);
			campo = entrada.substring(passo,passo+tamanho).replace("+", "");
			if (!campo.equals("00000")){
				ret.add(campo);
			}			
			passo = passo + tamanho;
		}
		
//		for (String string : ret) {
//			System.out.println("Campo: " + string);
//		}
		
		return ret;
	}
	
	/**
	 * Abre a conexão com o MainFrame
	 * 
	 * @return código da conexão retornado
	 * @throws AmbienteException
	 */
	protected int abrirConexao() {

		int iCon = 0;
		try {
			iCon = sqlada.SAConnect("00000000191", "PR0CURAC0ES34624","PROCURACOES-D", "10.3.9.1:3001");

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if (iCon < 0) {
			System.out.println("Error[Abrindo conexão]: " + sqlada.strMsgErro);
		}

		int iQtd = sqlada.SACommand(iCon, "SET PARAMETER ML=32000");

		if (iQtd < 0) {
			System.out
					.println("error[Setando PARAMETER]: " + sqlada.strMsgErro);
		}

		iQtd = sqlada.SACommand(iCon, "SET CONVERT ON");

		if (iQtd < 0) {
			System.out.println("error[Setando CONVERT ON]: "
					+ sqlada.strMsgErro);
		}

		return iCon;

	}

	/**
	 * Fecha a conexão com o MainFrame
	 * 
	 * @param conexaoGrandePorte
	 *            código da conexão retornado pelo método abrirConexao
	 */
	protected void fecharConexao(int conexaoGrandePorte) {
		if (conexaoGrandePorte >= 0) {
			try {
				sqlada.SADisconnect(conexaoGrandePorte);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	public ArrayList<ArrayList> execQuery(String qry) throws Exception {
		System.out.println("Query: " + qry);
		return exec(qry);
	}

	/**
	 * Cria um comando para execução no grande porte e armazena na requisição
	 */
	// protected String montarComandoGP(String logon, String nome,
	// String parametrosRotina) {
	//
	// // TODO: Verificar se o parâmetro para o EXEC não deve ser
	// // parametrizado.
	// String comandoGP = "EXEC O96026SN " + StringUtils.rightPad(logon, 8) +
	// StringUtils.rightPad(nome, 8);
	// comandoGP += parametrosRotina;
	// return comandoGP;
	// }

	public ArrayList<ArrayList> getSistemas() {
		ArrayList<ArrayList> retArr = new ArrayList<ArrayList>();

		try {
			retArr = execQuery("SELECT AA AB AC AE FROM 047.182 WHERE AA>0 ORDER AA");

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return retArr;
	}

	/*
	 * SELECT ISN AA AC AE AG AI AK001 AK002 AK003 AK004 AK005 AK006 AK007 AK008
	 * AK009 AK010 AK011 AK012 AK013 AK014 AK015 AK016 AK017 AK018 AK019 AK020
	 * AK021 AK022 AK023 AK024 AK025 AK026 AK027 AK028 AK029 AK030 AK031 FROM
	 * 147.185 WHERE
	 * S4=(NR-CPF-NR-CPF-CNPJ-PROCURADOR_1,NR-CPF-CNPJ-TITULAR_1,DT
	 * -INICIO-VIGENCIA_1,HR-INICIO-VIGENCIA_1) TO
	 * S4=(NR-CPF-NR-CPF-CNPJ-PROCURADOR_2
	 * ,NR-CPF-CNPJ-TITULAR_2,DT-INICIO-VIGENCIA_2,HR-INICIO-VIGENCIA_2)
	 */
	public ArrayList<ArrayList> getProcuracao(String ni_titular,
			String ni_procurador) {
		ArrayList<ArrayList> retArr = new ArrayList<ArrayList>();
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT ISN AA AC AE AG AI AK001 AK002 AK003 AK004 AK005 AK006 AK007 AK008 AK009 AK010 ");
		sb.append("AK011 AK012 AK013 AK014 AK015 AK016 AK017 AK018 AK019 AK020 AK021 AK022 AK023 AK024 ");
		sb.append("AK025 AK026 AK027 AK028 AK029 AK030 AK031 ");
		sb.append("FROM 047.185 ");
		sb.append("WHERE S4=(" + ni_procurador + "," + ni_titular
				+ ",00000000,000000) ");
		sb.append("TO S4=("+ ni_procurador + "," + ni_titular
				+ ",99999999,999999)");
		try {
			retArr = execQuery(sb.toString());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return retArr;
	}

	/*
	 * 	Codigo de controle		procurador			vigencia
	 * 	9A2FA.0DE9F.CDFD5.AF1E8	190.078.093-34	30/12/2016 - 01/01/2017
		AEF2D.41A4E.D4009.C1EE3	190.078.093-34	16/02/2017 - 16/02/2018
	 * */
	
	public static void main(String[] args) {
		GPDAO gp = GPDAO.getInstance();
		ArrayList<ArrayList> arr = new ArrayList<ArrayList>();
		arr = gp.getProcuracao("68281455500", "19007809334");
		for (ArrayList<String> procs : arr) {
			for (String proc : procs) {
				System.out.println("->" + proc);				
			}
			
		
		}
		

	}

}
