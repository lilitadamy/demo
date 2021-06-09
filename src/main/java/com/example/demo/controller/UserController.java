package com.example.demo.controller;

import com.example.demo.model.dto.UserDto;
import com.example.demo.model.dto.VmDto;
import com.example.demo.service.RegistrationService;
import com.example.demo.service.UserService;
import com.example.demo.service.VmService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping(path = "api/users")
@AllArgsConstructor
public class UserController {

    private RegistrationService registrationService;
    private VmService vmService;
    private UserService userService;

    @PostMapping("register")
    public ResponseEntity<String> register(@RequestBody UserDto userDto) {
        if (userDto.getUsername() == null || userDto.getPassword() == null) {
            return ResponseEntity.badRequest().build();
        }
        try {
            registrationService.registerUser(userDto);
        } catch (IllegalStateException e) {
            throw new ResponseStatusException(
                    HttpStatus.ALREADY_REPORTED, "user exists", e);
        }
        return ResponseEntity.ok(registrationService.registerUser(userDto));
    }

    @GetMapping("get-free-vms")
    public ResponseEntity<?> getVms() {
        if(vmService.getFrees().isEmpty()) {
            return ResponseEntity.ok("no free vms");
        }
        return ResponseEntity.ok(vmService.getFrees());
    }

    @PutMapping("assign-vm")
    public ResponseEntity<String> assign(@RequestParam Long id) {
        if (!vmService.exists(id)) {
            ResponseEntity.ok("vm with id" + id + " does not exist");
        }
        return ResponseEntity.ok(vmService.assign(id));
    }

    @PutMapping("release-vm")
    public ResponseEntity<String> release(@RequestParam Long id) {
        if (!vmService.exists(id)) {
            ResponseEntity.ok("vm with id" + id + " does not exist");
        }
        return ResponseEntity.ok(vmService.release(id));
    }

}
