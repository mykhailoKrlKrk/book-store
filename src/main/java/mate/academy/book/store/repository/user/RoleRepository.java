package mate.academy.book.store.repository.user;

import java.util.Optional;
import mate.academy.book.store.model.Role;
import mate.academy.book.store.model.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findRoleByRoleName(RoleName roleName);
}
