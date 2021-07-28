package com.zerone.secondhandmarket.message;
//前端发送来的物品过滤条件

import com.zerone.secondhandmarket.enums.ItemCheckCondition;
import com.zerone.secondhandmarket.enums.ItemType;
import com.zerone.secondhandmarket.enums.Ordering;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data               //生成get()、set()
@AllArgsConstructor //全参构造函数
@NoArgsConstructor  //无参构造函数
//select
public class ItemFilter {
    private Integer seller = null;
    private String keyword = null;
    private ItemType type = null;
    private String[] tags = null;
    private Ordering priceOrdering = Ordering.DEFAULT;
    private Ordering quantityOrdering = Ordering.DEFAULT;
    private ItemCheckCondition checkCondition = null;
    private boolean notEmpty = false;
    private boolean isInShop = false;
    private boolean imagesNeeded = false;
    private boolean tagsNeeded = false;
    private Integer page = null;
}
