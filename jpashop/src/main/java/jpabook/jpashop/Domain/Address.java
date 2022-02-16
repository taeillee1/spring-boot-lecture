package jpabook.jpashop.Domain;

import lombok.Getter;

import javax.persistence.Embeddable;

@Embeddable
@Getter
public class Address {

    private String city;
    private String street;
    private String zipcode;

    protected Address() {
    }
    public Address(String city, String street, String zipcode) {
        this.city = city;
        this.street = street;
        this.zipcode = zipcode;
    }
    //값 타입은 변경불가능하게 설계해야하기 떄문에 setter 를 사용하지말고 변경불가능한 클래스를 하나만드는것이좋다.
}
