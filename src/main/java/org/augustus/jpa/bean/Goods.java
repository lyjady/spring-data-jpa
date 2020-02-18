package org.augustus.jpa.bean;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * @author LinYongJin
 * @date 2020/2/14 15:56
 */
@Table(name = "goods")
@Entity
public class Goods implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "brand")
    private String brand;

    @Column(name = "price")
    private Double price;

    @Column(name = "sale_date")
    private Date saleDate;

    @Column(name = "stock")
    private Integer stock;

    @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    @JoinColumn(name = "braned_id")
    private Braned braned;

    @ManyToMany(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    // name: 中间表的名称
    // @JoinColumn(name = "goods_id"): 当前对象在中间表的外键
    // inverseJoinColumns = @JoinColumn(name = "user_id"): 另一侧多对多在中间表的外键
    @JoinTable(name = "t_user_goods", joinColumns = @JoinColumn(name = "goods_id"), inverseJoinColumns = @JoinColumn(name = "user_id"))
    private Set<User> users = new HashSet<>();

    public Goods() {
    }

    public Goods(String name, String brand, Double price, Date saleDate, Integer stock) {
        this.name = name;
        this.brand = brand;
        this.price = price;
        this.saleDate = saleDate;
        this.stock = stock;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Date getSaleDate() {
        return saleDate;
    }

    public void setSaleDate(Date saleDate) {
        this.saleDate = saleDate;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public Braned getBraned() {
        return braned;
    }

    public void setBraned(Braned braned) {
        this.braned = braned;
    }

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

    @Override
    public String toString() {
        return "Goods{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", brand='" + brand + '\'' +
                ", price=" + price +
                ", saleDate=" + saleDate +
                ", stock=" + stock +
                ", users=" + users +
                '}';
    }
}
