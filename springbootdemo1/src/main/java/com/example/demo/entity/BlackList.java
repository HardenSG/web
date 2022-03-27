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
 * @author admin
 * @since 2022-03-27
 */
@Data
  @EqualsAndHashCode(callSuper = false)
    public class BlackList implements Serializable {

    private static final long serialVersionUID = 1L;

      @TableId(value = "b_id", type = IdType.AUTO)
      private Integer bId;

    private String uEmail;

    private String blackEmail;

  public BlackList(String uEmail, String blackEmail) {
    this.uEmail = uEmail;
    this.blackEmail = blackEmail;
  }

  public BlackList() {
  }
}
