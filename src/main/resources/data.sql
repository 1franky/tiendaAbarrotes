INSERT INTO Images (id, created_at, updated_at, pathImage) VALUES
    ('a1b2c3d4-e5f6-7890-abcd-ef1234567890', '2024-01-01 10:00:00', '2024-01-01 10:00:00', '/images/test1.jpg'),
    ('b2c3d4e5-f6a7-8901-bcde-f23456789012', '2024-01-02 11:00:00', '2024-01-02 11:00:00', '/images/test2.jpg'),
    ('c3d4e5f6-a7b8-9012-cdef-345678901234', '2024-01-03 12:00:00', '2024-01-03 12:00:00', '/images/test3.jpg'),
    ('d4e5f6a7-b8c9-0123-def0-456789012345', '2024-01-04 13:00:00', '2024-01-04 13:00:00', '/images/test4.jpg'),
    ('e5f6a7b8-c9d0-1234-ef01-567890123456', '2024-01-05 14:00:00', '2024-01-05 14:00:00', '/images/test5.jpg'),
    ('f6a7b8c9-d0e1-2345-f012-678901234567', '2024-01-06 15:00:00', '2024-01-06 15:00:00', '/images/test6.jpg'),
    ('g7b8c9d0-e1f2-3456-0123-789012345678', '2024-01-07 16:00:00', '2024-01-07 16:00:00', '/images/test7.jpg'),
    ('h8c9d0e1-f2a3-4567-1234-890123456789', '2024-01-08 17:00:00', '2024-01-08 17:00:00', '/images/test8.jpg'),
    ('i9d0e1f2-a3b4-5678-2345-901234567890', '2024-01-09 18:00:00', '2024-01-09 18:00:00', '/images/test9.jpg'),
    ('j0e1f2a3-b4c5-6789-3456-012345678901', '2024-01-10 19:00:00', '2024-01-10 19:00:00', '/images/test10.jpg');

INSERT INTO Categories (id, created_at, updated_at, name) VALUES
    ('c1d2e3f4-g5h6-7890-ijkl-123456789012', '2024-01-01 09:00:00', '2024-01-01 09:00:00', 'Lácteos'),
    ('d2e3f4g5-h6i7-8901-jklm-234567890123', '2024-01-02 10:00:00', '2024-01-02 10:00:00', 'Harinas y Cereales'),
    ('e3f4g5h6-i7j8-9012-klmn-345678901234', '2024-01-03 11:00:00', '2024-01-03 11:00:00', 'Bebidas'),
    ('f4g5h6i7-j8k9-0123-lmno-456789012345', '2024-01-04 12:00:00', '2024-01-04 12:00:00', 'Panadería'),
    ('g5h6i7j8-k9l0-1234-mnop-567890123456', '2024-01-05 13:00:00', '2024-01-05 13:00:00', 'Aceites y Vinagres'),
    ('h6i7j8k9-l0m1-2345-nopq-678901234567', '2024-01-06 14:00:00', '2024-01-06 14:00:00', 'Snacks'),
    ('i7j8k9l0-m1n2-3456-opqr-789012345678', '2024-01-07 15:00:00', '2024-01-07 15:00:00', 'Conservas'),
    ('j8k9l0m1-n2o3-4567-pqrs-890123456789', '2024-01-08 16:00:00', '2024-01-08 16:00:00', 'Productos de Limpieza'),
    ('k9l0m1n2-o3p4-5678-qrst-901234567890', '2024-01-09 17:00:00', '2024-01-09 17:00:00', 'Frutas y Verduras'),
    ('l0m1n2o3-p4q5-6789-rstu-012345678901', '2024-01-10 18:00:00', '2024-01-10 18:00:00', 'Carnes y Embutidos');

INSERT INTO Phones (id, created_at, updated_at, phone, description) VALUES
    ('p1q2r3s4-t5u6-7890-vwxy-123456789012', '2024-01-01 08:00:00', '2024-01-01 08:00:00', '555-1234', 'Oficina'),
    ('q2r3s4t5-u6v7-8901-wxyz-234567890123', '2024-01-02 09:00:00', '2024-01-02 09:00:00', '555-2345', 'Repartidor'),
    ('r3s4t5u6-v7w8-9012-xyza-345678901234', '2024-01-03 10:00:00', '2024-01-03 10:00:00', '555-3456', 'Casa'),
    ('s4t5u6v7-w8x9-0123-yzab-456789012345', '2024-01-04 11:00:00', '2024-01-04 11:00:00', '555-4567', 'Proveedor D'),
    ('t5u6v7w8-x9y0-1234-zabc-567890123456', '2024-01-05 12:00:00', '2024-01-05 12:00:00', '555-5678', 'Proveedor E'),
    ('u6v7w8x9-y0z1-2345-abcd-678901234567', '2024-01-06 13:00:00', '2024-01-06 13:00:00', '555-6789', 'Proveedor F'),
    ('v7w8x9y0-z1a2-3456-bcde-789012345678', '2024-01-07 14:00:00', '2024-01-07 14:00:00', '555-7890', 'Proveedor G'),
    ('w8x9y0z1-a2b3-4567-cdef-890123456789', '2024-01-08 15:00:00', '2024-01-08 15:00:00', '555-8901', 'Proveedor H'),
    ('x9y0z1a2-b3c4-5678-def0-901234567890', '2024-01-09 16:00:00', '2024-01-09 16:00:00', '555-9012', 'Proveedor I'),
    ('y0z1a2b3-c4d5-6789-ef01-012345678901', '2024-01-10 17:00:00', '2024-01-10 17:00:00', '555-0123', 'Proveedor J');

INSERT INTO Emails (id, created_at, updated_at, email, description) VALUES
    ('e1f2g3h4-i5j6-7890-klmn-123456789012', '2024-01-01 07:00:00', '2024-01-01 07:00:00', 'proveedorA@example.com', 'Proveedor A'),
    ('f2g3h4i5-j6k7-8901-lmno-234567890123', '2024-01-02 08:00:00', '2024-01-02 08:00:00', 'proveedorB@example.com', 'Proveedor B'),
    ('g3h4i5j6-k7l8-9012-mnop-345678901234', '2024-01-03 09:00:00', '2024-01-03 09:00:00', 'proveedorC@example.com', 'Proveedor C'),
    ('h4i5j6k7-l8m9-0123-nopq-456789012345', '2024-01-04 10:00:00', '2024-01-04 10:00:00', 'proveedorD@example.com', 'Proveedor D'),
    ('i5j6k7l8-m9n0-1234-opqr-567890123456', '2024-01-05 11:00:00', '2024-01-05 11:00:00', 'proveedorE@example.com', 'Proveedor E'),
    ('j6k7l8m9-n0o1-2345-pqrs-678901234567', '2024-01-06 12:00:00', '2024-01-06 12:00:00', 'proveedorF@example.com', 'Proveedor F'),
    ('k7l8m9n0-o1p2-3456-qrst-789012345678', '2024-01-07 13:00:00', '2024-01-07 13:00:00', 'proveedorG@example.com', 'Proveedor G'),
    ('l8m9n0o1-p2q3-4567-rstu-890123456789', '2024-01-08 14:00:00', '2024-01-08 14:00:00', 'proveedorH@example.com', 'Proveedor H'),
    ('m9n0o1p2-q3r4-5678-stuv-901234567890', '2024-01-09 15:00:00', '2024-01-09 15:00:00', 'proveedorI@example.com', 'Proveedor I'),
    ('n0o1p2q3-r4s5-6789-tuvw-012345678901', '2024-01-10 16:00:00', '2024-01-10 16:00:00', 'proveedorJ@example.com', 'Proveedor J');

INSERT INTO Clients (id, created_at, updated_at, name, email, password) VALUES
    ('1a2b3c4d-5678-90ab-cdef-1234567890ab', '2024-01-01 08:30:00', '2024-01-01 08:30:00', 'Juan Pérez', 'juan.perez@example.com', 'hashed_password_1'),
    ('2b3c4d5e-6789-01bc-def0-2345678901bc', '2024-01-02 09:30:00', '2024-01-02 09:30:00', 'María López', 'maria.lopez@example.com', 'hashed_password_2'),
    ('3c4d5e6f-7890-12cd-ef01-3456789012cd', '2024-01-03 10:30:00', '2024-01-03 10:30:00', 'Carlos García', 'carlos.garcia@example.com', 'hashed_password_3'),
    ('4d5e6f7g-8901-23de-f012-4567890123de', '2024-01-04 11:30:00', '2024-01-04 11:30:00', 'Laura Martínez', 'laura.martinez@example.com', 'hashed_password_4'),
    ('5e6f7g8h-9012-34ef-0123-5678901234ef', '2024-01-05 12:30:00', '2024-01-05 12:30:00', 'Pedro Sánchez', 'pedro.sanchez@example.com', 'hashed_password_5'),
    ('6f7g8h9i-0123-45fg-1234-6789012345fg', '2024-01-06 13:30:00', '2024-01-06 13:30:00', 'Ana Torres', 'ana.torres@example.com', 'hashed_password_6'),
    ('7g8h9i0j-1234-56gh-2345-7890123456gh', '2024-01-07 14:30:00', '2024-01-07 14:30:00', 'Luis Ramírez', 'luis.ramirez@example.com', 'hashed_password_7'),
    ('8h9i0j1k-2345-67hi-3456-8901234567hi', '2024-01-08 15:30:00', '2024-01-08 15:30:00', 'Sofía Morales', 'sofia.morales@example.com', 'hashed_password_8'),
    ('9i0j1k2l-3456-78ij-4567-9012345678ij', '2024-01-09 16:30:00', '2024-01-09 16:30:00', 'Diego Fernández', 'diego.fernandez@example.com', 'hashed_password_9'),
    ('0j1k2l3m-4567-89jk-5678-0123456789jk', '2024-01-10 17:30:00', '2024-01-10 17:30:00', 'Valentina Díaz', 'valentina.diaz@example.com', 'hashed_password_10');

INSERT INTO Proveedores (id, created_at, updated_at, name, address, activo, category_id, image_id, phone_id, email_id) VALUES
    ('2b3c4d5e-6789-01bc-def0-2345678901bc', '2024-01-02 11:30:00', '2024-01-02 11:30:00', 'Proveedor B', 'Avenida 456, Ciudad', TRUE, 'd2e3f4g5-h6i7-8901-jklm-234567890123', 'b2c3d4e5-f6a7-8901-bcde-f23456789012', 'q2r3s4t5-u6v7-8901-wxyz-234567890123', 'f2g3h4i5-j6k7-8901-lmno-234567890123'),
    ('3c4d5e6f-7890-12cd-ef01-3456789012cd', '2024-01-03 12:30:00', '2024-01-03 12:30:00', 'Proveedor C', 'Boulevard 789, Ciudad', TRUE, 'e3f4g5h6-i7j8-9012-klmn-345678901234', 'c3d4e5f6-a7b8-9012-cdef-345678901234', 'r3s4t5u6-v7w8-9012-xyza-345678901234', 'g3h4i5j6-k7l8-9012-mnop-345678901234'),
    ('4d5e6f7g-8901-23de-f012-4567890123de', '2024-01-04 13:30:00', '2024-01-04 13:30:00', 'Proveedor D', 'Calle 321, Ciudad', TRUE, 'f4g5h6i7-j8k9-0123-lmno-456789012345', 'd4e5f6a7-b8c9-0123-def0-456789012345', 's4t5u6v7-w8x9-0123-yzab-456789012345', 'h4i5j6k7-l8m9-0123-nopq-456789012345'),
    ('1a2b3c4d-5678-90ab-cdef-1234567890ab', '2024-01-01 10:30:00', '2024-01-01 10:30:00', 'Proveedor A', 'Calle 123, Ciudad', TRUE, 'c1d2e3f4-g5h6-7890-ijkl-123456789012', 'a1b2c3d4-e5f6-7890-abcd-ef1234567890', 'p1q2r3s4-t5u6-7890-vwxy-123456789012', 'e1f2g3h4-i5j6-7890-klmn-123456789012'),
    ('5e6f7g8h-9012-34ef-0123-5678901234ef', '2024-01-05 14:30:00', '2024-01-05 14:30:00', 'Proveedor E', 'Avenida 654, Ciudad', TRUE, 'g5h6i7j8-k9l0-1234-mnop-567890123456', 'e5f6a7b8-c9d0-1234-ef01-567890123456', 't5u6v7w8-x9y0-1234-zabc-567890123456', 'i5j6k7l8-m9n0-1234-opqr-567890123456'),
    ('6f7g8h9i-0123-45fg-1234-6789012345fg', '2024-01-06 15:30:00', '2024-01-06 15:30:00', 'Proveedor F', 'Calle 987, Ciudad', TRUE, 'h6i7j8k9-l0m1-2345-nopq-678901234567', 'f6a7b8c9-d0e1-2345-f012-678901234567', 'u6v7w8x9-y0z1-2345-abcd-678901234567', 'j6k7l8m9-n0o1-2345-pqrs-678901234567'),
    ('7g8h9i0j-1234-56gh-2345-7890123456gh', '2024-01-07 16:30:00', '2024-01-07 16:30:00', 'Proveedor G', 'Boulevard 159, Ciudad', TRUE, 'i7j8k9l0-m1n2-3456-opqr-789012345678', 'g7b8c9d0-e1f2-3456-0123-789012345678', 'v7w8x9y0-z1a2-3456-bcde-789012345678', 'k7l8m9n0-o1p2-3456-qrst-789012345678'),
    ('8h9i0j1k-2345-67hi-3456-8901234567hi', '2024-01-08 17:30:00', '2024-01-08 17:30:00', 'Proveedor H', 'Avenida 753, Ciudad', TRUE, 'j8k9l0m1-n2o3-4567-pqrs-890123456789', 'h8c9d0e1-f2a3-4567-1234-890123456789', 'w8x9y0z1-a2b3-4567-cdef-890123456789', 'l8m9n0o1-p2q3-4567-rstu-890123456789'),
    ('9i0j1k2l-3456-78ij-4567-9012345678ij', '2024-01-09 18:30:00', '2024-01-09 18:30:00', 'Proveedor I', 'Calle 852, Ciudad', TRUE, 'k9l0m1n2-o3p4-5678-qrst-901234567890', 'i9d0e1f2-a3b4-5678-2345-901234567890', 'x9y0z1a2-b3c4-5678-def0-901234567890', 'm9n0o1p2-q3r4-5678-stuv-901234567890'),
    ('0j1k2l3m-4567-89jk-5678-0123456789jk', '2024-01-10 19:30:00', '2024-01-10 19:30:00', 'Proveedor J', 'Avenida 951, Ciudad', TRUE, 'l0m1n2o3-p4q5-6789-rstu-012345678901', 'j0e1f2a3-b4c5-6789-3456-012345678901', 'y0z1a2b3-c4d5-6789-ef01-012345678901', 'n0o1p2q3-r4s5-6789-tuvw-012345678901');

INSERT INTO Products (id, created_at, updated_at, name, description, precioVenta, precioProveedor, existencia, activo, proveedor_id, category_id, image_id) VALUES
    ('1a2b3c4d-5678-90ab-cdef-1234567890ab', '2024-01-01 09:30:00', '2024-01-01 09:30:00', 'Leche Entera', 'Leche entera de 1 litro', 20.0, 15.0, 100, TRUE, '1a2b3c4d-5678-90ab-cdef-1234567890ab', 'c1d2e3f4-g5h6-7890-ijkl-123456789012', 'a1b2c3d4-e5f6-7890-abcd-ef1234567890'),
    ('2b3c4d5e-6789-01bc-def0-2345678901bc', '2024-01-02 10:30:00', '2024-01-02 10:30:00', 'Harina de Trigo', 'Harina de trigo integral de 1kg', 25.0, 18.0, 150, TRUE, '2b3c4d5e-6789-01bc-def0-2345678901bc', 'd2e3f4g5-h6i7-8901-jklm-234567890123', 'b2c3d4e5-f6a7-8901-bcde-f23456789012'),
    ('3c4d5e6f-7890-12cd-ef01-3456789012cd', '2024-01-03 11:30:00', '2024-01-03 11:30:00', 'Jugo de Naranja', 'Jugo natural de naranja de 500ml', 15.0, 10.0, 200, TRUE, '3c4d5e6f-7890-12cd-ef01-3456789012cd', 'e3f4g5h6-i7j8-9012-klmn-345678901234', 'f6a7b8c9-d0e1-2345-f012-678901234567'),
    ('4d5e6f7g-8901-23de-f012-4567890123de', '2024-01-04 12:30:00', '2024-01-04 12:30:00', 'Pan Integral', 'Pan integral de 500g', 18.0, 12.0, 120, TRUE, '4d5e6f7g-8901-23de-f012-4567890123de', 'f4g5h6i7-j8k9-0123-lmno-456789012345', 'g7b8c9d0-e1f2-3456-0123-789012345678'),
    ('5e6f7g8h-9012-34ef-0123-5678901234ef', '2024-01-05 13:30:00', '2024-01-05 13:30:00', 'Aceite de Oliva', 'Aceite de oliva extra virgen de 1 litro', 50.0, 35.0, 80, TRUE, '5e6f7g8h-9012-34ef-0123-5678901234ef', 'g5h6i7j8-k9l0-1234-mnop-567890123456', 'h8c9d0e1-f2a3-4567-1234-890123456789'),
    ('6f7g8h9i-0123-45fg-1234-6789012345fg', '2024-01-06 14:30:00', '2024-01-06 14:30:00', 'Papas Fritas', 'Paquete de papas fritas de 200g', 10.0, 6.0, 300, TRUE, '6f7g8h9i-0123-45fg-1234-6789012345fg', 'h6i7j8k9-l0m1-2345-nopq-678901234567', 'f6a7b8c9-d0e1-2345-f012-678901234567'),
    ('7g8h9i0j-1234-56gh-2345-7890123456gh', '2024-01-07 15:30:00', '2024-01-07 15:30:00', 'Conserva de Atún', 'Conserva de atún en aceite de oliva', 12.0, 8.0, 180, TRUE, '7g8h9i0j-1234-56gh-2345-7890123456gh', 'i7j8k9l0-m1n2-3456-opqr-789012345678', 'd4e5f6a7-b8c9-0123-def0-456789012345'),
    ('8h9i0j1k-2345-67hi-3456-8901234567hi', '2024-01-08 16:30:00', '2024-01-08 16:30:00', 'Detergente Líquido', 'Detergente líquido para ropa de 1 litro', 8.0, 5.0, 250, TRUE, '8h9i0j1k-2345-67hi-3456-8901234567hi', 'j8k9l0m1-n2o3-4567-pqrs-890123456789', 'h8c9d0e1-f2a3-4567-1234-890123456789'),
    ('9i0j1k2l-3456-78ij-4567-9012345678ij', '2024-01-09 17:30:00', '2024-01-09 17:30:00', 'Manzana Roja', 'Manzanas rojas frescas por kilo', 30.0, 20.0, 220, TRUE, '9i0j1k2l-3456-78ij-4567-9012345678ij', 'k9l0m1n2-o3p4-5678-qrst-901234567890', 'i9d0e1f2-a3b4-5678-2345-901234567890'),
    ('0j1k2l3m-4567-89jk-5678-0123456789jk', '2024-01-10 18:30:00', '2024-01-10 18:30:00', 'Carne de Res', 'Carne de res fresca por kilo', 100.0, 70.0, 60, TRUE, '0j1k2l3m-4567-89jk-5678-0123456789jk', 'l0m1n2o3-p4q5-6789-rstu-012345678901', 'j0e1f2a3-b4c5-6789-3456-012345678901');

INSERT INTO Tickets (id, created_at, updated_at, fecha, total, client_id) VALUES
    ('1a2b3c4d-5678-90ab-cdef-1234567890ab', '2024-02-01 10:00:00', '2024-02-01 10:00:00', '2024-02-01 10:00:00', 150.0, '1a2b3c4d-5678-90ab-cdef-1234567890ab'),
    ('2b3c4d5e-6789-01bc-def0-2345678901bc', '2024-02-02 11:00:00', '2024-02-02 11:00:00', '2024-02-02 11:00:00', 200.0, '2b3c4d5e-6789-01bc-def0-2345678901bc'),
    ('3c4d5e6f-7890-12cd-ef01-3456789012cd', '2024-02-03 12:00:00', '2024-02-03 12:00:00', '2024-02-03 12:00:00', 75.0, '3c4d5e6f-7890-12cd-ef01-3456789012cd'),
    ('4d5e6f7g-8901-23de-f012-4567890123de', '2024-02-04 13:00:00', '2024-02-04 13:00:00', '2024-02-04 13:00:00', 300.0, '4d5e6f7g-8901-23de-f012-4567890123de'),
    ('5e6f7g8h-9012-34ef-0123-5678901234ef', '2024-02-05 14:00:00', '2024-02-05 14:00:00', '2024-02-05 14:00:00', 50.0, '5e6f7g8h-9012-34ef-0123-5678901234ef'),
    ('6f7g8h9i-0123-45fg-1234-6789012345fg', '2024-02-06 15:00:00', '2024-02-06 15:00:00', '2024-02-06 15:00:00', 180.0, '6f7g8h9i-0123-45fg-1234-6789012345fg'),
    ('7g8h9i0j-1234-56gh-2345-7890123456gh', '2024-02-07 16:00:00', '2024-02-07 16:00:00', '2024-02-07 16:00:00', 220.0, '7g8h9i0j-1234-56gh-2345-7890123456gh'),
    ('8h9i0j1k-2345-67hi-3456-8901234567hi', '2024-02-08 17:00:00', '2024-02-08 17:00:00', '2024-02-08 17:00:00', 90.0, '8h9i0j1k-2345-67hi-3456-8901234567hi'),
    ('9i0j1k2l-3456-78ij-4567-9012345678ij', '2024-02-09 18:00:00', '2024-02-09 18:00:00', '2024-02-09 18:00:00', 130.0, '9i0j1k2l-3456-78ij-4567-9012345678ij'),
    ('0j1k2l3m-4567-89jk-5678-0123456789jk', '2024-02-10 19:00:00', '2024-02-10 19:00:00', '2024-02-10 19:00:00', 60.0, '0j1k2l3m-4567-89jk-5678-0123456789jk');

INSERT INTO Products_Tickets (id, precioVenta, product_id, ticket_id, cantidad) VALUES
    ('pt-1a2b3c4d-5678-90ab-cdef-1234567890ab', 20.0, '1a2b3c4d-5678-90ab-cdef-1234567890ab', '1a2b3c4d-5678-90ab-cdef-1234567890ab', 1),
    ('pt-2b3c4d5e-6789-01bc-def0-2345678901bc', 25.0, '2b3c4d5e-6789-01bc-def0-2345678901bc', '1a2b3c4d-5678-90ab-cdef-1234567890ab', 1),
    ('pt-3c4d5e6f-7890-12cd-ef01-3456789012cd', 15.0, '3c4d5e6f-7890-12cd-ef01-3456789012cd', '2b3c4d5e-6789-01bc-def0-2345678901bc', 1),
    ('pt-4d5e6f7g-8901-23de-f012-4567890123de', 18.0, '4d5e6f7g-8901-23de-f012-4567890123de', '2b3c4d5e-6789-01bc-def0-2345678901bc', 1),
    ('pt-5e6f7g8h-9012-34ef-0123-5678901234ef', 50.0, '5e6f7g8h-9012-34ef-0123-5678901234ef', '3c4d5e6f-7890-12cd-ef01-3456789012cd', 1),
    ('pt-6f7g8h9i-0123-45fg-1234-6789012345fg', 10.0, '6f7g8h9i-0123-45fg-1234-6789012345fg', '4d5e6f7g-8901-23de-f012-4567890123de', 1),
    ('pt-7g8h9i0j-1234-56gh-2345-7890123456gh', 12.0, '7g8h9i0j-1234-56gh-2345-7890123456gh', '5e6f7g8h-9012-34ef-0123-5678901234ef', 1),
    ('pt-8h9i0j1k-2345-67hi-3456-8901234567hi', 8.0, '8h9i0j1k-2345-67hi-3456-8901234567hi', '6f7g8h9i-0123-45fg-1234-6789012345fg', 1),
    ('pt-9i0j1k2l-3456-78ij-4567-9012345678ij', 30.0, '9i0j1k2l-3456-78ij-4567-9012345678ij', '7g8h9i0j-1234-56gh-2345-7890123456gh', 1),
    ('pt-0j1k2l3m-4567-89jk-5678-0123456789jk', 100.0, '0j1k2l3m-4567-89jk-5678-0123456789jk', '8h9i0j1k-2345-67hi-3456-8901234567hi', 1);


INSERT INTO sec_role (`usr_created_by`, `usr_id_status`, `usr_modified_by`, `usr_role_name`)
VALUES
    (1, 1, 1, 'ADMIN'),
    (1, 1, 1, 'GERENTE'),
    (1, 1, 1, 'USER');

INSERT INTO sec_user (`use_created_by`, `use_email`, `use_first_name`, `use_id_status`, `use_last_name`, `use_modified_by`, `use_passwd`,`image_id`)
VALUES
    (1, 'user_1@example.com', 'Alice', 1, 'Smith', 1, '$2a$11$dDCPTJfr5yyH9sDTZYFOtutU1nVejYVVu6.djUg7misIiOykBwO46','e5f6a7b8-c9d0-1234-ef01-567890123456'),
    (1, 'user_2@example.com', 'Bob', 1, 'Johnson', 1, '$2a$11$dDCPTJfr5yyH9sDTZYFOtutU1nVejYVVu6.djUg7misIiOykBwO46','f6a7b8c9-d0e1-2345-f012-678901234567'),
    (1, 'user_3@example.com', 'Charlie', 1, 'Brown', 1, '$2a$11$dDCPTJfr5yyH9sDTZYFOtutU1nVejYVVu6.djUg7misIiOykBwO46','g7b8c9d0-e1f2-3456-0123-789012345678');

INSERT INTO sec_user_role_relation (`urr_id_user`, `urr_id_user_role`)
VALUES
    (1, 1), -- Alice es ADMIN
    (1, 2), -- Alice es GERENTE
    (1, 3), -- Alice es USER
    (2, 2), -- Bob es GERENTE
    (2, 3), -- Bob es USER
    (3, 3); -- Charlie es USER

