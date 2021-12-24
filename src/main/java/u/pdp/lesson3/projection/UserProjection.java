package u.pdp.lesson3.projection;

import org.springframework.data.rest.core.config.Projection;
import u.pdp.lesson3.entity.User;

@Projection(types = User.class)
public interface UserProjection {
    Integer getId();

    String getUserName();

    String getPassword();

    String getPhoneNumber();
}
