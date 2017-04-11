package todo.model;

public class Sistema {
	private String isn;
	private String codigo;
	private String sigla;
	private String descricao;
	private String tipoAcesso;
	
	public String getIsn() {
		return isn;
	}
	public void setIsn(String isn) {
		this.isn = isn;
	}
	public String getCodigo() {
		return codigo;
	}
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
	public String getSigla() {
		return sigla;
	}
	public void setSigla(String sigla) {
		this.sigla = sigla;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public String getTipoAcesso() {
		return tipoAcesso;
	}
	public void setTipoAcesso(String tipoAcesso) {
		this.tipoAcesso = tipoAcesso;
	}
	@Override
	public String toString() {
		return "Sistema [isn=" + isn + ", codigo=" + codigo + ", sigla="
				+ sigla + ", descricao=" + descricao + ", tipoAcesso="
				+ tipoAcesso + "]";
	}

	
}
