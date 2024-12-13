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
    `cantidad` integer,
    `product_id` VARCHAR(40),
    `ticket_id` VARCHAR(40),
    CHECK(precioVenta > 0),
    CHECK ( cantidad > 0 )
);

create table sec_user_role_relation (
    `urr_id_user` bigint not null,
    `urr_id_user_role` bigint not null,
    primary key (urr_id_user, urr_id_user_role)
);

create table sec_role (
    `usr_id` bigint auto_increment not null,
    `usr_created_by` bigint not null,
    `usr_created_date` timestamp DEFAULT CURRENT_TIMESTAMP not null,
    `usr_id_status` integer not null,
    `usr_modified_by` bigint not null,
    `usr_modified_date` timestamp DEFAULT CURRENT_TIMESTAMP not null,
    `usr_role_name` varchar(40) not null,
    primary key (usr_id)
);

create table sec_user (
    `use_id` bigint auto_increment not null,
    `use_created_by` bigint not null,
    `use_created_date` timestamp DEFAULT CURRENT_TIMESTAMP not null,
    `use_email` varchar(45) not null unique,
    `use_first_name` varchar(20) not null,
    `use_id_status` integer not null,
    `use_last_name` varchar(20) not null,
    `use_modified_by` bigint not null,
    `use_modified_date` timestamp DEFAULT CURRENT_TIMESTAMP not null,
    `use_passwd` varchar(64) not null,
    `image_id` VARCHAR(40),
    primary key (use_id)
);

alter table if exists sec_user
    drop index if exists UK_l5d40xj2psp6fq44cfrb2ufvs;

alter table if exists sec_user
    add constraint UK_l5d40xj2psp6fq44cfrb2ufvs unique (use_email);

alter table if exists sec_user_role_relation
    add constraint FK67ltcd8s4a4apeu28bgexinyq
    foreign key (urr_id_user_role)
    references sec_role (usr_id);

alter table if exists sec_user_role_relation
    add constraint FKmq1jxti1gnb1osoax3jgi3001
    foreign key (urr_id_user)
    references sec_user (use_id);




-- -- Crear la tabla Roles
-- CREATE TABLE Roles (
--     `id` VARCHAR(40) PRIMARY KEY,
--     `created_at` DATETIME NOT NULL,
--     `updated_at` DATETIME NOT NULL,
--     `name` VARCHAR(50) NOT NULL UNIQUE
-- );
--
-- -- Crear la tabla Users
-- CREATE TABLE Users (
--     `id` VARCHAR(40) PRIMARY KEY,
--     `created_at` DATETIME NOT NULL,
--     `updated_at` DATETIME NOT NULL,
--     `name` VARCHAR(100) NOT NULL,
--     `email` VARCHAR(100) NOT NULL UNIQUE,
--     `password` VARCHAR(255),  -- Contraseña encriptada
--     `image_id` VARCHAR(40),
--     `isActive` BOOLEAN DEFAULT TRUE
-- );
--
-- -- Crear la tabla intermedia UserRoles para la relación muchos a muchos
-- CREATE TABLE UserRoles (
--     `id` VARCHAR(40) PRIMARY KEY,
--     `user_id` VARCHAR(40) NOT NULL,
--     `role_id` VARCHAR(40) NOT NULL,
--     `assigned_at` DATETIME NOT NULL DEFAULT NOW(),
--     FOREIGN KEY (`user_id`) REFERENCES Users(`id`) ON DELETE CASCADE,
--     FOREIGN KEY (`role_id`) REFERENCES Roles(`id`) ON DELETE CASCADE
-- );

-- CREATE TABLE Users (
--     `id` VARCHAR(40) PRIMARY KEY,
--     `created_at` datetime NOT NULL,
--     `updated_at` datetime NOT NULL,
--     `name` varchar(100)  NOT NULL,
--     `email` varchar(100)  NOT NULL UNIQUE,
--     `tipoUser` integer NOT NULL,
--     `password` varchar(255),  -- Password encriptado
--     `image_id` VARCHAR(40)
-- );

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
    ADD CONSTRAINT fk_products_tickets_product FOREIGN KEY (product_id) REFERENCES Products(id),
    ADD CONSTRAINT fk_products_tickets_ticket FOREIGN KEY (ticket_id) REFERENCES Tickets(id);

ALTER TABLE sec_user
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
CREATE INDEX idx_products_tickets_product ON Products_Tickets(product_id);
CREATE INDEX idx_products_tickets_ticket ON Products_Tickets(ticket_id);
CREATE INDEX idx_user_image ON sec_user(image_id);
CREATE INDEX idx_product_name ON Products(name);
CREATE INDEX idx_product_activo ON Products(activo);
CREATE INDEX idx_proveedor_name ON Proveedores(name);
CREATE INDEX idx_proveedor_activo ON Proveedores(activo);
CREATE INDEX idx_client_name ON Clients(name);
CREATE INDEX idx_ticket_fecha ON Tickets(fecha);
CREATE INDEX idx_category_name ON Categories(name);
CREATE INDEX idx_user_name ON sec_user(use_first_name);
CREATE INDEX idx_user_email ON sec_user(use_email);
-- CREATE INDEX idx_user_image ON sec_user(image_id);