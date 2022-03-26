package com.example.demo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import java.time.LocalDateTime;
import java.util.Date;
import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.TableField;
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
 * @since 2022-03-15
 */
@TableName(value = "dynamic")
  @EqualsAndHashCode(callSuper = false)
@Data
    public class Dynamic  {


    private String content;

    private String picture;

  public void setLikes(Integer likes) {
    this.likes = likes;
  }

  private Integer likes = 0;

    private Date date;

      @TableId(value = "d_id", type = IdType.AUTO)
      private Integer dId;

  private String email;

  private Integer forwardCount = 0;

    private Integer commentCount = 0;

    private  Integer originalId;
    private  Integer tId ;

    private String forwardComment;

  @TableField(exist=false)
    private String topContent;

    public Dynamic(){ }

    public Dynamic(String email , String conetent, String picture, Date date ,Integer dId, String content){
      this.date = date;
      this.content = conetent;
      this.picture = picture;
      this.email = email;
      this.content = content;
      this.dId = dId;
    }


}
