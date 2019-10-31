package com.camel.oa.model;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.enums.IdType;
import com.camel.core.entity.BasePaginationEntity;
import com.camel.core.model.SysUser;
import com.camel.oa.enums.ZsMerchantStatus;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 *
 *                       .::::.
 *                     .::::::::.
 *                    :::::::::::
 *                 ..:::::::::::'
 *              '::::::::::::'
 *                .::::::::::
 *           '::::::::::::::..
 *                ..::::::::::::.
 *              ``::::::::::::::::
 *               ::::``:::::::::'        .:::.
 *              ::::'   ':::::'       .::::::::.
 *            .::::'      ::::     .:::::::'::::.
 *           .:::'       :::::  .:::::::::' ':::::.
 *          .::'        :::::.:::::::::'      ':::::.
 *         .::'         ::::::::::::::'         ``::::.
 *     ...:::           ::::::::::::'              ``::.
 *    ```` ':.          ':::::::::'                  ::::..
 *                       '.:::::'                    ':'````..
 * < 服务实现类>
 * @author baily
 * @since 1.0
 * @date 2019-10-30
 **/
@Data
public class ZsMerchant extends BaseOaEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 客商主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 客商名称
     */
    private String name;
    /**
     * 公司负责人
     */
    private String manager;
    /**
     * 公司地址
     */
    private String addr;
    /**
     * 主营业务
     */
    private String mainBusiness;
    /**
     * 投资意向
     */
    private String intention;
    /**
     * 联系人
     */
    private String contacts;
    /**
     * 联系人职务
     */
    private String contactsPost;
    /**
     * 联系人联系方式
     */
    private String contactsPhone;
    /**
     * 来源地
     */
    private String source;
    /**
     * 商会属性
     */
    private String attribute;
    /**
     * 创建时间
     */
    private Date createdAt;
    /**
     * 创建者
     */
    @TableField(value = "creator")
    private Integer creatorId;

    @TableField(exist = false)
    private SysUser creator;

    private ZsMerchantStatus status;


    @Override
    public String toString() {
        return "ZsMerchant{" +
                ", id=" + id +
                ", name=" + name +
                ", manager=" + manager +
                ", addr=" + addr +
                ", mainBusiness=" + mainBusiness +
                ", intention=" + intention +
                ", contacts=" + contacts +
                ", contactsPost=" + contactsPost +
                ", contactsPhone=" + contactsPhone +
                ", source=" + source +
                ", attribute=" + attribute +
                ", createdAt=" + createdAt +
                ", creator=" + creator +
                "}";
    }
}
