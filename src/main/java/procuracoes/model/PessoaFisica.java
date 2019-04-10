package procuracoes.model;


public class PessoaFisica {
	private String ni;
	private String nascimento;
	private String nome;
	private String nomeMae;
	public String getNi() {
		return ni;
	}

	public void setNi(String ni) {
		this.ni = ni;
	}
	public String getNascimento() {
		return nascimento;
	}
	public void setNascimento(String nascimento) {
		this.nascimento = nascimento;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getNomeMae() {
		return nomeMae;
	}
	public void setNomeMae(String nomeMae) {
		this.nomeMae = nomeMae;
	}
	@Override
	public String toString() {
		return "PessoaFisica [ni=" + ni + ", nascimento=" + nascimento
				+ ", nome=" + nome + ", nomeMae=" + nomeMae + "]";
	}	
	
	
}
