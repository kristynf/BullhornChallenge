package com.kristyn.springbootsecurity;

import org.springframework.data.repository.CrudRepository;

import java.util.ArrayList;

public interface MessageRepository extends CrudRepository<Message, Long>{
    ArrayList<Message> findMessageByTitleIgnoreCase(String title);
    ArrayList<Message> findMessageByContentIgnoreCase(String content);
    ArrayList<Message> findMessageByDateIgnoreCase(String date);
}