package procuracoes.dao;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import procuracoes.model.Ambiente;
import br.gov.serpro.jada.JAdaConexao;
import br.gov.serpro.jada.JAdaException;
import br.gov.serpro.jada.JAdaResultado;

public class JadaFactory {

	private static JadaFactory instance = null;
	static Logger logger = LogManager.getLogger(JadaFactory.class.getName());
	JAdaConexao conn;
	protected Ambiente env = GPDAO.getInstance().env;

	public static JadaFactory getInstance() {
		if (instance == null) {
			instance = new JadaFactory();
		}
		instance.obterConexao();
		return instance;
	}

	private JAdaConexao obterConexao() {
		JAdaConexao conn = null;

		switch (env) {
		case DESENVOLVIMENTO:
			conn = JAdaConexao.abrirConexao("00000000191",
					"PR0CURAC0ES34624", "PROCURACOES-D",
					"10.3.9.1:3001");
			break;
		case HOMOLOGACAO:
			conn = JAdaConexao.abrirConexao("00000000191",
					"PR0CURAC0ES34624", "PROCURACOES-H",
					"10.3.10.15:3004");
			break;
		case PRODUCAO:
			conn = JAdaConexao.abrirConexao("00000000191",
					"JANSPE34624TRL", "PROCURACOES",
					"10.3.10.3:3012");
			break;
		default:
			conn = JAdaConexao.abrirConexao("00000000191",
					"PR0CURAC0ES34624", "PROCURACOES-D",
					"10.3.9.1:3001");
			break;
		}

		return conn;
	}

	public void close(JAdaConexao conn) {

		if (conn != null) {
			conn.fechar();
		}

	}

	public void executarComando(String comando) throws Exception {
		try {
			int ret = obterConexao().executarComando(
					"SET PARAMETER ML=32000");
			logger.info("Ret da parametrização: " + ret);
			ret = obterConexao().executarComando(comando);
			
			logger.info("Retorno da execução: " + ret );
			close(conn);
		} catch (JAdaException e) {
			throw new Exception(e.getMessage());
		}

	}

	public JAdaResultado executarQuery(String query) throws Exception {
		JAdaResultado rs;
		logger.info("Executando: " + query);
		obterConexao().executarComando("SET CONVERT ON");
		obterConexao().executarComando("SET PARAMETER ML=32000");
		rs = obterConexao().executarConsultaNatural(query);

		if (rs.getRegistro() != null) {
			if (!"00".equals(rs.getRegistro().substring(0, 2))) {
				throw (new Exception(rs.getRegistro()
						.substring(2, 212).trim()));
			}

		}
		close(conn);
		return rs;

	}

}
