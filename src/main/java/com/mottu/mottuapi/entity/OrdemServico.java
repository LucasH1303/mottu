package com.mottu.mottuapi.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "ordem_servico")
public class OrdemServico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String tipoProblema;

    @Column(length = 1000)
    private String descricaoLivre;

    private String status;

    private LocalDateTime dataAbertura;

    private LocalDateTime dataConclusao;

    
    @ManyToOne
    @JoinColumn(name = "moto_id")
    private com.mottu.mottuapi.entity.Moto moto;

    public OrdemServico() {
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTipoProblema() { return tipoProblema; }
    public void setTipoProblema(String tipoProblema) { this.tipoProblema = tipoProblema; }

    public String getDescricaoLivre() { return descricaoLivre; }
    public void setDescricaoLivre(String descricaoLivre) { this.descricaoLivre = descricaoLivre; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public LocalDateTime getDataAbertura() { return dataAbertura; }
    public void setDataAbertura(LocalDateTime dataAbertura) { this.dataAbertura = dataAbertura; }

    public LocalDateTime getDataConclusao() { return dataConclusao; }
    public void setDataConclusao(LocalDateTime dataConclusao) { this.dataConclusao = dataConclusao; }

    public com.mottu.mottuapi.entity.Moto getMoto() { return moto; }
    public void setMoto(com.mottu.mottuapi.entity.Moto moto) { this.moto = moto; }
}
