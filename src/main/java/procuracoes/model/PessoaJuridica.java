package procuracoes.model;

public class PessoaJuridica {
	private String nome;
	private PessoaFisica responsavelLegal;
	private int codigoSituacaoCadastral;
	private String matriz;
		
	public PessoaFisica getResponsavelLegal() {
		return responsavelLegal;
	}
	public void setResponsavelLegal(PessoaFisica responsavelLegal) {
		this.responsavelLegal = responsavelLegal;
	}
	public int getCodigoSituacaoCadastral() {
		return codigoSituacaoCadastral;
	}
	public void setCodigoSituacaoCadastral(int codigoSituacaoCadastral) {
		this.codigoSituacaoCadastral = codigoSituacaoCadastral;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getMatriz() {
		return matriz;
	}
	public void setMatriz(String matriz) {
		this.matriz = matriz;
	}
	
	@Override
	public String toString() {
		return "PessoaJuridica [nome=" + nome + ", responsavelLegal="
				+ responsavelLegal + ", codigoSituacaoCadastral="
				+ codigoSituacaoCadastral + ", matriz=" + matriz + "]";
	}
	
}
