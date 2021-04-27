package com.fraballi.blog.api.dto;

import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.Range;
import org.springframework.validation.annotation.Validated;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Validated
@Data
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class PostDTO {

   @Range(min = 1)
   private int id;

   @Range(min = 1)
   private int topicId;

   @NotBlank
   private String title;

   @NotBlank
   private String text;
}
