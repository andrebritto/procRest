package procuracoes.model;

public enum Ambiente {
	DESENVOLVIMENTO(1), HOMOLOGACAO(2), PRODUCAO(3);

	public int valorAmbiente;

	Ambiente(int valor) {
		valorAmbiente = valor;
	}
	
}
