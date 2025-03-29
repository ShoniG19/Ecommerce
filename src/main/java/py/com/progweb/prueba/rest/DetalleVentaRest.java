package py.com.progweb.prueba.rest;

import py.com.progweb.prueba.ejb.DetalleVentaEJB;
import py.com.progweb.prueba.dto.DetalleVentaDTO;
import py.com.progweb.prueba.model.Venta;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/ventas/{idVenta}/detalles")
@Produces("application/json")
@Consumes("application/json")
public class DetalleVentaRest {
    @Inject
    private DetalleVentaEJB detalleVentaEJB;

    @PersistenceContext(unitName = "pruebaPU")
    private EntityManager em;

    @GET
    public Response listarDetallesVenta(@PathParam("idVenta") Long idVenta) {
        try {
            Venta venta = em.find(Venta.class, idVenta);
            if (venta == null) {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("Venta no encontrada con ID: " + idVenta)
                        .build();
            }
            
            List<DetalleVentaDTO> detalles = detalleVentaEJB.obtenerDetallesVenta(idVenta);
            if (detalles.isEmpty()) {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("No se encontraron detalles para la venta con ID: " + idVenta)
                        .build();
            }
            return Response.ok(detalles).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Error al obtener detalles: " + e.getMessage())
                    .build();
        }
    }
}
