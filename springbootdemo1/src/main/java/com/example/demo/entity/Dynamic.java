package com.example.demo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 
 * </p>
 *
 * @author admin
 * @since 2022-03-19
 */
@Data
  @EqualsAndHashCode(callSuper = false)
    public class Dynamic implements Serializable {

    private static final long serialVersionUID=1L;

      @TableId(value = "d_id", type = IdType.AUTO)
      private Integer dId;

    private String email;

    private String content;

    private String picture;

    private LocalDateTime date;

    private Integer likes;

    private Integer forwardCount;

    private Integer commentCount;

    private Integer originalId;

    private String forwardComment;


}
