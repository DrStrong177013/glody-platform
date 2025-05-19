package com.glody.glody_platform.community.service;

import com.glody.glody_platform.community.entity.Tag;
import com.glody.glody_platform.community.repository.TagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TagService {
    private final TagRepository tagRepository;

    public List<Tag> getAll() {
        return tagRepository.findAll();
    }
}
