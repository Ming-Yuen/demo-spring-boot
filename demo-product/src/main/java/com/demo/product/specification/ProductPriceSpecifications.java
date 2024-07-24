package com.demo.product.specification;

import com.demo.product.entity.ProductPrice;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.util.Pair;

import java.time.OffsetDateTime;
import java.util.Arrays;
import java.util.List;

public class ProductPriceSpecifications {
    public static Specification<ProductPrice> hasProductIdAndEffectiveDate(List<Pair<String, OffsetDateTime>> pairs) {
        return (root, query, criteriaBuilder) -> {
            Predicate[] predicates = pairs.stream().map(pair ->
                    criteriaBuilder.and(
//                            criteriaBuilder.equal(root.get("productId"), pair.getFirst()),
//                            criteriaBuilder.equal(root.get("effectiveDate"), pair.getSecond())
                            root.get("productId").get("effectiveDate").in(Arrays.asList(pair.getFirst(),pair.getSecond()))
                    )
            ).toArray(Predicate[]::new);
            return criteriaBuilder.or(predicates);
        };
    }
}
