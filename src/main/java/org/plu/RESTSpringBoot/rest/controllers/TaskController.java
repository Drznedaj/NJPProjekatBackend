package org.plu.RESTSpringBoot.rest.controllers;

import org.plu.RESTSpringBoot.repository.TaskRepository;
import org.plu.RESTSpringBoot.entities.Task;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    private TaskRepository taskRepository;

    public TaskController(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @PostMapping
    public void addTask(@RequestBody Task task) {
        taskRepository.save(task);
    }

    @GetMapping
    public List<Task> getTasks() {
        return taskRepository.findAll();
    }

    @PutMapping("/{id}")
    public void editTask(@PathVariable long id, @RequestBody Task task) {
        Task existingTask = taskRepository.findById(id).get();
        existingTask.setDescription(task.getDescription());
        taskRepository.save(existingTask);
    }

    @DeleteMapping("/{id}")
    public void deleteTask(@PathVariable long id) {
        Task taskToDel = taskRepository.findById(id).get();
        taskRepository.delete(taskToDel);
    }
}