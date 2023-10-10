package com.api.backend.Userapi.service;

import com.api.backend.Userapi.dto.UserDTO;
import com.api.backend.Userapi.model.User;
import com.api.backend.Userapi.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public List<UserDTO> getAll(){
        List<User> usuarios = userRepository.findAll();
        return usuarios.stream().map(UserDTO::convert).collect(Collectors.toList());
    }

    public UserDTO findById(long userId){
        User usuario = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("user not found."));
        return UserDTO.convert(usuario);
    }

    public UserDTO save(UserDTO userDTO){
        userDTO.setDataCadastro(LocalDateTime.now());
        User user = userRepository.save(User.convert(userDTO));
        return UserDTO.convert(user);
    }

    public UserDTO delete(long userId){
        User user = userRepository
                .findById(userId).orElseThrow(() ->
                        new RuntimeException());
        userRepository.delete(user);
        return UserDTO.convert(user);
    }

    public UserDTO findByCpf(String cpf){
        User user = userRepository.findByCpf(cpf);
        if(user != null){
            return UserDTO.convert(user);
        }
        return null;
    }

    public List<UserDTO> queryByName(String name){
        List<User> usuarios = userRepository.queryByNomeLike(name);
        return usuarios
                .stream()
                .map(UserDTO::convert)
                .collect(Collectors.toList());
    }

    public UserDTO editUser(Long userId, UserDTO userDTO){
        User user = userRepository.findById(userId).orElseThrow(()
                -> new RuntimeException());

        if (userDTO.getEmail() != null &&
                !user.getEmail().equals(userDTO.getEmail())){
            user.setEmail(userDTO.getEmail());
        }
        if (userDTO.getTelefone() != null &&
                !user.getTelefone().equals(userDTO.getTelefone())){
            user.setTelefone(userDTO.getTelefone());
        }
        if (userDTO.getEndereco() != null &&
                !user.getEndereco().equals(userDTO.getEndereco())){
            user.setEndereco(userDTO.getEndereco());
        }

        user = userRepository.save(user);
        return UserDTO.convert(user);

    }

    public Page<UserDTO> getAllPage(Pageable page){
        Page<User> users = userRepository.findAll(page);
        return users.map(UserDTO::convert);
    }
}
