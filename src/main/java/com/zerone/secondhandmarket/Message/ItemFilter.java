package com.zerone.secondhandmarket.Message;
//前端发送来的物品过滤条件
import com.zerone.secondhandmarket.Message.Enum.CheckCondition;
import com.zerone.secondhandmarket.Message.Enum.Ordering;
import com.zerone.secondhandmarket.entity.ItemType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data               //生成get()、set()
@AllArgsConstructor //全参构造函数
@NoArgsConstructor  //无参构造函数
//select
public class ItemFilter {
    private Integer seller = null;
    private ItemType type = null;
    private String keyWords = null;
    private Ordering priceOrdering = Ordering.DEFAULT;
    private Ordering quantityOrdering = Ordering.DEFAULT;
    private CheckCondition checkCondition = null;
}
