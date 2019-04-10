package procuracoes.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.KeyStore;
import java.security.Provider;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;

import br.gov.serpro.certificado.DadosCertificado;
import procuracoes.business.ContribuinteBC;
import procuracoes.business.DataHoraUtil;
import procuracoes.model.Ambiente;
import procuracoes.model.PessoaFisica;
import procuracoes.model.PessoaJuridica;

public class CertificadoVerificador {

	public static void main(String[] args) {

//		String filepath ="";
//		
//		filepath  = "/home/68281455500/Desktop/certValid/";
////		filepath  = "/home/68281455500/workspace/procRest/src/main/java/todo/resource/certs/todos/";
//		File file = new File(filepath);
//		
//
//		for (File path : file.listFiles()) {
//			try {				
//				verifica(path.getCanonicalPath());
//			} catch (IOException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		}
		
		
		verifica("/home/68281455500/Desktop/certValid/131328_RESPONSAVEL_LEGAL_DORMENTESPE__90101022468.p12");
		

	}

	public static void verifica(String caminhoDoCertificadoDoCliente) {
		
		String acsPath="/home/68281455500/Desktop/certValid/pasta/";
		
		
		CertificateFactory cf;
		FileInputStream fis;
		Collection c;
		Iterator i;
		File file = new File(acsPath);
		List<X509Certificate> listACs = new ArrayList<X509Certificate>();
		List<X509Certificate> listCerts = new ArrayList<X509Certificate>();
		
		try {
			for (File f : file.listFiles()) {
				fis = new FileInputStream(f.getAbsolutePath());
				cf = CertificateFactory.getInstance("X.509");
				c = cf.generateCertificates(fis);
				i = c.iterator();
				while (i.hasNext()) {
					X509Certificate cert = (X509Certificate)i.next();
//				    System.out.println(cert.getIssuerX500Principal());
				    listACs.add(cert);
				 }
				
			}
			 		
			
			InputStream entrada = new FileInputStream(caminhoDoCertificadoDoCliente);
			KeyStore ks = KeyStore.getInstance("pkcs12");
			ks.load(entrada, "serpro".toCharArray());
			String alias = null;
			Enumeration<String> al = ks.aliases();
			DadosCertificado dados = null;
			while (al.hasMoreElements()) {
				alias = al.nextElement();
				
				if (ks.containsAlias(alias)) {
					info("Alias exists : '" + alias + "'");
					X509Certificate cert = (X509Certificate) ks.getCertificate(alias);
//					dados = DadosCertificado.getInstance(cert);
//					System.out.println(cert.getIssuerX500Principal().getName());
					listCerts.add(cert);
				}
				
			}
			
			for (X509Certificate cert : listCerts) {
				System.out.println("Certificado:" + cert.getIssuerX500Principal());
				for (X509Certificate ac : listACs) {
					System.out.println("AC:[" + (cert.getIssuerX500Principal().equals(ac.getIssuerX500Principal())) + "]" + ac.getIssuerX500Principal());
				}
			}
			
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public static void verifica2(String caminhoDoCertificadoDoCliente) {
		try {
			PessoaJuridica pj = null;
			PessoaFisica pf = null;
			ContribuinteBC contribBc = ContribuinteBC.getInstance();
			
			contribBc.setAmbiente(Ambiente.HOMOLOGACAO);
			
			DadosCertificado dados = null;
			String dataNascimentoBase = "";
			String dataNascimentocert = "";

			String senhaDoCertificadoDoCliente = "serpro";

			InputStream entrada = new FileInputStream(
					caminhoDoCertificadoDoCliente);
			KeyStore ks = KeyStore.getInstance("pkcs12");
			try {
				ks.load(entrada, senhaDoCertificadoDoCliente.toCharArray());
			} catch (IOException e) {
				throw new Exception(
						"Senha do Certificado Digital incorreta ou Certificado inválido.");
			}

			Provider pp = ks.getProvider();
//			info("--------------------------------------------------------");
//			info("Provider   : " + pp.getName());
//			info("Prov.Vers. : " + pp.getVersion());
//			info("KS Type    : " + ks.getType());
//			info("KS DefType : " + ks.getDefaultType());

			String alias = null;
			Enumeration<String> al = ks.aliases();
			while (al.hasMoreElements()) {
				alias = al.nextElement();
				info("--------------------------------------------------------");
				if (ks.containsAlias(alias)) {
					info("Alias exists : '" + alias + "'");
					X509Certificate cert = (X509Certificate) ks
							.getCertificate(alias);
					dados = DadosCertificado.getInstance(cert);
					pf = contribBc.getPF(dados.getCpf());
					dataNascimentoBase = DataHoraUtil.dataDDMMYYYYFormatador(
							pf.getNascimento(), "ddMMyyy", "dd/MM/yyyy");
					dataNascimentocert = DataHoraUtil.dataDDMMYYYYFormatador(
							dados.getDataNascimentoPF(), "ddMMyyy",
							"dd/MM/yyyy");

					info("Validade: " + dados.getFimValidade());
					if (!dataNascimentoBase.equals(dataNascimentocert)) {
						throw new Exception("Datas de Nascimento divergentes");
					}
					if (dados.isCertificadoPessoaFisica()) {
						info("CPF: " + dados.getCpf());
						info("nascimento base: " + dataNascimentoBase);
						info("nacimento certificado: " + dataNascimentocert);
					} else {
						pj = contribBc.getPJ(dados.getCnpj());
						// Situação 2 é ativo
						info("Situação Cadastral: " + pj.getCodigoSituacaoCadastral());
						info("Responsavel Legal:");
						info("CPF Base : " + pj.getResponsavelLegal().getNi());
						info("CPF: " + dados.getCpf());
						info("nascimento base: "
								+ DataHoraUtil.dataDDMMYYYYFormatador(pj
										.getResponsavelLegal().getNascimento(),
										"ddMMyyy", "dd/MM/yyyy"));
						info("nacimento certificado: " + dataNascimentocert);
					}

					// info("Certificate  : '" + cert.toString() + "'");
					
					
					// info("SerialNumber : '" + cert.getSerialNumber() + "'");
					// info("SigAlgName   : '" + cert.getSigAlgName() + "'");
					// info("NotBefore    : '" + cert.getNotBefore().toString()
					// + "'");
					// info("NotAfter     : '" + cert.getNotAfter().toString() +
					// "'");
					
					info(new String(cert.getTBSCertificate()));
					
				} else {
					info("Alias doesn't exists : '" + alias + "'");
				}
			}
		} catch (Exception e) {
			error(e.toString());
		}
	}

	public static void info(String entrada) {
		System.out.println(entrada);
	}

	public static void error(String entrada) {
		System.err.println(entrada);
	}



}
