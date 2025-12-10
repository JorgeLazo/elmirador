package cl.tienda.elmirador.controller;

import cl.tienda.elmirador.dto.SucursalDTO;
import cl.tienda.elmirador.service.ISucursalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/sucursales")
public class SucursalController {

    @Autowired
    private ISucursalService sucService;

    @GetMapping
    public ResponseEntity<List<SucursalDTO>> traerSucursales(){

        return ResponseEntity.ok(sucService.trearSucursales());

    }

    @PostMapping
    public ResponseEntity<SucursalDTO> crearSucursal(@RequestBody SucursalDTO sucursalDTO){

        SucursalDTO suc = sucService.crearSucursal(sucursalDTO);
        return ResponseEntity.created(URI.create("/api/sucursales")).body(suc);

    }

    @PutMapping("/{id}")
    public ResponseEntity<SucursalDTO> actualizarSucursal(@PathVariable Long id,
                                                          @RequestBody SucursalDTO sucursalDTO){
        return  ResponseEntity.ok(sucService.actualizarSucursal(id, sucursalDTO));

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSucursal(@PathVariable Long id){

        sucService.eliminarSucursal(id);
        return   ResponseEntity.ok().build();

    }
}
