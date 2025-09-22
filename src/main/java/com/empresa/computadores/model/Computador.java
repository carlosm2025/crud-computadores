package com.empresa.computadores.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "computadores")
public class Computador {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotBlank(message = "La marca es obligatoria")
    @Size(max = 50, message = "La marca no puede exceder 50 caracteres")
    @Column(nullable = false, length = 50)
    private String marca;
    
    @NotBlank(message = "El modelo es obligatorio")
    @Size(max = 100, message = "El modelo no puede exceder 100 caracteres")
    @Column(nullable = false, length = 100)
    private String modelo;
    
    @NotBlank(message = "El procesador es obligatorio")
    @Size(max = 100, message = "El procesador no puede exceder 100 caracteres")
    @Column(nullable = false, length = 100)
    private String procesador;
    
    @NotBlank(message = "La memoria RAM es obligatoria")
    @Size(max = 20, message = "La memoria RAM no puede exceder 20 caracteres")
    @Column(name = "memoria_ram", nullable = false, length = 20)
    private String memoriaRam;
    
    @NotBlank(message = "El almacenamiento es obligatorio")
    @Size(max = 50, message = "El almacenamiento no puede exceder 50 caracteres")
    @Column(nullable = false, length = 50)
    private String almacenamiento;
    
    @NotNull(message = "El precio es obligatorio")
    @DecimalMin(value = "0.0", inclusive = false, message = "El precio debe ser mayor a 0")
    @Column(nullable = false)
    private Double precio;
    
    @NotNull(message = "El stock es obligatorio")
    @Min(value = 0, message = "El stock no puede ser negativo")
    @Column(nullable = false)
    private Integer stock;
    
    @Column(name = "fecha_creacion", nullable = false)
    private LocalDateTime fechaCreacion;
    
    // Constructores
    public Computador() {
        this.fechaCreacion = LocalDateTime.now();
    }
    
    public Computador(String marca, String modelo, String procesador, String memoriaRam, 
                     String almacenamiento, Double precio, Integer stock) {
        this();
        this.marca = marca;
        this.modelo = modelo;
        this.procesador = procesador;
        this.memoriaRam = memoriaRam;
        this.almacenamiento = almacenamiento;
        this.precio = precio;
        this.stock = stock;
    }
    
    // Getters y Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getMarca() {
        return marca;
    }
    
    public void setMarca(String marca) {
        this.marca = marca;
    }
    
    public String getModelo() {
        return modelo;
    }
    
    public void setModelo(String modelo) {
        this.modelo = modelo;
    }
    
    public String getProcesador() {
        return procesador;
    }
    
    public void setProcesador(String procesador) {
        this.procesador = procesador;
    }
    
    public String getMemoriaRam() {
        return memoriaRam;
    }
    
    public void setMemoriaRam(String memoriaRam) {
        this.memoriaRam = memoriaRam;
    }
    
    public String getAlmacenamiento() {
        return almacenamiento;
    }
    
    public void setAlmacenamiento(String almacenamiento) {
        this.almacenamiento = almacenamiento;
    }
    
    public Double getPrecio() {
        return precio;
    }
    
    public void setPrecio(Double precio) {
        this.precio = precio;
    }
    
    public Integer getStock() {
        return stock;
    }
    
    public void setStock(Integer stock) {
        this.stock = stock;
    }
    
    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }
    
    public void setFechaCreacion(LocalDateTime fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }
    
    @Override
    public String toString() {
        return "Computador{" +
                "id=" + id +
                ", marca='" + marca + '\'' +
                ", modelo='" + modelo + '\'' +
                ", procesador='" + procesador + '\'' +
                ", memoriaRam='" + memoriaRam + '\'' +
                ", almacenamiento='" + almacenamiento + '\'' +
                ", precio=" + precio +
                ", stock=" + stock +
                ", fechaCreacion=" + fechaCreacion +
                '}';
    }
}
