package py.com.progweb.prueba.rest;

import py.com.progweb.prueba.dto.VentaDTO;
import py.com.progweb.prueba.ejb.VentaEJB;
import py.com.progweb.prueba.model.Venta;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.List;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Path("/ventas")
@Consumes("Application/json")
@Produces("Application/json")
public class VentaRest {

    @Inject
    private VentaEJB ventaEJB;

    // Registrar una nueva venta
    @POST
    public Response registrarVenta(Venta venta) {
        try{
            Venta nuevaVenta = ventaEJB.registrarVenta(venta);
            return Response.status(Response.Status.CREATED).entity(nuevaVenta).build();
        }catch (Exception e){
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }

    // Obtener todas las ventas
    @GET
    public Response listarVentas(@QueryParam("fecha") String fechaStr, @QueryParam("idCliente") Integer idCliente) {
        Date fecha = null;

        if (fechaStr == null && idCliente == null) {
            List<VentaDTO> ventas = ventaEJB.listarVentas();
            return Response.ok(ventas).build();
        }

        // Si se recibe el parámetro "fecha", convertirlo a tipo Date
        if (fechaStr != null) {
            try {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                fecha = sdf.parse(fechaStr);
            } catch (ParseException e) {
                return Response.status(Response.Status.BAD_REQUEST).entity("Formato de fecha inválido. Use yyyy-MM-dd.").build();
            }
        }

        List<VentaDTO> ventas = ventaEJB.listarVentasConFiltro(fecha, idCliente);
        return Response.ok(ventas).build();
    }

    // Obtener una venta por id
    @GET
    @Path("/{id}")
    public Response obtenerVenta(@PathParam("id") Long id) {
        VentaDTO venta = ventaEJB.obtenerVenta(id);
        if (venta == null) {
            return Response.status(Response.Status.NOT_FOUND).entity("Venta no encontrada").build();
        }
        return Response.ok(venta).build();
    }
}
