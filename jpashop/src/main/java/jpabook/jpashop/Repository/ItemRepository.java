package jpabook.jpashop.Repository;

import jpabook.jpashop.Domain.item.Item;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class ItemRepository {

    private final EntityManager em;

    public void save(Item item){
        if(item.getId() == null){ //새로생성하는 객체이면 디비에 저장
            em.persist(item);
        }else{ //이미 있는 즉 수정시에는 업데이트를 하는 것?
            em.merge(item);
        }
    }

    public Item findOne(Long id){
        return em.find(Item.class,id);
    }

    public List<Item> findALl(){
        return em.createQuery("select i from Item i", Item.class)
                .getResultList();
    }
}
