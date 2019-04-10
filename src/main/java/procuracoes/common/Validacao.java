package procuracoes.common;


import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;

import procuracoes.business.ProcuracaoBC;
import procuracoes.model.Ambiente;
import procuracoes.model.Procuracao;
import procuracoes.model.Sistema;


public class Validacao {

	public static String montaQuery(Registro r){
		StringBuilder sb = new StringBuilder();
		

		sb.append("SELECT ISN AA AB AC AD AE AG AI AS AH AP AF");
		sb.append("AK001 AK002 AK003 AK004 AK005 AK006 AK007 AK008 AK009 AK010 ");
		sb.append("AK011 AK012 AK013 AK014 AK015 AK016 AK017 AK018 AK019 AK020 AK021 AK022 AK023 AK024 ");
		sb.append("AK025 AK026 AK027 AK028 AK029 AK030 AK031 ");
		sb.append("FROM 147.185 ");
		sb.append("WHERE S4=("+ r.cpfProc + "," + r.cnpjOut +",19000101,000001) TO S4=(" + r.cpfProc + ","+ r.cnpjOut + ","+r.dt+ ","+r.hr +")");
		return  sb.toString();
	}
	
	public static String utilDataDDMMYYYY(String data) {
		String formattedDate = null;
		if (StringUtils.EMPTY.equals(data) || "00000000".equals(data)) return StringUtils.EMPTY;
		try {
			Date date = new SimpleDateFormat("yyyyMMdd").parse(data);			
			formattedDate = new SimpleDateFormat("dd/MM/yyyy").format(date);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return formattedDate;
	}
	
	public static String utilHoraHHMMSS(String hora) {
		String formattedDate = null;
		if (StringUtils.EMPTY.equals(hora) || "000000".equals(hora)) return StringUtils.EMPTY; 
		try {
			Date date = new SimpleDateFormat("hhmmss").parse(hora);			
			formattedDate = new SimpleDateFormat("hh:mm:ss").format(date);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return formattedDate;
	}
	
	public static boolean validaAcesso(ArrayList<Procuracao> acesso, Registro reg) throws ParseException{
		
		Date dateInicioVigencia = null;		
		Date dateFimVigencia = null;
		Date dateCancelamento = null;		
		Date dateReg = null;
		
		System.out.println("=============================================================================================================");
		System.out.println("                                                VALIDANDO                                                    ");
		System.out.println("=============================================================================================================");
		
		for (Procuracao proc : acesso) {
			System.out.println(proc.toString());
			
			// Se tem procuraçção para o SiscoServ
			for (Sistema sistema : proc.getSistemas()) {
				if (sistema.getCodigo().equals("00038") || sistema.getCodigo().equals("8888")){
					System.out.println("Acessa Siscoserv");
				}
			}
			
			// Se cancelada
			
			if ("C".equals(proc.getSituacao())){
				System.out.println("Cancelada");
				System.out.println("inicio vigencia: " + proc.getDataInicioVigencia());
				System.out.println("Data do registro de acesso: " + utilDataDDMMYYYY(reg.dt));
				System.out.println("Data Cancelamento: " + proc.getDataCancelamento());
				System.out.println("Hora Cancelamento: " + proc.getHoraCancelamento());
				
				dateInicioVigencia = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss").parse(proc.getDataInicioVigencia() + " " + proc.getHoraInicioVigencia());
				dateReg = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss").parse(proc.getDataInicioVigencia() + " " + utilHoraHHMMSS(reg.hr));
				dateCancelamento = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss").parse(proc.getDataCancelamento() + " " + proc.getHoraCancelamento());
				System.out.println("Dentro do periodo:" + (dateReg.before(dateCancelamento) && dateReg.after(dateInicioVigencia)));
			}

			if ("A".equals(proc.getSituacao())){
				System.out.println("Ativa");
				System.out.println("Data inicio Vigencia: " + proc.getDataInicioVigencia() + " às " + proc.getHoraInicioVigencia());
				
				dateInicioVigencia = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss").parse(proc.getDataInicioVigencia() + " " + proc.getHoraInicioVigencia());
				
				System.out.println("Data do registro de acesso: " + utilDataDDMMYYYY(reg.dt));
				System.out.println("Data fim Vigencia: " + proc.getDataFimVigencia());
				
				dateFimVigencia = new SimpleDateFormat("dd/MM/yyyy").parse(proc.getDataFimVigencia());
				dateReg = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss").parse(utilDataDDMMYYYY(reg.dt)  + " " + utilHoraHHMMSS(reg.hr));

				System.out.println("Dentro do periodo:" + (dateReg.before(dateFimVigencia) && dateReg.after(dateInicioVigencia)));				
			}			
		}		
		return true;		
	}
	
	public static void main(String[] args) {
		
		BufferedReader br = null;
		ProcuracaoBC procBc = ProcuracaoBC.getInstance();		
		try {
			procBc.setAmbiente(Ambiente.DESENVOLVIMENTO);
			br = new BufferedReader(new FileReader("/home/68281455500/workspace/procRest/src/main/java/todo/resource/valid.txt"));
		    StringBuilder sb = new StringBuilder();
		    String line = br.readLine();
		    Registro reg = new Registro();
		    ArrayList<Procuracao> retArr = new ArrayList<Procuracao>();
		    while (line != null) {
		        sb.append(line);
		        sb.append(System.lineSeparator());
		        line = br.readLine();
		        if (line!=null){
		        	//System.out.println("linha: " + line);
		        	reg.dt= StringUtils.substring(line,0,8);
		        	reg.hr= StringUtils.substring(line,8,14);
		        	reg.cpfProc= StringUtils.substring(line,14,25);
		        	reg.cnpjOut= StringUtils.substring(line,25,39);		        	
		        	//retArr = gp.execQuery(montaQuery(reg));
		        	retArr = procBc.getProcuracaoTitularProcuradorDataHora(reg.cnpjOut, reg.cpfProc, reg.dt, reg.hr);		        	
		        	validaAcesso(retArr,reg);
		        	reg = new Registro();
		        }
		    }
		    String everything = sb.toString();
		    br.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
}
