package com.kristyn.springbootsecurity;

import com.cloudinary.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

import javax.validation.Valid;

@Controller
public class HomeController {
    @Autowired
    private UserService userService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    MessageRepository messageRepository;

    @Autowired
    CloudinaryConfig cloudc;

    @RequestMapping("/")
    public String index(Model model){
        model.addAttribute("categories", categoryRepository.findAll());
        model.addAttribute("messages",messageRepository.findAll());
        model.addAttribute("users", userRepository.findAll());
        return "index";
    }
    @GetMapping("/addcategory")
    public String newCategory(Model model) {

        model.addAttribute("category", new Category());

        return "categoryform";
    }

    @PostMapping("/processcategory")
    public String processCategory(@ModelAttribute Category category) {
        categoryRepository.save(category);
        return "redirect:/addmessage";
    }

    @PostMapping("/updatecategory")
    public String updateCompany(@ModelAttribute Category category) {
        categoryRepository.save(category);
        return "redirect:/";
    }


   @GetMapping("/addmessage")
    public String newMessage(Model model) {
        model.addAttribute("categories", categoryRepository.findAll());
        model.addAttribute("message", new Message());
        return "messageform";
    }
    @PostMapping("/processmessage")
    public String processMessage(@ModelAttribute Message message, @RequestParam("file") MultipartFile file){
        if(file.isEmpty()){
            return "redirect:/messageform";
        }
        try {
            Map uploadResult = cloudc.upload(file.getBytes(),
                    ObjectUtils.asMap("resourcetype", "auto"));
            message.setPhoto(uploadResult.get("url").toString());
            messageRepository.save(message);
        } catch (IOException e) {
            e.printStackTrace();
            return "redirect:/messageform";
        }
        return "redirect:/";
    }

   /* @PostMapping("/processcar")
    public String processCar(@ModelAttribute Car car) {
        carRepository.save(car);
        return "redirect:/";
    }*/

    /*@RequestMapping("/search")
    public String search(@RequestParam("search") String search, Model model) {
        model.addAttribute("categorySearch", categoryRepository.findByNameIgnoreCase(search));
        model.addAttribute("messageSearch1", messageRepository.findMessageByTitleIgnoreCase(search));
        model.addAttribute("messageSearch2", messageRepository.findMessageByContentIgnoreCase(search));
        model.addAttribute("messageSearch3", messageRepository.findMessageByDateIgnoreCase(search));
        model.addAttribute("usersearch", userRepository.findByUsername(search));
        return "list";
    }*/

    @RequestMapping("detail/{id}")
    public String showCategory(@PathVariable("id") long id, Model model) {
        model.addAttribute("category", categoryRepository.findById(id).get());
        model.addAttribute("users", userRepository.findAll());
        return "showcategory";
    }

    @RequestMapping("detailc/{id}")
    public String showMessage(@PathVariable("id") long id, Model model) {
        model.addAttribute("message", messageRepository.findById(id).get());
        model.addAttribute("categories", categoryRepository.findAll());
        model.addAttribute("users", userRepository.findAll());
        return "showmessage";
    }

 /*   @RequestMapping("update/{id}")
    public String updateCategory(@PathVariable("id") long id, Model model) {
        model.addAttribute("category", categoryRepository.findById(id).get());
        return "categoryform";
    }*/

    @RequestMapping("updatec/{id}")
    public String updateMessage(@PathVariable("id") long id, Model model) {
        model.addAttribute("message", messageRepository.findById(id).get());
        model.addAttribute("categories", categoryRepository.findAll());
        model.addAttribute("users", userRepository.findAll());
        return "messageform";
    }

    @RequestMapping("delete/{id}")
    public String deleteCategory(@PathVariable("id") long id, Model model) {
        categoryRepository.deleteById(id);
        return "redirect:/";
    }

    @RequestMapping("deletec/{id}")
    public String deleteMessage(@PathVariable("id") long id, Model model) {
        messageRepository.deleteById(id);
        return "redirect:/";
    }

    @RequestMapping("/listmessages")
    public String listMessages(Model model) {
        model.addAttribute("messages", messageRepository.findAll());
        model.addAttribute("categories", categoryRepository.findAll());
        model.addAttribute("users", userRepository.findAll());
        return "showmessages";
    }
    @RequestMapping("/category/{id}")
    public String displayCat(@PathVariable("id") long id, Model model) {
        model.addAttribute("category", categoryRepository.findById(id).get());
        model.addAttribute("messages", messageRepository.findAll());
        return "listcategory";
    }

    @GetMapping("/register")
       public String showRegistrationPage(Model model){
           model.addAttribute("user", new User());
           return "registration";
        }

     @PostMapping("/register")
     public String processRegistrationPage(@Valid @ModelAttribute("user") User user, BindingResult result, Model model){
        model.addAttribute("user", user);
        if(result.hasErrors()){
            return "registration";
        }
        else{
            userService.saveUser(user);
            model.addAttribute("message", "User Account Created");
        }

        return "index";
        }


    @RequestMapping("/login")
    public String login(){
        return "login";
    }

}
