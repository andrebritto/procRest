package procuracoes.common;

public class Registro {
	public String dt;
	public String hr;
	public String cpfProc;
	public String cnpjOut;
	
	
	public String toString(){
		return "dt: " + dt + ", hr: " + hr + ", procurador: " + cpfProc + ", outogante: " + cnpjOut;
	}
}
