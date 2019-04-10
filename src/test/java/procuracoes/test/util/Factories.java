package procuracoes.test.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import procuracoes.model.Procuracao;
import procuracoes.model.RequisicaoProcuracao;
import procuracoes.model.Sistema;


import com.google.gson.Gson;

public class Factories<T> {

	
	@SuppressWarnings("unchecked")
	public ArrayList<RequisicaoProcuracao> getLoteProcuracoes(int quantidade){
		File f = new File("src/test/resource/procuracoes_lote.txt");
		ArrayList<RequisicaoProcuracao> objects = new ArrayList<>();
		ArrayList<RequisicaoProcuracao> procuracoes = new ArrayList<>();
		RequisicaoProcuracao p = null;
		objects = (ArrayList<RequisicaoProcuracao>) getObjetos((Class<T>) RequisicaoProcuracao.class,f);		
		for (Object object : objects) {
			p = (RequisicaoProcuracao) object;
			procuracoes.add(p);			
		}
		return procuracoes;
	}
	
	@SuppressWarnings("unchecked")
	public ArrayList<Procuracao> getProcuracoes(int quantidade){
		File f = new File("src/test/resource/procuracoes.txt");
		ArrayList<Procuracao> objects = new ArrayList<>();
		ArrayList<Procuracao> procuracoes = new ArrayList<>();
		Procuracao p = null;
		objects = (ArrayList<Procuracao>) getObjetos((Class<T>) Procuracao.class,f);		
		for (Object object : objects) {
			p = (Procuracao) object;
			procuracoes.add(p);			
		}
		return procuracoes;
	}
	
	@SuppressWarnings("unchecked")
	public ArrayList<Sistema> getSistemas(int quantidade){
		File f = new File("src/test/resource/sistemas.txt");
		ArrayList<Sistema> objects = new ArrayList<>();
		ArrayList<Sistema> sistemas = new ArrayList<>();
		Sistema p = null;
		objects = (ArrayList<Sistema>) getObjetos((Class<T>) Sistema.class,f);
		for(int i=0; i<quantidade; i++){			
			p = (Sistema) objects.get(i);
			sistemas.add(p);
		}			
		return sistemas;
	}
	
	private ArrayList<T> getObjetos(Class<T> obj, File f){
		ArrayList<T> objs = new ArrayList<>();
		String linha = "";
		Gson json = new Gson();
		
		try (Scanner scanner =  new Scanner(f)){
		      while (scanner.hasNextLine()){
		    	  linha = scanner.nextLine();			      
		    	  objs.add(json.fromJson(linha, obj));
		      }
		      
		    } catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		return objs;
	}
	
	public static void main(String[] args) {
		Factories<Sistema> f = new Factories<>();		
		ArrayList<Sistema> procuracoes  = f.getSistemas(3);
		System.out.println("procuracao ....");
		for (Sistema procuracao : procuracoes) {
			System.out.println(procuracao);
		}
		
	}
}
