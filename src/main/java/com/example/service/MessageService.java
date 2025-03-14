package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.Message;
import com.example.repository.MessageRepository;
import com.example.repository.AccountRepository;

import java.util.List;
import java.util.Optional;

@Service
public class MessageService {

    private MessageRepository messageRepository;
    private AccountRepository accountRepository;

    @Autowired
    public MessageService(MessageRepository messageRepository, AccountRepository accountRepository) {
        this.messageRepository = messageRepository;
        this.accountRepository = accountRepository;
    }

    public Message createMessage(Message message) {
        if (message.getMessageText().isBlank() || message.getMessageText().length() > 255 || !accountRepository.existsByAccountId(message.getPostedBy())) {
            throw new IllegalArgumentException("The provided message is invalid.");
        }

        return messageRepository.save(message);
    }

    public List<Message> getMessages() {
        return messageRepository.findAll();
    }

    public Message getMessageById(Integer messageId) {
        Optional<Message> optionalMessage = messageRepository.findByMessageId(messageId);

        if (optionalMessage.isPresent()) {
            return optionalMessage.get();
        }

        return null;
    }

    public Integer deleteMessageById(Integer messageId) {
        Optional<Message> optionalMessage = messageRepository.findById(messageId);

        if (optionalMessage.isPresent()) {
            messageRepository.deleteById(messageId);

            return 1;
        }

        return null;
    }

    public List<Message> getMessagesByUser(Integer accountId) {
        return messageRepository.findAllByPostedBy(accountId);
    }

    public Integer updateMessage(Message message, Integer messageId) {
        Optional<Message> optionalMessage = messageRepository.findByMessageId(messageId);

        if (optionalMessage.isEmpty()) {
            throw new IllegalArgumentException("The user does not exist.");
        }

        Message updatedMessage = optionalMessage.get();

        if (message.getMessageText().isBlank() || message.getMessageText().length() > 255) {
            throw new IllegalArgumentException("The provided message text is invalid.");
        }

        updatedMessage.setMessageText(message.getMessageText());
        messageRepository.save(message);
        
        return 1;
    }
}
