package cl.tienda.elmirador.repository;

import cl.tienda.elmirador.model.Venta;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VentaRepository extends JpaRepository<Venta,Long> {
}
