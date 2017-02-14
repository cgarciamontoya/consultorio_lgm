package consultorio.lgm.dao.basedatos;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.testng.annotations.BeforeClass;
import org.jasypt.encryption.pbe.config.EnvironmentPBEConfig;

@ContextConfiguration (locations = {
		"classpath:spring/UT/applicationContext-integracion.xml", 
		"classpath:spring/applicationContext-dao.xml"})
@TransactionConfiguration (defaultRollback=false)
public abstract class TestDAOBaseDatosBase extends AbstractTransactionalTestNGSpringContextTests {
	
	

	@Autowired
	@Qualifier("environmentPBEConfig")
	protected EnvironmentPBEConfig environmentPBEConfig ;
	
	@Autowired
	@Qualifier("fixedEnvironmentPBEConfig")
	protected EnvironmentPBEConfig fixedEnvironmentPBEConfig ;

	@BeforeClass
	protected void setUp(){
		//Establece el password de cifrado de la base de datos.
		environmentPBEConfig.setPassword("=Ñ3!w%Az[*7H2k/0");
		
		fixedEnvironmentPBEConfig.setPassword("=Ñ3!w%Az[*7H2k/0");		
		
		//Ejecuta el script que elimina todas las tablas de la base de tatos
		executeSqlScript( "classpath:datos/script-inicial-pruebas.sql" , false );
	}
	
	// ----------------------------------- Getters y Setters --------------------------------
	@Autowired
	public void setDataSource(@Qualifier("lgmDataSource") final DataSource dataSource) {
	    super.setDataSource (dataSource); 
	}
	
	
}
