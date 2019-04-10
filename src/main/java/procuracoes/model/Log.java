package procuracoes.model;

import br.gov.serpro.util.adabas.annotation.Adabas;

public class Log {

	@Adabas(adaName="ISN")
	private String isn; 	
	@Adabas(adaName="AA")
	private String nrCpfCnpjUsuario;
	@Adabas(adaName="AC")
	private String dataOcorrencia;	
	@Adabas(adaName="AD")
	private String horaOcorrencia;	
	@Adabas(adaName="AN")
	private String codigoControtrole;	
	@Adabas(adaName="AE")
	private String nomeOperacao;
	@Adabas(adaName="AF")
	private String nomeDados;
	@Adabas(adaName="AM")
	private String codigoUa;
	
	private String dia;
	public String getDia() {
		return dia;
	}
	public void setDia(String dia) {
		this.dia = dia;
	}
	public String getMes() {
		return mes;
	}
	public void setMes(String mes) {
		this.mes = mes;
	}
	public String getAno() {
		return ano;
	}
	public void setAno(String ano) {
		this.ano = ano;
	}
	private String mes;
	private String ano;
		
	
	public String getIsn() {
		return isn;
	}
	public void setIsn(String isn) {
		this.isn = isn;
	}
	
	public String getNrCpfCnpjUsuario() {
		return nrCpfCnpjUsuario;
	}
	public void setNrCpfCnpjUsuario(String nrCpfCnpjUsuario) {
		this.nrCpfCnpjUsuario = nrCpfCnpjUsuario;
	}

	
	public String getDataOcorrencia() {
		return dataOcorrencia;
	}
	public void setDataOcorrencia(String dataOcorrencia) {
		this.dataOcorrencia = dataOcorrencia;
	}
	public String getHoraOcorrencia() {
		return horaOcorrencia;
	}
	public void setHoraOcorrencia(String horaOcorrencia) {
		this.horaOcorrencia = horaOcorrencia;
	}
	public String getCodigoControtrole() {
		return codigoControtrole;
	}
	public void setCodigoControtrole(String codigoControtrole) {
		this.codigoControtrole = codigoControtrole;
	}
	public String getNomeOperacao() {
		return nomeOperacao;
	}
	public void setNomeOperacao(String nomeOperacao) {
		this.nomeOperacao = nomeOperacao;
	}
	
	public String getNomeDados() {
		return nomeDados;
	}
	public void setNomeDados(String nomeDados) {
		this.nomeDados = nomeDados;
	}
	
	public String getCodigoUa() {
		return codigoUa;
	}
	public void setCodigoUa(String codigoUa) {
		this.codigoUa = codigoUa;
	}
	@Override
	public String toString() {
		return "Log [isn=" + isn + ", dataOcorrencia=" + dataOcorrencia
				+ ", nrCpfCnpjUsuario=" + nrCpfCnpjUsuario 
				+ ", horaOcorrencia=" + horaOcorrencia + ", codigoControtrole="
				+ codigoControtrole + ", nomeOperacao=" + nomeOperacao + ", nomeDados=" + nomeDados
				+ ", codigoUa=" + codigoUa + "]";
	}
	
}
