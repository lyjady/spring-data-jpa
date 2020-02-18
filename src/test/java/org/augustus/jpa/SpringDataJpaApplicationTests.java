package org.augustus.jpa;

import org.augustus.jpa.bean.Performance;
import org.augustus.jpa.bean.Phone;
import org.augustus.jpa.repository.PhoneRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

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

    @Test
    public void findAll() {
        Phone phone = new Phone("iPhone 11 Pro", 8999.0);
        Performance performance = new Performance();
        performance.setSpeed(6);
        phone.setPerformance(performance);
        performance.setPhone(phone);
        phoneRepository.save(phone);
    }

    @Test
    public void find() {
        Optional<Phone> byId = phoneRepository.findById(14);
        byId.ifPresent(System.out::println);
    }



}
