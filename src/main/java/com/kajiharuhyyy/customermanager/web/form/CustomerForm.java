package com.kajiharuhyyy.customermanager.web.form;

import com.kajiharuhyyy.customermanager.domain.CustomerRank;
import com.kajiharuhyyy.customermanager.domain.CustomerStatus;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CustomerForm {

   private Long id;

   @NotBlank(message = "名前は必須項目です")
   private String name;

   @NotBlank(message = "メールは必須項目です")
   @Email(message = "有効なメールアドレスを入力してください")
   private String email;

   @NotBlank(message = "電話番号は必須項目です")
   private String phone;

   @NotNull(message = "ランクを選択してください")
   private CustomerRank rank;

   @NotNull(message = "状態を選択してください")
   private CustomerStatus status;

   private String memo;

   @NotNull(message = "有効/無効を選択してください")
   private Boolean active = true;
}
