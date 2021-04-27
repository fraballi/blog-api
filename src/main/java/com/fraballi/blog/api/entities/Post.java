package com.fraballi.blog.api.entities;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Data
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@ToString(exclude = { "topic" })
public class Post {

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   @Column(length = 11)
   private int id;

   @ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
   @JoinColumn(name = "topic_id", columnDefinition = "int(11)", nullable = false)
   @JsonIgnore
   private Topic topic;

   @Column(length = 16536, nullable = false)
   private String title;

   @Column(length = 16536, nullable = false)
   private String text;
}
