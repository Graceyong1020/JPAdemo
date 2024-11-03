package jpabook.japshop.repository;


import jakarta.persistence.EntityManager;
import jpabook.japshop.domain.item.Item;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ItemRepository {

    private final EntityManager em;

    public void save(Item item){
        if(item.getId() == null){ // 아이템이 새로 생성된 객체라면 persist를 사용하여 영속성 컨텍스트에 저장
            em.persist(item);
        } else {
            em.merge(item); // 이미 DB에 저장된 객체라면 merge를 사용하여 업데이트
        }
    }
    // 아이템 조회
    public Item findOne(Long id){
        return em.find(Item.class, id);
    }
    // 아이템 목록 조회
    public List<Item> findAll(){
        return em.createQuery("select i from Item i", Item.class)
                .getResultList();
    }
}
