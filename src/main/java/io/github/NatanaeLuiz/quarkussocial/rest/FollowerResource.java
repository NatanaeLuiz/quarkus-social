package io.github.NatanaeLuiz.quarkussocial.rest;

import io.github.NatanaeLuiz.quarkussocial.domain.model.Follower;
import io.github.NatanaeLuiz.quarkussocial.domain.repository.FollowerRepository;
import io.github.NatanaeLuiz.quarkussocial.domain.repository.UserRepository;
import io.github.NatanaeLuiz.quarkussocial.rest.dto.FollowerRequest;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/users/{userId}/followers")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class FollowerResource {

    private final FollowerRepository followerRepository;
    private final UserRepository userRepository;

    @Inject
    public FollowerResource(FollowerRepository followerRepository, UserRepository userRepository){

        this.followerRepository = followerRepository;
        this.userRepository = userRepository;
    }

    @PUT
    @Transactional
    public Response followUser(@PathParam("userId") Long userId, FollowerRequest request){

        var user = userRepository.findById(userId); //Busca do id do usuario a ser seguido

        if(user == null) { //verifica se o usuario existe
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        var follower = userRepository.findById(request.getFollowerId());

        boolean follows = followerRepository.follows(follower, user); //verifica se o seguidor já segue o usuario

        if (!follows) { // caso não segue então realiza a operação de seguir normalmente
            var entity = new Follower();
            entity.setUser(user); //usuario seguido
            entity.setFollower(follower); //usuario seguidor

            followerRepository.persist(entity);
        }

        return Response.status(Response.Status.NO_CONTENT).build();
    }
}
