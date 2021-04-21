package cn.iocoder.springboot.lab72.bean;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.io.Serializable;

@Data
@ToString
@EqualsAndHashCode
//@ApiModel(value = "查询分类列表入参")
public class FindClassifyDTO implements Serializable {

//    @ApiModelProperty(value = "主键id")
    private Long id;

//    @ApiModelProperty(value = "分类类型1 （服务）2 课程  3 （实物）4 (协议)")
    /*@NotNull(message = "商品分类不能为空")*/
    private Integer goodsCategoryId;

}
