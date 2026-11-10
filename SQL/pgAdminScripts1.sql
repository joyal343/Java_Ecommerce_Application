SELECT * FROM public.product
ORDER BY id ASC 


ALTER TABLE public.product
ADD COLUMN category VARCHAR(100),
ADD COLUMN brand VARCHAR(100);

ALTER TABLE public.product
ADD COLUMN price Numeric(12,2);

UPDATE product
SET
    category = 'Clothing',
    brand = 'Nike',
    price = 499.00
WHERE id = '0192f3a0-0000-7000-8000-000000000001';

UPDATE product
SET
    category = 'Groceries',
    brand = 'India Gate',
    price = 299.00
WHERE id = '0192f3a0-0000-7000-8000-000000000002';

CREATE TABLE category (
    id          UUID PRIMARY KEY,
    title       VARCHAR(100) NOT NULL UNIQUE,
    description TEXT
);

CREATE TABLE brand (
    id          UUID PRIMARY KEY,
    title       VARCHAR(100) NOT NULL UNIQUE,
    description TEXT
);

  ALTER TABLE product
      ADD COLUMN category_id UUID REFERENCES category(id),
      ADD COLUMN brand_id    UUID REFERENCES brand(id);

INSERT INTO category (id, title, description) VALUES
('0192f3a0-0000-7000-8000-000000000001', 'Electronics',     'Phones, laptops, audio gear and other gadgets'),
('0192f3a0-0000-7000-8000-000000000002', 'Footwear',        'Sneakers, running shoes, sandals and boots'),
('0192f3a0-0000-7000-8000-000000000003', 'Clothing',        'Everyday wear, activewear and outerwear'),
('0192f3a0-0000-7000-8000-000000000004', 'Home & Kitchen',  'Appliances, cookware and home essentials'),
('0192f3a0-0000-7000-8000-000000000005', 'Books',           'Fiction, non-fiction, textbooks and comics');

INSERT INTO brand (id, title, description) VALUES
('0192f3a0-0000-7000-8000-000000000011', 'Apple',    'Consumer electronics, software and services'),
('0192f3a0-0000-7000-8000-000000000012', 'Samsung',  'Smartphones, TVs and home appliances'),
('0192f3a0-0000-7000-8000-000000000013', 'Sony',     'Audio, cameras and gaming hardware'),
('0192f3a0-0000-7000-8000-000000000014', 'Nike',     'Athletic footwear, apparel and equipment'),
('0192f3a0-0000-7000-8000-000000000015', 'Adidas',   'Sportswear and sneakers'),
('0192f3a0-0000-7000-8000-000000000016', 'Levi''s',  'Denim and casual clothing'),
('0192f3a0-0000-7000-8000-000000000017', 'Prestige', 'Cookers, cookware and kitchen appliances');