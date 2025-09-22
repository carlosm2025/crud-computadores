package com.empresa.computadores.repository;

import com.empresa.computadores.model.Computador;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ComputadorRepository extends JpaRepository<Computador, Long> {
    
    /**
     * Buscar computadores por marca
     */
    List<Computador> findByMarcaContainingIgnoreCase(String marca);
    
    /**
     * Buscar computadores por modelo
     */
    List<Computador> findByModeloContainingIgnoreCase(String modelo);
    
    /**
     * Buscar computadores por rango de precio
     */
    List<Computador> findByPrecioBetween(Double precioMin, Double precioMax);
    
    /**
     * Buscar computadores con stock disponible
     */
    List<Computador> findByStockGreaterThan(Integer stockMinimo);
    
    /**
     * Buscar computadores por marca y modelo
     */
    List<Computador> findByMarcaContainingIgnoreCaseAndModeloContainingIgnoreCase(
            String marca, String modelo);
    
    /**
     * Buscar computadores por procesador
     */
    List<Computador> findByProcesadorContainingIgnoreCase(String procesador);
    
    /**
     * Consulta personalizada para buscar por múltiples criterios
     */
    @Query("SELECT c FROM Computador c WHERE " +
           "(:marca IS NULL OR LOWER(c.marca) LIKE LOWER(CONCAT('%', :marca, '%'))) AND " +
           "(:modelo IS NULL OR LOWER(c.modelo) LIKE LOWER(CONCAT('%', :modelo, '%'))) AND " +
           "(:procesador IS NULL OR LOWER(c.procesador) LIKE LOWER(CONCAT('%', :procesador, '%'))) AND " +
           "(:precioMin IS NULL OR c.precio >= :precioMin) AND " +
           "(:precioMax IS NULL OR c.precio <= :precioMax)")
    List<Computador> buscarPorCriterios(@Param("marca") String marca,
                                       @Param("modelo") String modelo,
                                       @Param("procesador") String procesador,
                                       @Param("precioMin") Double precioMin,
                                       @Param("precioMax") Double precioMax);
    
    /**
     * Contar computadores por marca
     */
    Long countByMarca(String marca);
    
    /**
     * Verificar si existe un computador con la misma marca y modelo
     */
    boolean existsByMarcaAndModelo(String marca, String modelo);
    
    /**
     * Buscar computador por marca y modelo exactos
     */
    Optional<Computador> findByMarcaAndModelo(String marca, String modelo);
    
    /**
     * Buscar computadores con stock menor a un valor específico
     */
    List<Computador> findByStockLessThan(Integer stockMaximo);
}
