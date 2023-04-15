package pl.gm.bankapi.user.service;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Service;
import pl.gm.bankapi.user.dto.RoleDto;
import pl.gm.bankapi.user.dto.UserDto;
import pl.gm.bankapi.user.model.Role;
import pl.gm.bankapi.user.repository.RoleRepository;

import java.util.List;

@Service
public class RoleService {

    private final RoleRepository roleRepository;
    private final ModelMapper modelMapper;

    public RoleService(RoleRepository roleRepository, ModelMapper modelMapper) {
        this.roleRepository = roleRepository;
        this.modelMapper = modelMapper;
    }

    public List<RoleDto> getAllRoles() {
        List<Role> roles = roleRepository.findAll();
        return modelMapper.map(roles, new TypeToken<List<UserDto>>() {
        }.getType());
    }

    public boolean isRolesExist() {
        return roleRepository.findAll().size() > 0;
    }
}
