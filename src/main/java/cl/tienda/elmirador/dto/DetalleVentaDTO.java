package cl.tienda.elmirador.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DetalleVentaDTO {

    private Long id;
    private Long idProducto;
    private Integer cantProd;
    private Double precio;
    private Double subtotal;
}
