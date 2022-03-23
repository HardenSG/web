package com.example.demo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 
 * </p>
 *
 * @author no
 * @since 2022-03-21
 */
@Data
  @EqualsAndHashCode(callSuper = false)
    public class Topic implements Serializable {

    private static final long serialVersionUID = 1L;

      @TableId(value = "t_id", type = IdType.AUTO)
      private Integer tId;

    private String content;

    private Integer hot;

    String picture;

  public Topic(String content, Integer hot, String picture) {
    this.content = content;
    this.hot = hot;
    this.picture = picture;
  }

  public Topic() {
  }
}