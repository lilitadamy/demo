package com.example.demo.model.mapper;

import com.example.demo.model.dto.VmDto;
import com.example.demo.model.entity.Vm;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
public class VmMapper {
    ModelMapper modelMapper = new ModelMapper();

    public Vm mapDtoToEntity(VmDto vmDto) {
        return modelMapper.map(vmDto, Vm.class);
    }

    public VmDto mapEntityToDto(Vm vm) {
        return modelMapper.map(vm, VmDto.class);
    }

    public List<VmDto> mapListDtoToEntity(List<Vm> vms) {
        List<VmDto> vmDtos = new ArrayList<>();
        for (Vm vm : vms) {
            vmDtos.add(mapEntityToDto(vm));
        }
        return vmDtos;
    }
}
