package py.com.progweb.prueba.dto;

public class DetalleVentaDTO {
    private Long idProducto;
    private String nombreProducto;
    private Long idCategoria;
    private String nombreCategoria;
    private Integer cantidad;
    private Double precioUnitario;
    private String precioTotalDetalle;

    // Constructor
    public DetalleVentaDTO(Long idProducto, String nombreProducto, Long idCategoria, String nombreCategoria, Integer cantidad, Double precioUnitario) {
        this.idProducto = idProducto;
        this.nombreProducto = nombreProducto;
        this.idCategoria = idCategoria;
        this.nombreCategoria = nombreCategoria;
        this.cantidad = cantidad;
        this.precioUnitario = precioUnitario;
        this.precioTotalDetalle = String.format("%,.2f", cantidad * precioUnitario);
    }

    // Getters and Setters
    public Long getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(Long idProducto) {
        this.idProducto = idProducto;
    }

    public String getNombreProducto() {
        return nombreProducto;
    }

    public void setNombreProducto(String nombreProducto) {
        this.nombreProducto = nombreProducto;
    }

    public Long getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(Long idCategoria) {
        this.idCategoria = idCategoria;
    }

    public String getNombreCategoria() {
        return nombreCategoria;
    }

    public void setNombreCategoria(String nombreCategoria) {
        this.nombreCategoria = nombreCategoria;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public Double getPrecioUnitario() {
        return precioUnitario;
    }

    public void setPrecioUnitario(Double precioUnitario) {
        this.precioUnitario = precioUnitario;
    }

    public String getPrecioTotalDetalle() {
        return precioTotalDetalle;
    }

    public void setPrecioTotalDetalle(Double precioTotalDetalle) {
        this.precioTotalDetalle = String.format("%,.2f", precioTotalDetalle);
    }
}
