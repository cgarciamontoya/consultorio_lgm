package consultorio.lgm.integracion.dao.basedatos;

import consultorio.lgm.excepciones.LGMFabricaExcepciones;
import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public class DAOBDGenerico <T , PK extends Serializable >  implements IDAOBDGenerico<T, PK> {
    @PersistenceContext
    protected EntityManager entityManager ;
    
    protected LGMFabricaExcepciones fabricaExcepciones = LGMFabricaExcepciones.recuperaInstancia();
    
    protected Class<T> domainClass;

    public DAOBDGenerico(Class<T> domainClass) {
        this.domainClass = domainClass ;
    }
    
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public T guardar(T entidad) {
        return entityManager.merge ( entidad );
    }
    
    @Override
    public T guardarYVacia(T entidad) {
    	entidad = entityManager.merge ( entidad );
    	entityManager.flush();
        return entidad;
    }
    
    
    @Override
    public void guardar (List<T> entidades) {
        long i = 0 ;
        for ( T valor : entidades ){
            guardar ( valor );
            //Batch para cada 20 entidades, el recomendado está entre 5 y 30 y es en particular para Hibernate.
            //http://docs.jboss.org/hibernate/core/3.3/reference/en/html/batch.html
            if ( i == 20 ){
                i = 0;
                entityManager.flush();
                entityManager.clear();
            }
            i++;
        }
    }

    @Override
    public void eliminar(PK id) {
    	T entidad = entityManager.find( domainClass , id );
    	if ( entidad != null ) {
    		entityManager.remove( entidad );
    	}
    }
    
    @Override
    public void eliminar(T entidad) {
    	
        entityManager.remove( entidad );
    }

    @Override
    public void actualizar(T entidad) {
        entityManager.merge( entidad );
    }
    
    @Override
    public void flush () {
        entityManager.flush(); 
    }

    @Override
    public T recuperaPorID(PK id) {
        return entityManager.find( domainClass , id );
    }
    public List<T> recuperaTodo (){
    	return entityManager.createQuery("Select e From " + domainClass.getName() + " e" , domainClass ).getResultList() ;    
    }
    
    @Override
    public boolean existe ( PK id){
    	//TODO Agregar código
    	return false;
    }

}
