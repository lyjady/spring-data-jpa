package org.augustus.jpa.repository;

import org.augustus.jpa.bean.Goods;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @author LinYongJin
 * @date 2020/2/14 16:02
 */
public interface GoodsRepository extends JpaRepository<Goods, Long>, JpaSpecificationExecutor<Goods> {

    /**
     * 根据价格与名称进行模糊查询再根据价格降序
     * @param name
     * @param price
     * @return
     */
    List<Goods> findByNameContainingAndPriceLessThanOrderByPrice(String name, Double price);

    @Query(value = "select id, brand, max(price), avg(price) from demo.goods group by brand", nativeQuery = true)
    List groupBy();
}
