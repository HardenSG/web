package com.example.demo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import java.time.LocalDate;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.stereotype.Repository;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * <p>
 * 
 * </p>
 *
 * @author admin
 * @since 2022-03-12
 */
@Repository
@Data
  @EqualsAndHashCode(callSuper = false)
    public class User implements Serializable {

    private static final long serialVersionUID=1L;

      @TableId(value = "u_id", type = IdType.AUTO)
      private Integer uId;

    private String name;

    private String email;
    private LocalDate birthday;
    private String password;
    private String headPicture;

    private String token;



}
