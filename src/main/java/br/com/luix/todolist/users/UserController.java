package br.com.luix.todolist.users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import at.favre.lib.crypto.bcrypt.BCrypt;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private IUserRepositoy userRepositoy;

    @PostMapping("/")
    public ResponseEntity create(@RequestBody UserModel userData){
        var user = this.userRepositoy.findByUsername(userData.getUsername());
       
        if(user != null){
            System.out.println("User exists, please change your UserName!");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        var passwordHash = BCrypt.withDefaults().hashToString(12, userData.getPassword().toCharArray());
        userData.setPassword(passwordHash);
        var userCreated = this.userRepositoy.save(userData);
        return ResponseEntity.status(HttpStatus.CREATED).body(userCreated);
    }
    
    @GetMapping("/testando")
    public void getName(){
        System.out.println("PORRA");
    }
}
