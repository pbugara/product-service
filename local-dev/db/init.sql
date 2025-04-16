CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE IF NOT EXISTS products (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    name VARCHAR(255) NOT NULL,
    description TEXT NOT NULL,
    price DECIMAL(10, 2) NOT NULL CHECK (price > 0)
);

INSERT INTO products (id, name, description, price)
VALUES
    ('550e8400-e29b-41d4-a716-446655440000', 'Product 1', 'Description for Product 1', 19.99),
    ('550e8400-e29b-41d4-a716-446655440001', 'Product 2', 'Description for Product 2', 29.99),
    ('550e8400-e29b-41d4-a716-446655440002', 'Product 3', 'Description for Product 3', 39.99),
    ('550e8400-e29b-41d4-a716-446655440003', 'Product 4', 'Description for Product 4', 49.99),
    ('550e8400-e29b-41d4-a716-446655440004', 'Product 5', 'Description for Product 5', 59.99);

CREATE TABLE discount (
      id BIGINT PRIMARY KEY,
      type VARCHAR(31) NOT NULL,
      CONSTRAINT discount_type_check CHECK (type IN ('PERCENTAGE', 'QUANTITY'))
);

CREATE UNIQUE INDEX unique_discount_type ON discount(type);

CREATE TABLE percentage_discount (
     id BIGINT PRIMARY KEY,
     percentage INTEGER NOT NULL CHECK (percentage >= 0 and percentage <= 100),
     CONSTRAINT fk_percentage_discount
         FOREIGN KEY (id)
             REFERENCES discount(id)
             ON DELETE CASCADE
);

CREATE TABLE quantity_discount (
   id BIGINT PRIMARY KEY,
   CONSTRAINT fk_quantity_discount
       FOREIGN KEY (id)
           REFERENCES discount(id)
           ON DELETE CASCADE
);

CREATE TABLE quantity_discount_configs (
    id BIGINT PRIMARY KEY,
    min_qty INTEGER NOT NULL CHECK (min_qty > 0),
    max_qty INTEGER,
    percentage INTEGER NOT NULL CHECK (percentage >= 0 AND percentage <= 100),
    discount_id BIGINT NOT NULL,
    CONSTRAINT fk_quantity_config_discount
       FOREIGN KEY (discount_id)
           REFERENCES quantity_discount(id)
           ON DELETE CASCADE,
    CONSTRAINT check_max_qty CHECK (max_qty IS NULL OR max_qty > min_qty)
);

CREATE UNIQUE INDEX only_one_null_max_qty_per_discount
    ON quantity_discount_configs(discount_id)
    WHERE max_qty IS NULL;

CREATE OR REPLACE FUNCTION check_quantity_ranges_no_overlap()
RETURNS TRIGGER AS $$
BEGIN
    IF EXISTS (
        SELECT 1
        FROM quantity_discount_configs q
        WHERE q.discount_id = NEW.discount_id
          AND q.id <> COALESCE(NEW.id, -1)
          AND (
            (NEW.max_qty IS NULL AND q.max_qty IS NULL)
            OR
            (NEW.max_qty IS NULL AND q.min_qty >= NEW.min_qty)
            OR
            (q.max_qty IS NULL AND NEW.min_qty >= q.min_qty)
            OR
            (NEW.max_qty IS NOT NULL AND q.max_qty IS NOT NULL
              AND NEW.min_qty <= q.max_qty AND q.min_qty <= NEW.max_qty)
          )
    ) THEN
        RAISE EXCEPTION 'Overlapping quantity range for the same discount_id: [% - %]', NEW.min_qty, NEW.max_qty;
END IF;
RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trg_quantity_range_no_overlap
BEFORE INSERT OR UPDATE ON quantity_discount_configs
FOR EACH ROW EXECUTE FUNCTION check_quantity_ranges_no_overlap();

INSERT INTO discount (id, type) VALUES
    (1, 'PERCENTAGE'),
    (2, 'QUANTITY');

INSERT INTO percentage_discount (id, percentage) VALUES
    (1, 10);

INSERT INTO quantity_discount (id) VALUES
    (2);

INSERT INTO quantity_discount_configs (id, min_qty, max_qty, percentage, discount_id) VALUES
    (1, 1, 9, 0, 2),
    (2, 10, 19, 5, 2),
    (3, 20, 49, 10, 2),
    (4, 50, NULL, 15, 2);

CREATE TABLE discount_interaction (
      id BIGINT PRIMARY KEY,
      name VARCHAR(31) NOT NULL,
      active BOOLEAN NOT NULL DEFAULT FALSE,
      CONSTRAINT discount_interaction_name CHECK (name IN ('HIGHER', 'CUMULATIVE'))
);

CREATE UNIQUE INDEX only_one_active_discount_interaction ON discount_interaction (active) WHERE active = TRUE;

INSERT INTO discount_interaction (id, name, active)
VALUES
    (1, 'HIGHER', TRUE),
    (2, 'CUMULATIVE', FALSE);
