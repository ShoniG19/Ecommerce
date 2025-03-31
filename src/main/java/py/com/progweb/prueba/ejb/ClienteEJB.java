package py.com.progweb.prueba.ejb;

import py.com.progweb.prueba.model.Cliente;

import javax.ejb.Stateless;
import javax.persistence.PersistenceContext;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;


@Stateless
public class ClienteEJB {

    @PersistenceContext(unitName = "pruebaPU")
    private EntityManager em;

    // Crear un nuevo cliente
    public void agregarCliente(Cliente cliente) {
        em.persist(cliente);
    }

    // Obtener todos los clientes
    public List<Cliente> listarClientes() {
        TypedQuery<Cliente> query = em.createQuery("SELECT c FROM Cliente c", Cliente.class);
        return query.getResultList();
    }

    // Obtener un cliente por su id
    public Cliente obtenerCliente(Long idCliente) {
        return em.find(Cliente.class, idCliente);
    }

    // Actualizar un cliente
    public void actualizarCliente(Cliente cliente) {
        em.merge(cliente);
    }

    // Eliminar un cliente
    public void eliminarCliente(Long idCliente) {
        Cliente cliente = em.find(Cliente.class, idCliente);
        if (cliente != null) {
            em.remove(cliente);
        }
    }
}
