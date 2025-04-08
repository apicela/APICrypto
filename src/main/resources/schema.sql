-- Tabela Users
CREATE TABLE IF NOT EXISTS users
(
    id         UUID    DEFAULT RANDOM_UUID() PRIMARY KEY, -- O UUID será gerado automaticamente
    name       VARCHAR(255)          NOT NULL,
    last_name  VARCHAR(255)          NOT NULL,
    mail       VARCHAR(255)          NOT NULL UNIQUE,     -- E-email único
    password   VARCHAR(255)          NOT NULL,
    is_deleted BOOLEAN DEFAULT FALSE NOT NULL,             -- Campo de controle de exclusão lógico
    roles      TEXT                                          -- Roles armazenadas como texto (ex: "ROLE_ADMIN,ROLE_USER")
    );

-- Tabela Monitorings
CREATE TABLE IF NOT EXISTS monitorings
(
    id            BIGINT AUTO_INCREMENT PRIMARY KEY,               -- Chave primária com incremento automático
    user_id       UUID                  NOT NULL,                  -- Referência ao usuário
    coin_id       VARCHAR(255)          NOT NULL,
    price         DOUBLE PRECISION      NOT NULL,                  -- Preço de mercado da moeda
    greather_than BOOLEAN               NOT NULL,                  -- Indicador de valor
    is_deleted    BOOLEAN DEFAULT FALSE NOT NULL,                  -- Controle de exclusão lógico
    CONSTRAINT fk_user FOREIGN KEY (user_id) REFERENCES users (id) -- Chave estrangeira referenciando a tabela users
);
