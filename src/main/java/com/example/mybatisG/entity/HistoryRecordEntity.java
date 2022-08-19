package com.example.mybatisG.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 用户搜索历史记录
 * </p>
 *
 * @author 郭旗
 * @since 2022-08-19
 */
@Getter
@Setter
@TableName("tb_yx_history_record")
public class HistoryRecordEntity {

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 用户userId
     */
    private Long yxUserId;

    /**
     * 历史记录信息
     */
    private String record;

    /**
     * 权重值/排序
     */
    private Integer weights;

    /**
     * 是否删除
     */
    private Integer deleted;

    /**
     * 创建时间
     */
    private Long createTime;

    /**
     * 更新时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Long updateTime;


}
