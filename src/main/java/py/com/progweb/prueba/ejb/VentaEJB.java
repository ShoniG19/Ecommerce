package py.com.progweb.prueba.ejb;

import py.com.progweb.prueba.model.Cliente;
import py.com.progweb.prueba.model.Producto;
import py.com.progweb.prueba.model.Venta;
import py.com.progweb.prueba.model.DetalleVenta;
import py.com.progweb.prueba.dto.VentaDTO;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.PersistenceContext;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;
import javax.persistence.NoResultException;

@Stateless
public class VentaEJB {

    @PersistenceContext(unitName = "pruebaPU")
    private EntityManager em;

    @Inject
    private MailService mailService;

    // Registrar una nueva venta con detalles
    public Venta registrarVenta(Venta venta) throws Exception {
        // Validar cliente
        if ( venta.getCliente() == null ) {
            throw new Exception("El cliente no puede ser nulo");
        }

        Cliente cliente = em.find(Cliente.class, venta.getCliente().getIdCliente());
        if ( cliente == null ) {
            throw new Exception("El cliente con ID " + venta.getCliente().getIdCliente() + " no existe");
        }

        venta.setCliente(cliente);

        // Validar stock de los productos
        for ( DetalleVenta detalle : venta.getDetalles() ){
            Producto producto = em.find(Producto.class, detalle.getProducto().getIdProducto());

            if ( producto == null) {
                throw new Exception("Producto con ID " + detalle.getProducto().getIdProducto() + " no encontrado.");
            }

            if (producto.getCantidadExistente() < detalle.getCantidad()) {
                throw new Exception("Stock insuficiente para el producto: " + producto.getNombre());
            }

            detalle.setProducto(producto);
        }

        // Actualizar stock de los productos y calcular total de la venta
        Double totalVenta = 0.0;
        for ( DetalleVenta detalle : venta.getDetalles() ){
            Producto producto = em.find(Producto.class, detalle.getProducto().getIdProducto());

            // Restar stock del producto
            producto.setCantidadExistente(producto.getCantidadExistente() - detalle.getCantidad());
            em.merge(producto);

            // Asignar precio del producto al momento de la venta
            detalle.setPrecio(producto.getPrecioVenta());
            detalle.setVenta(venta);

            totalVenta += detalle.getCantidad() * detalle.getPrecio();
        }

        // Registrar la venta
        venta.setFecha(new Date());
        venta.setTotal(totalVenta);
        em.persist(venta);

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss", new Locale("es", "PY"));
        sdf.setTimeZone(TimeZone.getTimeZone("America/Asuncion"));
        String fechaCompraFormateada = sdf.format(venta.getFecha());

        // Enviar email al cliente
        mailService.enviarCorreo(
                venta.getCliente().getEmail(),
                venta.getCliente().getNombre(),
                fechaCompraFormateada,
                venta.getTotal(),
                venta.getDetalles()
        );

        return venta;
    }

    // Listar todas las ventas
    public List<VentaDTO> listarVentas() {
        String jpql = "SELECT NEW py.com.progweb.prueba.dto.VentaDTO(" +
                "v.idVenta, v.fecha, v.total, v.cliente.idCliente, v.cliente.nombre, v.cliente.apellido) " +
                "FROM Venta v";
        return em.createQuery(jpql, VentaDTO.class).getResultList();
    }

    // Obtener una venta por id
    public VentaDTO obtenerVenta(Long idVenta) {
        try{
            String jpql = "SELECT NEW py.com.progweb.prueba.dto.VentaDTO(" +
                    "v.idVenta, v.fecha, v.total, v.cliente.idCliente, v.cliente.nombre, v.cliente.apellido) " +
                    "FROM Venta v WHERE v.idVenta = :idVenta";
            TypedQuery<VentaDTO> query = em.createQuery(jpql, VentaDTO.class);
            query.setParameter("idVenta", idVenta);
            return query.getSingleResult();
        }catch (NoResultException e){
            return null;
        }
    }

    // Listar todas las ventas con filtros opcionales por cliente y fecha
    public List<VentaDTO> listarVentasConFiltro(Date fecha, Integer idCliente) {
        String jpql = "SELECT NEW py.com.progweb.prueba.dto.VentaDTO(" +
                "v.idVenta, v.fecha, v.total, v.cliente.idCliente, v.cliente.nombre, v.cliente.apellido) " +
                "FROM Venta v WHERE 1=1";

        if (fecha != null) {
            jpql += " AND DATE(v.fecha) = DATE(:fecha)";
        }
        if (idCliente != null) {
            jpql += " AND v.cliente.idCliente = :idCliente";
        }

        TypedQuery<VentaDTO> query = em.createQuery(jpql, VentaDTO.class);

        if (fecha != null) {
            query.setParameter("fecha", fecha);
        }
        if (idCliente != null) {
            query.setParameter("idCliente", idCliente);
        }

        return query.getResultList();
    }
}
