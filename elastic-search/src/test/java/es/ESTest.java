package es;

import es.pojo.Item;
import es.repository.ItemRepository;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.aggregations.AbstractAggregationBuilder;
import org.elasticsearch.search.aggregations.Aggregation;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.aggregations.bucket.terms.ParsedStringTerms;
import org.elasticsearch.search.aggregations.bucket.terms.StringTerms;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.aggregation.AggregatedPage;
import org.springframework.data.elasticsearch.core.query.*;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

/**
 * @author amos
 * 与你同在
 * @create 2019/6/3 - 12:54
 */

@RunWith(SpringRunner.class)
@SpringBootTest
public class ESTest {

    @Autowired
    private ElasticsearchTemplate template;

    @Autowired
    private ItemRepository itemRepository;

    @Test
    public void testSave(){
        //创建索引库
        template.createIndex(Item.class);
        //创建映射关系
        template.putMapping(Item.class);
    }


    @Test
    public void indexList() {
        List<Item> list = new ArrayList<>();
        list.add(new Item(1L, "小米手机7", "手机", "小米", 3299.00, "http://image.leyou.com/13123.jpg"));
        list.add(new Item(2L, "坚果手机R1", "手机", "锤子", 3699.00, "http://image.leyou.com/13123.jpg"));
        list.add(new Item(3L, "华为META10", "手机", "华为", 4499.00, "http://image.leyou.com/13123.jpg"));
        list.add(new Item(4L, "小米Mix2S", "手机", "小米", 4299.00, "http://image.leyou.com/13123.jpg"));
        list.add(new Item(5L, "荣耀V10", "手机", "华为", 2799.00, "http://image.leyou.com/13123.jpg"));
        // 接收对象集合，实现批量新增
        itemRepository.saveAll(list);
    }

    @Test
    public void testFind(){
        Iterable<Item> items = itemRepository.findAll();
        for (Item item : items) {
            System.out.println(item);
        }
    }

    @Test
    public void testFindBy(){
        List<Item> items = itemRepository.findByPriceBetween(2000d, 4000d);
        for (Item item : items) {
            System.out.println("item = " + item);
        }
    }

    /**
     * 测试原生查询
     */
    @Test
    public void testQuery(){
        NativeSearchQueryBuilder builder = new NativeSearchQueryBuilder();
        //添加过滤条件 只显示"images", "title", "price"三个字段
        builder.withSourceFilter(new FetchSourceFilter(new String[]{"images", "title", "price"}, null));
        //添加查询条件
        builder.withQuery(QueryBuilders.matchQuery("title", "小米手机"));
        //对查询进行排序:按照价格降序
        builder.withSort(SortBuilders.fieldSort("price").order(SortOrder.DESC));
        //进行分页 从第二页开始显示 每一页显示两条数据 注意第一个参数从0开始不是从1开始
        builder.withPageable(PageRequest.of(1, 2));

        Page<Item> result = itemRepository.search(builder.build());
        long totalElements = result.getTotalElements();
        System.out.println("totalElements = " + totalElements);
        System.out.println("result.getTotalPages() = " + result.getTotalPages());
        List<Item> content = result.getContent();
        for (Item item : content) {
            System.out.println(item);
        }

    }

    @Test
    public void testAgg() {

        NativeSearchQueryBuilder builder = new NativeSearchQueryBuilder();
        //创建一个聚合
        builder.addAggregation(AggregationBuilders.terms("popularBrand").field("brand"));
        //查询并返回聚合结果
        AggregatedPage<Item> items = template.queryForPage(builder.build(), Item.class);
        //解析聚合结果

        //获取聚合结果
        Aggregations aggs = items.getAggregations();
        //获取指定名称的聚合
        StringTerms terms = aggs.get("popularBrand");
        //获取桶
        List<StringTerms.Bucket> buckets = terms.getBuckets();
        for (StringTerms.Bucket bucket : buckets) {
            System.out.println("bucket.getKeyAsString() = " + bucket.getKeyAsString());
            System.out.println("bucket.count() = " + bucket.getDocCount());
            System.out.println("bucket.key() = " + bucket.getKey());

        }
    }

}
