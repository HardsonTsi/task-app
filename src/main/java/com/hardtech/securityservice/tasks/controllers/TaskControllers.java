package com.hardtech.securityservice.tasks.controllers;

import com.hardtech.securityservice.tasks.entities.Task;
import com.hardtech.securityservice.tasks.services.TaskService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/tasks")
@CrossOrigin("*")
public class TaskControllers {

    private final TaskService taskService;

    @GetMapping("/all")
    public List<Task> getUserTasks() {
        return taskService.findAllUserTasks();
    }

    @GetMapping("/alltasks")
    public List<Task> findAllTasks() {
        return taskService.findAllTasks();
    }

    @GetMapping("/{id}")
    public Task getTask(@PathVariable Long id) {
        return taskService.getTaskById(id);
    }

    @PostMapping("/save")
    public Task saveTask(@RequestBody Task task) {
        return taskService.saveTask(task);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteTask(@PathVariable Long id) {
        taskService.deleteTask(id);
    }

    @PutMapping("/update")
    public Task updateTask(@RequestBody Task task) {
        return taskService.updateTask(task);
    }

    @PutMapping("/maketask")
    public Task makeTask(@RequestBody Task task){
        return taskService.makeTask(task);
    }

    @GetMapping("/search")
    public List<Task> search(@RequestParam(defaultValue = "") String name, @RequestParam(defaultValue = "") String description){
        return taskService.search(name, description);
    }
}
