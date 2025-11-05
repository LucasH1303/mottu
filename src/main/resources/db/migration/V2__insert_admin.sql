-- Inserção de usuários iniciais
INSERT INTO usuario (username, password, role, nome, email, telefone) VALUES
('admin', '123456', 'ROLE_ADMIN', 'Administrador', 'admin@mottu.com', '11999999999'),
('user', '123456', 'ROLE_USER', 'Usuário', 'user@mottu.com', '11988888888');