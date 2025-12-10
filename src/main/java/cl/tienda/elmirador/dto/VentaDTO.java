package cl.tienda.elmirador.dto;

import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class VentaDTO {

    //datos de la venta
    private Long id;
    private LocalDate fecha;
    private String estado;
    private Long idSucursal;

    //datos de la sucursal
    private List<DetalleVentaDTO> detalle;

    //lista de la venta
    private Double total;
}
