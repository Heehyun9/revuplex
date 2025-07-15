package com.fastcampus.shoplex.Service;

import com.fastcampus.shoplex.Repository.ItemRepository;
import com.fastcampus.shoplex.Repository.MemberRepository;
import com.fastcampus.shoplex.Repository.OrderRepository;
import com.fastcampus.shoplex.domain.*;

import com.fastcampus.shoplex.domain.Item.Item;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final MemberRepository memberRepository;
    private final ItemRepository itemRepository;

    //주문
    @Transactional
    public Long order(Long memberId, Long itemId, int count){
        //회원의 id, 아이템의 id, 만 가져오는것 --> 멤버랑 아이템 리파지토리를 위에서 가져와야 가능
        //멤버 리파지토리에서 멤버 엔티티를 찾고, 멤버 엔티티에서 아이디 가져옴

        //엔티티 조회
        Member member = memberRepository.findOne(memberId);
        Item item = itemRepository.findOne(itemId);

        //배송정보 생성
        //Order를 persist하면 OrderItem, Delivery도 같이 persist(cascade option때문에) lifecycle동일
        //cascade의 범위는 보통 Order만 OrderItem, Delivery 를 관리(private owner)하는 경우에 사용
        //그게 아니고 다른 엔티티에서도 delivery를 참조하면 사용 x --> 별도의 repo생성해야함

        Delivery delivery = new Delivery();
        delivery.setAddress(member.getAddress());

        //주문상품 생성 --> 간단하게 하나만 선택 가능하게 함
        OrderItem orderItem = OrderItem.createOrderItem(item, item.getPrice(), count);

        //주문 생성
        Order order = Order.createOrder(member, delivery, orderItem);

        //주문 저장
        orderRepository.save(order);

        return order.getId();

    }

    //취소
    @Transactional
    public void cancelOrder(Long orderId){
        //주문 엔티티 조회
        Order order = orderRepository.findOne(orderId);
        //주문 취소
        order.cancel();
    }

    //검색
//    public List<Order> findOrders(OrderSearch orderSearch){
//        return orderRepository.findAll(orderSearch);
//    }
}
