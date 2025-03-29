package py.com.progweb.prueba.ejb;

import py.com.progweb.prueba.model.Producto;

import javax.ejb.Stateless;
import javax.persistence.PersistenceContext;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

@Stateless
public class ProductoEJB {

    @PersistenceContext(unitName = "pruebaPU")
    private EntityManager em;

    // Crear un nuevo producto
    public void agregarProducto(Producto producto) {
        em.persist(producto);
    }

    // Obtener todos los productos
    public List<Producto> listarProductos() {
        TypedQuery<Producto> query = em.createQuery("SELECT p FROM Producto p", Producto.class);
        return query.getResultList();
    }

    // Obtener un producto por id
    public Producto obtenerProducto(Long idProducto) {
        return em.find(Producto.class, idProducto);
    }

    // Obtener productos con filtros opcionales por nombre y categoria
    public List<Producto> listarProductosConFiltro(String nombre, Long idCategoria){
        String jpql = "SELECT p FROM Producto p WHERE 1=1";
        if (nombre != null && !nombre.isEmpty()) {
            jpql += " AND LOWER(p.nombre) LIKE LOWER(:nombre)";
        }
        if (idCategoria != null) {
            jpql += " AND p.categoria.idCategoria = :idCategoria";
        }
        TypedQuery<Producto> query = em.createQuery(jpql, Producto.class);
        if (nombre != null && !nombre.isEmpty()) {
            query.setParameter("nombre", "%" + nombre + "%");
        }
        if (idCategoria != null) {
            query.setParameter("idCategoria", idCategoria);
        }
        return query.getResultList();
    }

    // Actualizar un producto
    public void actualizarProducto(Producto producto) {
        em.merge(producto);
    }

    // Eliminar un producto
    public void eliminarProducto(Long idProducto) {
        Producto producto = em.find(Producto.class, idProducto);
        if (producto != null) {
            em.remove(producto);
        }
    }
}
