CREATE TABLE users (
    customer_id   uuid         PRIMARY KEY DEFAULT uuidv7(),
    customer_name varchar(255) NOT NULL
);

CREATE TABLE cart (
    cart_id     uuid PRIMARY KEY DEFAULT uuidv7(),
    customer_id uuid NOT NULL REFERENCES users (customer_id)
);

CREATE TABLE cart_item (
    id      uuid PRIMARY KEY DEFAULT uuidv7(),
    cart_id uuid NOT NULL REFERENCES cart (cart_id) ON DELETE CASCADE,
    sku_id  uuid NOT NULL REFERENCES sku (id),
    CONSTRAINT cart_item_cart_sku_key UNIQUE (cart_id, sku_id)
);

-- Seed data with fixed UUIDs so the curl commands can reference them.
-- Run after schema.sql:  psql -d ecommerce -f seed.sql

INSERT INTO users (customer_id, customer_name) VALUES
  ('a1111111-1111-4111-8111-111111111111', 'Alice Sharma'),
  ('a2222222-2222-4222-8222-222222222222', 'Rahul Verma'),
  ('a3333333-3333-4333-8333-333333333333', 'Priya Nair');

INSERT INTO category (id, title, description) VALUES
  ('c1111111-1111-4111-8111-111111111111', 'Electronics', 'Gadgets and accessories'),
  ('c2222222-2222-4222-8222-222222222222', 'Books', 'Printed and digital books');

INSERT INTO brand (id, title, description) VALUES
  ('b1111111-1111-4111-8111-111111111111', 'Acme', 'General electronics brand'),
  ('b2222222-2222-4222-8222-222222222222', 'Zenith Press', 'Technical publisher');

INSERT INTO product (id, name, description, category, brand, price, category_id, brand_id) VALUES
  ('d1111111-1111-4111-8111-111111111111', 'Wireless Headphones', 'Over-ear Bluetooth headphones',
   'Electronics', 'Acme', 2999.00, '0192f3a0-0000-7000-8000-000000000001', 'b1111111-1111-4111-8111-111111111111'),
  ('d2222222-2222-4222-8222-222222222222', 'Mechanical Keyboard', '87-key hot-swappable keyboard',
   'Electronics', 'Acme', 4499.00, '0192f3a0-0000-7000-8000-000000000001', 'b1111111-1111-4111-8111-111111111111'),
  ('d3333333-3333-4333-8333-333333333333', 'Python Cookbook', 'Recipes for everyday Python',
   'Books', 'Zenith Press', 799.00, '0192f3a0-0000-7000-8000-000000000005', 'b2222222-2222-4222-8222-222222222222');

INSERT INTO sku (id, product_id, sku_code, name, price, quantity, is_active) VALUES
  ('e1111111-1111-4111-8111-111111111111', 'd1111111-1111-4111-8111-111111111111', 'HP-BLK', 'Wireless Headphones - Black', 2999.00, 50, true),
  ('e2222222-2222-4222-8222-222222222222', 'd1111111-1111-4111-8111-111111111111', 'HP-WHT', 'Wireless Headphones - White', 2999.00, 30, true),
  ('e3333333-3333-4333-8333-333333333333', 'd2222222-2222-4222-8222-222222222222', 'KB-RED', 'Mechanical Keyboard - Red Switch', 4499.00, 20, true),
  -- inactive on purpose: adding it to a cart should return 409
  ('e4444444-4444-4444-8444-444444444444', 'd2222222-2222-4222-8222-222222222222', 'KB-BLU', 'Mechanical Keyboard - Blue Switch', 4499.00, 0, false),
  ('e5555555-5555-4555-8555-555555555555', 'd3333333-3333-4333-8333-333333333333', 'BK-PY', 'Python Cookbook - Paperback', 799.00, 100, true);


  select * from users;
  select * from category;
  select * from product;
  select * from sku;
  select * from brand;