package org.augustus.jpa.repository;

import org.augustus.jpa.bean.Phone;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author LinYongJin
 * @date 2020/2/10 17:11
 */
@Repository
public interface PhoneRepository extends JpaRepository<Phone, Integer> {

    @Override
    <S extends Phone> S save(S s);

    List<Phone> findByPrice(Double price);

}
