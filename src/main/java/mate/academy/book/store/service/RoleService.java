package mate.academy.book.store.service;

import mate.academy.book.store.model.Role;
import mate.academy.book.store.model.RoleName;

public interface RoleService {
    Role getRoleByRoleName(RoleName roleName);
}
