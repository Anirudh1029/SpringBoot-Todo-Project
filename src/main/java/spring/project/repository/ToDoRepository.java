package spring.project.repository;

import org.springframework.stereotype.Repository;
import spring.project.entity.ToDo;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class ToDoRepository {

    private final List<ToDo> todoList = new ArrayList<>();
    private final AtomicLong idCounter = new AtomicLong(1);

    // Save a new ToDo item
    public ToDo save(ToDo todo) {
        if (todo.getId() == 0) {
            todo.setId(idCounter.getAndIncrement());
        }
        todoList.add(todo);
        return todo;
    }

    // Find all ToDo items
    public List<ToDo> findAll() {
        return todoList;
    }

    // Find a ToDo item by ID
    public Optional<ToDo> findById(long id) {
        return todoList.stream().filter(todo -> todo.getId() == id).findFirst();
    }

    // Delete a ToDo item by ID and reassign IDs
    public void deleteById(long id) {
        // Remove the item with the given ID
        todoList.removeIf(todo -> todo.getId() == id);
        
        // Reassign IDs sequentially
        long newId = 1;
        for (ToDo todo : todoList) {
            todo.setId(newId++);
        }
        
        // Reset the ID counter
        idCounter.set(newId);
    }
}


