package jpabook.jpashop.Service;

import jpabook.jpashop.Domain.*;
import jpabook.jpashop.Domain.item.Item;
import jpabook.jpashop.Repository.ItemRepository;
import jpabook.jpashop.Repository.MemberRepository;
import jpabook.jpashop.Repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrderService {

    private final OrderRepository orderRepository;
    private final MemberRepository memberRepository;
    private final ItemRepository itemRepository;

    //주문하기
    @Transactional
    public Long order(Long memberId, Long itemId, int count){

        //엔티티 조회
        Member member = memberRepository.findOne(memberId);
        Item item = itemRepository.findOne(itemId);

        //배송정보 설정
        Delivery delivery = new Delivery();
        delivery.setAddress(member.getAddress());

        //주문상품 설정
        OrderItem orderItem = OrderItem.createOrderItem(item, item.getPrice(), count);

        //주문생성
        Order order = Order.createOrder(member, delivery, orderItem);

        //주문 저장
        orderRepository.save(order); //원래는 delivery 랑 orderItem 도 save해줘야하지만
        // order 도메인에 cascade로 설정을 해놧기때문에 자동으로 persist해준다

        return order.getId();

    }

    //주문 취소
    @Transactional
    public void cancelOrder(Long orderId){
        Order order = orderRepository.findOne(orderId);
        order.cancel();
    }

//    //주문 검색
//    public List<Order> findOrders(OrderSearch orderSearch){
//
//    }




}
