package com.empresa.computadores.service;

import com.empresa.computadores.model.Computador;
import com.empresa.computadores.repository.ComputadorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ComputadorService {
    
    @Autowired
    private ComputadorRepository computadorRepository;
    
    /**
     * Obtener todos los computadores
     */
    @Transactional(readOnly = true)
    public List<Computador> obtenerTodos() {
        return computadorRepository.findAll();
    }
    
    /**
     * Obtener computador por ID
     */
    @Transactional(readOnly = true)
    public Optional<Computador> obtenerPorId(Long id) {
        return computadorRepository.findById(id);
    }
    
    /**
     * Crear un nuevo computador
     */
    public Computador crear(Computador computador) {
        // Validar que no exista otro computador con la misma marca y modelo
        if (computadorRepository.existsByMarcaAndModelo(computador.getMarca(), computador.getModelo())) {
            throw new RuntimeException("Ya existe un computador con la marca " + 
                                    computador.getMarca() + " y modelo " + computador.getModelo());
        }
        
        return computadorRepository.save(computador);
    }
    
    /**
     * Actualizar un computador existente
     */
    public Computador actualizar(Long id, Computador computadorActualizado) {
        Computador computadorExistente = computadorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Computador no encontrado con ID: " + id));
        
        // Verificar si se está cambiando marca y modelo
        if (!computadorExistente.getMarca().equals(computadorActualizado.getMarca()) ||
            !computadorExistente.getModelo().equals(computadorActualizado.getModelo())) {
            
            // Verificar que no exista otro computador con la nueva marca y modelo
            if (computadorRepository.existsByMarcaAndModelo(
                    computadorActualizado.getMarca(), computadorActualizado.getModelo())) {
                throw new RuntimeException("Ya existe un computador con la marca " + 
                                        computadorActualizado.getMarca() + " y modelo " + 
                                        computadorActualizado.getModelo());
            }
        }
        
        // Actualizar los campos
        computadorExistente.setMarca(computadorActualizado.getMarca());
        computadorExistente.setModelo(computadorActualizado.getModelo());
        computadorExistente.setProcesador(computadorActualizado.getProcesador());
        computadorExistente.setMemoriaRam(computadorActualizado.getMemoriaRam());
        computadorExistente.setAlmacenamiento(computadorActualizado.getAlmacenamiento());
        computadorExistente.setPrecio(computadorActualizado.getPrecio());
        computadorExistente.setStock(computadorActualizado.getStock());
        
        return computadorRepository.save(computadorExistente);
    }
    
    /**
     * Eliminar un computador
     */
    public void eliminar(Long id) {
        if (!computadorRepository.existsById(id)) {
            throw new RuntimeException("Computador no encontrado con ID: " + id);
        }
        computadorRepository.deleteById(id);
    }
    
    /**
     * Buscar computadores por marca
     */
    @Transactional(readOnly = true)
    public List<Computador> buscarPorMarca(String marca) {
        return computadorRepository.findByMarcaContainingIgnoreCase(marca);
    }
    
    /**
     * Buscar computadores por modelo
     */
    @Transactional(readOnly = true)
    public List<Computador> buscarPorModelo(String modelo) {
        return computadorRepository.findByModeloContainingIgnoreCase(modelo);
    }
    
    /**
     * Buscar computadores por rango de precio
     */
    @Transactional(readOnly = true)
    public List<Computador> buscarPorRangoPrecio(Double precioMin, Double precioMax) {
        return computadorRepository.findByPrecioBetween(precioMin, precioMax);
    }
    
    /**
     * Buscar computadores con stock disponible
     */
    @Transactional(readOnly = true)
    public List<Computador> buscarConStockDisponible(Integer stockMinimo) {
        return computadorRepository.findByStockGreaterThan(stockMinimo);
    }
    
    /**
     * Buscar computadores por múltiples criterios
     */
    @Transactional(readOnly = true)
    public List<Computador> buscarPorCriterios(String marca, String modelo, String procesador, 
                                              Double precioMin, Double precioMax) {
        return computadorRepository.buscarPorCriterios(marca, modelo, procesador, precioMin, precioMax);
    }
    
    /**
     * Actualizar stock de un computador
     */
    public Computador actualizarStock(Long id, Integer nuevoStock) {
        Computador computador = computadorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Computador no encontrado con ID: " + id));
        
        if (nuevoStock < 0) {
            throw new RuntimeException("El stock no puede ser negativo");
        }
        
        computador.setStock(nuevoStock);
        return computadorRepository.save(computador);
    }
    
    /**
     * Obtener estadísticas básicas
     */
    @Transactional(readOnly = true)
    public long contarTotal() {
        return computadorRepository.count();
    }
    
    /**
     * Obtener computadores con stock bajo (menos de 5 unidades)
     */
    @Transactional(readOnly = true)
    public List<Computador> obtenerConStockBajo() {
        return computadorRepository.findByStockLessThan(5);
    }
}
