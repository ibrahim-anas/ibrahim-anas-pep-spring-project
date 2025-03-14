package com.example.controller;

import com.example.entity.Account;
import com.example.entity.Message;
import com.example.exception.DuplicateUsernameException;
import com.example.exception.InvalidLoginCredentialsException;
import com.example.service.AccountService;
import com.example.service.MessageService;

import org.springframework.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller using Spring. The endpoints you will need can be
 * found in readme.md as well as the test cases. You be required to use the @GET/POST/PUT/DELETE/etc Mapping annotations
 * where applicable as well as the @ResponseBody and @PathVariable annotations. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
@RestController
public class SocialMediaController {

    @Autowired
    private AccountService accountService;

    @Autowired
    private MessageService messageService;

    @PostMapping("/register")
    public Account createAccount(@RequestBody Account account) {
        return accountService.createAccount(account);
    }

    @PostMapping("/login")
    public Account userLogin(@RequestBody Account account) {
        return accountService.userLoginAuthentication(account);
    }

    @PostMapping("/messages")
    public Message createMessage(@RequestBody Message message) {
        return messageService.createMessage(message);
    }

    @GetMapping("/messages") 
    public List<Message> getMessages() {
        return messageService.getMessages();
    }

    @GetMapping("/messages/{messageId}")
    public Message getMessage(@PathVariable Integer messageId) {
        return messageService.getMessageById(messageId);
    }

    @DeleteMapping("/messages/{messageId}")
    public Integer deleteMessage(@PathVariable Integer messageId) {
        return messageService.deleteMessageById(messageId);
    }

    @GetMapping("accounts/{accountId}/messages")
    public List<Message> getMessagesByUser(@PathVariable Integer accountId) {
        return messageService.getMessagesByUser(accountId);
    }

    @PatchMapping("messages/{messageId}")
    public Integer updateMessage(@RequestBody Message message, @PathVariable Integer messageId) {
        return messageService.updateMessage(message, messageId);
    }

    @ExceptionHandler(DuplicateUsernameException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public String handleDuplicateUsername(DuplicateUsernameException e) {
        return e.getMessage();
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleIllegalArgument(IllegalArgumentException e) {
        return e.getMessage();
    }

    @ExceptionHandler(InvalidLoginCredentialsException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public String handleInvalidLoginCredentials(InvalidLoginCredentialsException e) {
        return e.getMessage();
    }
}
