CREATE EXTENSION IF NOT EXISTS "pgcrypto";

CREATE TABLE orders (
        id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
        product_name VARCHAR(100) NOT NULL,
        amount NUMERIC(10, 2) NOT NULL,
        quantity INT NOT NULL,
        status VARCHAR(20) NOT NULL DEFAULT 'PENDING',
        created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
        updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);
