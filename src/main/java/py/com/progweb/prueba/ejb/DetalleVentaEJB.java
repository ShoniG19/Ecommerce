package py.com.progweb.prueba.ejb;

import py.com.progweb.prueba.dto.DetalleVentaDTO;

import javax.ejb.Stateless;
import javax.persistence.PersistenceContext;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

@Stateless
public class DetalleVentaEJB {

    @PersistenceContext(unitName = "pruebaPU")
    private EntityManager em;

    // Obtener detalles de venta por id
    public List<DetalleVentaDTO> obtenerDetallesVenta(Long idVenta) {
        String jpql = "SELECT NEW py.com.progweb.prueba.dto.DetalleVentaDTO(" +
                "d.producto.idProducto, d.producto.nombre, " +
                "d.producto.categoria.idCategoria, d.producto.categoria.nombre, " +
                "d.cantidad, d.precio) " +
                "FROM DetalleVenta d WHERE d.venta.idVenta = :idVenta";

        TypedQuery<DetalleVentaDTO> query = em.createQuery(jpql, DetalleVentaDTO.class);
        query.setParameter("idVenta", idVenta);
        return query.getResultList();
    }

}
