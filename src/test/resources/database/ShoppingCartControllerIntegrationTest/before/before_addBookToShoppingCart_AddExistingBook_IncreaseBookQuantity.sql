INSERT INTO books (id, title, author, isbn, price, description, cover_image)
VALUES (1, 'The Dark Half', 'Stephen King', '112-3884-87645-2', 49.90, 'horror novel', 'image.jpg');

INSERT INTO categories (id, name, description) VALUES (1, 'Horror', 'Scary fantasy story');

INSERT INTO books_categories (book_id, category_id) VALUES (1, 1);

INSERT INTO shopping_carts (user_id) VALUES (1);

INSERT INTO cart_items (id, shopping_cart_id, book_id, quantity) VALUES (1,1,1,10);