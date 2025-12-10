package cl.tienda.elmirador.controller;

import cl.tienda.elmirador.dto.ProductoDTO;
import cl.tienda.elmirador.service.IProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/productos")
public class ProductoController {

    @Autowired
    private IProductoService productoService;

    @GetMapping
    public ResponseEntity<List<ProductoDTO>> traerProductos(){

        return ResponseEntity.ok(productoService.traerProductos());

    }

    @PostMapping
    public ResponseEntity<ProductoDTO> crearProducto(@RequestBody ProductoDTO productoDto){

        ProductoDTO creado = productoService.crearProducto(productoDto);

        return ResponseEntity.created(URI.create("/api/productos/" +creado.getId())).body(creado);

    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductoDTO> actualizarProducto(@PathVariable Long id, @RequestBody ProductoDTO productoDto){

    return   ResponseEntity.ok(productoService.actualizarProducto(id, productoDto));

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void>  eliminarProducto(@PathVariable Long id){
        productoService.eliminarProducto(id);

        return ResponseEntity.noContent().build();
    }

}
