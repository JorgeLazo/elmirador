package cl.tienda.elmirador.service;

import cl.tienda.elmirador.dto.ProductoDTO;
import cl.tienda.elmirador.exception.NotFoundException;
import cl.tienda.elmirador.mapper.Mapper;
import cl.tienda.elmirador.model.Producto;
import cl.tienda.elmirador.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductoService implements IProductoService {

    @Autowired
    private ProductoRepository repo;

    @Override
    public List<ProductoDTO> traerProductos() {
        return repo.findAll().stream().map(Mapper::toDTO).toList();
    }

    @Override
    public ProductoDTO crearProducto(ProductoDTO productoDto) {

        Producto prod = Producto.builder()
                .nombre(productoDto.getNombre())
                .categoria(productoDto.getCategoria())
                .precio(productoDto.getPrecio())
                .cantidad(productoDto.getCantidad())
                .build();

        return Mapper.toDTO(repo.save(prod));

    }

    @Override
    public ProductoDTO actualizarProducto(Long id, ProductoDTO productoDto) {

        Producto prod = repo.findById(id).orElseThrow(
                () -> new NotFoundException("No existe el producto con el id: " + id)
        );

        prod.setNombre(productoDto.getNombre());
        prod.setCategoria(productoDto.getCategoria());
        prod.setPrecio(productoDto.getPrecio());
        prod.setCantidad(productoDto.getCantidad());

        return Mapper.toDTO(repo.save(prod));

    }

    @Override
    public void eliminarProducto(Long id) {

        if (!repo.existsById(id)) {
            throw new NotFoundException("No existe el producto con el id: " + id);
        }

        repo.deleteById(id);
    }
}
