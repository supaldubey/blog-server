package in.cubestack.apps.blog.core.repository;


import in.cubestack.apps.blog.core.domain.Role;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class RoleRepository implements PanacheRepository<Role> {

}
