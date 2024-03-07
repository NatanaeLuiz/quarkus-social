package io.github.NatanaeLuiz.quarkussocial.ret;

import io.github.NatanaeLuiz.quarkussocial.ret.dto.CreateUserRequest;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;

@Path("/users")
public class UserResource {

    @POST
    public Response createUser(CreateUserRequest userRequest){

        return Response.ok().build();
    }
}
