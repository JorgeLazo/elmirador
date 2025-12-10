package cl.tienda.elmirador.service;

import cl.tienda.elmirador.dto.SucursalDTO;
import cl.tienda.elmirador.exception.NotFoundException;
import cl.tienda.elmirador.mapper.Mapper;
import cl.tienda.elmirador.model.Sucursal;
import cl.tienda.elmirador.repository.SucursalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SucursalService implements ISucursalService {

    @Autowired
    private SucursalRepository repo;

    @Override
    public List<SucursalDTO> trearSucursales() {

        return repo.findAll()
                .stream()
                .map(Mapper::toDTO)
                .toList();
    }

    @Override
    public SucursalDTO crearSucursal(SucursalDTO sucursalDto) {

        Sucursal suc = Sucursal.builder()
                .nombre(sucursalDto.getNombre())
                .direccion(sucursalDto.getDireccion())
                .build();

        return Mapper.toDTO(repo.save(suc));

    }

    @Override
    public SucursalDTO actualizarSucursal(Long id, SucursalDTO sucursalDto) {

        Sucursal suc = repo.findById(id).orElseThrow(
                () -> new NotFoundException("No existe la sucursal con el id: " + id)
        );

        suc.setNombre(sucursalDto.getNombre());
        suc.setDireccion(sucursalDto.getDireccion());
        suc = repo.save(suc);

        return Mapper.toDTO(repo.save(suc));

    }

    @Override
    public void eliminarSucursal(Long id) {

        if (!repo.existsById(id)) {
            throw new NotFoundException("No existe la sucursal con el id: " + id);
        }

        repo.deleteById(id);
    }
}
