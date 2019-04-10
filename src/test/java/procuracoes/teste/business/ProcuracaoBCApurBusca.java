package procuracoes.teste.business;

import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.MarkerManager;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.google.gson.Gson;

import procuracoes.business.ProcuracaoBC;
import procuracoes.dao.MongoDAO;
import procuracoes.dao.QueueDAO;
import procuracoes.model.Ambiente;
import procuracoes.model.Log;
import procuracoes.model.Procuracao;
import procuracoes.model.RequisicaoProcuracao;
import procuracoes.test.util.Factories;

/*
 * TODO: 1- ajustar os testes para não utilizar gravação em banco.
 *               2- criar testes de integração para gravar no banco
 * 
 * */
//@RunWith(MockitoJUnitRunner.class)
public class ProcuracaoBCApurBusca {

	Logger logger;
	private static final Marker PROCURACAO_MARKER = MarkerManager
			.getMarker("PROCURACAO");
	private static final Marker BUSCA_MARKER = MarkerManager
			.getMarker("BUSCA_MARKER")
			.setParents(PROCURACAO_MARKER);
	private static final Marker CANCELA_MARKER = MarkerManager
			.getMarker("CANCELA_MARKER")
			.setParents(PROCURACAO_MARKER);
	int envNumber = 0;

	@Before
	public void init() {
		System.setProperty("configurationFile", "log4j2-test.xml");
		logger = LogManager.getLogger(ProcuracaoBCApurBusca.class.getName());

		envNumber = Ambiente.DESENVOLVIMENTO.valorAmbiente;

	}

	@Test
	public void testProcuracaPorTitularSucesso() {
		ProcuracaoBC bc = ProcuracaoBC.getInstance();
		Gson json = new Gson();
		ArrayList<String> titular = new ArrayList<>();
		titular.add("90101022468");
//		titular.add("09333243000116");
//		titular.add("98717731968");
//		titular.add("04446960830");
//		titular.add("13471754000171");
//		titular.add("13648033000194");
//		titular.add("03344500000139");
//		titular.add("49057961000121");
//		titular.add("28543319000182");
//		titular.add("03150167000127");
//		titular.add("09433001000102");
//		titular.add("07841928000148");
//		titular.add("01858933000187");
//		titular.add("25118717000163");
		ArrayList<Procuracao> procs = null;
		try {
			bc.setAmbiente(envNumber);
			for (String string : titular) {

				procs = bc.getProcuracoesPorTitular(string);

				for (Procuracao procuracao : procs) {
					logger.info(json.toJson(procuracao));
//					logger.info("ISN: "+ procuracao.getIsn());
//					logger.info("UA Atendimento: " + procuracao.getCdUaAtendimento());
//					logger.info("Situação: " + procuracao.getSituacao());
//					logger.info("cod controle: "+ procuracao.getNumeroControle());
				}
			}
			Assert.assertNotNull(procs);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	
//	 @Test
	public void obterProcuracaoCodControleSucesso() {
		ProcuracaoBC bc = ProcuracaoBC.getInstance();
		Gson json = new Gson();
		ArrayList<String> codControle = new ArrayList<>();
		ArrayList<Procuracao> procs = new ArrayList<>();
		codControle.add("20EA9219B2DD25FE9D9B");
		codControle.add("A4812AD7978A66CFC202");
		codControle.add("A4812AD7978A66CFC202");
		codControle.add("A83019537095C699A7BD");
		codControle.add("A83019537095C699A7BD");
		codControle.add("4A121A1EE76F8B329275");
		codControle.add("4A121A1EE76F8B329275");
		codControle.add("C79115ED9155EF853381");
		codControle.add("C79115ED9155EF853381");
		codControle.add("3AD4BC4A48C6F9D8747C");
		codControle.add("3AD4BC4A48C6F9D8747C");
		codControle.add("3AD4BC4A48C6F9D8747C");
		codControle.add("69393026017D719DBA49");
		codControle.add("69393026017D719DBA49");
		codControle.add("69393026017D719DBA49");
		codControle.add("20A8DE2095547E8C51F0");
		codControle.add("20A8DE2095547E8C51F0");
		codControle.add("20A8DE2095547E8C51F0");
		codControle.add("20A8DE2095547E8C51F0");
		codControle.add("20A8DE2095547E8C51F0");
		codControle.add("20A8DE2095547E8C51F0");
		codControle.add("8C107A617C887F0575C7");
		codControle.add("8C107A617C887F0575C7");
		codControle.add("8C107A617C887F0575C7");
		codControle.add("8C107A617C887F0575C7");
		codControle.add("8C107A617C887F0575C7");
		codControle.add("009026E7C941393EE4C6");
		codControle.add("042FE1430EC9D5DF2931");
		codControle.add("0AB511C39FE9B61B167D");
		codControle.add("0B69D4DFD6D94BC49FE6");
		codControle.add("11A92409C20B2234C359");
		codControle.add("12FAFF28CD0E98C11B21");
		codControle.add("171771FBD3555203CF1E");
		codControle.add("1863E7614ADEC7153C92");
		codControle.add("1BED44DCA7A7835BF253");
		codControle.add("1D7BBB481ECFF4179C60");
		codControle.add("1D8131F796DB6D0A67FD");
		codControle.add("1ED00B88018231A2066C");
		codControle.add("30262D472FEE4D05F966");
		codControle.add("341C557021B8B53E3294");
		codControle.add("3930F4E16EC8E1BBF41B");
		codControle.add("41FA04BC21F52FDFC29E");
		codControle.add("425AED63DCD20310AEBF");
		codControle.add("4372A722E822DA96C6AE");
		codControle.add("48A72265194A36BE24FB");
		codControle.add("49431FA73BDB7D0D31C0");
		codControle.add("570AB427B54D05D9229D");
		codControle.add("587A55946B351822EE98");
		codControle.add("5A80777251C3CF9F835E");
		codControle.add("5BA277E6F9AAC7DB7361");
		codControle.add("5CB83DE30E8FD097F7D4");
		codControle.add("5F2361E5523EB252A940");
		codControle.add("609DD05E4CD3B3411925");
		codControle.add("619028A7D1DEAC99E281");
		codControle.add("624C2A9D43CADEF6CB09");
		codControle.add("64AA3727BDC0FFB8B93E");
		codControle.add("6DC5C9040CFE8AC11AE3");
		codControle.add("6F14CA712A4B0A75AA32");
		codControle.add("707244E2A956DCCDAF6F");
		codControle.add("71080BCD656C1F195B5C");
		codControle.add("79F02982D4DCFEC7BD8F");
		codControle.add("7AEA58C65E65F359ECFA");
		codControle.add("7B171ECC166EC92CCDC6");
		codControle.add("7C188F90F8775C714563");
		codControle.add("7FB3033EB3DCBCAC461D");
		codControle.add("810461536A4DEA20ABF2");
		codControle.add("81E0F7561835B11B6AED");
		codControle.add("8209A47C8B5BB1C3F78D");
		codControle.add("86FF102DCF1E2FB77DC4");
		codControle.add("8AC3C155ED7EF0A575E8");
		codControle.add("8ACA292981B5D1E269A5");
		codControle.add("8D28E151BA584747443D");
		codControle.add("8DA65C9F8D79267B1A6E");
		codControle.add("8F5402DBD26A25EAD725");
		codControle.add("95F87FB8C249E5E008B4");
		codControle.add("9A319C30234319C509EE");
		codControle.add("9AD03FB774FD5FF40E74");
		codControle.add("A111CDC39E67AEFF1349");
		codControle.add("AFCF8B61B5D7A9647D15");
		codControle.add("B039F34A63D4F8F1143C");
		codControle.add("B46EEE5D72BE3F329996");
		codControle.add("B504A2BBF92DC4F78749");
		codControle.add("B71D1872E73E9388F0C8");
		codControle.add("BC84CC6ACE8A2300748A");
		codControle.add("BCE2E8922BD31A5AC6AC");
		codControle.add("C3F5E46F05BB12AC6640");
		codControle.add("C4241E094620D98A7A2F");
		codControle.add("CDAF13EDD8137949358D");
		codControle.add("D5D179DF4B8EAF912763");
		codControle.add("D9CFF5083189AF337F30");
		codControle.add("DF2CC1B2B97A99A34715");
		codControle.add("EA2F25BA1B136B2838B5");
		codControle.add("ECCCF931CC0C598AC037");
		codControle.add("F253DFE12673E32E50DA");
		codControle.add("F551F22354CF50C58888");
		codControle.add("FA7ACED7879C22108B2C");
		codControle.add("FD7E2A2E7CB5CAE026D2");
		codControle.add("FE4487206C68FAC7C4AF");
		codControle.add("02C17F2F5F0607EC1BA1");
		codControle.add("02EF9F0E9F5CDFD54863");
		codControle.add("039BAD86A8A2F4CD0BE8");
		codControle.add("03E36DEA500C13B1C334");
		codControle.add("08E6A01CFF8A464B9E67");
		codControle.add("0920E9C0BC5196A226C5");
		codControle.add("0A6B2A36EE18D9E4BED1");
		codControle.add("0FAE1A1E5614D5690C4F");
		codControle.add("105C9B4D71FE85C5062E");
		codControle.add("111EE46DB171391901F9");
		codControle.add("1251C9EDB6E4B319D3BA");
		codControle.add("17216D37EA4D56980134");
		codControle.add("1A0AC2C6CD4ED0B51A2F");
		codControle.add("1A6C9CA2A4DCF82D561E");
		codControle.add("1C5F1B40A549AE3A897B");
		codControle.add("1CC5B40321B7F68FAE94");
		codControle.add("20F80A644349121C716B");
		codControle.add("21EE319E442A2AFC9FAB");
		codControle.add("22A81494D7E65AF6E36A");
		codControle.add("24CC0A6D5F64027A9AB8");
		codControle.add("25BFC16CA9655C089118");
		codControle.add("28627B62EAB8B704AF09");
		codControle.add("292F5CD9A7D1398DAF28");
		codControle.add("2A4217F561569060F4E9");
		codControle.add("2AA5805A9F69DCD03560");
		codControle.add("2FA653CA0DEE0D449FBB");
		codControle.add("3070A1A8CE6AE9A8D4B6");
		codControle.add("34C09DC64F3D2BD49FC2");
		codControle.add("36D0F0A0FD1BEDDC6A37");
		codControle.add("36E6DBDA6788A0A6A7EE");
		codControle.add("3AB977B249C02D3F886D");
		codControle.add("3AF48152C618251A8925");
		codControle.add("3FC0049F4B26891DC245");
		codControle.add("41D75131A10BE95B05D2");
		codControle.add("4711E7D9D0BFA088B30B");
		codControle.add("492B7113D22A02293E9C");
		codControle.add("4D1EC482F759006E1645");
		codControle.add("4F63CA198288F22283A0");
		codControle.add("4F9081A258F249E512E6");
		codControle.add("4FA273AA6A1A55B942B1");
		codControle.add("51A243D6434B7480BD0A");
		codControle.add("51AC8898778155ED1C37");
		codControle.add("53C4AD8B0DF053D4C1E0");
		codControle.add("548638AFE076093F751A");
		codControle.add("579162287721A065EA03");
		codControle.add("57E0EE430CE258D9A7EA");
		codControle.add("5E067ED392DE00343F47");
		codControle.add("60CB6300428FAF367DD5");
		codControle.add("60CF78A189CFF4908DA5");
		codControle.add("61AC271DE9BBB1B99A46");
		codControle.add("61FA1E73A87ECB168F29");
		codControle.add("64C6FFC9A13364F477F8");
		codControle.add("65DCDB21899EE49ED7F7");
		codControle.add("68FC889B0E020F2043C5");
		codControle.add("6AE0EAFCA50C608CC98B");
		codControle.add("6CAADC266AE3FFD1E136");
		codControle.add("6F13B85A03F5125ABCB8");
		codControle.add("6F4F1D85434DDB09B260");
		codControle.add("75ED7C814F1807432B8C");
		codControle.add("767B5E902DB75F344BC7");
		codControle.add("7777D2FFC5026018C223");
		codControle.add("77CB6BDDCF5BB9135773");
		codControle.add("781C7B2C1F61FD5E1951");
		codControle.add("791525E6BD3A811487F0");
		codControle.add("7A85F142D24FF104A4A9");
		codControle.add("7D6F78B65931FF8DDDC1");
		codControle.add("7DF9B3AE0A992E606295");
		codControle.add("83FCD74571B36EAF0DC4");
		codControle.add("855F7ABFCFB9F8090FAB");
		codControle.add("85670CB4A70DB79A8210");
		codControle.add("85BA19E7CAB9C6DB8B31");
		codControle.add("862376398E6CBFF13C4A");
		codControle.add("8A6121A6955E57636B24");
		codControle.add("8ACCE42A01C879C85434");
		codControle.add("8CBB6D5E641B87294FB9");
		codControle.add("8E2D23502B589E73EA18");
		codControle.add("8EE895112D0BF6BA89C4");
		codControle.add("8F9A0C9C8EFBAB9CAA25");
		codControle.add("9025CDC80C26368484EE");
		codControle.add("90C2CF45AE3BEE9BA1C2");
		codControle.add("918DC1CA099CA9AA62CB");
		codControle.add("924E25FA34F28B47CC27");
		codControle.add("98D6FAF32C30D48EB611");
		codControle.add("99D2F5A8D210651802FA");
		codControle.add("9AAFA78C47206BB5E1D2");
		codControle.add("9AE7671BB4ED8C68787B");
		codControle.add("9AEAF340B60AF095ECF3");
		codControle.add("9B1D95DC95D59DFAE28E");
		codControle.add("9BB7EF57F2F99A4C9534");
		codControle.add("9BEB600A673EC4B923D8");
		codControle.add("9C254C1E123918AFAAD6");
		codControle.add("9CD3ECBCAB30B483694C");
		codControle.add("A32F4A4E9A9B9F3303EF");
		codControle.add("A46CE6D4FD6ACAABBB28");
		codControle.add("A72BF9E5F71EBE9716F0");
		codControle.add("A7D2FFC176CD442D789E");
		codControle.add("A877196D904FD0CDB1FD");
		codControle.add("AB128E3D498D0F6BE357");
		codControle.add("AC4CEB00C8983FD06569");
		codControle.add("AE4DEBBF84B1BDD862DA");
		codControle.add("AFEA39F7E250E8B54769");
		codControle.add("B6A16D2971FE6D1441A1");
		codControle.add("B718E6790F894F7C0447");
		codControle.add("B76808D83A6540847D7B");
		codControle.add("B8DAD3663EBA0152BF46");
		codControle.add("BB47A207AB35B484E354");
		codControle.add("BE9D71E04A034B255C9A");
		codControle.add("BEB8F4A7C1E802B34BC6");
		codControle.add("BF1B7AFD0B364080FDC3");
		codControle.add("C0CDEF7B100475A04CBD");
		codControle.add("C18A94A9C825A7EED7DA");
		codControle.add("C1D9A907094D8DE36885");
		codControle.add("C3A2178F0CC1ED6E1350");
		codControle.add("CAE2C528AB7327199C65");
		codControle.add("CE023FD36EDAA4C74DEE");
		codControle.add("CFB41F201FADD04B83E4");
		codControle.add("D396F6B02E77674DAD3F");
		codControle.add("D398E5F319DB7F98AFEB");
		codControle.add("D479C47D6952FA6645A8");
		codControle.add("D5A7EA23E1C7305DF9BB");
		codControle.add("D5FC159ADB936C7D32BC");
		codControle.add("D85BDC60B66313952907");
		codControle.add("D89A017C843DBF678D03");
		codControle.add("D8D1AB97F0426522FE02");
		codControle.add("DB8B5700EDEE84C6D049");
		codControle.add("DEAD5ABF08B65E6A3A4C");
		codControle.add("E23AFF68F795C0EC50D7");
		codControle.add("E3443ED2807466D0CEF5");
		codControle.add("E3443ED2807466D0CEF5");
		codControle.add("E5671803A2EEA66D8BC4");
		codControle.add("E5E745878412C94D39C0");
		codControle.add("E5FD83D687D9EEDD14F6");
		codControle.add("E850CD6FC3EDAA0DB0DF");
		codControle.add("E975B2F2807855AD063B");
		codControle.add("EB4C63E0898F32D2AEBE");
		codControle.add("EEE127E21408825A2323");
		codControle.add("EF34A745CF418D8A442A");
		codControle.add("F15EAEED738220F4E44D");
		codControle.add("F349DBBC3B5B79E0DEFA");
		codControle.add("F55D089A8209C1286CA3");
		codControle.add("F570E2EB0B30A36D9C50");
		codControle.add("F97A939B52A9AD907F86");
		codControle.add("FACE2AFB8F80FAEBD388");
		codControle.add("FB9FD7D84EEB6B465C40");
		codControle.add("FC0B4BE184EF27A5CD04");
		codControle.add("FCB447E0AA3D3A3847E5");
		codControle.add("FCD0A1EF7E87DDABDE07");
		codControle.add("FD0D401AF6F424AE1524");
		codControle.add("FFF07A9CC941C2F3B33D");
		
		try {
			bc.setAmbiente(envNumber);

			for (String cdControl : codControle) {
				procs = bc.obterProcuracaoCodControle(cdControl);
				for (Procuracao procuracao : procs) {
					logger.info(procuracao.getIsn() + "," + procuracao.getNumeroControle() + "," + procuracao.getCpfConferencia() +","+ procuracao.getDataConferencia() + "," + procuracao.getDataInicioVigencia() + "," + procuracao.getDataFimVigencia() + "," + procuracao.getSituacao() );					
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
//	 @Test
	public void obterProcuracaoIsnSucesso() {
		ProcuracaoBC bc = ProcuracaoBC.getInstance();
		Gson json = new Gson();
		ArrayList<String> isnProcuracao = new ArrayList<>();
		isnProcuracao.add("17886623");		
		try {
			bc.setAmbiente(envNumber);
			for (String isn : isnProcuracao) {
				Procuracao p = bc.getProcuracoesPorIsn(isn);
				logger.info(BUSCA_MARKER,json.toJson(p));

			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


//	 @Test
	public void testProcuracaoPorOutorgadoSucesso() {
		
		/*
		 * 	16313363000117	Baixado	Incorporação	2054	931.524.778-72	2031200
                        21133657000105	Baixado	Incorporação	2062	936.351.848-53	2949299
                        15229396000110	Baixado	Incorporação	2054	017.630.678-15	2031200
                        17156514000133	Baixado	Incorporação	2046	192.959.956-00	6421200
                        11826476000100	Baixado	Incorporação	2054	472.032.001-59	2423702
		 * 
		 * */
		
		ProcuracaoBC bc = ProcuracaoBC.getInstance();
		
		try {
			
			bc.setAmbiente(envNumber);
			ArrayList<Procuracao> procs = bc.getProcuracoesPorProcurador("81275714587");
			Gson json = new Gson();

			for (Procuracao procuracao : procs) {
				logger.info(json.toJson(procuracao));
				//gravaNaFila(json.toJson(procuracao));
				//gravaNoBanco(procuracao);
			}
			//gravaNaFila("SHUTDOWN");
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	 private void gravaNoBanco(Procuracao reg) {
		 MongoDAO mongodao = MongoDAO.getInstance();
		 mongodao.insertProcuracao(reg);
	 }
	 
	 private void gravaNaFila(String msg) throws Exception {
		 QueueDAO q = new QueueDAO("serpro.queue.34624.procuracoes.procuracao");
		 q.produce(msg);
	 }

	/*
	 * Para verificação do erro, peço verificar se o CPF 44600844904 tem
	 * procuração eletrônica (perfil "eSocial - Grupo Acesso WEB") para o
	 * CNPJ 28702346000150.
	 * 
	 */
	 
	 /*
	  * 
	  * 
	  * 2)

jsonConsulta = {"outorgante":"02789478000178","tipoOutorgante":"2","outorgado":"07771999000111","tipoOutorgado":"2","sistemas":["00129","00130","00131","00195"]}

jsonRecebido = {"mensagem":"[07]Nao existe procuracao ativa para esse Titular e Procurador"}
R: Não existe procuração

3)

jsonConsulta = {"outorgante":"19711817000104","tipoOutorgante":"2","outorgado":"95492003968","tipoOutorgado":"1","sistemas":["00129","00130","00131","00195"]}

jsonRecebido = {"mensagem":"[07]Nao existe procuracao ativa para esse Titular e Procurador"}
R: Pode ter caido no intervalo onde o cache não estava atualizado


4)

jsonConsulta = {"outorgante":"32599075000127","tipoOutorgante":"2","outorgado":"85406198904","tipoOutorgado":"1","sistemas":["00129","00130","00131","00195"]}

jsonRecebido = {"mensagem":"[07]Nao existe procuracao ativa para esse Titular e Procurador"}
R: caso 8888

	  */
	 
//	 @Test
	public void testProcuracaoTitularProcuradorSucesso() {		 
		ProcuracaoBC bc = ProcuracaoBC.getInstance();
		ArrayList<String> outorgantes = new ArrayList<>();
//		03584544000136A11048310000100
//		03068852000109A11118373000188
//		19210663000169A02009911911
		// String procurador = " 09068656000110";
		outorgantes.add("19210663000169");
		String procurador = "02009911911";
		// outorgantes.add("11316164000149");
		// outorgantes.add("01250090000131");
		// outorgantes.add("10593824000176");
		ArrayList<Procuracao> procs = null;
		Gson json = new Gson();

		try {
			logger.info("Buscando procuração");
			bc.setAmbiente(envNumber);
			logger.info(Procuracao.cabecalhoCsv());
			for (String outorgante : outorgantes) {
//				logger.info("Titular: " + outorgante);
//				logger.info("Procurador: " + procurador);
				procs = bc.getProcuracaoTitularProcurador(outorgante, procurador);
				for (Procuracao procuracao : procs) {
					logger.info(json.toJson(procuracao));
//					logger.info(procuracao.csv());
				}
			}
			Assert.assertNotNull(procs);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
//	@Test
	public void testeBuscaProcuracaoPorPeriodo() {
		ProcuracaoBC bc = ProcuracaoBC.getInstance();
		
		
		ArrayList<Procuracao> procs = null;
		Gson json = new Gson();

		try {
			logger.info("Buscando procuração por periodo");
			bc.setAmbiente(envNumber);
			
//				logger.info("Titular: " + outorgante);
//				logger.info("Procurador: " + procurador);
				procs = bc.listaProcuracaoFromPeriodo("20180301", "20180330");
				logger.info(Procuracao.cabecalhoCsv());
				for (Procuracao procuracao : procs) {
					//logger.info(BUSCA_MARKER,json.toJson(procuracao.getCodigoControtrole()));
					logger.info(procuracao.csv());
				}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

//	@Test
	public void testeBuscaLogPorPeriodo() {
		ProcuracaoBC bc = ProcuracaoBC.getInstance();
		
		
		ArrayList<Log> logs = null;
		Gson json = new Gson();

		try {
			logger.info("Buscando procuração por periodo");
			bc.setAmbiente(envNumber);
			
//				logger.info("Titular: " + outorgante);
//				logger.info("Procurador: " + procurador);
				logs = bc.listaLogFromPeriodo("20180804", "20180810");
				for (Log log : logs) {
					logger.info("'" + log.getCodigoControtrole() + "','" + log.getNrCpfCnpjUsuario()+"'");
					
					//System.out.println("'" + log.getCodigoControtrole() + "','" + log.getNrCpfCnpjUsuario() + "'");
				}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	

}
