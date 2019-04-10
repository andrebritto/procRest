package procuracoes.business;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import procuracoes.dao.ContribuinteDAO;
import procuracoes.dao.GPDAO;
import procuracoes.exception.PJException;
import procuracoes.model.Ambiente;
import procuracoes.model.PessoaFisica;
import procuracoes.model.PessoaJuridica;
import procuracoes.model.Procuracao;

//PF: 0041830946668FROILAN MOREIRA DE MORAES                                   LENY MOREIRA DE MORAES                                      196610231041230610100BELO HORIZONTE                          MG30360010R CORONEL ANTONIO GARCIA DE PAIVA       79    APARTAMENTO NO 1304  SAO BENTO          003197237799000000000000FROILAN@UAI.COM.BR                                00000000N
//PJ: 00HUMAR DE CABO FRIO COMERCIO E TRANSPORTE LTDA                                                                      397570057202HUMAR DE CABO FRIO                                     AVENIDA                                                                         125                                                     VILA ACTURA                                       252250405833RJ

public class ContribuinteBC {

	static Logger logger;

	private static ContribuinteBC instance = null;

	final static int SITUACAO_NULA = 1;
	final static int SITUACAO_ATIVA = 2;
	final static int SITUACAO_SUSPENSA = 3;
	final static int SITUACAO_INAPTA = 4;
	final static int SITUACAO_ATIVA_NAO_REGULAR = 05; // Discontinuada
	final static int SITUACAO_SUSPENSO = 06;
	final static int SITUACAO_EXTINTO = 7;
	final static int SITUACAO_BAIXADA = 8;
	final static int SITUACAO_CANCELADO = 9;

	protected ContribuinteBC() {

	}

	public void setAmbiente(Ambiente _env) {
		GPDAO.getInstance().configuraAmbiente(_env.valorAmbiente);
	}

	ContribuinteDAO dao = ContribuinteDAO.getInstance();
	String ni = "";
	String nascimento = "";
	String nome = "";
	String nomeMae = "";
	String cpfRespLegal = "";
	int codigoSituacaoCadastral = 0;

	public static ContribuinteBC getInstance() {
		if (instance == null) {
			instance = new ContribuinteBC();
		}
		System.setProperty("configurationFile", "log4j2-test.xml");
		logger = LogManager.getLogger(ContribuinteBC.class.getName());
		return instance;
	}

	public PessoaFisica getPF(String cpf) throws Exception {
		PessoaFisica ret = new PessoaFisica();
		String pf = dao.getPF(cpf);
		ret.setNi(pf.substring(2, 13));
		ret.setNascimento(new SimpleDateFormat("ddMMyyyy")
				.format(new SimpleDateFormat("yyyyMMdd")
						.parse(pf.substring(133, 141))));
		ret.setNome(pf.substring(13, 73));
		ret.setNomeMae(nomeMae = pf.substring(73, 132));
		return ret;
	}

	public String getCpfResponsavelLegal() {
		return this.cpfRespLegal;
	}

	public String getSituacaoCadastral() {
		String descricaoSituacao = "";
		// System.out.println("Codigo retornado: " +
		// this.codigoSituacaoCadastral );
		switch (this.codigoSituacaoCadastral) {
		case (1):
			descricaoSituacao = "Nula";
			break;
		case (2):
			descricaoSituacao = "Ativa";
			break;
		case (3):
			descricaoSituacao = "Suspensa";
			break;
		case (4):
			descricaoSituacao = "Inapta";
			break;
		case (6):
			descricaoSituacao = "Suspensa";
			break;
		case (7):
			descricaoSituacao = "Baixada";
			break;
		case (8):
			descricaoSituacao = "Cancelada";
			break;
		default:
			descricaoSituacao = "Situação não cadastrada";
			break;
		}
		return descricaoSituacao;
	}

	public String getNomeResponsavelLegal(String cnpj) throws Exception {
		String ret;
		String pj;

		pj = dao.getPJ(cnpj);
		cpfRespLegal = pj.substring(117, 128);
		ret = getPF(cpfRespLegal).getNome();

		return ret.trim();
	}

	public PessoaJuridica getPJ(String cnpj) throws Exception {
		PessoaJuridica ret = new PessoaJuridica();
		String pj;

		if (StringUtils.EMPTY == cnpj)
			return null;
		pj = dao.getPJ(cnpj);

		cpfRespLegal = pj.substring(117, 128);
//		logger.info("cpfRespLegal: " + cpfRespLegal);

		ret.setNome(pj.substring(2, 116).trim());
		ret.setCodigoSituacaoCadastral(Integer.parseInt(pj.substring(
				128, 129)));
		ret.setResponsavelLegal(getPF(cpfRespLegal));

		return ret;
	}

	public String getNomePF(String cpf) throws Exception {
		String ret;

		String pf = dao.getPF(cpf);
		ret = pf.substring(13, 73);

		return ret.trim();
	}

	public Procuracao setNomes(Procuracao procuracao) throws Exception {
		
//		logger.info(procuracao==null);
		
		if (procuracao.getTipoNiTitular().equals("1")) {
			procuracao.setNomeTitular(this.getPF(
					procuracao.getNiTitular()).getNome());
		} else if (procuracao.getTipoNiTitular().equals("2")) {
			procuracao.setNomeTitular(this
					.getPJ(procuracao.getNiTitular())
					.getResponsavelLegal().getNome());
		}

		if (procuracao.getTipoNiProcurador().equals("1")) {
			procuracao.setNomeProcurador(this.getPF(
					procuracao.getNiProcurador()).getNome());
		} else if (procuracao.getTipoNiProcurador().equals("2")) {
			procuracao.setNomeProcurador(this
					.getPJ(procuracao.getNiProcurador())
					.getResponsavelLegal().getNome());
		}

		return procuracao;
	}

	public String getMatriz(String cnpj) throws PJException {
		String ret = null;
//		logger.info("Buncando Matriz de " + cnpj);
		try {
			ret = dao.getMatriz(cnpj);
		} catch (Exception e) {
			throw new PJException("Matriz não encontrada.");
		}
		return ret;
	}
	
	public static void main(String[] args) {
		ContribuinteBC bc = ContribuinteBC.getInstance();
		bc.setAmbiente(Ambiente.HOMOLOGACAO);
		List<String> col = new ArrayList<String>();
//		col.add("00000000066028");
//		col.add("00000000077143");
//		col.add("00000000083119");
//		col.add("00000000084514");
//		col.add("00000000085081");
//		col.add("00000000085596");
//		col.add("00000000088269");
//		col.add("00000000088340");
		col.add("29339895000175");		
		
		try {
			for (String  pj : col) {
				System.out.println(bc.getPJ(pj));				
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
