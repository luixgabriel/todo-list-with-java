package br.com.luix.todolist.tasks;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/task")
public class TaskController {
    @Autowired
    private ITaskRepository taskRepository;
    
    @GetMapping("/testando")
    public ResponseEntity getName(){
       return ResponseEntity.status(HttpStatus.ACCEPTED).body("Successsed");
    }

    @PostMapping("/create")
    public ResponseEntity create(@RequestBody TaskModel taskData){
        System.out.println(taskData);
        var task = this.taskRepository.save(taskData);
        return ResponseEntity.status(HttpStatus.CREATED).body(task);
    }
}
