package jpabook.jpashop.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
@Getter @Setter
public class Order {

    @Id
    @GeneratedValue
    @Column(name = "order_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany(mappedBy = "order", fetch = FetchType.LAZY , cascade = CascadeType.ALL)
    private List<OrderItem> orderItems = new ArrayList<>();

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "delivery_id")
    private Delivery delivery;
// cascade는 값만 멀쩡히 들어있다면 order을 저장할 때 연관된 것들 다 같이 저장해준다는의미 일일히 다저장할필요 x 삭제도 마찬가지
    private LocalDateTime orderTime;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    //양방향 매핑일시 사용하는 연관관계 메서드들
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
    //요까지

    //==생성 메서드 ==//
    public static Order createOrder(Member member, Delivery delivery, OrderItem... orderItems){
        Order order =  new Order();
        order.setMember(member);
        order.setDelivery(delivery);
        for(OrderItem orderItem : orderItems){
            order.addOrderItem(orderItem);
        }
        order.setStatus(OrderStatus.ORDER);
        order.setOrderTime(LocalDateTime.now());
        return order;
    }

    //== 비지니스 로직 ==//
    //주문취소
    public void cancel(){
        if(delivery.getStatus()==DeliveryStatus.COMP){
            throw new IllegalStateException("이미 배송완료된 상품은 취소가 불가능합니다.");
        }
        this.setStatus(OrderStatus.CANCEL);
        for(OrderItem orderItem: orderItems){
            orderItem.cancel();
        }
    }

    //== 조회 로직 ==//
    //전체 주문가격 조회
    public int getTotalPrice(){
        int totalPrice =0;
        for(OrderItem orderItem : orderItems){
            totalPrice+=orderItem.getTotalPrice();
        }
        return totalPrice;
//        int totalPrice = orderItems.stream().mapToInt(OrderItem::getTotalPrice).sum();
//        return totalPrice; // 인텔리제이가 실행해주는 replace sum()이걸 누르면 이렇게 간단하게 바꿔준다
    }
}
