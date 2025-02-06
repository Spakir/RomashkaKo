package org.example.romashkako.model;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "product_supplies")
public class ProductSupply {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "document_name",nullable = false)
    private String documentName;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    private Integer quantity;

    public ProductSupply(String documentName, Product product, Integer quantity) {
        this.documentName = documentName;
        this.product = product;
        this.quantity = quantity;
    }

    public ProductSupply(Long id, String documentName, Product product, Integer quantity) {
        this.id = id;
        this.documentName = documentName;
        this.product = product;
        this.quantity = quantity;
    }

    public ProductSupply(){

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDocumentName() {
        return documentName;
    }

    public void setDocumentName(String documentName) {
        this.documentName = documentName;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductSupply that = (ProductSupply) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "ProductSupply{" +
                "id=" + id +
                ", documentName='" + documentName + '\'' +
                ", product=" + product +
                ", quantity=" + quantity +
                '}';
    }
}
