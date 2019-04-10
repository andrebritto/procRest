package procuracoes.dao;

import java.util.ArrayList;
import java.util.HashMap;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import procuracoes.dao.GPDAO;
import procuracoes.dao.SistemaDAO;
import procuracoes.model.Ambiente;


public class GPDAOTest {

	int envNumber = 0;
	SistemaDAO dao;

	@Before
	public void init() {
		envNumber = Ambiente.DESENVOLVIMENTO.valorAmbiente;
	}

	@Test
	public void execQuery() {

		GPDAO gp = GPDAO.getInstance();
		gp.configuraAmbiente(Ambiente.DESENVOLVIMENTO.valorAmbiente);
		
		String qry = "SELECT ISN AA AB AC AD AE AG AI AS AH AP AF AT BA AV AW AX AY AL AK001 AK002 AK003 AK004 AK005 AK006 AK007 AK008 AK009 AK010 AK011 AK012 AK013 AK014 AK015 AK016 AK017 AK018 AK019 AK020 AK021 AK022 AK023 AK024 AK025 AK026 AK027 AK028 AK029 AK030 AK031 FROM 047.185 WHERE S4=(82586004515,68281455500,00000000,000000) TO S4=(82586004515,68281455500,99999999,999999)";
		try {
			ArrayList<HashMap> procs = gp.execQuery(qry);
			Assert.assertNotNull(procs);

		} catch (Exception e) {
			//Assert.assertEquals("Nenhum Sistema Encontrado.", e.getMessage());
			e.printStackTrace();
		}
	}

}
