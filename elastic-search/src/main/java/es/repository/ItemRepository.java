package es.repository;

import es.pojo.Item;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

/**
 * @author amos
 * 与你同在
 * @create 2019/6/3 - 15:50
 */

public interface ItemRepository extends ElasticsearchRepository<Item, Long> {

    List<Item> findByPriceBetween(Double begin, Double end);
}
