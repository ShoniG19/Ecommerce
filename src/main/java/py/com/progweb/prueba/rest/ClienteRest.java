package py.com.progweb.prueba.rest;

import py.com.progweb.prueba.ejb.ClienteEJB;
import py.com.progweb.prueba.model.Cliente;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;
import javax.ws.rs.core.Response;

@Path("/clientes")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ClienteRest {

    @Inject
    private ClienteEJB clienteEJB;

    // Obtener todos los clientes
    @GET
    public List<Cliente> listarClientes() {
        return clienteEJB.listarClientes();
    }

    // Obtener un cliente por id
    @GET
    @Path("/{idCliente}")
    public Response obtenerCliente(@PathParam("idCliente") Long idCliente) {
        Cliente cliente = clienteEJB.obtenerCliente(idCliente);
        if (cliente == null) {
            return Response.status(Response.Status.NOT_FOUND).entity("Cliente no encontrado").build();
        }
        return Response.ok(cliente).build();
    }

    // Crear un nuevo cliente
    @POST
    public Response agregarCliente(Cliente cliente) {
        clienteEJB.agregarCliente(cliente);
        return Response.status(Response.Status.CREATED).entity("Cliente creado con éxito").build();
    }

    // Actualizar un cliente
    @PUT
    @Path("/{idCliente}")
    public Response actualizarCliente(@PathParam("idCliente") Long idCliente, Cliente cliente) {
        Cliente clienteExistente = clienteEJB.obtenerCliente(idCliente);
        if (clienteExistente == null) {
            return Response.status(Response.Status.NOT_FOUND).entity("Cliente no encontrado").build();
        }

        if ( cliente.getNombre() != null ) {
            clienteExistente.setNombre(cliente.getNombre());
        }
        if ( cliente.getApellido() != null ) {
            clienteExistente.setApellido(cliente.getApellido());
        }
        if (cliente.getCedula() != null) {
            clienteExistente.setCedula(cliente.getCedula());
        }
        if (cliente.getEmail() != null) {
            clienteExistente.setEmail(cliente.getEmail());
        }

        clienteEJB.actualizarCliente(clienteExistente);
        return Response.ok().entity("Cliente actualizado con éxito").build();
    }

    // Eliminar un cliente
    @DELETE
    @Path("/{idCliente}")
    public Response eliminarCliente(@PathParam("idCliente") Long idCliente) {
        Cliente cliente = clienteEJB.obtenerCliente(idCliente);
        if (cliente == null) {
            return Response.status(Response.Status.NOT_FOUND).entity("Cliente no encontrado").build();
        }
        clienteEJB.eliminarCliente(idCliente);
        return Response.ok().entity("Cliente eliminado con éxito").build();
    }
}
