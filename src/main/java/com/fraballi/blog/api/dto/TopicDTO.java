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
public class TopicDTO {

   @Range(min = 1)
   private int id;

   @NotBlank
   private String name;
}
