package com.example.demo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * 
 * </p>
 *
 * @author admin
 * @since 2022-03-29
 */
@Data
  @EqualsAndHashCode(callSuper = false)

    public class DynamicPicture implements Serializable {

    private static final long serialVersionUID = 1L;

      @TableId(value = "p_id", type = IdType.AUTO)
      private Integer pId;

    private Integer dId;

    private String picture;


  public DynamicPicture(Integer pId, Integer dId, String picture) {
    this.pId = pId;
    this.dId = dId;
    this.picture = picture;
  }

  public DynamicPicture() {
  }
}
