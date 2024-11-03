package jpabook.japshop.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
public class Member {

    @Id @GeneratedValue
    @Column(name = "member_id")
    private Long id;
    private String name;
    @Embedded // 내장 타입
    private Address address;

    @OneToMany(mappedBy = "member")// 맴버와 오더는 일대다 관계
    private List<Order> orders = new ArrayList<>();


}
