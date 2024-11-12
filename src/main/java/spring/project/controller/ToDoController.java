package spring.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import spring.project.entity.ToDo;
import spring.project.repository.ToDoRepository;
import java.util.Optional;

/**
 * @Author: apple
 * @created on 30/04/2020
 * @Project is SpringSimpleTodo
 */
@Controller
public class ToDoController {

    @Autowired
    private ToDoRepository todoRepository;

    @GetMapping("")
    public String index(){
        return "index";
    }

    @GetMapping("/logout")
    public String logoutPage(){
        return "logout";
    }

    @GetMapping("/todos")
    public String todos(Model model){
        model.addAttribute("todos", todoRepository.findAll());
        return "todos";
    }

    @PostMapping("/todoNew")
    public String add(@RequestParam String todoItem, @RequestParam String status, Model model) {
        ToDo todo = new ToDo();
        todo.setTodoItem(todoItem);
        todo.setCompleted(status);
        todoRepository.save(todo); // Adds the ToDo item with an auto-incremented ID
        model.addAttribute("todos", todoRepository.findAll());
        return "redirect:/todos";
    }


    @PostMapping("/todoDelete/{id}")
    public String delete(@PathVariable long id, Model model) {
       todoRepository.deleteById(id);
       model.addAttribute("todos", todoRepository.findAll());
       return "redirect:/todos";
    }

    @PostMapping("/todoUpdate/{id}")
    public String update(@PathVariable long id, Model model) {
    // Find the existing ToDo item
    Optional<ToDo> optionalToDo = todoRepository.findById(id);
    
    if (optionalToDo.isPresent()) {
        ToDo toDo = optionalToDo.get();
        
        // Toggle the 'completed' status
        if ("Yes".equals(toDo.getCompleted())) {
            toDo.setCompleted("No");
        } else {
            toDo.setCompleted("Yes");
        }
        
        // No need to call save(); the item is updated in place
    }
    
    model.addAttribute("todos", todoRepository.findAll());
    return "redirect:/todos";
}

}
