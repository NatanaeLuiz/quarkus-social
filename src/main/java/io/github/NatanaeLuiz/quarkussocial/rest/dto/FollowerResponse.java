package io.github.NatanaeLuiz.quarkussocial.rest.dto;

import io.github.NatanaeLuiz.quarkussocial.domain.model.Follower;
import lombok.Data;

@Data
public class FollowerResponse {

    private Long id;
    private String nome;

    public FollowerResponse() {
    }

    public FollowerResponse(Follower follower) {
        this(follower.getFollower().getId(), follower.getFollower().getName());
    }

    public FollowerResponse(Long id, String nome) {
        this.id = id;
        this.nome = nome;
    }
}
