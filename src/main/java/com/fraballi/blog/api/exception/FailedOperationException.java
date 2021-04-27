package com.fraballi.blog.api.exception;

import com.fraballi.blog.api.util.ApiOperation;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class FailedOperationException extends RuntimeException {

   private ApiOperation operation;
}
