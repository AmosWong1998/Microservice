package es.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

/**
 * @author amos
 * 与你同在
 * @create 2019/6/3 - 12:49
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(indexName = "test1", shards = 3, replicas = 1)
public class Item {

    @Field(type = FieldType.Long)
    @Id
    private Long id;

    @Field(type = FieldType.Text, analyzer = "ik_smart")
    private String title;

    @Field(type = FieldType.Keyword)
    private String category;

    @Field(type = FieldType.Keyword)
    private String brand;

    @Field(type = FieldType.Double)
    private Double price;

    @Field(type = FieldType.Keyword, index = false)
    private String image;
}
