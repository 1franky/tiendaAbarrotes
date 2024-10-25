/*
* DEFIICION DE LA BASE DE DATOS DEL PROYECTO
* Francisco Miztli López Salinas
* ************************
*
*/


DROP DATABASE IF EXISTS pvi;

CREATE DATABASE IF NOT EXISTS pvi;
USE pvi;

CREATE TABLE Images (
    `id` VARCHAR(40) PRIMARY KEY,
    `created_at` datetime NOT NULL,
    `updated_at` datetime NOT NULL,
    `pathImage` varchar(255) UNIQUE
);

CREATE TABLE Categories (
    `id` VARCHAR(40) PRIMARY KEY,
    `created_at` datetime NOT NULL,
    `updated_at` datetime NOT NULL,
    `name` varchar(255) NOT NULL UNIQUE
);

CREATE TABLE Proveedores (
    `id` VARCHAR(40) PRIMARY KEY,
    `created_at` datetime NOT NULL,
    `updated_at` datetime NOT NULL,
    `name` varchar(255) NOT NULL,
    `address` varchar(255) NOT NULL,
    `activo` bool NOT NULL,
    `category_id` VARCHAR(40),
    `image_id` VARCHAR(40),
    `phone_id` VARCHAR(40),
    `email_id` VARCHAR(40)
);

CREATE TABLE Phones (
    `id` VARCHAR(40) PRIMARY KEY,
    `created_at` datetime NOT NULL,
    `updated_at` datetime NOT NULL,
    `phone` varchar(15) NOT NULL UNIQUE,
    `description` varchar(255)
);

CREATE TABLE Emails (
    `id` VARCHAR(40) PRIMARY KEY,
    `created_at` datetime NOT NULL,
    `updated_at` datetime NOT NULL,
    `email` varchar(255) UNIQUE,
    `description` varchar(255)
);

CREATE TABLE Products (
    `id` VARCHAR(40) PRIMARY KEY,
    `created_at` datetime NOT NULL,
    `updated_at` datetime NOT NULL,
    `name` varchar(255) NOT NULL,
    `description` varchar(255) NOT NULL,
    `precioVenta` float,
    `precioProveedor` float,
    `existencia` integer,
    `activo` bool,
    `proveedor_id` VARCHAR(40),
    `category_id` VARCHAR(40),
    `image_id` VARCHAR(40),
    CHECK(precioVenta > 0),
    CHECK(precioVenta > precioProveedor),
    CHECK(precioProveedor > 0),
    CHECK(existencia > 0)
);

CREATE TABLE Clients (
    `id` VARCHAR(40) PRIMARY KEY,
    `created_at` datetime NOT NULL,
    `updated_at` datetime NOT NULL,
    `name` varchar(100) NOT NULL,
    `email` varchar(50) NOT NULL UNIQUE,
    `password` varchar(255) -- Password encriptado
);

CREATE TABLE Tickets (
    `id` VARCHAR(40) PRIMARY KEY,
    `created_at` datetime NOT NULL,
    `updated_at` datetime NOT NULL,
    `fecha` datetime NOT NULL,
    `total` float,
    `client_id` VARCHAR(40),
    CHECK(total > 0)
);

CREATE TABLE Products_Tickets (
    `id` VARCHAR(40) PRIMARY KEY,
    `precioVenta` float, -- Se guarda el precio de venta aqui para que al momento de actualizar el precio de un producto no se modifuque el precio del ticket
    `product_id` VARCHAR(40),
    `ticket_id` VARCHAR(40),
    CHECK(precioVenta > 0)
);

CREATE TABLE Users (
    `id` VARCHAR(40) PRIMARY KEY,
    `name` varchar(100)  NOT NULL,
    `tipoUser` integer NOT NULL,
    `password` varchar(255),  -- Password encriptado
    `image_id` VARCHAR(40)
);

-- Add foreign key constraints
ALTER TABLE Proveedores
    ADD CONSTRAINT fk_proveedor_category FOREIGN KEY (category_id) REFERENCES Categories(id),
    ADD CONSTRAINT fk_proveedor_image FOREIGN KEY (image_id) REFERENCES Images(id),
    ADD CONSTRAINT fk_proveedor_email FOREIGN KEY (email_id) REFERENCES Emails(id),
    ADD CONSTRAINT fk_proveedor_phone FOREIGN KEY (phone_id) REFERENCES Phones(id);

ALTER TABLE Products
    ADD CONSTRAINT fk_product_proveedor FOREIGN KEY (proveedor_id) REFERENCES Proveedores(id),
    ADD CONSTRAINT fk_product_category FOREIGN KEY (category_id) REFERENCES Categories(id),
    ADD CONSTRAINT fk_product_image FOREIGN KEY (image_id) REFERENCES Images(id);

ALTER TABLE Tickets
    ADD CONSTRAINT fk_ticket_client FOREIGN KEY (client_id) REFERENCES Clients(id);

ALTER TABLE Products_Tickets
    ADD CONSTRAINT fk_productstickets_product FOREIGN KEY (product_id) REFERENCES Products(id),
    ADD CONSTRAINT fk_productstickets_ticket FOREIGN KEY (ticket_id) REFERENCES Tickets(id);

ALTER TABLE Users
    ADD CONSTRAINT fk_user_image FOREIGN KEY (image_id) REFERENCES Images(id);

-- Create indices
CREATE INDEX idx_provider_category ON Proveedores(category_id);
CREATE INDEX idx_provider_image ON Proveedores(image_id);
CREATE INDEX idx_provider_email ON Proveedores(email_id);
CREATE INDEX idx_provider_phone ON Proveedores(phone_id);
CREATE INDEX idx_product_provider ON Products(proveedor_id);
CREATE INDEX idx_product_category ON Products(category_id);
CREATE INDEX idx_product_image ON Products(image_id);
CREATE INDEX idx_ticket_client ON Tickets(client_id);
CREATE INDEX idx_productstickets_product ON Products_Tickets(product_id);
CREATE INDEX idx_productstickets_ticket ON Products_Tickets(ticket_id);
CREATE INDEX idx_user_image ON Users(image_id);
CREATE INDEX idx_product_name ON Products(name);
CREATE INDEX idx_product_activo ON Products(activo);
CREATE INDEX idx_proveedor_name ON Proveedores(name);
CREATE INDEX idx_proveedor_activo ON Proveedores(activo);
CREATE INDEX idx_client_name ON Clients(name);
CREATE INDEX idx_ticket_fecha ON Tickets(fecha);
CREATE INDEX idx_category_name ON Categories(name);
CREATE INDEX idx_user_name ON Users(name);
CREATE INDEX idx_user_tipoUser ON Users(tipoUser);