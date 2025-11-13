Comandos Query para MySQL:

1-
CREATE DATABASE IF NOT EXISTS biblioteca;

USE biblioteca;


CREATE TABLE IF NOT EXISTS libros (
    id INT AUTO_INCREMENT PRIMARY KEY,
    titulo VARCHAR(255) NOT NULL,
    autor VARCHAR(255) NOT NULL,
    ano_publicacion INT,
    isbn VARCHAR(20) UNIQUE,
    disponible BOOLEAN DEFAULT TRUE
);

INSERT INTO libros (titulo, autor, ano_publicacion, isbn, disponible) VALUES
('Cien Años de Soledad', 'Gabriel García Márquez', 1967, '978-0307350438', TRUE),
('El Señor de los Anillos', 'J.R.R. Tolkien', 1954, '978-8445071793', TRUE),
('1984', 'George Orwell', 1949, '978-0451524935', TRUE);

2-
CREATE DATABASE IF NOT EXISTS tienda;

USE tienda;

CREATE TABLE IF NOT EXISTS categorias (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS productos (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(255) NOT NULL,
    precio DECIMAL(10, 2) NOT NULL,
    stock INT DEFAULT 0,
    categoria_id INT,
    FOREIGN KEY (categoria_id) REFERENCES categorias(id)
        ON DELETE RESTRICT ON UPDATE CASCADE
);

INSERT INTO categorias (nombre) VALUES
('Lácteos'),
('Panadería'),
('Bebidas'),
('Frutas y Verduras');

INSERT INTO productos (nombre, precio, stock, categoria_id) VALUES
('Leche Entera 1L', 1.50, 100, (SELECT id FROM categorias WHERE nombre = 'Lácteos')),
('Queso Cremoso 500g', 4.20, 50, (SELECT id FROM categorias WHERE nombre = 'Lácteos')),
('Pan Francés', 0.80, 200, (SELECT id FROM categorias WHERE nombre = 'Panadería')),
('Gaseosa 2L', 2.10, 80, (SELECT id FROM categorias WHERE nombre = 'Bebidas'));
