package mx.unam.dgtic;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

/**
 * @author FRANCISCO MIZTLI LOPEZ SALINAS
 * @user franciscolopez
 * @date 25/09/24
 * @project M8AP_Francisco_Lopez
 * Descripción: [...]
 */

@SpringBootTest
@Sql({"/schema.sql", "/data.sql"})
class PVIFranciscoLopezApplicationTests {

	@Test
	void contextLoads() {
		System.out.println("Francisco Miztli Lopez Salinas");
		System.out.println("Cargando schema y datos");
	}

}
