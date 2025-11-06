CREATE TABLE ordem_servico (
    id BIGINT IDENTITY(1,1) PRIMARY KEY,
    tipo_problema VARCHAR(255),
    descricao_livre VARCHAR(1000),
    status VARCHAR(50) NOT NULL,
    data_abertura DATETIME NOT NULL DEFAULT GETDATE(),
    data_conclusao DATETIME NULL,
    moto_id BIGINT,
    CONSTRAINT fk_ordem_moto FOREIGN KEY (moto_id)
        REFERENCES moto(id)
        ON DELETE SET NULL
        ON UPDATE CASCADE
);