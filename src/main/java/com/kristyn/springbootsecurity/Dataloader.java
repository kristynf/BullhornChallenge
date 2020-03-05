package com.kristyn.springbootsecurity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

//import javax.management.relation.Role;

@Component
public class Dataloader implements CommandLineRunner {
    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    MessageRepository messageRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    public void run (String... strings) throws Exception{
      roleRepository.save(new Role("USER"));
      roleRepository.save(new Role("ADMIN"));
       Role adminRole = roleRepository.findByRole("ADMIN");
       Role userRole = roleRepository.findByRole("USER");

       User user = new User("kristyn@kristyn.com", "password", "Kristyn", "Ferber", true, "kristyn");
       user.setRoles(Arrays.asList(userRole));
       userRepository.save(user);

       User user1 = new User("sara@sara.com", "password", "Sara", "Smith", true, "sara");
       user.setRoles(Arrays.asList(userRole));
       userRepository.save(user1);

        User user0 = new User("admin@admin.com", "password", "Admin", "User", true, "admin");
        user.setRoles(Arrays.asList(adminRole));
        userRepository.save(user0);

        Category category = new Category();
        category.setName("Life Events");

        Message message = new Message();
        message.setTitle("New Job");
        message.setContent("I got a new job");
        message.setDate("03/01/2020 1300");
        message.setPhoto("https://res.cloudinary.com/kristynf/image/upload/v1583422792/find-new-job_vouxat.jpg");
        message.setUser(user);


        Message message1 = new Message();
        message1.setTitle("New Car");
        message1.setContent("I got a new car");
        message1.setDate("03/01/2020 0851");
        message1.setPhoto("https://res.cloudinary.com/kristynf/image/upload/v1582923471/subaruforester_rwn0g6.jpg");
        message1.setUser(user1);

        Message message2 = new Message();
        message2.setTitle("New Dog");
        message2.setContent("I got a new dog");
        message2.setDate("03/02/2020 0952");
        message2.setPhoto("https://res.cloudinary.com/kristynf/image/upload/v1582130633/IMG_20200215_211646_mpruls.jpg");
        message2.setUser(user);

        Set<Message> messages = new HashSet<>();
        messages.add(message);
        messages.add(message1);
        messages.add(message2);
        category.setMessages(messages);
        categoryRepository.save(category);


        message.setCategory(category);
        messageRepository.save(message);


        Category category1 = new Category();
        category1.setName("General updates");

        Message message3 = new Message();
        message3.setTitle("Programming Life");
        message3.setContent("Programming is hard because people are involved");
        message3.setDate("03/03/2020 1002");
        message3.setPhoto("https://res.cloudinary.com/kristynf/image/upload/v1582130633/IMG_20200113_225103_nly5qn.jpg");
        message3.setUser(user1);

        Message message4 = new Message();
        message4.setTitle("Life");
        message4.setContent("Life is good");
        message4.setDate("03/04/2020 0941");
        message4.setPhoto("https://res.cloudinary.com/kristynf/image/upload/v1583422786/lifegood_mjnql5.jpg");
        message4.setUser(user);

        messages = new HashSet<>();
        messages.add(message3);
        messages.add(message4);

        category.setMessages(messages);
        categoryRepository.save(category1);

        message.setCategory(category);
        message1.setCategory(category);
        message2.setCategory(category);
        message3.setCategory(category1);
        message4.setCategory(category1);
        messageRepository.save(message);
        messageRepository.save(message1);
        messageRepository.save(message2);
        messageRepository.save(message3);
        messageRepository.save(message4);

    }
}
