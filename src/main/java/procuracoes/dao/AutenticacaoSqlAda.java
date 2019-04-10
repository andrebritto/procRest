package procuracoes.dao;

import java.util.Calendar;

import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;

public class AutenticacaoSqlAda {

	/*
	 * EXEC O96026ZN LOTA 0420004200SAO02 00090387600000272770000 Parâmetros
	 * retornados :
	 * 
	 * Órgão-Cad-Usuário Alfanumérico 05 bytes
	 * 
	 * Órgão-usuário Alfanumérico 05 bytes
	 * 
	 * Nodo-Sistema Alfanumérico 10 bytes
	 * 
	 * Código-Serviço-Sistema Alfanumérico 09 bytes
	 * 
	 * Local-Usuário Numérico 10 bytes
	 * 
	 * Setor-Usuário Numérico 04 bytes
	 * 
	 * 
	 * 04200 04200 SAO02 000903876 0000027277 0000
	 */
	@Inject
	GPDAO gpdao;

	private static AutenticacaoSqlAda instance;

	public static AutenticacaoSqlAda getInstance() {
		if (instance == null) {
			instance = new AutenticacaoSqlAda();
		}
		return instance;
	}

	public boolean autenticar(String cpf, String pwd) throws Exception {
		GPDAO gpdao = GPDAO.getInstance();
		return gpdao.autentica(cpf, pwd);
	}

	public static String MD5(String md5) {
		   try {
		        java.security.MessageDigest md = java.security.MessageDigest.getInstance("MD5");
		        byte[] array = md.digest(md5.getBytes());
		        StringBuffer sb = new StringBuffer();
		        for (int i = 0; i < array.length; ++i) {
		          sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100).substring(1,3));
		       }
		        return sb.toString();
		    } catch (java.security.NoSuchAlgorithmException e) {
		    }
		    return null;
		}
	
	public static void main(String[] args) {
			
		
		AutenticacaoSqlAda auth = AutenticacaoSqlAda.getInstance();		
		
		String credencial = "68281455500 : senharede";		
		String cred[] = StringUtils.split(credencial,":");
		StringBuilder token = new StringBuilder();
		token.append("[").append(Calendar.getInstance().getTime().toString()).append("]");
		token.append("[").append(credencial).append("]");
		System.out.println("hash: " + MD5(token.toString()));
		
//		try {
//			System.out.println(auth.autenticar(cred[0].trim(), cred[1].trim()));
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
	}

}
