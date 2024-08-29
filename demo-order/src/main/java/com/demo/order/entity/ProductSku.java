//package com.demo.product.entity;
//
//import com.demo.common.entity.BaseEntity;
//import lombok.Data;
//import lombok.EqualsAndHashCode;
//
//import jakarta.persistence.Column;
//import jakarta.persistence.Entity;
//import jakarta.persistence.Table;
//import jakarta.persistence.UniqueConstraint;
//import java.time.LocalDate;
//
//@EqualsAndHashCode(callSuper = true)
//@Entity
//@Data
//@Table(name = "productSku",
//        uniqueConstraints  = {
//                @UniqueConstraint(name = "uk_productSku", columnNames = {"org", "region","productId", "sku"})
//        }
//)
//public class ProductSku extends BaseEntity {
//    @Column(nullable = false, insertable = true, updatable = false)
//    private String org;
//    @Column(nullable = false, insertable = true, updatable = false)
//    private String region;
//    @Column(nullable = false, insertable = true, updatable = false)
//    private String productId;
//    @Column(nullable = false, insertable = true, updatable = false)
//    private String sku;
//    @Column(nullable = false)
//    private String skuName;
//    private LocalDate expiryDate;
//}
