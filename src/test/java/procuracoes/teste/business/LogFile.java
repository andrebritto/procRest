package procuracoes.teste.business;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import org.apache.commons.lang3.StringUtils;

public class LogFile {

	public static void main(String[] args) {
		String linha = "";
		String[] reg;
		BufferedReader reader = null;
		int qtd;
		int index=1;
		int j=0;
		ArrayList<Integer> qtds = new ArrayList<>();
		
		
		String[] pathes = {
				"/home/68281455500/git/procRest/k34624d1.txt",
				"/home/68281455500/git/procRest/k34624d2.txt",
				"/home/68281455500/git/procRest/k34624d3.txt",
				"/home/68281455500/git/procRest/k34624d4.txt",
				"/home/68281455500/git/procRest/k34624d5.txt"
				}; 
		FileWriter arq = null;
		FileWriter arq2 = null;
		PrintWriter gravarArq = null;
		PrintWriter gravarReg = null;
		
		try {
			arq = new FileWriter("/home/68281455500/git/procRest/k34624db.txt");
			arq2 = new FileWriter("/home/68281455500/git/procRest/k34624Regs.txt");
			gravarArq = new PrintWriter(arq);			
			gravarReg = new PrintWriter(arq2);
			
			
			for (String path : pathes) {
				reader = new BufferedReader(new FileReader(path));	
				qtd=0;
				try {
					gravarReg.println(path);
					while ((linha = reader.readLine()) != null) {
						
						if(linha.contains("Procuracao cancelada com sucesso.")) {
							//if(!StringUtils.isAsciiPrintable(reg[2]) && !StringUtils.isAsciiPrintable(reg[3]) ) {
							reg = linha.split(",");
							if(!StringUtils.isAsciiPrintable(reg[2]) ) {
								System.out.println("Arquivo: " + path);
								System.out.println("ISN: " + reg[0] );
								System.out.println("UA: 0" + reg[3].substring(0, 4) );
								System.out.println("Controle: " + reg[3]);
								gravarArq.println(reg[0]);
								gravarReg.println(reg[0]  + ","+ reg[1]+ ","+reg[2]+ ","+reg[3]);
								qtd=++qtd;
							}
						}						
					}
					qtds.add(qtd);				
				}catch(ArrayIndexOutOfBoundsException ex) {
					ex.printStackTrace();
					System.err.println(path);				
				}
			}			
			gravarReg.printf("Quantidade de registros por arquivo");
			System.out.println("Quantidade de registros por arquivo");
			int p=0;
			int total=0;
			for (int i : qtds) {
				total = total+i;
				gravarReg.printf("k34624d%d: %d",p++,i);
				System.out.println("k34624d" + p + ":" + i);
			}
			gravarReg.printf("Total: %d",total);
			System.out.println("Total: " + total);
			reader.close();			
			arq.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
