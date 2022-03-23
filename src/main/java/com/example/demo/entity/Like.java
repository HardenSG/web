package com.example.demo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 
 * </p>
 *
 * @author Chu
 * @since 2022-03-18
 */
@TableName("`like`")
@Data
  @EqualsAndHashCode(callSuper = false)
    public class Like implements Serializable {

    private static final long serialVersionUID=1L;

      @TableId(value = "like_id", type = IdType.AUTO)
      private Integer likeId;

    private Integer dId;

    private String email;

  public Like() {
  }

  public Like(Integer dId, String email) {
    this.dId = dId;
    this.email = email;
  }
}
