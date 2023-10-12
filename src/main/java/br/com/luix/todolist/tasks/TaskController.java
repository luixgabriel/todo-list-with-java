package br.com.luix.todolist.tasks;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.luix.todolist.utils.Utils;
import jakarta.servlet.http.HttpServletRequest;


@RestController
@RequestMapping("/task/")
public class TaskController {
    @Autowired
    private ITaskRepository taskRepository;
    
    @GetMapping("/testando")
    public ResponseEntity getName(){
       return ResponseEntity.status(HttpStatus.ACCEPTED).body("Successsed");
    }

    @PostMapping()
    public ResponseEntity create(@RequestBody TaskModel taskData, HttpServletRequest request){
        var requestId = request.getAttribute("userId");
        taskData.setIdUser((UUID) requestId);
        var currentDate = LocalDateTime.now();
        if(currentDate.isAfter(taskData.getStart_At()) || currentDate.isAfter(taskData.getEnd_At())){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Date Error");
        }
        if(taskData.getStart_At().isAfter(taskData.getEnd_At())){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Date Error");
        }
        var task = this.taskRepository.save(taskData);
            return ResponseEntity.status(HttpStatus.CREATED).body(task);
    }

    @GetMapping()
    public ResponseEntity list(HttpServletRequest request){
        var requestId = request.getAttribute("userId");
        var tasks = this.taskRepository.findByIdUser((UUID) requestId);
        return ResponseEntity.status(HttpStatus.OK).body(tasks);
    }

    @PutMapping("/{id}")
    public ResponseEntity update(@RequestBody TaskModel taskData ,HttpServletRequest request, @PathVariable UUID id){
        var taskExists = this.taskRepository.findById(id).orElse(null);
        Utils.copyNonNullProperties(taskData, taskExists);
        var task = this.taskRepository.save(taskData);
        return ResponseEntity.status(HttpStatus.OK).body(task);
    }
}
