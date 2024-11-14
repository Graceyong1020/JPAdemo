package jpabook.jpashop.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
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

    @NotEmpty
    private String name;
    @Embedded // 내장 타입
    private Address address;

    @JsonIgnore // 양방향 연관관계에서 한쪽을 JsonIgnore로 설정해야 한다.
    @OneToMany(mappedBy = "member")// 맴버와 오더는 일대다 관계
    private List<Order> orders = new ArrayList<>();


}
