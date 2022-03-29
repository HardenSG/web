package com.example.demo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import java.util.Date;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * <p>
 * 
 * </p>
 *
 * @author Chu
 * @since 2022-03-19
 */
   @Data
  @EqualsAndHashCode(callSuper = false)
    public class Comments implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer dId;

    private String email;

    private String comment;

    @TableField(exist=false)
    private String name;

    @TableField(exist=false)
    private String headPhoto;

    private Date commentDate;

      @TableId(value = "comment_id", type = IdType.AUTO)
      private Integer commentId;

      private Date commentDate;
      private int commentRead;

    public Comments(){ }

  public Comments(Integer dId, String email, String comment , Date commentDate) {
    this.dId = dId;
    this.email = email;
    this.comment = comment;
    this.commentDate = commentDate;
  }


}
