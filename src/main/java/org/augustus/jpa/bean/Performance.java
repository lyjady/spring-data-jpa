package org.augustus.jpa.bean;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author LinYongJin
 * @date 2020/2/18 10:24
 */
@Entity
@Table(name = "t_performance")
public class Performance implements Serializable {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "speed")
    private Integer speed;

    @OneToOne(mappedBy = "performance")
    private Phone phone;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getSpeed() {
        return speed;
    }

    public void setSpeed(Integer speed) {
        this.speed = speed;
    }

    public Phone getPhone() {
        return phone;
    }

    public void setPhone(Phone phone) {
        this.phone = phone;
    }

    @Override
    public String toString() {
        return "Performance{" +
                "id=" + id +
                ", speed=" + speed +
                '}';
    }
}
