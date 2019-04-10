package procuracoes.model;

import java.util.ArrayList;

import br.gov.serpro.util.adabas.annotation.Adabas;
import br.gov.serpro.util.adabas.annotation.Rotina;

public class Procuracao {

	@Adabas(adaName = "ISN")
	private String isn;
	@Adabas(adaName = "AA")
	private String niTitular;

	private String nomeTitular;
	@Adabas(adaName = "AC")
	private String niProcurador;

	private String nomeProcurador;

	@Adabas(adaName = "AE")
	private String dataInicioVigencia;
	@Adabas(adaName = "AF")
	private String horaInicioVigencia;
	@Adabas(adaName = "AG")
	private String dataFimVigencia;
	@Adabas(adaName = "AH")
	private String dataCancelamento;
	@Adabas(adaName = "AP")
	private String horaCancelamento;
	@Adabas(adaName = "AI")
	private String situacao;
	@Adabas(adaName = "AS")
	private String origem;
	@Adabas(adaName = "AL")
	private String origemCancelamento;
	@Adabas(adaName = "AB")
	private String tipoNiTitular;

	@Adabas(adaName = "AD")
	private String tipoNiProcurado;

	private ArrayList<Sistema> sistemas;

	private String strSistemas;

	@Adabas(adaName = "AT")
	private String numeroControle;
	@Adabas(adaName = "BA")
	private String cpfCancelamento;
	@Adabas(adaName = "AV")
	private String cpfConferencia;
	@Adabas(adaName = "AW")
	private String dataConferencia;
	@Adabas(adaName = "AX")
	private String cpfAprovacao;
	@Adabas(adaName = "AY")
	private String dataAprovacao;
	@Adabas(adaName = "AZ")
	private String cdUaAtendimento;

	private boolean vigente;

	static final String ECAC = "e-CAC";
	static final String RFB = "Receita Federal";

	public String getCdUaAtendimento() {
		return cdUaAtendimento;
	}

	public void setCdUaAtendimento(String cdUaAtendimento) {
		this.cdUaAtendimento = cdUaAtendimento;
	}

	public String getIsn() {
		return isn;
	}

	public void setIsn(String isn) {
		this.isn = isn;
	}

	public String getNiTitular() {
		return niTitular;
	}

	public void setNiTitular(String niTitular) {
		this.niTitular = niTitular;
	}

	public String getNiProcurador() {
		return niProcurador;
	}

	public void setNiProcurador(String niProcurador) {
		this.niProcurador = niProcurador;
	}

	public String getDataInicioVigencia() {
		return dataInicioVigencia;
	}

	public void setDataInicioVigencia(String dataInicioVigencia) {
		this.dataInicioVigencia = dataInicioVigencia;
	}

	public String getDataFimVigencia() {
		return dataFimVigencia;
	}

	public void setDataFimVigencia(String dataFimVigencia) {
		this.dataFimVigencia = dataFimVigencia;
	}

	public String getHoraInicioVigencia() {
		return horaInicioVigencia;
	}

	public void setHoraInicioVigencia(String horaInicioVigencia) {
		this.horaInicioVigencia = horaInicioVigencia;
	}

	public ArrayList<Sistema> getSistemas() {
		return sistemas;
	}

	public void setSistemas(ArrayList<Sistema> sistemas) {
		this.sistemas = sistemas;
	}

	public static String cabecalhoCsv() {
		return "isn,niTitular,niProcurador,dataInicioVigencia,horaInicioVigencia,dataFimVigencia,dataCancelamento,horaCancelamento,situacao,origem,				origemCancelamento, tipoNiTitular,tipoNiProcurado,sistemas,numeroControle,cpfCancelamento,cdUaAtendimento,cpfConferencia,dataConferencia,cpfAprovacao, dataAprovacao";
	}
	
	public String csv() {
		return isn +","+ niTitular +","+ niProcurador +","+ dataInicioVigencia +","+ horaInicioVigencia +","+ dataFimVigencia +","+ dataCancelamento
				+","+ horaCancelamento +","+ situacao +","+ origem +","+ origemCancelamento +","+ tipoNiTitular +","+ tipoNiProcurado +","+ 
				cdsistema001 + ";" + cdsistema002 + ";" + cdsistema003 +
				";" + cdsistema004 + ";" + cdsistema005 + ";" + cdsistema006 +
				";" + cdsistema007 + ";" + cdsistema008 + ";" + cdsistema009 +
				";" + cdsistema010 + ";" + cdsistema011 + ";" + cdsistema012 +
				";" + cdsistema013 + ";" + cdsistema014 + ";" + cdsistema015 +
				";" + cdsistema016 + ";" + cdsistema017 + ";" + cdsistema018 +
				";" + cdsistema019 + ";" + cdsistema020 + ";" + cdsistema021 +
				";" + cdsistema022 + ";" + cdsistema023 + ";" + cdsistema024 +
				";" + cdsistema025 + ";" + cdsistema026 + ";" + cdsistema027 +
				";" + cdsistema028 + ";" + cdsistema029 + ";" + cdsistema030 +
				";" + cdsistema031 + ";" + cdsistema032 + ";" + cdsistema033 +
				";" + cdsistema034 + ";" + cdsistema035 + ";" + cdsistema036 +
				";" + cdsistema037 + ";" + cdsistema038 + ";" + cdsistema039
				+","+ numeroControle +","+ cpfCancelamento +","+ cdUaAtendimento +","+ cpfConferencia +","+ dataConferencia +","+ cpfAprovacao
				+","+ dataAprovacao;
	}

	@Override
	public String toString() {
		return "Procuracao [isn=" + isn + ", niTitular=" + niTitular + ", nomeTitular=" + nomeTitular
				+ ", niProcurador=" + niProcurador + ", nomeProcurador=" + nomeProcurador + ", dataInicioVigencia="
				+ dataInicioVigencia + ", horaInicioVigencia=" + horaInicioVigencia + ", dataFimVigencia="
				+ dataFimVigencia + ", dataCancelamento=" + dataCancelamento + ", horaCancelamento=" + horaCancelamento
				+ ", situacao=" + situacao + ", origem=" + origem + ", origemCancelamento=" + origemCancelamento
				+ ", tipoNiTitular=" + tipoNiTitular + ", tipoNiProcurado=" + tipoNiProcurado + ", sistemas=" + sistemas
				+ ", numeroControle=" + numeroControle + ", cpfCancelamento=" + cpfCancelamento + ", cdUaAtendimento="
				+ cdUaAtendimento + ", cpfConferencia=" + cpfConferencia + ", dataConferencia=" + dataConferencia
				+ ", cpfAprovacao=" + cpfAprovacao + ", dataAprovacao=" + dataAprovacao + ", vigente=" + vigente
				+ ", cdsistema001=" + cdsistema001 + ", cdsistema002=" + cdsistema002 + ", cdsistema003=" + cdsistema003
				+ ", cdsistema004=" + cdsistema004 + ", cdsistema005=" + cdsistema005 + ", cdsistema006=" + cdsistema006
				+ ", cdsistema007=" + cdsistema007 + ", cdsistema008=" + cdsistema008 + ", cdsistema009=" + cdsistema009
				+ ", cdsistema010=" + cdsistema010 + ", cdsistema011=" + cdsistema011 + ", cdsistema012=" + cdsistema012
				+ ", cdsistema013=" + cdsistema013 + ", cdsistema014=" + cdsistema014 + ", cdsistema015=" + cdsistema015
				+ ", cdsistema016=" + cdsistema016 + ", cdsistema017=" + cdsistema017 + ", cdsistema018=" + cdsistema018
				+ ", cdsistema019=" + cdsistema019 + ", cdsistema020=" + cdsistema020 + ", cdsistema021=" + cdsistema021
				+ ", cdsistema022=" + cdsistema022 + ", cdsistema023=" + cdsistema023 + ", cdsistema024=" + cdsistema024
				+ ", cdsistema025=" + cdsistema025 + ", cdsistema026=" + cdsistema026 + ", cdsistema027=" + cdsistema027
				+ ", cdsistema028=" + cdsistema028 + ", cdsistema029=" + cdsistema029 + ", cdsistema030=" + cdsistema030
				+ ", cdsistema031=" + cdsistema031 + ", cdsistema032=" + cdsistema032 + ", cdsistema033=" + cdsistema033
				+ ", cdsistema034=" + cdsistema034 + ", cdsistema035=" + cdsistema035 + ", cdsistema036=" + cdsistema036
				+ ", cdsistema037=" + cdsistema037 + ", cdsistema038=" + cdsistema038 + ", cdsistema039=" + cdsistema039
				+ "]";
	}

	public String getSituacao() {
		return situacao;
	}

	public void setSituacao(String situacao) {
		this.situacao = situacao;
	}

	public String getOrigem() {

		if ("0".equals(origem)) {
			return ECAC;
		} else if ("1".equals(origem)) {
			return RFB;
		} else {
			return "Origem invalida[" + origem + "]";
		}

	}

	public void setOrigem(String origem) {
		this.origem = origem;
	}

	public String getTipoNiTitular() {
		return tipoNiTitular;
	}

	public void setTipoNiTitular(String tipoNiTitular) {
		this.tipoNiTitular = tipoNiTitular;
	}

	public String getTipoNiProcurador() {
		return tipoNiProcurado;
	}

	public void setTipoNiProcurado(String tipoNiProcurado) {
		this.tipoNiProcurado = tipoNiProcurado;
	}

	public String getNomeTitular() {
		return nomeTitular;
	}

	public void setNomeTitular(String nomeTitular) {
		this.nomeTitular = nomeTitular;
	}

	public String getNomeProcurador() {
		return nomeProcurador;
	}

	public void setNomeProcurador(String nomeProcurador) {
		this.nomeProcurador = nomeProcurador;
	}

	public String getDataCancelamento() {
		return dataCancelamento;
	}

	public void setDataCancelamento(String dataCancelamento) {
		this.dataCancelamento = dataCancelamento;
	}

	public String getHoraCancelamento() {
		return horaCancelamento;
	}

	public void setHoraCancelamento(String horaCancelamento) {
		this.horaCancelamento = horaCancelamento;
	}

	public String getNumeroControle() {
		return numeroControle;
	}

	public void setNumeroControle(String numeroControle) {
		this.numeroControle = numeroControle;
	}

	public String getCpfCancelamento() {
		return cpfCancelamento;
	}

	public void setCpfCancelamento(String cpfCancelamento) {
		this.cpfCancelamento = cpfCancelamento;
	}

	public String getCpfAprovacao() {
		return cpfAprovacao;
	}

	public void setCpfAprovacao(String cpfAprovacao) {
		this.cpfAprovacao = cpfAprovacao;
	}

	public String getDataAprovacao() {
		return dataAprovacao;
	}

	public void setDataAprovacao(String dataAprovacao) {
		this.dataAprovacao = dataAprovacao;
	}

	public String getCpfConferencia() {
		return cpfConferencia;
	}

	public void setCpfConferencia(String cpfConferencia) {
		this.cpfConferencia = cpfConferencia;
	}

	public String getDataConferencia() {
		return dataConferencia;
	}

	public void setDataConferencia(String dataConferencia) {
		this.dataConferencia = dataConferencia;
	}

	@Adabas(adaName = "AK001")
	private String cdsistema001;
	@Adabas(adaName = "AK002")
	private String cdsistema002;
	@Adabas(adaName = "AK003")
	private String cdsistema003;
	@Adabas(adaName = "AK004")
	private String cdsistema004;
	@Adabas(adaName = "AK005")
	private String cdsistema005;
	@Adabas(adaName = "AK006")
	private String cdsistema006;
	@Adabas(adaName = "AK007")
	private String cdsistema007;
	@Adabas(adaName = "AK008")
	private String cdsistema008;
	@Adabas(adaName = "AK009")
	private String cdsistema009;
	@Adabas(adaName = "AK010")
	private String cdsistema010;
	@Adabas(adaName = "AK011")
	private String cdsistema011;
	@Adabas(adaName = "AK012")
	private String cdsistema012;
	@Adabas(adaName = "AK013")
	private String cdsistema013;
	@Adabas(adaName = "AK014")
	private String cdsistema014;
	@Adabas(adaName = "AK015")
	private String cdsistema015;
	@Adabas(adaName = "AK016")
	private String cdsistema016;
	@Adabas(adaName = "AK017")
	private String cdsistema017;
	@Adabas(adaName = "AK018")
	private String cdsistema018;
	@Adabas(adaName = "AK019")
	private String cdsistema019;
	@Adabas(adaName = "AK020")
	private String cdsistema020;
	@Adabas(adaName = "AK021")
	private String cdsistema021;
	@Adabas(adaName = "AK022")
	private String cdsistema022;
	@Adabas(adaName = "AK023")
	private String cdsistema023;
	@Adabas(adaName = "AK024")
	private String cdsistema024;
	@Adabas(adaName = "AK025")
	private String cdsistema025;
	@Adabas(adaName = "AK026")
	private String cdsistema026;
	@Adabas(adaName = "AK027")
	private String cdsistema027;
	@Adabas(adaName = "AK028")
	private String cdsistema028;
	@Adabas(adaName = "AK029")
	private String cdsistema029;
	@Adabas(adaName = "AK030")
	private String cdsistema030;
	@Adabas(adaName = "AK031")
	private String cdsistema031;
	@Adabas(adaName = "AK032")
	private String cdsistema032;
	@Adabas(adaName = "AK033")
	private String cdsistema033;
	@Adabas(adaName = "AK034")
	private String cdsistema034;
	@Adabas(adaName = "AK035")
	private String cdsistema035;
	@Adabas(adaName = "AK036")
	private String cdsistema036;
	@Adabas(adaName = "AK037")
	private String cdsistema037;
	@Adabas(adaName = "AK038")
	private String cdsistema038;
	@Adabas(adaName = "AK039")
	private String cdsistema039;

	public String getCdsistema001() {
		return cdsistema001;
	}

	public void setCdsistema001(String cdsistema001) {
		this.cdsistema001 = cdsistema001;
	}

	public String getCdsistema002() {
		return cdsistema002;
	}

	public void setCdsistema002(String cdsistema002) {
		this.cdsistema002 = cdsistema002;
	}

	public String getCdsistema003() {
		return cdsistema003;
	}

	public void setCdsistema003(String cdsistema003) {
		this.cdsistema003 = cdsistema003;
	}

	public String getCdsistema004() {
		return cdsistema004;
	}

	public void setCdsistema004(String cdsistema004) {
		this.cdsistema004 = cdsistema004;
	}

	public String getCdsistema005() {
		return cdsistema005;
	}

	public void setCdsistema005(String cdsistema005) {
		this.cdsistema005 = cdsistema005;
	}

	public String getCdsistema006() {
		return cdsistema006;
	}

	public void setCdsistema006(String cdsistema006) {
		this.cdsistema006 = cdsistema006;
	}

	public String getCdsistema007() {
		return cdsistema007;
	}

	public void setCdsistema007(String cdsistema007) {
		this.cdsistema007 = cdsistema007;
	}

	public String getCdsistema008() {
		return cdsistema008;
	}

	public void setCdsistema008(String cdsistema008) {
		this.cdsistema008 = cdsistema008;
	}

	public String getCdsistema009() {
		return cdsistema009;
	}

	public void setCdsistema009(String cdsistema009) {
		this.cdsistema009 = cdsistema009;
	}

	public String getCdsistema010() {
		return cdsistema010;
	}

	public void setCdsistema010(String cdsistema010) {
		this.cdsistema010 = cdsistema010;
	}

	public String getCdsistema011() {
		return cdsistema011;
	}

	public void setCdsistema011(String cdsistema011) {
		this.cdsistema011 = cdsistema011;
	}

	public String getCdsistema012() {
		return cdsistema012;
	}

	public void setCdsistema012(String cdsistema012) {
		this.cdsistema012 = cdsistema012;
	}

	public String getCdsistema013() {
		return cdsistema013;
	}

	public void setCdsistema013(String cdsistema013) {
		this.cdsistema013 = cdsistema013;
	}

	public String getCdsistema014() {
		return cdsistema014;
	}

	public void setCdsistema014(String cdsistema014) {
		this.cdsistema014 = cdsistema014;
	}

	public String getCdsistema015() {
		return cdsistema015;
	}

	public void setCdsistema015(String cdsistema015) {
		this.cdsistema015 = cdsistema015;
	}

	public String getCdsistema016() {
		return cdsistema016;
	}

	public void setCdsistema016(String cdsistema016) {
		this.cdsistema016 = cdsistema016;
	}

	public String getCdsistema017() {
		return cdsistema017;
	}

	public void setCdsistema017(String cdsistema017) {
		this.cdsistema017 = cdsistema017;
	}

	public String getCdsistema018() {
		return cdsistema018;
	}

	public void setCdsistema018(String cdsistema018) {
		this.cdsistema018 = cdsistema018;
	}

	public String getCdsistema019() {
		return cdsistema019;
	}

	public void setCdsistema019(String cdsistema019) {
		this.cdsistema019 = cdsistema019;
	}

	public String getCdsistema020() {
		return cdsistema020;
	}

	public void setCdsistema020(String cdsistema020) {
		this.cdsistema020 = cdsistema020;
	}

	public String getCdsistema021() {
		return cdsistema021;
	}

	public void setCdsistema021(String cdsistema021) {
		this.cdsistema021 = cdsistema021;
	}

	public String getCdsistema022() {
		return cdsistema022;
	}

	public void setCdsistema022(String cdsistema022) {
		this.cdsistema022 = cdsistema022;
	}

	public String getCdsistema023() {
		return cdsistema023;
	}

	public void setCdsistema023(String cdsistema023) {
		this.cdsistema023 = cdsistema023;
	}

	public String getCdsistema024() {
		return cdsistema024;
	}

	public void setCdsistema024(String cdsistema024) {
		this.cdsistema024 = cdsistema024;
	}

	public String getCdsistema025() {
		return cdsistema025;
	}

	public void setCdsistema025(String cdsistema025) {
		this.cdsistema025 = cdsistema025;
	}

	public String getCdsistema026() {
		return cdsistema026;
	}

	public void setCdsistema026(String cdsistema026) {
		this.cdsistema026 = cdsistema026;
	}

	public String getCdsistema027() {
		return cdsistema027;
	}

	public void setCdsistema027(String cdsistema027) {
		this.cdsistema027 = cdsistema027;
	}

	public String getCdsistema028() {
		return cdsistema028;
	}

	public void setCdsistema028(String cdsistema028) {
		this.cdsistema028 = cdsistema028;
	}

	public String getCdsistema029() {
		return cdsistema029;
	}

	public void setCdsistema029(String cdsistema029) {
		this.cdsistema029 = cdsistema029;
	}

	public String getCdsistema030() {
		return cdsistema030;
	}

	public void setCdsistema030(String cdsistema030) {
		this.cdsistema030 = cdsistema030;
	}

	public String getCdsistema031() {
		return cdsistema031;
	}

	public void setCdsistema031(String cdsistema031) {
		this.cdsistema031 = cdsistema031;
	}

	public String getCdsistema032() {
		return cdsistema032;
	}

	public void setCdsistema032(String cdsistema032) {
		this.cdsistema032 = cdsistema032;
	}

	public String getCdsistema033() {
		return cdsistema033;
	}

	public void setCdsistema033(String cdsistema033) {
		this.cdsistema033 = cdsistema033;
	}

	public String getCdsistema034() {
		return cdsistema034;
	}

	public void setCdsistema034(String cdsistema034) {
		this.cdsistema034 = cdsistema034;
	}

	public String getCdsistema035() {
		return cdsistema035;
	}

	public void setCdsistema035(String cdsistema035) {
		this.cdsistema035 = cdsistema035;
	}

	public String getCdsistema036() {
		return cdsistema036;
	}

	public void setCdsistema036(String cdsistema036) {
		this.cdsistema036 = cdsistema036;
	}

	public String getCdsistema037() {
		return cdsistema037;
	}

	public void setCdsistema037(String cdsistema037) {
		this.cdsistema037 = cdsistema037;
	}

	public String getCdsistema038() {
		return cdsistema038;
	}

	public void setCdsistema038(String cdsistema038) {
		this.cdsistema038 = cdsistema038;
	}

	public String getCdsistema039() {
		return cdsistema039;
	}

	public void setCdsistema039(String cdsistema039) {
		this.cdsistema039 = cdsistema039;
	}

	public String getOrigemCancelamento() {
		return origemCancelamento;
	}

	public void setOrigemCancelamento(String origemCancelamento) {
		this.origemCancelamento = origemCancelamento;
	}

	public boolean isVigente() {
		return vigente;
	}

	public void setVigente(boolean vigente) {
		this.vigente = vigente;
	}

	public String getStrSistemas() {
		return strSistemas;
	}

	public void setStrSistemas(String strSistemas) {
		this.strSistemas = strSistemas;
	}

//	public static void main(String[] args) {
//			for(int i=10; i<40;i++){
//				System.out.println("@Adabas(adaName='AK0"+i+"')");		
//				System.out.println("private String cdsistema0"+i);				
//			}
//	}

}
