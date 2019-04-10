package procuracoes.model;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Certificado {
	private String alias;
	private String ni;
	private boolean valido;
	private String tipoCertificado;
	private Date dataValidade;
	private String nomeRespLegal;
	private String versao;

	public String getNi() {
		return ni;
	}
	public void setNi(String ni) {
		this.ni = ni;
	}
	public boolean isValido() {
		return valido;
	}
	public void setValido(boolean valido) {
		this.valido = valido;
	}
	public String getTipoCertificado() {
		return tipoCertificado;
	}
	public void setTipoCertificado(String tipoCertificado) {
		this.tipoCertificado = tipoCertificado;
	}
	public Date getDataValidade() {
		return dataValidade;
	}
	public void setDataValidade(Date dataValidade) {
		this.dataValidade = dataValidade;
	}	
	public String getAlias() {
		return alias;
	}
	public void setAlias(String alias) {
		this.alias = alias;
	}
	@Override
	public String toString() {
		return "Certificado [alias=" + alias + ", ni=" + ni  + ", nomeRespLegal=" + nomeRespLegal+ ", valido="
				+ valido + ", tipoCertificado=" + tipoCertificado
				+ ", dataValidade=" + new SimpleDateFormat("dd/MM/yyyy").format(dataValidade) + "]";
	}
	public String getNomeRespLegal() {
		return nomeRespLegal;
	}
	public void setNomeRespLegal(String nomeRespLegal) {
		this.nomeRespLegal = nomeRespLegal;
	}
	public String getVersao() {
		return versao;
	}
	public void setVersao(String versao) {
		this.versao = versao;
	}
	
	
}
