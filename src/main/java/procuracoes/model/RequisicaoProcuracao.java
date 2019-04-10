package procuracoes.model;

public class RequisicaoProcuracao {

	private String niTitular;
	private String[] niProcurador;
	private String dataFimVigencia;
	private String dataInicioVigencia;
	private String[] sistema;
	public String getNiTitular() {
		return niTitular;
	}
	public void setNiTitular(String niTitular) {
		this.niTitular = niTitular;
	}
	public String[] getNiProcurador() {
		return niProcurador;
	}
	public void setNiProcurador(String[] niProcurador) {
		this.niProcurador = niProcurador;
	}
	public String getDataFimVigencia() {
		return dataFimVigencia;
	}
	public void setDataFimVigencia(String dataFimVigencia) {
		this.dataFimVigencia = dataFimVigencia;
	}
	public String getDataInicioVigencia() {
		return dataInicioVigencia;
	}
	public void setDataInicioVigencia(String dataInicioVigencia) {
		this.dataInicioVigencia = dataInicioVigencia;
	}
	public String[] getSistema() {
		return sistema;
	}
	public void setSistema(String[] sistema) {
		this.sistema = sistema;
	}
	
	
}
