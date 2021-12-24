package u.pdp.lesson3.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import u.pdp.lesson3.entity.User;
import u.pdp.lesson3.projection.UserProjection;

@RepositoryRestResource(path = "user",excerptProjection = UserProjection.class)
public interface UserRepository extends JpaRepository<User,Integer> {
}
