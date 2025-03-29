package py.com.progweb.prueba.rest;

import py.com.progweb.prueba.model.Producto;
import py.com.progweb.prueba.ejb.ProductoEJB;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;
import javax.ws.rs.core.Response;

@Path("/productos")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ProductoRest {

    @Inject
    private ProductoEJB productoEJB;

    // Obtener todos los productos o filtrados por nombre y categoria
    @GET
    public List<Producto> listarProductos(@QueryParam("nombre") String nombre, @QueryParam("idCategoria") Long idCategoria) {
        if (nombre != null || idCategoria != null){
            return productoEJB.listarProductosConFiltro(nombre, idCategoria);
        }
        return productoEJB.listarProductos();
    }

    // Obtener un producto por id
    @GET
    @Path("/{idProducto}")
    public Response obtenerProducto(@PathParam("idProducto") Long idProducto) {
        Producto producto = productoEJB.obtenerProducto(idProducto);
        if (producto == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(producto).build();
    }

    // Crear un nuevo producto
    @POST
    public Response agregarProducto(Producto producto) {
        productoEJB.agregarProducto(producto);
        return Response.status(Response.Status.CREATED).entity("Producto creado con éxito").build();
    }

    // Actualizar un producto
    @PUT
    @Path("/{idProducto}")
    public Response actualizarProducto(@PathParam("idProducto") Long idProducto, Producto producto) {
        Producto productoExistente = productoEJB.obtenerProducto(idProducto);
        if (productoExistente == null) {
            return Response.status(Response.Status.NOT_FOUND).entity("Producto no encontrado").build();
        }

        if ( producto.getNombre() != null ) {
            productoExistente.setNombre(producto.getNombre());
        }
        if ( producto.getCategoria() != null ) {
            productoExistente.setCategoria(producto.getCategoria());
        }
        if ( producto.getPrecioVenta() != null ) {
            productoExistente.setPrecioVenta(producto.getPrecioVenta());
        }
        if ( producto.getCantidadExistente() != null ) {
            productoExistente.setCantidadExistente(producto.getCantidadExistente());
        }

        productoEJB.actualizarProducto(productoExistente);
        return Response.ok().entity("Producto actualizado con éxito").build();
    }

    // Eliminar un producto
    @DELETE
    @Path("/{idProducto}")
    public Response eliminarProducto(@PathParam("idProducto") Long idProducto) {
        Producto producto = productoEJB.obtenerProducto(idProducto);
        if (producto == null) {
            return Response.status(Response.Status.NOT_FOUND).entity("Producto no encontrado").build();
        }
        productoEJB.eliminarProducto(idProducto);
        return Response.ok().entity("Producto eliminado con éxito").build();
    }
}
