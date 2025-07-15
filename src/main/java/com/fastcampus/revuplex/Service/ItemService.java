package com.fastcampus.revuplex.Service;

import com.fastcampus.revuplex.Repository.ItemRepository;
import com.fastcampus.revuplex.domain.Item.Item;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
//repository에 단순히 위임하는 service
//테스트는 직접 해보기(단순하기때문)

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;

    @Transactional
    public void saveItem(Item item) {
        itemRepository.save(item);
    }

    public List<Item> findItems(){
        return itemRepository.findAll();
    }

    public Item findOne(Long itemId){
        return itemRepository.findOne(itemId);
    }
}
