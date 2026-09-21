package ecom.base.app.role;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import ecom.base.app.exceptions.ResourceNotFoundException;
import jakarta.transaction.Transactional;

@Service
public class RoleService {

    private final RoleRepository roleRepository;

    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public List<RoleResponseDTO> getAllRoles() {

        List<Role> roles = roleRepository.findAll();

        return roles.stream()
                .map(RoleMapper::toRoleResponseDTO)
                .toList();
    }

    public RoleResponseDTO getRoleById(Long id) {

        Optional<Role> optionalRole = roleRepository.findById(id);

        if (optionalRole.isEmpty())
            throw new ResourceNotFoundException(
                    "Role with id : " + id + " not found");

        return RoleMapper.toRoleResponseDTO(optionalRole.get());
    }

    @Transactional
    public RoleResponseDTO addRole(RoleRequestDTO request) {

        Role role = RoleMapper.toRole(request);
        role.setActive(true);

        Role savedRole = roleRepository.save(role);

        return RoleMapper.toRoleResponseDTO(savedRole);
    }

    @Transactional
    public RoleResponseDTO updateRole(
            Long id,
            RoleRequestDTO request) {

        Optional<Role> optionalRole = roleRepository.findById(id);

        if (optionalRole.isEmpty())
            throw new ResourceNotFoundException(
                    "Role with id : " + id + " not found");

        Role role = optionalRole.get();

        role.setName(request.getName());
        role.setDescription(request.getDescription());

        return RoleMapper.toRoleResponseDTO(role);
    }

    @Transactional
    public RoleResponseDTO toggleRoleStatus(Long id) {

        Optional<Role> optionalRole = roleRepository.findById(id);

        if (optionalRole.isEmpty())
            throw new ResourceNotFoundException(
                    "Role with id : " + id + " not found");

        Role role = optionalRole.get();

        role.setActive(!role.getActive());

        return RoleMapper.toRoleResponseDTO(role);
    }
}
