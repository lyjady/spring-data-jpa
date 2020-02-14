package org.augustus.jpa;

import org.augustus.jpa.bean.Goods;
import org.augustus.jpa.bean.Phone;
import org.augustus.jpa.repository.GoodsRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.*;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author LinYongJin
 * @date 2020/2/14 16:03
 */
@SpringBootTest
public class GoodsJpaTest {

    @Autowired
    private GoodsRepository goodsRepository;

    @Test
    public void test() {
        System.out.println(goodsRepository);
        System.out.println(goodsRepository.getClass());
    }

    @Test
    public void saveAll() {
        Stream<Goods> stream = Stream.of(new Goods("iPhone Xs Max", "Apple", 8999.0, new Date(), 200),
                new Goods("RTX 2080", "Nvida", 6000.0, new Date(), 300),
                new Goods("i9-9900KS", "Intel", 5000.0, new Date(), 250),
                new Goods("RTX 2080 Ti", "Nvidda", 9999.0, new Date(), 340),
                new Goods("RTX Titan", "Nvdia", 19999.0, new Date(), 100),
                new Goods("iPhone 11 Pro", "Apple", 12000.0, new Date(), 340),
                new Goods("i9-10120", "Intel", 9999.0, new Date(), 210));
        goodsRepository.saveAll(stream.collect(Collectors.toList()));
    }

    @Test
    public void queryByMethodName() {
        List<Goods> graphicsCards = goodsRepository.findByNameContainingAndPriceLessThanOrderByPrice("RTX", 15000.0);
        graphicsCards.forEach(System.out::println);
    }

    @Test
    @Transactional
    @Rollback(false)
    public void updateGoods() {
        Optional<Goods> goods = goodsRepository.findById(1L);
        goods.ifPresent(good -> {
            good.setStock(8888);
        });
    }

    @Test
    public void deleteGoods() {
        Optional<Goods> goods = goodsRepository.findById(1L);
        goods.ifPresent(good -> {
            goodsRepository.deleteById(good.getId());
        });
    }

    @Test
    @Transactional
    @Rollback(false)
    public void flushDB() {
        goodsRepository.deleteAll();
    }

    @Test
    public void findById() {
        Optional<Goods> goods = goodsRepository.findById(10L);
        goods.ifPresent(System.out::println);
    }

    @Test
    public void findAll() {
        List<Goods> all = goodsRepository.findAll();
        if (all.size() > 0) {
            System.out.println(all);
        }
    }

    @Test
    public void findByCondition1() {
        // 查询品牌是苹果并且发售日期晚于2019-10-01
        List<Goods> all = goodsRepository.findAll(((root, query, criteriaBuilder) -> criteriaBuilder.and(criteriaBuilder.equal(root.get("brand"), "Apple"),
                criteriaBuilder.greaterThan(root.get("saleDate"), Date.from(LocalDateTime.of(2019, 10, 1, 0, 0, 0).atZone(ZoneId.systemDefault()).toInstant())))));
        System.out.println(all);
    }

    @Test
    public void findByCondition2() {
        //模糊查询
        List<Goods> all = goodsRepository.findAll(((root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("name"), "RTX%")));
        System.out.println(all);
    }

    @Test
    public void exists() {
        Goods nvida = new Goods();
        nvida.setBrand("Nvida");
        nvida.setPrice(6000.0);
        boolean exists = goodsRepository.exists(Example.of(nvida));
        System.out.println(exists);
    }

    @Test
    public void pagingAndSortingQuery() {
        Sort sort = Sort.by(Sort.Direction.DESC, "price", "name");
        Pageable pageable = PageRequest.of(2, 3, sort);
        Page<Goods> page = goodsRepository.findAll(((root, query, criteriaBuilder) -> criteriaBuilder.ge(root.get("id"), 1L)), pageable);
        System.out.println("总数: " + page.getTotalElements());
        System.out.println("总页数: " + page.getTotalPages());
        List<Goods> content = page.getContent();
        content.forEach(System.out::println);
    }

    @Test
    public void groupBy() {
        List goods = goodsRepository.groupBy();
        goods.forEach(good -> {
            Object[] objects = (Object[]) good;
            System.out.println("brand: " + objects[1]);
            System.out.println("max price: " + objects[2]);
            System.out.println("avg price: " + objects[3]);
        });
    }

    @Test
    public void childQuery() {
        //select * from demo.goods where goods.id in (select id from t_phone where t_phone.price > 5000);
        List<Goods> all = goodsRepository.findAll(((root, query, criteriaBuilder) -> {
            Subquery<String> childSubQuery = query.subquery(String.class);
            Root<Phone> childRoot = childSubQuery.from(Phone.class);
            childSubQuery.select(childRoot.get("id")).where(criteriaBuilder.greaterThan(childRoot.get("price"), 5000));
            return criteriaBuilder.and(root.get("id").in(childSubQuery));
        }));
        all.forEach(System.out::println);
    }

    @Test
    public void childQuery2() {
        //select * from goods where id in ( select id from t_phone where id in ( select id from t_phone where price> 5000 ) and price > 5000 )
        List<Goods> all = goodsRepository.findAll(((root, query, criteriaBuilder) -> {
            Subquery<String> childSubQuery = query.subquery(String.class);
            Root<Phone> childRoot = childSubQuery.from(Phone.class);
            childSubQuery.select(childRoot.get("id")).where(criteriaBuilder.greaterThan(childRoot.get("price"), 5000));
            Subquery<String> fatherSubQuery = query.subquery(String.class);
            Root<Phone> fatherRoot = fatherSubQuery.from(Phone.class);
            fatherSubQuery.select(fatherRoot.get("id")).where(criteriaBuilder.and(fatherRoot.get("id").in(childSubQuery), criteriaBuilder.greaterThan(fatherRoot.get("price"), 5500)));
            return criteriaBuilder.and(root.get("id").in(fatherSubQuery));
        }));
        all.forEach(System.out::println);
    }
}
