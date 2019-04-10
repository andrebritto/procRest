package procuracoes.business;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;

import procuracoes.model.Data;

public class DataHoraUtil {
	
	public static Data grandePorteToData(String data) throws Exception{
		if (StringUtils.EMPTY.equals(data) || "00000000".equals(data))
			return null;
		Data dt = new Data();
		Date date = new SimpleDateFormat("yyyyMMdd").parse(data);
		String formattedDate = new SimpleDateFormat("dd/MM/yyyy").format(date);
		
		SimpleDateFormat sdf = new SimpleDateFormat();
		sdf.applyPattern("dd");
		dt.setDia(sdf.format(date));
//		System.out.println("Dia: " + sdf.format(date));
		sdf.applyPattern("MM");
		dt.setMes(sdf.format(date));
//		System.out.println("Mês: " + sdf.format(date));
		sdf.applyPattern("YYYY");
		dt.setAno(sdf.format(date));
//		System.out.println("Ano: " + sdf.format(date));
		return dt;
	}
	
	public static String dataGrandePorte(String data) throws Exception {
		String formattedDate = null;
		if (StringUtils.EMPTY.equals(data) || "00000000".equals(data))
			return StringUtils.EMPTY;

		Date date = new SimpleDateFormat("dd/MM/yyyy").parse(data);
		formattedDate = new SimpleDateFormat("yyyyMMdd").format(date);

		return formattedDate;
	}

	public static String dataDDMMYYYY(String data) throws Exception {
		String formattedDate = null;
		if (StringUtils.EMPTY.equals(data) || "00000000".equals(data))
			return StringUtils.EMPTY;

		Date date = new SimpleDateFormat("yyyyMMdd").parse(data);
		formattedDate = new SimpleDateFormat("dd/MM/yyyy").format(date);

		return formattedDate;
	}
	
	public static String dataDDMMYYYYFormatador(String data,String formatoEntrada,String formatoSaida) throws Exception {
		String formattedDate = null;
		if (StringUtils.EMPTY.equals(data) || "00000000".equals(data))
			return StringUtils.EMPTY;

		Date date = new SimpleDateFormat(formatoEntrada).parse(data);
		formattedDate = new SimpleDateFormat(formatoSaida).format(date);

		return formattedDate;
	}

	public static String horaHHMMSS(String hora) throws Exception {
		String formattedDate = null;
		if (StringUtils.EMPTY.equals(hora) || "000000".equals(hora))
			return StringUtils.EMPTY;

		Date date = new SimpleDateFormat("hhmmss").parse(hora);
		formattedDate = new SimpleDateFormat("hh:mm:ss").format(date);

		return formattedDate;
	}
}