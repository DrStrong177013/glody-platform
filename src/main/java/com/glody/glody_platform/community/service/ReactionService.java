package com.glody.glody_platform.community.service;

import com.glody.glody_platform.community.dto.ReactionDto;
import com.glody.glody_platform.community.entity.Post;
import com.glody.glody_platform.community.entity.Reaction;
import com.glody.glody_platform.community.repository.PostRepository;
import com.glody.glody_platform.community.repository.ReactionRepository;
import com.glody.glody_platform.users.entity.User;
import com.glody.glody_platform.users.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ReactionService {

    private final ReactionRepository reactionRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    @Transactional
    public Reaction react(ReactionDto dto) {
        Post post = postRepository.findById(dto.getPostId()).orElseThrow();
        User user = userRepository.findById(dto.getUserId()).orElseThrow();

        return reactionRepository.findByPostAndUser(post, user)
                .map(r -> {
                    r.setType(dto.getType());
                    return reactionRepository.save(r);
                })
                .orElseGet(() -> {
                    Reaction newReaction = new Reaction();
                    newReaction.setPost(post);
                    newReaction.setUser(user);
                    newReaction.setType(dto.getType());
                    return reactionRepository.save(newReaction);
                });
    }
}
