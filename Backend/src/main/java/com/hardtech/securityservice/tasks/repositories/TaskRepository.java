package com.hardtech.securityservice.tasks.repositories;

import com.hardtech.securityservice.tasks.entities.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {

    List<Task> findAllByUsername(String username);

    List<Task> getByNameLikeOrDescriptionLike(String name, String description);

}
