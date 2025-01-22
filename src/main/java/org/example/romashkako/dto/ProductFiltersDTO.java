package org.example.romashkako.dto;

import jakarta.validation.constraints.*;

import java.util.Objects;

public class ProductFiltersDTO {

    @Size(max = 255, message = "Название товара не должно превышать 255 символов")
    private String filterName;

    @Min(value = 0, message = "Минимальная цена должна быть >= 0")
    private Integer minPrice;

    @Min(value = 0, message = "Максимальная цена должна быть >= 0")
    private Integer maxPrice;

    private Boolean inStock;

    @Positive(message = "Лимит возвращаемых товаров должен быть больше 0")
    private int limit = 10;

    @Min(value = 0, message = "Страница не может быть отрицательным числом")
    private int page;

    @Pattern(regexp = "^(name|price)$", message = "Тип сортировки должен быть 'name' или 'price'")
    private String sortType = "name";

    @Pattern(regexp = "^(ASC|DESC)$", message = "Направление сортировки должно быть 'ASC' или 'DESC'")
    private String sortDirection = "DESC";

    public ProductFiltersDTO(){
    }

    public ProductFiltersDTO(String filterName,
                             Integer minPrice,
                             Integer maxPrice,
                             Boolean inStock,
                             int limit,
                             int page,
                             String sortType,
                             String sortDirection) {
        this.filterName = filterName;
        this.minPrice = minPrice;
        this.maxPrice = maxPrice;
        this.inStock = inStock;
        this.limit = limit;
        this.page = page;
        this.sortType = sortType;
        this.sortDirection = sortDirection;
    }

    public ProductFiltersDTO(String filterName,
                             Integer minPrice,
                             Integer maxPrice,
                             Boolean inStock,
                             int limit,
                             int page) {
        this.filterName = filterName;
        this.minPrice = minPrice;
        this.maxPrice = maxPrice;
        this.inStock = inStock;
        this.limit = limit;
        this.page = page;
    }

    public String getFilterName() {
        return filterName;
    }

    public void setFilterName(String filterName) {
        this.filterName = filterName;
    }

    public Integer getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(Integer minPrice) {
        this.minPrice = minPrice;
    }

    public Integer getMaxPrice() {
        return maxPrice;
    }

    public void setMaxPrice(Integer maxPrice) {
        this.maxPrice = maxPrice;
    }

    public Boolean getInStock() {
        return inStock;
    }

    public void setInStock(Boolean inStock) {
        this.inStock = inStock;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public String getSortType() {
        return sortType;
    }

    public void setSortType(String sortType) {
        this.sortType = sortType;
    }

    public String getSortDirection() {
        return sortDirection;
    }

    public void setSortDirection(String sortDirection) {
        this.sortDirection = sortDirection;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductFiltersDTO that = (ProductFiltersDTO) o;
        return limit == that.limit &&
                page == that.page &&
                Objects.equals(filterName, that.filterName) &&
                Objects.equals(minPrice, that.minPrice) &&
                Objects.equals(maxPrice, that.maxPrice) &&
                Objects.equals(inStock, that.inStock) &&
                Objects.equals(sortType, that.sortType) &&
                Objects.equals(sortDirection, that.sortDirection);
    }

    @Override
    public int hashCode() {
        return Objects.hash(filterName, minPrice, maxPrice, inStock, limit, page, sortType, sortDirection);
    }

    @AssertTrue(message = "Минимальная цена не может быть выше максимальной")
    private boolean isPriceRangeValid(){
        if(minPrice == null || maxPrice == null){
            return true;
        }

        return minPrice <= maxPrice;
    }
}
