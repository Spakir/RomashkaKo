CREATE TABLE  IF NOT EXISTS Product_supplies
(
    id BIGSERIAL PRIMARY KEY,
    document_name VARCHAR(255),
    product_id BIGSERIAL,
    quantity BIGINT CHECK ( quantity > 0 ),
    CONSTRAINT fk_product FOREIGN KEY (product_id) REFERENCES products(id)
)