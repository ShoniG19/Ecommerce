package py.com.progweb.prueba.ejb;

import py.com.progweb.prueba.model.Categoria;

import javax.ejb.Stateless;
import javax.persistence.PersistenceContext;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

@Stateless
public class CategoriaEJB {

    @PersistenceContext(unitName = "pruebaPU")
    private EntityManager em;

    // Crear una nueva categoria
    public void agregarCategoria(Categoria categoria) {
        em.persist(categoria);
    }

    // Obtener todas las categorias
    public List<Categoria> listarCategorias() {
        TypedQuery<Categoria> query = em.createQuery("SELECT c FROM Categoria c", Categoria.class);
        return query.getResultList();
    }

    // Obtener una categoria por id
    public Categoria obtenerCategoria(Long idCategoria) {
        return em.find(Categoria.class, idCategoria);
    }

    // Actualizar una categoria
    public void actualizarCategoria(Categoria categoria) {
        em.merge(categoria);
    }

    // Eliminar una categoria
    public void eliminarCategoria(Long idCategoria) {
        Categoria categoria = em.find(Categoria.class, idCategoria);
        if (categoria != null) {
            em.remove(categoria);
        }
    }
}
