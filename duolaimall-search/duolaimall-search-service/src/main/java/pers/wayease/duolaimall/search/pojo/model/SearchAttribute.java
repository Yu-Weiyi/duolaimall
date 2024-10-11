package pers.wayease.duolaimall.search.pojo.model;

import lombok.Data;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

/**
 * @author 为伊WaYease <a href="mailto:yu_weiyi@outlook.com">yu_weiyi@outlook.com</a>
 * @version 1.0
 * @project duolaimall
 * @package pers.wayease.duolaimall.search.pojo.model
 * @name SearchAttribute
 * @description Search attribute class.
 * @since 2024-10-10 16:35
 */
@Data
public class SearchAttribute {

    @Field(type = FieldType.Long)
    private Long attrId;
    @Field(type = FieldType.Keyword)
    private String attrValue;
    @Field(type = FieldType.Keyword)
    private String attrName;
}
