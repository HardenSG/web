package com.example.demo.entity;

import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 
 * </p>
 *
 * @author admin
 * @since 2022-03-24
 */
@Data
  @EqualsAndHashCode(callSuper = false)
    public class Follow implements Serializable {

    private static final long serialVersionUID = 1L;

      private String uEmail;

      private int fId;

  public Follow() {
  }

  public Follow(String uEmail, String followEmail) {
    this.uEmail = uEmail;
    this.followedEmail = followEmail;
  }

  private String followedEmail;


}
