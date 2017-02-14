package consultorio.lgm.integracion.dao.basedatos;

import java.io.Serializable;
import java.util.List;


public interface IDAOBDGenerico <T , PK extends Serializable >  {
    public T guardar ( T entidad );
    public T guardarYVacia(T entidad);
    public void guardar ( List<T> entidades );
    public void eliminar ( T entidad );
    public void eliminar ( PK entidad );
    public void actualizar ( T entidad );
    public void flush ();
    public T recuperaPorID ( PK id);
    public List<T> recuperaTodo ();
    public boolean existe ( PK id);
}
