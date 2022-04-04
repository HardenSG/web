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
 * @since 2022-04-04
 */
@Data
  @EqualsAndHashCode(callSuper = false)
    public class Forward implements Serializable {

    private static final long serialVersionUID = 1L;

      @TableId(value = "forward_id", type = IdType.AUTO)
      private Integer forwardId;

    private String dId;

    private String email;

    private LocalDateTime forwardDate;

    private Integer forwardRead;


}
