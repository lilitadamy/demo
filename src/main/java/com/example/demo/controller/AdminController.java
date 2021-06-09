package com.example.demo.controller;

import com.example.demo.model.dto.UserDto;
import com.example.demo.model.dto.VmDto;
import com.example.demo.model.entity.Vm;
import com.example.demo.service.RegistrationService;
import com.example.demo.service.VmService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/admin")
@AllArgsConstructor
public class AdminController {
    private RegistrationService registrationService;
    private VmService vmService;

    @PostMapping("register")
    public ResponseEntity register(@RequestBody UserDto userDto) {
        if(userDto.getUsername() == null || userDto.getPassword() == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(registrationService.registerAdmin(userDto));
    }

    @PostMapping("registerVM")
    public Long registerVm() {
        return vmService.registerVm();
    }

    @GetMapping("get-all-vms")
    public ResponseEntity<?> getVms() {
        if(vmService.getFrees().isEmpty()) {
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
