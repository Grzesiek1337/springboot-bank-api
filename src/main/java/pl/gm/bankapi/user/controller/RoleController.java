package pl.gm.bankapi.user.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.gm.bankapi.user.dto.RoleDto;
import pl.gm.bankapi.user.service.RoleService;

import java.util.List;

@RestController
@RequestMapping("/roles")
public class RoleController {

    private final RoleService roleService;

    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @GetMapping("/all")
    public List<RoleDto> getAllRoles() {
        return roleService.getAllRoles();
    }


}
