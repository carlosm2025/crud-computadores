package com.empresa.computadores.controller;

import com.empresa.computadores.model.Computador;
import com.empresa.computadores.service.ComputadorService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/computadores")
@CrossOrigin(origins = "*")
public class ComputadorController {
    
    @Autowired
    private ComputadorService computadorService;
    
    /**
     * GET /api/computadores - Obtener todos los computadores
     */
    @GetMapping
    public ResponseEntity<List<Computador>> obtenerTodos() {
        List<Computador> computadores = computadorService.obtenerTodos();
        return ResponseEntity.ok(computadores);
    }
    
    /**
     * GET /api/computadores/{id} - Obtener computador por ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerPorId(@PathVariable Long id) {
        Optional<Computador> computador = computadorService.obtenerPorId(id);
        
        if (computador.isPresent()) {
            return ResponseEntity.ok(computador.get());
        } else {
            Map<String, String> error = new HashMap<>();
            error.put("mensaje", "Computador no encontrado con ID: " + id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        }
    }
    
    /**
     * POST /api/computadores - Crear nuevo computador
     */
    @PostMapping
    public ResponseEntity<?> crear(@Valid @RequestBody Computador computador) {
        try {
            Computador computadorCreado = computadorService.crear(computador);
            return ResponseEntity.status(HttpStatus.CREATED).body(computadorCreado);
        } catch (RuntimeException e) {
            Map<String, String> error = new HashMap<>();
            error.put("mensaje", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }
    }
    
    /**
     * PUT /api/computadores/{id} - Actualizar computador existente
     */
    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(@PathVariable Long id, @Valid @RequestBody Computador computador) {
        try {
            Computador computadorActualizado = computadorService.actualizar(id, computador);
            return ResponseEntity.ok(computadorActualizado);
        } catch (RuntimeException e) {
            Map<String, String> error = new HashMap<>();
            error.put("mensaje", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }
    }
    
    /**
     * DELETE /api/computadores/{id} - Eliminar computador
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id) {
        try {
            computadorService.eliminar(id);
            Map<String, String> mensaje = new HashMap<>();
            mensaje.put("mensaje", "Computador eliminado exitosamente");
            return ResponseEntity.ok(mensaje);
        } catch (RuntimeException e) {
            Map<String, String> error = new HashMap<>();
            error.put("mensaje", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        }
    }
    
    /**
     * GET /api/computadores/buscar/marca/{marca} - Buscar por marca
     */
    @GetMapping("/buscar/marca/{marca}")
    public ResponseEntity<List<Computador>> buscarPorMarca(@PathVariable String marca) {
        List<Computador> computadores = computadorService.buscarPorMarca(marca);
        return ResponseEntity.ok(computadores);
    }
    
    /**
     * GET /api/computadores/buscar/modelo/{modelo} - Buscar por modelo
     */
    @GetMapping("/buscar/modelo/{modelo}")
    public ResponseEntity<List<Computador>> buscarPorModelo(@PathVariable String modelo) {
        List<Computador> computadores = computadorService.buscarPorModelo(modelo);
        return ResponseEntity.ok(computadores);
    }
    
    /**
     * GET /api/computadores/buscar/precio - Buscar por rango de precio
     */
    @GetMapping("/buscar/precio")
    public ResponseEntity<List<Computador>> buscarPorRangoPrecio(
            @RequestParam Double precioMin, 
            @RequestParam Double precioMax) {
        List<Computador> computadores = computadorService.buscarPorRangoPrecio(precioMin, precioMax);
        return ResponseEntity.ok(computadores);
    }
    
    /**
     * GET /api/computadores/buscar/stock - Buscar con stock disponible
     */
    @GetMapping("/buscar/stock")
    public ResponseEntity<List<Computador>> buscarConStockDisponible(
            @RequestParam(defaultValue = "0") Integer stockMinimo) {
        List<Computador> computadores = computadorService.buscarConStockDisponible(stockMinimo);
        return ResponseEntity.ok(computadores);
    }
    
    /**
     * GET /api/computadores/buscar - Búsqueda avanzada con múltiples criterios
     */
    @GetMapping("/buscar")
    public ResponseEntity<List<Computador>> buscarPorCriterios(
            @RequestParam(required = false) String marca,
            @RequestParam(required = false) String modelo,
            @RequestParam(required = false) String procesador,
            @RequestParam(required = false) Double precioMin,
            @RequestParam(required = false) Double precioMax) {
        
        List<Computador> computadores = computadorService.buscarPorCriterios(
                marca, modelo, procesador, precioMin, precioMax);
        return ResponseEntity.ok(computadores);
    }
    
    /**
     * PUT /api/computadores/{id}/stock - Actualizar stock de un computador
     */
    @PutMapping("/{id}/stock")
    public ResponseEntity<?> actualizarStock(@PathVariable Long id, @RequestParam Integer stock) {
        try {
            Computador computador = computadorService.actualizarStock(id, stock);
            return ResponseEntity.ok(computador);
        } catch (RuntimeException e) {
            Map<String, String> error = new HashMap<>();
            error.put("mensaje", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }
    }
    
    /**
     * GET /api/computadores/estadisticas - Obtener estadísticas básicas
     */
    @GetMapping("/estadisticas")
    public ResponseEntity<Map<String, Object>> obtenerEstadisticas() {
        Map<String, Object> estadisticas = new HashMap<>();
        estadisticas.put("totalComputadores", computadorService.contarTotal());
        estadisticas.put("computadoresConStockBajo", computadorService.obtenerConStockBajo().size());
        return ResponseEntity.ok(estadisticas);
    }
    
    /**
     * GET /api/computadores/stock-bajo - Obtener computadores con stock bajo
     */
    @GetMapping("/stock-bajo")
    public ResponseEntity<List<Computador>> obtenerConStockBajo() {
        List<Computador> computadores = computadorService.obtenerConStockBajo();
        return ResponseEntity.ok(computadores);
    }
}
