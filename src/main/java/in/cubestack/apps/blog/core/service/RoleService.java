package in.cubestack.apps.blog.core.service;

import in.cubestack.apps.blog.core.domain.Role;
import in.cubestack.apps.blog.core.repository.RoleRepository;
import in.cubestack.apps.blog.core.resource.RoleCandidate;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
@Transactional
public class RoleService {

    @Inject
    RoleRepository roleRepository;

    public List<RoleCandidate> findAll() {
        return roleRepository.findAll().list().stream().map(RoleCandidate::from).collect(Collectors.toList());
    }

    public void save(RoleCandidate candidate) {
        roleRepository.persist(candidate.toRole());
    }

    public void update(RoleCandidate candidate) {
        Role role = roleRepository.findByIdOptional(candidate.getRoleId()).orElseThrow(() -> new RuntimeException("Role not found for id " + candidate.getRoleId()));
        role.setRoleName(candidate.getRoleName());
        role.setUpdatedAt(LocalDateTime.now());
        roleRepository.persist(role);
    }

    public void delete(long roleId) {
        roleRepository.deleteById(roleId);
    }
}
