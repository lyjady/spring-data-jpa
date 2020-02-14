package org.augustus.jpa;

import org.augustus.jpa.bean.Phone;
import org.augustus.jpa.repository.PhoneRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class SpringDataJpaApplicationTests {

    @Autowired
    private PhoneRepository phoneRepository;

    @Test
    void contextLoads() {
        Phone phone = new Phone(1, "iPhone Xs Max", 8999.0);
        Phone save = phoneRepository.save(phone);
        System.out.println(save);
    }

    @Test
    void findByPrice() {
        List<Phone> byPrice = phoneRepository.findByPrice(8999.0);
        System.out.println(byPrice);
    }

}
