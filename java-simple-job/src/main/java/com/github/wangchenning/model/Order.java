package com.github.wangchenning.model;

import lombok.Data;
import lombok.ToString;

/**
 * @author Musk
 */
@Data
@ToString
public class Order {
    private Integer id;
    /**
     * 1已处理
     */
    private Integer status;
}
