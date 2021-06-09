package com.example.demo.service;

import com.example.demo.model.dto.VmDto;
import com.example.demo.model.entity.User;
import com.example.demo.model.entity.Vm;
import com.example.demo.model.mapper.VmMapper;
import com.example.demo.repository.UserRepository;
import com.example.demo.repository.VmRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

//@AllArgsConstructor
@Service
public class VmService {

    private final VmRepository vmRepository;
    private final UserRepository userRepository;
    private final VmMapper vmMapper = new VmMapper();

    public VmService(VmRepository vmRepository, UserRepository userRepository) {
        this.vmRepository = vmRepository;
        this.userRepository = userRepository;
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
        return vm.getId();
    }

    public List<VmDto> getAll() {
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
        return vmDtos;
    }

    @Transactional
    public String delete(Long id) {
        if(!vmRepository.findById(id).get().getIsFree()) {
            return "user by id: " + id + " is not free";
        }
        vmRepository.deleteById(id);
        return "user by id: " + id + " has been deleted";
    }

    @Transactional
    public String assign(Long id) {
        Optional<Vm> vm = vmRepository.findById(id);
        if(vm.isEmpty()) {
            return "vm by id: " + id + " does not exist";
        }
        if(vm.get().getIsFree()) {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            String username = (String) auth.getPrincipal();
            Optional<User> user = userRepository.findByUsername(username);
            vm.get().setUser(user.get());
            vm.get().setIsFree(false);
            return "vm by id: " + id + " has been assigned to you";
        } else
            return "vm by id: " + id + " is not free";
    }

    @Transactional
    public String release(Long id) {
        Optional<Vm> vm = vmRepository.findById(id);
        if(vm.isEmpty()) {
            return "vm by id: " + id + " does not exist";
        }
        if(!vm.get().getIsFree()) {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            String username = (String) auth.getPrincipal();
            Optional<User> user = userRepository.findByUsername(username);
            if(vm.get().getUser() == user.get()) {
                vm.get().setUser(null);
                vm.get().setIsFree(true);
            }
            return "vm by id: " + id + " is free";
        } else
            return "vm by id: " + id + " is free";

    }




}
