package todo.business;

import java.util.ArrayList;

import todo.dao.ProcuracaoDAO;
import todo.model.Procuracao;
import todo.model.Sistema;

public class ProcuracaoBC {

	private static ProcuracaoBC instance = null;

	protected ProcuracaoBC() {

	}

	public static ProcuracaoBC getInstance() {
		if (instance == null) {
			instance = new ProcuracaoBC();
		}
		return instance;
	}

	public ArrayList<Procuracao> getProcuracoes(String ni_titular, String ni_procurador) {
		
		ArrayList<ArrayList> procs = ProcuracaoDAO.getInstance().getProcuracao(ni_titular, ni_procurador);
		ArrayList<Procuracao> retorno = new ArrayList<Procuracao>();
		Procuracao procuracao = new Procuracao();
		ArrayList<Sistema> sistemas = new ArrayList<Sistema>();
		SistemaBC sistemabc = SistemaBC.getInstance();
		for (ArrayList<String> proc : procs) {
			procuracao.setIsn(proc.get(0));
			procuracao.setNiTitular(proc.get(1));
			procuracao.setNiProcurador(proc.get(2));
			procuracao.setDataInicioVigencia(proc.get(3));
			procuracao.setDataFimVigencia(proc.get(4));
			procuracao.setSituacao(proc.get(5));
			System.out.println("tam: " + proc.size());
			for(int i = 6;i<proc.size();i++){
				System.out.println("sistema:[" + i +"]" + proc.get(i) );
				sistemas.add(sistemabc.getSistema(proc.get(i)));				
			}
			procuracao.setSistemas(sistemas);
			retorno.add(procuracao);
			procuracao = new Procuracao();
		}
		return retorno;
	}
	
	public static void main(String[] args) {
		ArrayList<Procuracao> rt = ProcuracaoBC.getInstance().getProcuracoes("68281455500","00000008680");
		for (Procuracao procuracao : rt) {
			System.out.println(procuracao);
		}
		
//		->+0000016895
//		->68281455500   
//		->19007809334   
//		->+20150715
//		->+20150715
//		->A
//		->+00090
//		->+00078
		
	}
}
