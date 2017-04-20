package todo.business;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

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
			procuracao.setOrigem(proc.get(6));
			System.out.println("tam: " + proc.size());
			for(int i = 7;i<proc.size();i++){
				System.out.println("sistema:[" + i +"]" + proc.get(i) );
				sistemas.add(sistemabc.getSistema(proc.get(i)));				
			}
			procuracao.setSistemas(sistemas);
			retorno.add(procuracao);
			procuracao = new Procuracao();
		}
		return retorno;
	}
	
		void criarProcuracoes(ArrayList<Procuracao> procuracoes){
			
			for (Procuracao procuracao : procuracoes) {
				ProcuracaoDAO.getInstance().criarProcuracao(procuracao);
			}
			
			
		}
		
	
		public void montaProcuracoes(ArrayList<Procuracao> procuracoes, ArrayList<String> niProcuradores, ArrayList<Sistema> sistemas){
			
			for (Procuracao procuracao : procuracoes) {
				for (String procurador : niProcuradores) {
					procuracao.setNiProcurador(procurador);
					procuracao.setSistemas(sistemas);
					criarProcuracoes(procuracoes);
				}
			}
		}
		
	private static String utilDataGrandePorte(String data){
		String formattedDate = null;
		try {
			Date date = new SimpleDateFormat("dd/MM/yyyy").parse(data);
			formattedDate = new SimpleDateFormat("yyyyMMdd").format(date);			
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return formattedDate;  
	}
	public static void main(String[] args) {
//		ArrayList<Procuracao> rt = ProcuracaoBC.getInstance().getProcuracoes("19007809334","81275714587");
//		for (Procuracao procuracao : rt) {
//			System.out.println(procuracao);
		
		SistemaBC sistemabc = SistemaBC.getInstance();
		ArrayList<Sistema> sistemas = new ArrayList<Sistema>();
		Procuracao procuracao = new Procuracao();		
		ArrayList<Procuracao> procuracoes = new ArrayList<Procuracao>();  
		ArrayList<String> niProcuradores = new ArrayList<String>();
		
		
		String dtInicioVigencia= utilDataGrandePorte("01/01/2017");
		String dtFimVigencia = utilDataGrandePorte("31/01/2018");
		
		
		
		procuracao.setNiTitular("19007809334");		
		procuracao.setDataFimVigencia(dtFimVigencia);
		procuracao.setDataInicioVigencia(dtInicioVigencia);		
		procuracao.setTipoNiProcurado("1");
		procuracao.setTipoNiTitular("1");
		
		niProcuradores.add("81275714587");
		niProcuradores.add("00000004260");
		niProcuradores.add("00000004693");
		niProcuradores.add("00000005070");
		niProcuradores.add("00000008680");
		niProcuradores.add("19007809334");
		niProcuradores.add("82586004515");
		
		sistemas.add(sistemabc.getSistema("00001"));				
		sistemas.add(sistemabc.getSistema("00002"));
		sistemas.add(sistemabc.getSistema("00003"));
		sistemas.add(sistemabc.getSistema("00120"));
		
		procuracao.setSistemas(sistemas);
		procuracoes.add(procuracao);		
		
		ProcuracaoBC.getInstance().montaProcuracoes(procuracoes, niProcuradores, sistemas);
		
		
		
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
