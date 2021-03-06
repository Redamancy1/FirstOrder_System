package com.cici.order.repository;

import com.cici.order.model.ProductInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * 商品对象 repository
 *
 * @author Redamancy
 * @version 1.0
 * @since jdk 1.8
 */
public interface ProductInfoRepository extends JpaRepository<ProductInfo,String> {

    List<ProductInfo> findByProductStatus(Integer productStatus);

}
