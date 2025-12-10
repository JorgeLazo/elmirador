package cl.tienda.elmirador.service;

import cl.tienda.elmirador.dto.DetalleVentaDTO;
import cl.tienda.elmirador.dto.VentaDTO;
import cl.tienda.elmirador.exception.NotFoundException;
import cl.tienda.elmirador.mapper.Mapper;
import cl.tienda.elmirador.model.DetalleVenta;
import cl.tienda.elmirador.model.Producto;
import cl.tienda.elmirador.model.Sucursal;
import cl.tienda.elmirador.model.Venta;
import cl.tienda.elmirador.repository.ProductoRepository;
import cl.tienda.elmirador.repository.SucursalRepository;
import cl.tienda.elmirador.repository.VentaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class VentaService implements IVentaService {

    @Autowired
    private VentaRepository ventaRepo;
    @Autowired
    private ProductoRepository productoRepo;
    @Autowired
    private SucursalRepository sucursalRepo;


    @Override
    public List<VentaDTO> traerVentas() {

        List<Venta> ventas = ventaRepo.findAll();
        List<VentaDTO> ventasDto = new ArrayList<>();

        VentaDTO ventaDto;
        for (Venta venta : ventas) {
            ventaDto = Mapper.toDTO(venta);
            ventasDto.add(ventaDto);
        }

        return ventasDto;
    }

    @Override
    public VentaDTO crearVenta(VentaDTO ventaDto) {

        if(ventaDto == null) throw new NotFoundException("Venta no existe");
        if (ventaDto.getIdSucursal() == null) throw new NotFoundException("Sucursal no existe");
        if (ventaDto.getDetalle() == null || ventaDto.getDetalle().isEmpty()) throw new NotFoundException("Detalle no existe");

        Sucursal sucursal = sucursalRepo.findById(ventaDto.getIdSucursal()).orElse(null);
        if (sucursal == null) throw new NotFoundException("Sucursal no existe");

        Venta venta = new Venta();

        venta.setFecha(ventaDto.getFecha());
        venta.setEstado(ventaDto.getEstado());
        venta.setSucursal(sucursal);
        venta.setTotal(ventaDto.getTotal());


        List<DetalleVenta>  detalleVentas = new ArrayList<>();

        Double totalCalculado = 0.0;

        for (DetalleVentaDTO detalleVentaDTO : ventaDto.getDetalle() ) {

            Producto producto = productoRepo.findById(detalleVentaDTO.getIdProducto()).orElse(null);
            if (producto == null) throw new NotFoundException("Producto no existe");

            DetalleVenta detalleVenta = new DetalleVenta();
            detalleVenta.setProducto(producto);
            detalleVenta.setPrecio(detalleVentaDTO.getPrecio());
            detalleVenta.setCantProd(detalleVentaDTO.getCantProd());
            detalleVenta.setVenta(venta);

            detalleVentas.add(detalleVenta);

            totalCalculado += (detalleVenta.getPrecio() *  detalleVenta.getCantProd());

        }

        venta.setDetalle(detalleVentas);

        venta = ventaRepo.save(venta);

        VentaDTO ventaDTO = Mapper.toDTO(venta);

        return ventaDTO;

    }

    @Override
    public VentaDTO actualizarVenta(Long id, VentaDTO ventaDto) {

        Venta  venta = ventaRepo.findById(id).orElse(null);

        if (venta == null) throw new NotFoundException("Venta no existe");

        if (ventaDto.getFecha() != null) {
            venta.setFecha(ventaDto.getFecha());
        }

        if (ventaDto.getEstado() != null) {
            venta.setEstado(ventaDto.getEstado());
        }

        if (ventaDto.getTotal() != null) {
            venta.setTotal(ventaDto.getTotal());
        }

        if (ventaDto.getIdSucursal() != null) {
            Sucursal sucursal = sucursalRepo.findById(ventaDto.getIdSucursal()).orElse(null);
            if (sucursal == null) throw new NotFoundException("Sucursal no existe");
            venta.setSucursal(sucursal);
        }

        ventaRepo.save(venta);

        VentaDTO ventaDTO = Mapper.toDTO(venta);

        return ventaDTO;


    }

    @Override
    public void eliminarVenta(Long id) {

        Venta venta = ventaRepo.findById(id).orElse(null);
        if (venta == null) throw new NotFoundException("Venta no existe");
        ventaRepo.delete(venta);
    }
}
