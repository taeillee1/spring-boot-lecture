package jpabook.jpashop;

import jpabook.jpashop.domain.*;
import jpabook.jpashop.domain.item.Book;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;

@Component
@RequiredArgsConstructor
public class InitDb {

    private final InitService initService;

    @PostConstruct
    public void init(){
        initService.dbInit1();
        initService.dbInit2();
    }

    @Component
    @Transactional
    @RequiredArgsConstructor
    static class InitService{

        private final EntityManager em;

        public void dbInit1(){
            Member member1 = new Member();
            member1.setName("userA");
            member1.setAddress(new Address("서울","1","111"));
            em.persist(member1);

            Book book1 = new Book();
            book1.setName("JPA1 Book");
            book1.setPrice(10000);
            book1.setStockQuantity(100);
            em.persist(book1);

            Book book2 = new Book();
            book2.setName("JPA2 Book");
            book2.setPrice(20000);
            book2.setStockQuantity(100);
            em.persist(book2);

            OrderItem orderItem1 = OrderItem.createOrderItem(book1, 10000, 1);
            OrderItem orderItem2 = OrderItem.createOrderItem(book2, 20000, 2);

            Delivery delivery = new Delivery();
            delivery.setAddress(member1.getAddress());
            Order order = Order.createOrder(member1, delivery, orderItem1, orderItem2);
            em.persist(order);
        }
        public void dbInit2(){
            Member member2 = new Member();
            member2.setName("userB");
            member2.setAddress(new Address("부산","1","111"));
            em.persist(member2);

            Book book1 = new Book();
            book1.setName("Spring1 Book");
            book1.setPrice(20000);
            book1.setStockQuantity(200);
            em.persist(book1);

            Book book2 = new Book();
            book2.setName("Spring2 Book");
            book2.setPrice(40000);
            book2.setStockQuantity(300);
            em.persist(book2);

            OrderItem orderItem1 = OrderItem.createOrderItem(book1, 20000, 3);
            OrderItem orderItem2 = OrderItem.createOrderItem(book2, 40000, 4);

            Delivery delivery = new Delivery();
            delivery.setAddress(member2.getAddress());
            Order order = Order.createOrder(member2, delivery, orderItem1, orderItem2);
            em.persist(order);
        }
    }
}
