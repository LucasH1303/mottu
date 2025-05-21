package com.mottu.mottuapi.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class MotoDTO {

    private Long id;

    @NotBlank(message = "A placa não pode estar em branco")
    @Size(min = 7, max = 8, message = "A placa deve ter entre 7 e 8 caracteres")
    private String placa;

    @NotBlank(message = "O modelo não pode estar em branco")
    @Size(max = 50, message = "O modelo pode ter no máximo 50 caracteres")
    private String modelo;

    @NotBlank(message = "O fabricante não pode estar em branco")
    @Size(max = 50, message = "O fabricante pode ter no máximo 50 caracteres")
    private String fabricante;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getFabricante() {
        return fabricante;
    }

    public void setFabricante(String fabricante) {
        this.fabricante = fabricante;
    }
}
