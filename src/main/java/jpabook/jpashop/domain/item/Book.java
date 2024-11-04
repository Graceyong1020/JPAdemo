package jpabook.jpashop.domain.item;


import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@DiscriminatorValue("B") // 엔티티를 저장할 때 구분 컬럼에 입력할 값을 지정
public class Book extends Item{

    private String author;
    private String isbn;
}
