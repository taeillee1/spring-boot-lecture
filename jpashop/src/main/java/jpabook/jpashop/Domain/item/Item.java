package jpabook.jpashop.Domain.item;

import jpabook.jpashop.Domain.Category;
import jpabook.jpashop.Domain.CategoryItem;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn
@Getter @Setter
public abstract class Item {

    @Id
    @GeneratedValue
    @Column(name = "item_id")
    private Long id;

    private String name;
    private int price;
    private int stockQuantity;

//    @ManyToMany(mappedBy = "items")
//    private List<Category> categories = new ArrayList<>();
    @OneToMany(mappedBy = "item",fetch = FetchType.LAZY)
    private List<CategoryItem> categoryItems = new ArrayList<>();
}
