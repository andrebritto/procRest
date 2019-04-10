package procuracoes.model;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import br.gov.serpro.util.adabas.annotation.Adabas;



/*
 * 	AA NR-SISTEMA
	AB SG-SISTEMA
	AC NM-SISTEMA
	AD NR-EXERCICIO
	AE IN-TIPO-SISTEMA
 * 
 * */
@XmlRootElement
public class Sistema {	
	
	@Adabas(adaName="ISN")
	private String isn;
	
	@Adabas(adaName="AA")
	private String codigo;
	
	@Adabas(adaName="AB")
	private String sigla;
	
	@Adabas(adaName="AC")
	private String descricao;
	
	@Adabas(adaName="AE")
	private String tipoPessoa;
	
	
	public Sistema(){
		
	}
	
	
	public Sistema(ArrayList<String> novo) {
		super();
		this.isn = novo.get(0);
		this.codigo = novo.get(1);
		this.sigla = novo.get(2);
		this.descricao = novo.get(3);
		this.tipoPessoa = novo.get(4);
	}
	@XmlElement
	public String getIsn() {
		return isn;
	}
	public void setIsn(String isn) {
		this.isn = isn;
	}
	@XmlElement
	public String getCodigo() {
		return codigo;
	}
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
	@XmlElement
	public String getSigla() {
		return sigla;
	}
	
	public void setSigla(String sigla) {
		this.sigla = sigla;
	}
	@XmlElement
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	@XmlElement
	public String getTipoPessoa() {
		return tipoPessoa;
	}
	public void setTipoPessoa(String tipoPessoa) {
		this.tipoPessoa = tipoPessoa;
	}
	
//	@Override
//	public String toString() {
//		return isn + "," + codigo + ","
//				+ sigla + "," + descricao + ","
//				+ tipoPessoa;
//	}
	@Override
	public String toString() {
		return "Sistema [isn=" + isn + ", codigo=" + codigo + ", sigla="
				+ sigla + ", descricao=" + descricao + ", tipoPessoa="
				+ tipoPessoa + "]";
	}

	
}
