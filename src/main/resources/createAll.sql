
CREATE TABLE IF NOT EXISTS patogeno (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    tipo VARCHAR(255) NOT NULL,
    cantidadDeEspecies INT DEFAULT 0
);

