-- Crear la base de datos banco
CREATE DATABASE banco;
-- Usar la base de datos banco
USE banco;
CREATE TABLE Usuarios (
    ID INT PRIMARY KEY AUTO_INCREMENT,
    Nombre VARCHAR(100),
    NombreUsuario VARCHAR(50) UNIQUE,
    Contraseña VARCHAR(255), -- Se recomienda almacenar la contraseña encriptada
    Saldo DECIMAL(10, 2)
);
CREATE TABLE Transacciones (
    ID INT PRIMARY KEY AUTO_INCREMENT,
    UsuarioID INT,
    TipoTransaccion VARCHAR(50),
    Monto DECIMAL(10, 2),
    FechaHora TIMESTAMP,
    FOREIGN KEY (UsuarioID) REFERENCES Usuarios(ID)
);
INSERT INTO Usuarios (Nombre, NombreUsuario, Contraseña, Saldo) VALUES
('Juan Pérez', 'juanperez', 'contraseña123', 1500.00),
('María García', 'mariagarcia', 'maria123', 2500.00),
('Pedro López', 'pedrolopez', 'pedro456', 1800.00);
INSERT INTO Transacciones (UsuarioID, TipoTransaccion, Monto, FechaHora) VALUES
(1, 'Depósito', 500.00, NOW()),
(1, 'Retiro', 200.00, NOW()),
(2, 'Depósito', 1000.00, NOW()),
(3, 'Depósito', 800.00, NOW());

