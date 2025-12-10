package cl.tienda.elmirador.service;

import cl.tienda.elmirador.dto.SucursalDTO;

import java.util.List;

public interface ISucursalService {

    List<SucursalDTO> trearSucursales();
    SucursalDTO crearSucursal(SucursalDTO sucursalDto);
    SucursalDTO actualizarSucursal(Long id, SucursalDTO sucursalDto);
    void eliminarSucursal(Long id);
}
