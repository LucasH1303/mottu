-- Inserção de usuários iniciais
INSERT INTO usuario (username, password, role, nome, email, telefone) VALUES
('admin', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', 'ROLE_ADMIN', 'Administrador', 'admin@mottu.com', '11999999999'),
('user', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', 'ROLE_USER', 'Usuário', 'user@mottu.com', '11988888888');

