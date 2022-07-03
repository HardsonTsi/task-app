package com.hardtech.securityservice.tasks.services;

import com.hardtech.securityservice.tasks.entities.Task;
import com.hardtech.securityservice.tasks.repositories.TaskRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class TaskService {
    private final TaskRepository taskRepository;

    public Task saveTask(Task task) {
        return taskRepository.save(task);
    }

    public List<Task> findAllTasks(){
        return taskRepository.findAll();
    }

    public List<Task> findAllUserTasks() {
        Authentication currentUser = SecurityContextHolder.getContext().getAuthentication();
        return taskRepository.findAllByUsername(currentUser.getName());
    }

    public Task getTaskById(Long id) {
        return taskRepository.findById(id).orElse(null);
    }

    public void deleteTask(Long id) {
        taskRepository.deleteById(id);
    }

    public Task updateTask(Task task) {
        return taskRepository.save(task);
    }

    public Task makeTask(Task task) {
        task.setStatus(!task.isStatus());
        return taskRepository.save(task);
    }

    public List<Task> search(String name, String description) {
        return taskRepository.getByNameLikeOrDescriptionLike("%" + name + "%", "%" + description + "%");
    }
}
