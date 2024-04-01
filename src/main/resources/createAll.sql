
CREATE TABLE patogeno (
    id LONG PRIMARY KEY AUTO_INCREMENT,
    tipo VARCHAR(255) NOT NULL,
    cantidadDeEspecies INT DEFAULT 0
);

CREATE TABLE IF NOT EXISTS especie (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    patogeno_id BIGINT NOT NULL,
    nombre VARCHAR(255) NOT NULL,
    paisDeOrigen VARCHAR(255) NOT NULL,
    FOREIGN KEY (patogeno_id) REFERENCES Patogeno(id)
);
