package procuracoes.test.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;
import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import procuracoes.business.DataHoraUtil;
import procuracoes.dao.GPDAO;
import procuracoes.model.Ambiente;
import procuracoes.model.Data;
import procuracoes.model.Log;
import procuracoes.model.RegistroEstatistica;

import com.google.gson.Gson;

public class LogDAO {
	Logger logger = LoggerFactory.getLogger(LogDAO.class);
	private static String st;
	private static CharsetEncoder utf8Encoder = Charset.forName("ISO-8859-1").newEncoder();
	private static CharsetDecoder utf8Decoder = Charset.forName("ISO-8859-1").newDecoder();
	private static CharsetEncoder asciiEncoder = Charset.forName("ASCII").newEncoder();
	private static CharsetDecoder asciiDecoder = Charset.forName("ASCII").newDecoder();
	private static CharsetEncoder ebcdicEncoder = Charset.forName("Cp1047").newEncoder();
	private static CharsetDecoder ebcdicDecoder = Charset.forName("Cp1047").newDecoder();
	

	static GPDAO gpdao = null;
	
	public static void listaLog(){		
		ArrayList<Log> logs = new ArrayList<Log>();
		ArrayList<RegistroEstatistica> regEstat = new ArrayList<RegistroEstatistica>();
		gpdao = GPDAO.getInstance();
		gpdao.configuraAmbiente(Ambiente.DESENVOLVIMENTO.valorAmbiente);
		String query="SELECT ISN AC AD AE AF AM AN FROM 047.183 WHERE AC >=20180611";
		Gson json = new Gson();
		DataHoraUtil dthrUtil = new DataHoraUtil();
		try {
			logs = gpdao.execQuery(query, Log.class);
			
			Data data = null;
			RegistroEstatistica reg = new RegistroEstatistica();
			for (Log log : logs) {
				data = dthrUtil.grandePorteToData(log.getDataOcorrencia());
				reg.setAno(data.getAno());
				reg.setDia(data.getDia());
				reg.setMes(data.getMes());
				reg.setCodigoUa(log.getCodigoUa());
				regEstat.add(reg);
				reg = new RegistroEstatistica();
				System.out.println(json.toJson(log));
			}
			
			for (RegistroEstatistica registroEstatistica : regEstat) {
				System.out.println(json.toJson(registroEstatistica));
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public static void main(String[] args) {		
		listaLog();
	}
	

	public static void main2(String[] args) {
		
		File f = new File(
				"/home/68281455500/git/procRest/src/test/java/procuracoes/test/util/Log-Desenvovimento");
			String x;
			
			// ISN, NM-DADOS, CD-UN-CAC, CD-CONTROLE
			String[] nome = {"ISN" , "NM-DADOS", "CD-UN-CAC", "CD-CONTROLE"};
			int i=0;
			BufferedReader br;
			try {
				br = new BufferedReader(new FileReader(f));
				while ((st = br.readLine()) != null){					
					System.out.println("=====================================================");
					System.out.println("Linha: " + st);
					System.out.println("=====================================================");
					for (String campo : st.split(",")) {
						System.out.println(nome[i++] + ": " + campo);
					}				
					
					i=0;
					try {						
						decode(st, ebcdicDecoder ,ebcdicEncoder,"ebcdic");
					} catch (Exception e) {			
						try {				
							decode(st, asciiDecoder ,asciiEncoder,"ascii");
						} catch (Exception e1) {
							try {					
								decode(st, utf8Decoder, utf8Encoder,"utf8");
							} catch (Exception e2) {
								System.out.println("Apagar ISN: " + st.split(",")[0]);
//								e2.printStackTrace();
							}
						}
					}
				}
			} catch (FileNotFoundException e3) {
				// TODO Auto-generated catch block
				e3.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

	}


	private static void decode(String linha, CharsetDecoder decoder, CharsetEncoder encoder, String contexto) throws Exception {
		CharBuffer charBuffer = CharBuffer.wrap(linha);
		ByteBuffer bbuf = encoder.encode(charBuffer);
		CharBuffer cbuf = decoder.decode(bbuf);
		String s = cbuf.toString();
		System.out.println("Contexto: " + contexto);		
		System.out.println("Decodificado com sucesso em " + contexto + " : " + s);
	}

}
