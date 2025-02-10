package org.example.romashkako.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;

import java.util.Objects;

public class ProductSupplyDTO {

    private Long id;

    @Size(max = 255,message = "Название поставки товара должно содержать меньше 255 символов")
    private String documentName;

    @Min(value = 1,message = "Продукт должен быть существующим")
    private Long productId;

    @Min(value = 1,message = "Кол-во поставляемого товара должно быть больше 0")
    private Integer quantity;

    public ProductSupplyDTO(){

    }

    public ProductSupplyDTO(Long id, String documentName, Long productId, Integer quantity) {
        this.id = id;
        this.documentName = documentName;
        this.productId = productId;
        this.quantity = quantity;
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

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
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
        ProductSupplyDTO that = (ProductSupplyDTO) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "ProductSupplyDTO{" +
                "id=" + id +
                ", documentName='" + documentName + '\'' +
                ", productId=" + productId +
                ", quantity=" + quantity +
                '}';
    }
}
