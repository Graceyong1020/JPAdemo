package jpabook.japshop.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
@Getter
@Setter
public class Order {

    @Id @GeneratedValue
    @Column(name = "order_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY) // order와 member는 다대일 관계
    @JoinColumn(name = "member_id") // 외래키를 매핑할 때 사용
    private Member member;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL) // orderItem과 order는 일대다 관계
    private List<OrderItem> orderItems = new ArrayList<>();

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "delivery_id")
    private Delivery delivery;

    private LocalDateTime orderDate;

    @Enumerated(EnumType.STRING) // EnumType.ORDINAL은 숫자로 저장되기 때문에 STRING으로 사용, @Enumerated는 Enum 타입을 매핑할 때 사용
    private OrderStatus status; // 주문 상태 [ORDER, CANCEL]

    //==연관관계 메서드==//
    public void setMember(Member member) {
        this.member = member;
        member.getOrders().add(this);
    }
    public void addOrderItem(OrderItem orderItem) {
        orderItems.add(orderItem);
        orderItem.setOrder(this);
    }
    public void setDelivery(Delivery delivery) {
        this.delivery = delivery;
        delivery.setOrder(this);
    }
    //==생성 메서드==//
    public static Order createOrder(Member member, Delivery delivery, OrderItem... orderItems) { // OrderItem을 여러개 받을 수 있도록
        Order order = new Order();
        order.setMember(member);
        order.setDelivery(delivery);
        for (OrderItem orderItem : orderItems) { // OrderItem을 여러개 넣을 수 있도록
            order.addOrderItem(orderItem); // OrderItem을 추가
        }
        order.setStatus(OrderStatus.ORDER);
        order.setOrderDate(LocalDateTime.now());
        return order;
    } // 생성 메서드를 사용하면 OrderItem을 추가할 때 OrderItem의 생성자를 사용하지 않아도 된다.

    //==비즈니스 로직==//
    /**
     * 주문 취소
     */
    public void cancel() {
        if(delivery.getStatus() == DeliveryStatus.COMP) { // 배송이 완료되면 취소 불가능
            throw new IllegalStateException("이미 배송완료된 상품은 취소가 불가능합니다.");
        }
        this.setStatus(OrderStatus.CANCEL); // 주문 상태를 취소로 변경
        for (OrderItem orderItem : orderItems) { // 주문 상품들을 하나씩 돌면서
            orderItem.cancel(); // 주문 상품 취소
        }
    }
    //==조회 로직==//
    /**
     * 전체 주문 가격 조회
     */
    public int getTotalPrice() {
        int totalPrice = 0;
        for (OrderItem orderItem : orderItems) { // 주문 상품들을 하나씩 돌면서
            totalPrice += orderItem.getTotalPrice(); // 주문 상품의 가격을 더함
        }
        return totalPrice;
    }
}
