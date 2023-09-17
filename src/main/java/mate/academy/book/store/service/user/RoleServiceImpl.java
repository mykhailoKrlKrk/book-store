package mate.academy.book.store.service.user;

import lombok.RequiredArgsConstructor;
import mate.academy.book.store.model.Role;
import mate.academy.book.store.model.RoleName;
import mate.academy.book.store.repository.user.RoleRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;

    @Override
    public Role getRoleByRoleName(RoleName roleName) {
        return roleRepository.findRoleByRoleName(roleName).orElseThrow(
                () -> new RuntimeException("Can't find role by role name:" + roleName));
    }
}
