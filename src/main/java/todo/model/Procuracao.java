package todo.model;

import java.util.ArrayList;

public class Procuracao {
	private String isn;
	private String niTitular;
	private String niProcurador;
	private String dataInicioVigencia;
	private String dataFimVigencia;
	private String horaInicioVigencia;
	private String horaFimVigencia;
	private String situacao;
	private ArrayList<Sistema> sistemas;
	
	
	public String getIsn() {
		return isn;
	}
	public void setIsn(String isn) {
		this.isn = isn;
	}
	public String getNiTitular() {
		return niTitular;
	}
	public void setNiTitular(String niTitular) {
		this.niTitular = niTitular;
	}
	public String getNiProcurador() {
		return niProcurador;
	}
	public void setNiProcurador(String niProcurador) {
		this.niProcurador = niProcurador;
	}
	public String getDataInicioVigencia() {
		return dataInicioVigencia;
	}
	public void setDataInicioVigencia(String dataInicioVigencia) {
		this.dataInicioVigencia = dataInicioVigencia;
	}
	public String getDataFimVigencia() {
		return dataFimVigencia;
	}
	public void setDataFimVigencia(String dataFimVigencia) {
		this.dataFimVigencia = dataFimVigencia;
	}
	public String getHoraInicioVigencia() {
		return horaInicioVigencia;
	}
	public void setHoraInicioVigencia(String horaInicioVigencia) {
		this.horaInicioVigencia = horaInicioVigencia;
	}
	public String getHoraFimVigencia() {
		return horaFimVigencia;
	}
	public void setHoraFimVigencia(String horaFimVigencia) {
		this.horaFimVigencia = horaFimVigencia;
	}
	public ArrayList<Sistema> getSistemas() {
		return sistemas;
	}
	public void setSistemas(ArrayList<Sistema> sistemas) {
		this.sistemas = sistemas;
	}

	@Override
	public String toString() {
		return "Procuracao [isn=" + isn + ", niTitular=" + niTitular
				+ ", niProcurador=" + niProcurador + ", dataInicioVigencia="
				+ dataInicioVigencia + ", dataFimVigencia=" + dataFimVigencia
				+ ", horaInicioVigencia=" + horaInicioVigencia
				+ ", horaFimVigencia=" + horaFimVigencia + ", situacao="
						+ situacao + ", sistemas="
				+ sistemas + "]";
	}
	public String getSituacao() {
		return situacao;
	}
	public void setSituacao(String situacao) {
		this.situacao = situacao;
	}
	
}
