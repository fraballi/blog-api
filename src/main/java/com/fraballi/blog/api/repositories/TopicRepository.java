package com.fraballi.blog.api.repositories;

import static com.fraballi.blog.api.constants.Constants.DEFAULT_PAGE_NUMBER;
import static com.fraballi.blog.api.constants.Constants.DEFAULT_PAGE_SIZE;
import static java.util.stream.Collectors.toList;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.fraballi.blog.api.dto.TopicDTO;
import com.fraballi.blog.api.entities.Topic;

@Repository
public class TopicRepository {

   @PersistenceContext
   private EntityManager em;

   public Optional<TopicDTO> get(final int id) {
      final Optional<Topic> topic = find(id);
      return topic.map(t -> TopicDTO.builder().id(t.getId()).name(t.getName()).build());
   }

   Optional<Topic> find(final int id) {
      final Topic topic = em.find(Topic.class, id);
      return Optional.ofNullable(topic);
   }

   public Collection<TopicDTO> getAll() {
      final List<Topic> topics = em.createQuery("select t from Topic t", Topic.class).getResultList();
      return topics.stream().map(t -> TopicDTO.builder().id(t.getId()).name(t.getName()).build()).collect(toList());
   }

   public Collection<TopicDTO> getPaginated(Optional<Integer> pageNumber, Optional<Integer> pageSize) {
      final int size = pageSize.orElse(DEFAULT_PAGE_SIZE);
      final List<Topic> topics = em
            .createQuery("select t from Topic t", Topic.class)
            .setFirstResult((pageNumber.orElse(DEFAULT_PAGE_NUMBER) - 1) * size)
            .setMaxResults(size)
            .getResultList();

      return topics.stream().map(t -> TopicDTO.builder().id(t.getId()).name(t.getName()).build()).collect(Collectors.toList());
   }

   @Transactional
   public void save(final TopicDTO dto) {
      final Topic entity = Topic.builder().name(dto.getName()).build();
      em.persist(entity);
   }

   @Transactional
   public boolean update(final int id, final TopicDTO dto) {
      final Optional<Topic> topic = find(id);
      return topic.map(t -> {
         t.setName(dto.getName());
         em.persist(t);

         return true;
      }).orElse(false);
   }

   @Transactional
   public boolean delete(final int id) {
      final Optional<Topic> topic = find(id);
      return topic.map(t -> {
         em.remove(t);
         em.flush();

         return true;
      }).orElse(false);
   }
}
