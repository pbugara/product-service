CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE IF NOT EXISTS products (
                                        id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    name VARCHAR(255) NOT NULL,
    description TEXT NOT NULL,
    price DECIMAL(10, 2) NOT NULL CHECK (price > 0)
    );

INSERT INTO products (id, name, description, price)
VALUES
    ('550e8400-e29b-41d4-a716-446655440000', 'Product 1', 'Description for Product 1', 19.99);
