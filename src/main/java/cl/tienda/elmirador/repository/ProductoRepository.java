package cl.tienda.elmirador.repository;

import cl.tienda.elmirador.model.Producto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductoRepository extends JpaRepository<Producto,Long> {
}
