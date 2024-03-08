package io.github.NatanaeLuiz.quarkussocial.domain.repository;

import io.github.NatanaeLuiz.quarkussocial.domain.model.User;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class UserRepository implements PanacheRepository<User> {

}
