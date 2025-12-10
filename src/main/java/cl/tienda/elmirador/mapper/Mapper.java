package cl.tienda.elmirador.mapper;

import cl.tienda.elmirador.dto.DetalleVentaDTO;
import cl.tienda.elmirador.dto.ProductoDTO;
import cl.tienda.elmirador.dto.SucursalDTO;
import cl.tienda.elmirador.dto.VentaDTO;
import cl.tienda.elmirador.model.Producto;
import cl.tienda.elmirador.model.Sucursal;
import cl.tienda.elmirador.model.Venta;

public class Mapper {

    //Mapeo de Producto a ProductoDTO

    public static ProductoDTO toDTO(Producto p) {

        if (p == null) return null;

        return ProductoDTO.builder()
                .id(p.getId())
                .nombre(p.getNombre())
                .categoria(p.getCategoria())
                .precio(p.getPrecio())
                .cantidad(p.getCantidad())
                .build();

    }

    //Mapeo de Venta a VentaDTO

    public static VentaDTO toDTO(Venta v) {

        if (v == null) return null;

        var detalle = v.getDetalle().stream()
                .map(det -> DetalleVentaDTO.builder()
                        .id(det.getId())
                        .idProducto(det.getProducto().getId())
                        .cantProd(det.getCantProd())
                        .precio(det.getPrecio())
                        .subtotal(det.getPrecio() * det.getCantProd())
                        .build()
                ).toList();

        var total = detalle.stream()
                .map(DetalleVentaDTO::getSubtotal)
                .reduce(0.0,  Double::sum);

        return VentaDTO.builder()
                .id(v.getId())
                .fecha(v.getFecha())
                .idSucursal(v.getSucursal().getId())
                .estado(v.getEstado())
                .detalle(detalle)
                .total(total)
                .build();

    }

    //Mapeo a Sucursal a SucursalDTO

    public static SucursalDTO toDTO(Sucursal s) {

        if (s == null) return null;

        return SucursalDTO.builder()
                .id(s.getId())
                .nombre(s.getNombre())
                .direccion(s.getDireccion())
                .build();

    }
}
