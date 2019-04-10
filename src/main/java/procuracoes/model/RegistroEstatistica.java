package procuracoes.model;


public class RegistroEstatistica {
	
	private String codigoUa;
	private String mes;
	private String ano;
	private String dia;
	
	public String getCodigoUa() {
		return codigoUa;
	}
	public void setCodigoUa(String codigoUa) {
		this.codigoUa = codigoUa;
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
	public String getDia() {
		return dia;
	}
	public void setDia(String dia) {
		this.dia = dia;
	}
	
	@Override
	public String toString() {
		return "RegistroEstatistica [codigoUa=" + codigoUa + ", mes=" + mes
				+ ", ano=" + ano + ", dia=" + dia + "]";
	}
	
}
