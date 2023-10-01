INSERT INTO books (id, title, author, isbn, price, description, cover_image)
VALUES (1, 'The Dark Half', 'Stephen King', '112-3884-87645-2', 49.90, 'horror novel', 'image.jpg');

INSERT INTO books (id, title, author, isbn, price, description, cover_image)
VALUES (2, 'Pet Sematary', 'Stephen King', '112-3884-00000-2', 39.90, ' best campy horror', 'image.jpg');

INSERT INTO orders (user_id, status, total, order_date, shipping_address)
VALUES (2, 'PENDING', 800, '2024-12-03T10:15:30', 'BobAddress');

INSERT INTO orders (user_id, status, total, order_date, shipping_address)
VALUES (2, 'PENDING', 1000, '2024-12-03T10:16:30', 'BobAddress');
