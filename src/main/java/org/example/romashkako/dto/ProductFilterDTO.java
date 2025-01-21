package org.example.romashkako.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public class ProductFilterDTO {

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
    private int page = 0;

    @Pattern(regexp = "^(name|price)$", message = "Тип сортировки должен быть 'name' или 'price'")
    private String sortType = "name";

    @Pattern(regexp = "^(ASC|DESC)$", message = "Направление сортировки должно быть 'ASC' или 'DESC'")
    private String sortDirection = "DESC";

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
}
