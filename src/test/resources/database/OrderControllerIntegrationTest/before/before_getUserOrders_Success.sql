INSERT INTO books (id, title, author, isbn, price, description, cover_image)
VALUES (1, 'The Dark Half', 'Stephen King', '112-3884-87645-2', 220, 'horror novel', 'image.jpg');

INSERT INTO books (id, title, author, isbn, price, description, cover_image)
VALUES (2, 'Pet Sematary', 'Stephen King', '112-3884-00000-2', 340, ' best campy horror', 'image.jpg');

INSERT INTO shopping_carts (user_id) VALUES (1);

INSERT INTO cart_items (id, shopping_cart_id, book_id, quantity) VALUES (1, 1, 1, 1);
INSERT INTO cart_items (id, shopping_cart_id, book_id, quantity) VALUES (2, 1, 2, 2);

INSERT INTO orders (id, user_id, status, order_date, total, shipping_address)
VALUES (1, 1, 'PENDING', '2023-10-01T11:35:49.707898',220, 'Kyiv, Shevchenko ave, 1');

INSERT INTO orders (id, user_id, status, order_date, total, shipping_address)
VALUES (2, 1, 'PENDING', '2023-10-01T11:35:49.707898',340, 'Kyiv, Shevchenko ave, 1');
