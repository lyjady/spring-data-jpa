package org.augustus.jpa.bean;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author LinYongJin
 * @date 2020/2/18 10:47
 */
@Entity
@Table(name = "t_braned")
public class Braned implements Serializable {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "braned", fetch = FetchType.EAGER)
    private Set<Goods> goods = new HashSet<>();

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Goods> getGoods() {
        return goods;
    }

    public void setGoods(Set<Goods> goods) {
        this.goods = goods;
    }

    @Override
    public String toString() {
        return "Braned{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", goods=" + goods +
                '}';
    }
}
