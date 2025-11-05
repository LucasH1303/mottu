CREATE TABLE ordem_servico (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    tipo_problema VARCHAR(255),
    descricao_livre VARCHAR(1000),
    status VARCHAR(50) NOT NULL,
    data_abertura TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    data_conclusao TIMESTAMP NULL,
    moto_id BIGINT,
    CONSTRAINT fk_ordem_moto FOREIGN KEY (moto_id)
        REFERENCES moto(id)
        ON DELETE SET NULL
        ON UPDATE CASCADE
);