package aiss.gitminer.controller;

import aiss.gitminer.exception.UserNotFoundException;
import aiss.gitminer.model.User;
import aiss.gitminer.repository.UserRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Tag(name = "User",description = "User management API")
@RestController
@RequestMapping("/gitminer/users")
public class UserController {

    @Autowired
    UserRepository userRepository;

    // Listar todos los usuarios
    @Operation(
            summary = "Retrieve a list of users",
            description = "Lists every user stored in the database",
            tags = {"users","get"})
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(schema = @Schema(implementation = User.class),mediaType = "application/json")}),
    })
    @GetMapping
    public List<User> getAll() {
        return userRepository.findAll();
    }

    // Listar usuario por Id
    @Operation(
            summary = "Retrieve a user by Id",
            description = "Get a user by specifying its id",
            tags = {"users","get"})
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(schema = @Schema(implementation = User.class),mediaType = "application/json")}),
            @ApiResponse(responseCode = "404", content = {@Content(schema =@Schema())})
    })
    @GetMapping("/{id}")
    public User findOne(@Parameter(description = "id of the user to be searched") @PathVariable String id) throws UserNotFoundException {
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()) {
            return user.get();
        }
        throw new UserNotFoundException();
    }

    // Crear un usuario
    @Operation(
            summary = "Create a user",
            description = "Add a new user with the body specified into the database",
            tags = {"users","post"})
    @ApiResponses({
            @ApiResponse(responseCode = "201", content = {@Content(schema = @Schema(implementation = User.class),mediaType = "application/json")}),
            @ApiResponse(responseCode = "400", content = {@Content(schema = @Schema()) }) })

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public User create(@RequestBody User user) {
        User _user = new User();
        _user.setId(user.getId());
        _user.setUsername(user.getUsername());
        _user.setName(user.getName());
        _user.setAvatarUrl(user.getAvatarUrl());
        _user.setWebUrl(user.getWebUrl());
        userRepository.save(_user);
        return _user;
    }

    // Editar un usuario
    @Operation(
            summary = "Edit an existing user in the database",
            description = "Changes the body of the user object stored in the database that matches given id",
            tags = {"users","put"}
    )
    @ApiResponses({
            @ApiResponse(responseCode = "204", content = {@Content(schema = @Schema())}),
            @ApiResponse(responseCode = "404", content = {@Content(schema = @Schema())}),
            @ApiResponse(responseCode = "400", content = {@Content(schema = @Schema()) }) })

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping("/{id}")
    public void update(@RequestBody User updatedUser,@Parameter(description = "id of the user to be edited") @PathVariable String id) throws UserNotFoundException {
        Optional<User> userData = userRepository.findById(id);
        if (userData.isPresent()) {
            User _user = userData.get();
            _user.setUsername(updatedUser.getUsername());
            _user.setName(updatedUser.getName());
            _user.setAvatarUrl(updatedUser.getAvatarUrl());
            _user.setWebUrl(updatedUser.getWebUrl());
            userRepository.save(_user);
        } else {
            throw new UserNotFoundException();
        }
    }



}
