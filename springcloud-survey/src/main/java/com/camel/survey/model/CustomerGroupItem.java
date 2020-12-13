package com.camel.survey.model;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.enums.IdType;
import lombok.Data;

/**
 * <p>
 * 客户信息
 * </p>
 *
 * @author baily
 * @since 2020-12-13
 */
@Data
public class CustomerGroupItem extends Customer {

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private static final long serialVersionUID = 1L;
    /**
     * 所属打捆分组
     */
    private Integer groupId;

    public CustomerGroupItem() {
    }

    public CustomerGroupItem(Customer customer, Integer groupId) {
        setTel(customer.getTel());
        setUsername(customer.getUsername());
        setCompany(customer.getCompany());
        setPosition(customer.getPosition());
        setDept(customer.getDept());
        setCity(customer.getCity());
        setCounty(customer.getCounty());
        setStreet(customer.getStreet());
        setIndustry(customer.getIndustry());
        setLiabler(customer.getLiabler());
        setLiablerTel(customer.getLiablerTel());
        setOpen(customer.getOpen());
        this.groupId = groupId;
    }

    @Override
    public String toString() {
        return "CustomerGroupItem{" +
                "}";
    }
}
