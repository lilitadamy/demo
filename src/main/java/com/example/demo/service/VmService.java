package com.example.demo.service;

import com.example.demo.model.dto.VmDto;
import com.example.demo.model.entity.Log;
import com.example.demo.model.entity.User;
import com.example.demo.model.entity.Vm;
import com.example.demo.model.mapper.VmMapper;
import com.example.demo.model.role.UserRole;
import com.example.demo.repository.VmRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

//@AllArgsConstructor
@Service
public class VmService {

    private final VmRepository vmRepository;
    private final UserService userService;
    private final LogService logService;
    private final VmMapper vmMapper = new VmMapper();


    public VmService(VmRepository vmRepository, UserService userService, LogService logService) {
        this.vmRepository = vmRepository;
        this.userService = userService;
        this.logService = logService;
    }

//    public String save(VmDto vmDto) {
//        vmRepository.save(vmMapper.mapDtoToEntity(vmDto));
//        return "vm registered";
//    }
    public boolean exists(Long id) {
        if(vmRepository.findById(id).isPresent()) {
            return true;
        }
        return false;
    }

    public Long registerVm() {
        Vm vm = new Vm(true);
        vmRepository.save(vm);
        String action = "registered vm";
        User user = userService.getCurrentUser();
        Log log = new
                Log(user, UserRole.ADMIN, action, java.util.Calendar.getInstance().getTime());
        logService.save(log);
        return vm.getId();
    }

    public List<VmDto> getAll() {
        String action = "get all vms";
        User user = userService.getCurrentUser();
        Log log = new
                Log(user, user.getUserRole(), action, java.util.Calendar.getInstance().getTime());
        logService.save(log);

        return vmMapper.mapListDtoToEntity(vmRepository.findAll());
    }

    public List<VmDto> getFrees() {
        List<VmDto> vmDtos = new ArrayList<>();
        List<Vm> vms = vmRepository.findAll();
        for (Vm vm : vms) {
            if (vm.getIsFree()) {
                vmDtos.add(vmMapper.mapEntityToDto(vm));
            }
        }
        String action = "get free vms";
        User user = userService.getCurrentUser();
        Log log = new
                Log(user, UserRole.USER, action, java.util.Calendar.getInstance().getTime());
        logService.save(log);
        return vmDtos;
    }

    @Transactional
    public String delete(Long id) {
        String action = "deleted vm";
        User user = userService.getCurrentUser();
        Log log = new
                Log(user, UserRole.ADMIN, action, java.util.Calendar.getInstance().getTime());
        logService.save(log);
        if(!vmRepository.findById(id).get().getIsFree()) {
            return "user by id: " + id + " is not free";
        }
        vmRepository.deleteById(id);
        return "user by id: " + id + " has been deleted";
    }

    @Transactional
    public String assign(Long id) {
        String action = "assign vm";
        User user = userService.getCurrentUser();
        Log log = new
                Log(user, UserRole.USER, action, java.util.Calendar.getInstance().getTime());
        logService.save(log);
        Optional<Vm> vm = vmRepository.findById(id);
        if(vm.isEmpty()) {
            return "vm by id: " + id + " does not exist";
        }
        if(vm.get().getIsFree()) {
            vm.get().setUser(user);
            vm.get().setIsFree(false);
            return "vm by id: " + id + " has been assigned to you";
        } else
            return "vm by id: " + id + " is not free";
    }

    @Transactional
    public String release(Long id) {
        String action = "release vm";
        User user = userService.getCurrentUser();
        Log log = new
                Log(user, UserRole.ADMIN, action, java.util.Calendar.getInstance().getTime());
        logService.save(log);
        Optional<Vm> vm = vmRepository.findById(id);
        if(vm.isEmpty()) {
            return "vm by id: " + id + " does not exist";
        }
        if(!vm.get().getIsFree()) {
            if(vm.get().getUser() == user) {
                vm.get().setUser(null);
                vm.get().setIsFree(true);
            }
            return "vm by id: " + id + " is free";
        } else
            return "vm by id: " + id + " is free";

    }




}
