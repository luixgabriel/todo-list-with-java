package br.com.luix.todolist.users;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

public interface IUserRepositoy extends JpaRepository<UserModel, UUID> {
    UserModel findByUsername(String userName);
}
