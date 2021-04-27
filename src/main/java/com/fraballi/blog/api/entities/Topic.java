package com.fraballi.blog.api.entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Topic {

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   @Column(length = 11)
   private int id;

   @Column(length = 255, nullable = false)
   private String name;

   @OneToMany(mappedBy = "topic", cascade = CascadeType.ALL)
   private List<Post> posts = new ArrayList<>();
}
