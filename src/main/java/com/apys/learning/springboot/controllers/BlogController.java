package com.apys.learning.springboot.controllers;

import com.apys.learning.springboot.models.Post;
import com.apys.learning.springboot.repo.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
public class BlogController {
    @Autowired
    private PostRepository postRepository;

    @GetMapping("/blog")
    public String blogMain (Model model) {
        //Получаем все записи
        Iterable<Post> posts = postRepository.findAll();
        // Передаем все записи в Модель та в свою очередь на Вью, а там с помошбю Таймлифа разбираем
        model.addAttribute("posts", posts);
        return "blog-main";
    }

    @GetMapping("/blog/add")
    public String blogAdd (Model model) {
        return "blog-add";
    }

    @PostMapping("/blog/add")
    public String blogPostAdd (@RequestParam String title,
                               @RequestParam String anons,
                               @RequestParam String full_text, Model model) {

        Post post = new Post(title, anons, full_text);

        postRepository.save(post);
        return "redirect:/blog";
    }
    // Динамический параметр {...} - то есть мы еще не знаем какую страницу запросят получаем динамический параметр из URL
    @GetMapping("/blog/{id}")
    public String blogDetails (@PathVariable(value = "id") long id, Model model) {
        //Проверяем есть ли такой Id в DB
        if(!postRepository.existsById(id)) {
            return "redirect:/blog";
        }
        // Optional() - используем когда не уверены, что получим что-то при запросе
        Optional<Post> byId = postRepository.findById(id);
        List<Post> res = new ArrayList<>();
        byId.ifPresent(res::add);
        model.addAttribute("post", res);
        return "blog-details";
    }

    @GetMapping("/blog/{id}/edit")
    public String blogEdit (@PathVariable(value = "id") long id, Model model) {
        //Проверяем есть ли такой Id в DB
        if(!postRepository.existsById(id)) {
            return "redirect:/blog";
        }
        // Optional() - используем когда не уверены, что получим что-то при запросе
        Optional<Post> byId = postRepository.findById(id);
        List<Post> res = new ArrayList<>();
        byId.ifPresent(res::add);
        model.addAttribute("post", res);
        return "blog-edit";
    }

    @PostMapping("/blog/{id}/edit")
    public String blogPostUpdate (@PathVariable(value = "id") long id,
                                  @RequestParam String title,
                                  @RequestParam String anons,
                                  @RequestParam String full_text, Model model) {

        Post post = postRepository.findById(id).orElseThrow();
        post.setTitle(title);
        post.setAnons(anons);
        post.setFullText(full_text);

        postRepository.save(post);
        return "redirect:/blog";
    }

    @GetMapping("/blog/{id}/remove")
    public String blogPostRemove (@PathVariable(value = "id") long id, Model model) {
        Post post = postRepository.findById(id).orElseThrow();

        postRepository.delete(post);
        return "redirect:/blog";
    }
}
