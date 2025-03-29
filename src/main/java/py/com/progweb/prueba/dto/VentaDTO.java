package py.com.progweb.prueba.dto;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

public class VentaDTO {
    private Long idVenta;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "America/Asuncion")
    private Date fecha;
    private Double total;
    private Long idCliente;
    private String nombreCliente;
    private String apellidoCliente;

    // Constructor
    public VentaDTO(Long idVenta, Date fecha, Double total, Long idCliente, String nombreCliente, String apellidoCliente) {
        this.idVenta = idVenta;
        this.total = total;
        this.fecha = fecha;
        this.idCliente = idCliente;
        this.nombreCliente = nombreCliente;
        this.apellidoCliente = apellidoCliente;
    }

    // Getters and Setters
    public Long getIdVenta() {
        return idVenta;
    }

    public void setIdVenta(Long idVenta) {
        this.idVenta = idVenta;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public Long getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(Long idCliente) {
        this.idCliente = idCliente;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getNombreCliente() {
        return nombreCliente;
    }

    public void setNombreCliente(String nombreCliente) {
        this.nombreCliente = nombreCliente;
    }

    public String getApellidoCliente() {
        return apellidoCliente;
    }

    public void setApellidoCliente(String apellidoCliente) {
        this.apellidoCliente = apellidoCliente;
    }
}
