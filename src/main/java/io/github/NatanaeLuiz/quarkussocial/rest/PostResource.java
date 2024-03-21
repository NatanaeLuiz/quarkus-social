package io.github.NatanaeLuiz.quarkussocial.rest;

import io.github.NatanaeLuiz.quarkussocial.domain.model.Post;
import io.github.NatanaeLuiz.quarkussocial.domain.model.User;
import io.github.NatanaeLuiz.quarkussocial.domain.repository.PostRepository;
import io.github.NatanaeLuiz.quarkussocial.domain.repository.UserRepository;
import io.github.NatanaeLuiz.quarkussocial.rest.dto.CreatePostRequest;
import io.github.NatanaeLuiz.quarkussocial.rest.dto.PostResponse;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.panache.common.Sort;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;
import java.util.stream.Collectors;

@Path("/users/{userId}/posts")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class PostResource {

    private final UserRepository userRepository;
    private final PostRepository repository;

    @Inject
    public PostResource(UserRepository userRepository, PostRepository repository) {
        this.userRepository = userRepository;
        this.repository = repository;
    }

    @POST
    @Transactional
    public Response savePost(@PathParam("userId") Long userId, CreatePostRequest request){

        User user = userRepository.findById(userId);

        if (user == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        Post post = new Post();
        post.setText(request.getText());
        post.setUser(user);

        repository.persist(post);

        return Response.status(Response.Status.CREATED).build();
    }

    @GET
    public Response listost(@PathParam("userId") Long userId){

        User user = userRepository.findById(userId);

        if (user == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        PanacheQuery<Post> query = repository.find(
                "user", Sort.by("dateTime", Sort.Direction.Descending), user); //Realiza uma busca pelo usuario passado no request de forma decresente (da ultima postagem)

        List<Post> posts = query.list(); //Lista de posts

        var postResponseList = posts.stream()
                .map(post -> PostResponse.fromEntity(post)) //Converte um POST em um PostResponse para retornar apenas o que interessa do DTO
                .collect(Collectors.toList());
        return Response.ok(postResponseList).build();
    }
}
