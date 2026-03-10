package org.example.uniproject.repository.specification;

import org.example.uniproject.dto.ProductFilter;
import org.example.uniproject.entity.Product;
import org.springframework.data.jpa.domain.Specification;

public class ProductSpecification {

    public static Specification<Product> filter(ProductFilter filter) {
        return Specification.where(category(filter))
                .and(active(filter))
                .and(nameLike(filter));
    }

    private static Specification<Product> category(ProductFilter filter) {
        if (filter == null || filter.getCategoryId() == null) return null;
        return (root, query, cb) ->
                cb.equal(root.get("productCategory").get("id"), filter.getCategoryId());
    }

    private static Specification<Product> active(ProductFilter filter) {
        if (filter == null || filter.getActive() == null) return null;
        return (root, query, cb) ->
                cb.equal(root.get("active"), filter.getActive());
    }

    private static Specification<Product> nameLike(ProductFilter filter) {
        if (filter == null || filter.getName() == null || filter.getName().isBlank()) return null;
        String pattern = "%" + filter.getName().toLowerCase() + "%";
        return (root, query, cb) ->
                cb.like(cb.lower(root.get("name")), pattern);
    }
}
