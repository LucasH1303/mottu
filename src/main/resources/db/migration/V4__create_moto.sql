CREATE TABLE moto (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    placa VARCHAR(50) NOT NULL UNIQUE,
    modelo VARCHAR(255),
    fabricante VARCHAR(255),
    patio_id BIGINT,
    CONSTRAINT fk_moto_patio FOREIGN KEY (patio_id)
        REFERENCES patio(id)
        ON DELETE SET NULL
        ON UPDATE CASCADE
);
