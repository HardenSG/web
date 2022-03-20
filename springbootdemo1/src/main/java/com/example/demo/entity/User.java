package com.example.demo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import java.time.LocalDate;
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
 * @author admin
 * @since 2022-03-12
 */
@Data
  @EqualsAndHashCode(callSuper = false)

    public class User implements Serializable {

    private static final long serialVersionUID=1L;

      @TableId(value = "u_id", type = IdType.AUTO)
      private Integer uId;

    private String name;

    private String email;
  @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date birthday;
    private String password;
    private String headPicture;
    private String sex;
    private String introduction;
    private String schoolName;
    private String schoolState;
    private  String schoolTime;



}
