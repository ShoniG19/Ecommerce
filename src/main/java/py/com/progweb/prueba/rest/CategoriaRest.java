package py.com.progweb.prueba.rest;

import py.com.progweb.prueba.ejb.CategoriaEJB;
import py.com.progweb.prueba.model.Categoria;

import javax.inject.Inject;
import javax.ws.rs.*;
import java.util.List;
import javax.ws.rs.core.Response;

@Path("/categorias")
@Produces("application/json")
@Consumes("application/json")
public class CategoriaRest {

    @Inject
    private CategoriaEJB categoriaEJB;

    // Obtener todas las categorias
    @GET
    public List<Categoria> listarCategorias() {
        return categoriaEJB.listarCategorias();
    }

    // Obtener una categoria por id
    @GET
    @Path("/{idCategoria}")
    public Response obtenerCategoria(@PathParam("idCategoria") Long idCategoria) {
        Categoria categoria = categoriaEJB.obtenerCategoria(idCategoria);
        if (categoria == null) {
            return Response.status(Response.Status.NOT_FOUND).entity("Categoria no encontrada").build();
        }
        return Response.ok(categoria).build();
    }

    // Crear una nueva categoria
    @POST
    public Response agregarCategoria(Categoria categoria) {
        categoriaEJB.agregarCategoria(categoria);
        return Response.status(Response.Status.CREATED).entity("Categoria creada con éxito").build();
    }

    // Actualizar una categoria
    @PUT
    @Path("/{idCategoria}")
    public Response actualizarCategoria(@PathParam("idCategoria") Long idCategoria, Categoria categoria) {
        Categoria categoriaExistente = categoriaEJB.obtenerCategoria(idCategoria);
        if (categoriaExistente == null) {
            return Response.status(Response.Status.NOT_FOUND).entity("Categoria no encontrada").build();
        }

        if ( categoria.getNombre() != null ) {
            categoriaExistente.setNombre(categoria.getNombre());
        }

        categoriaEJB.actualizarCategoria(categoriaExistente);
        return Response.ok().entity("Categoria actualizada con éxito").build();
    }

    // Eliminar una categoria
    @DELETE
    @Path("/{idCategoria}")
    public Response eliminarCategoria(@PathParam("idCategoria") Long idCategoria) {
        Categoria categoria = categoriaEJB.obtenerCategoria(idCategoria);
        if (categoria == null) {
            return Response.status(Response.Status.NOT_FOUND).entity("Categoria no encontrada").build();
        }
        categoriaEJB.eliminarCategoria(idCategoria);
        return Response.ok().entity("Categoria eliminada con éxito").build();
    }
}
