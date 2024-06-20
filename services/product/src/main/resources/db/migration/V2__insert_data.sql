BEGIN;

-- Insert records into the category table
INSERT INTO category (id, description, name)
VALUES
    (nextval('category_seq'), 'Electronics items such as phones, laptops, etc.', 'Electronics'),
    (nextval('category_seq'), 'Books of various genres and subjects', 'Books'),
    (nextval('category_seq'), 'Clothing items including shirts, pants, etc.', 'Clothing'),
    (nextval('category_seq'), 'Home appliances and furniture', 'Home & Kitchen'),
    (nextval('category_seq'), 'Sports equipment and outdoor gear', 'Sports & Outdoors'),
    (nextval('category_seq'), 'Beauty and personal care products', 'Beauty & Personal Care');

-- Insert records into the product table
INSERT INTO product (id, description, name, available_quantity, price, category_id)
VALUES
    (nextval('product_seq'), 'A high-quality smartphone', 'Smartphone', 50, 699.99, (SELECT id FROM category WHERE name = 'Electronics')),
    (nextval('product_seq'), 'A lightweight laptop', 'Laptop', 30, 999.99, (SELECT id FROM category WHERE name = 'Electronics')),
    (nextval('product_seq'), 'A mystery novel', 'Mystery Book', 100, 19.99, (SELECT id FROM category WHERE name = 'Books')),
    (nextval('product_seq'), 'A pair of jeans', 'Jeans', 200, 49.99, (SELECT id FROM category WHERE name = 'Clothing')),
    (nextval('product_seq'), 'A refrigerator', 'Refrigerator', 20, 499.99, (SELECT id FROM category WHERE name = 'Home & Kitchen')),
    (nextval('product_seq'), 'A microwave oven', 'Microwave Oven', 35, 199.99, (SELECT id FROM category WHERE name = 'Home & Kitchen')),
    (nextval('product_seq'), 'A yoga mat', 'Yoga Mat', 150, 29.99, (SELECT id FROM category WHERE name = 'Sports & Outdoors')),
    (nextval('product_seq'), 'A mountain bike', 'Mountain Bike', 10, 799.99, (SELECT id FROM category WHERE name = 'Sports & Outdoors')),
    (nextval('product_seq'), 'A set of makeup brushes', 'Makeup Brushes', 80, 39.99, (SELECT id FROM category WHERE name = 'Beauty & Personal Care')),
    (nextval('product_seq'), 'A skincare set', 'Skincare Set', 60, 59.99, (SELECT id FROM category WHERE name = 'Beauty & Personal Care')),
    (nextval('product_seq'), 'An electric kettle', 'Electric Kettle', 40, 29.99, (SELECT id FROM category WHERE name = 'Home & Kitchen')),
    (nextval('product_seq'), 'A set of kitchen knives', 'Kitchen Knives', 50, 89.99, (SELECT id FROM category WHERE name = 'Home & Kitchen')),
    (nextval('product_seq'), 'A pair of running shoes', 'Running Shoes', 120, 69.99, (SELECT id FROM category WHERE name = 'Sports & Outdoors')),
    (nextval('product_seq'), 'A basketball', 'Basketball', 100, 24.99, (SELECT id FROM category WHERE name = 'Sports & Outdoors')),
    (nextval('product_seq'), 'A hair dryer', 'Hair Dryer', 70, 49.99, (SELECT id FROM category WHERE name = 'Beauty & Personal Care')),
    (nextval('product_seq'), 'A smartwatch', 'Smartwatch', 40, 199.99, (SELECT id FROM category WHERE name = 'Electronics'));

COMMIT;
