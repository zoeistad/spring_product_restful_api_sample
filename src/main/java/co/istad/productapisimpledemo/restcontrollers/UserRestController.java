package co.istad.productapisimpledemo.restcontrollers;


import co.istad.productapisimpledemo.dto.user.CreateUserRequest;
import co.istad.productapisimpledemo.dto.user.UserResponse;
import co.istad.productapisimpledemo.service.UserService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
@Slf4j
public class UserRestController {
    private final UserService userService;

    @GetMapping
    public List<UserResponse> getUsers() {
        log.info("Getting all users");
        return userService.getAllUsers();
    }
    @PostMapping
    public UserResponse createNew(@RequestBody CreateUserRequest request){
        log.info("Creating new user {}", request);
        return userService.createUser(request);
    }
}
