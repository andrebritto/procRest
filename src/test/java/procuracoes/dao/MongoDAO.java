package procuracoes.dao;

import java.util.ArrayList;
import java.util.Arrays;

import org.apache.commons.lang3.StringUtils;
import org.bson.Document;

import com.google.gson.Gson;
import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.client.AggregateIterable;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Accumulators;
import com.mongodb.client.model.Aggregates;
import com.mongodb.client.model.Filters;

import procuracoes.model.Log;
import procuracoes.model.Procuracao;
import procuracoes.model.Sistema;

public class MongoDAO {

	private static MongoDAO instance;
	private static MongoCredential credential;
	private static MongoClient mongo;
	static MongoDatabase database;

	public MongoDAO() {

	}

	public static MongoDAO getInstance() {
		if (instance == null) {
			return new MongoDAO();
		}
		return instance;
	}

	static MongoClient connect() {
		credential = MongoCredential.createCredential("usuario",
				"logDB", "senha".toCharArray());

		return new MongoClient("localhost", 27017);
	}

	// ***************************************** Sistema
	public void insertSistema(Sistema sistema) {
		mongo = connect();
		Gson json = new Gson();
		Document document = new Document();
		database = mongo.getDatabase("procuracaoDB");
		MongoCollection<Document> collection = database
				.getCollection("sistema");
		collection.insertOne(document
				.parse(json.toJson(sistema, Sistema.class)));
	}

	public FindIterable<Document> recuperaSistemas() {
		mongo = connect();
		database = mongo.getDatabase("procuracaoDB");
		MongoCollection<Document> collection = database
				.getCollection("sistema");
		FindIterable<Document> iterDoc = collection.find();
		return iterDoc;
	}

	// ******************************************** Log

	public void insertLog(Log log) {
		mongo = connect();
		Gson json = new Gson();
		Document document = new Document();
		database = mongo.getDatabase("logDB");
		MongoCollection<Document> collection = database
				.getCollection("procuracao");
		collection.insertOne(
				document.parse(json.toJson(log, Log.class)));
	}

	public FindIterable<Document> filtrarLogDataOcorrenciaNomeOperacao(
			String dtOcorrencia, String nmOperacao) {
		mongo = connect();
		database = mongo.getDatabase("logDB");
		MongoCollection<Document> collection = database
				.getCollection("procuracao");
		FindIterable<Document> iterDoc = collection.find(Filters.and(
				Filters.eq("dataOcorrencia", dtOcorrencia),
				Filters.eq("nomeOperacao", nmOperacao)));
		return iterDoc;
	}

	// ************************************** Procuracao

	public void insertProcuracao(Procuracao procuracao) {
		mongo = connect();
		Gson json = new Gson();
		Document document = new Document();
		database = mongo.getDatabase("procuracaoDB");
		MongoCollection<Document> collection = database
				.getCollection("procuracao");
		collection.insertOne(document.parse(
				json.toJson(procuracao, Procuracao.class)));
	}

	public FindIterable<Document> filtrarProcuracaoProcurador(
			String niProcurador) {
		mongo = connect();
		database = mongo.getDatabase("procuracaoDB");
		MongoCollection<Document> collection = database
				.getCollection("procuracao");
		FindIterable<Document> iterDoc = collection.find(Filters.eq(
				"niProcurador",
				StringUtils.rightPad(niProcurador, 14, " ")));
		return iterDoc;
	}
	
	public FindIterable<Document> filtrarProcuracaoProcuradores(
			ArrayList<String> niProcurador) {
		mongo = connect();
		database = mongo.getDatabase("procuracaoDB");
				
		MongoCollection<Document> collection = database.getCollection("procuracao");

		FindIterable<Document> iterDoc = collection.find(Filters.in("niProcurador",niProcurador));
		
		
		return iterDoc;
	}
	
	public AggregateIterable<Document> grupoByProcuracao(String agrupador) {
		mongo = connect();
		database = mongo.getDatabase("procuracaoDB");
				
		MongoCollection<Document> collection = database.getCollection("procuracao");
		
		AggregateIterable<Document> iterDoc = collection.aggregate(Arrays.asList(Aggregates.group("$"+agrupador, Accumulators.sum("count", 1))));
		
		return iterDoc;
	}

	public FindIterable<Document> filtrarProcuracaoTitularProcurador(
			String niProcurador, String niTitular) {
		mongo = connect();
		database = mongo.getDatabase("procuracaoDB");
		
		MongoCollection<Document> collection = database.getCollection("procuracao");
		FindIterable<Document> iterDoc = collection.find(Filters.and(Filters.eq("niProcurador", niProcurador),Filters.eq("niTitular", niTitular)));		
		
		return iterDoc;
	}

	public FindIterable<Document> filtrarProcuracaoTitular(
			String niTitular) {
		mongo = connect();
		database = mongo.getDatabase("procuracaoDB");
		
		MongoCollection<Document> collection = database
				.getCollection("procuracao");
		System.out.println("Buscando por: " + niTitular);
		FindIterable<Document> iterDoc = collection.find(Filters.eq(
				"niTitular",
				StringUtils.rightPad(niTitular, 14, " ")));
		return iterDoc;
	}

	public static void main(String[] args) {
		MongoDAO dao = MongoDAO.getInstance();
		Gson json = new Gson();
		FindIterable<Document> iterDoc = null;
		AggregateIterable<Document> iterAgrDoc = null;
//		String niProcurador = "68281455500";
		
		ArrayList<String> lista = new ArrayList<>();
//		lista.add(StringUtils.rightPad("68281455500", 14, " "));
//		lista.add(StringUtils.rightPad("00000004260", 14, " "));
//		lista.add(StringUtils.rightPad("00000004693", 14, " "));
//		lista.add(StringUtils.rightPad("00000005070", 14, " "));	
//		lista.add(StringUtils.rightPad("05272431758", 14, " "));
//		lista.add(StringUtils.rightPad("06105181868", 14, " "));
//		lista.add(StringUtils.rightPad("19007809334", 14, " "));
		lista.add(StringUtils.rightPad("96806397549", 14, " "));
		
//		
//		 iterDoc = dao.filtrarProcuracaoTitular("96806397549");
//		 Procuracao proc;
//		 for (Document reg : iterDoc) {
////			 System.out.println("reg: " + json.fromJson(reg.toJson(), Procuracao.class));
//			 proc = json.fromJson(reg.toJson(), Procuracao.class);
//			 System.out.println("ISN: " + proc.getIsn());
//			 System.out.println("Titular: " + proc.getNiTitular());
//			 System.out.println("Procurador: " + proc.getNiProcurador());
//			 System.out.println("Atendimento: [" + proc.getOrigem() +"]" + proc.getCdUaAtendimento());
//			 System.out.println("Conferencia: " + proc.getDataConferencia());
//			 System.out.println("Conferente: " + proc.getCpfConferencia());
//			 System.out.println("Aprovação: " + proc.getDataAprovacao());
//			 System.out.println("Aprovador: " + proc.getCpfAprovacao());
//			 System.out.println("Cancelamento: " + proc.getDataCancelamento());
//			 System.out.println("Cancelador: " + proc.getCpfCancelamento());
//		}
//		 
		System.out.println("Por UA");
		 iterAgrDoc  = dao.grupoByProcuracao("cdUaAtendimento");
		 for (Document reg : iterAgrDoc) {
			 System.out.println("reg: " + reg.getString("_id") + ": " + reg.getInteger("count"));
		}
//		 
//		 
//		 System.out.println("Por Origem");
//		 iterAgrDoc  = dao.grupoByProcuracao("origem");
//		 for (Document reg : iterAgrDoc) {
//			 System.out.println("reg: " + getOrigem(reg.getString("_id")) + ": " + reg.getInteger("count"));
//		}
//		 
//		 
//		 System.out.println("Por procurador");
//		 iterAgrDoc  = dao.grupoByProcuracao("niProcurador");
//		 for (Document reg : iterAgrDoc) {
//			 System.out.println("reg: " + reg.getString("_id") + ": " + reg.getInteger("count"));
//		}
//		 
		 
		 
//		iterDoc = dao.recuperaSistemas();
//		Sistema sistema = null;		
//		for (Document doc : iterDoc) {
//			sistema = json.fromJson(doc.toJson(), Sistema.class);
//			System.out.println("reg: " + sistema.getDescricao());
//		}

	}
	
	static String getOrigem(String cod) {
		return (cod.equals("0"))?"e-Cac":"Receita Federal";
	}
	
}
