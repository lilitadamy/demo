package com.example.demo.controller;

import com.example.demo.model.dto.UserDto;
import com.example.demo.model.dto.VmDto;
import com.example.demo.model.entity.Log;
import com.example.demo.model.entity.Vm;
import com.example.demo.service.LogService;
import com.example.demo.service.RegistrationService;
import com.example.demo.service.VmService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping(path = "api/admin")
@AllArgsConstructor
public class AdminController {
    private final RegistrationService registrationService;
    private final VmService vmService;

    @PostMapping("register")
    public ResponseEntity<?> register(@RequestBody UserDto userDto) {
        if(userDto.getUsername() == null || userDto.getPassword() == null) {
            return ResponseEntity.badRequest().build();
        }
        try {
            registrationService.registerUser(userDto);
        } catch (IllegalStateException e) {
            throw new ResponseStatusException(
                    HttpStatus.ALREADY_REPORTED, "user exists", e);
        }
        return ResponseEntity.ok(registrationService.registerAdmin(userDto));
    }

    @PostMapping("registerVM")
    public ResponseEntity<?> registerVm() {
        return ResponseEntity.ok(vmService.registerVm());
    }

    @GetMapping("get-all-vms")
    public ResponseEntity<?> getVms() {
        if(vmService.getAll().isEmpty()) {
            return ResponseEntity.ok("no vms to show");
        }
        return ResponseEntity.ok(vmService.getAll());
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        if (!vmService.exists(id)) {
            ResponseEntity.ok("vm with id" + id + " does not exist");
        }
        return ResponseEntity.ok(vmService.delete(id));
    }
}
