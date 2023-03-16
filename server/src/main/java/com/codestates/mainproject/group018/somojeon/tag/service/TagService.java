package com.codestates.mainproject.group018.somojeon.tag.service;

import com.codestates.mainproject.group018.somojeon.club.entity.Club;
import com.codestates.mainproject.group018.somojeon.club.entity.ClubTag;
import com.codestates.mainproject.group018.somojeon.club.repository.ClubRepository;
import com.codestates.mainproject.group018.somojeon.club.repository.ClubTagRepository;
import com.codestates.mainproject.group018.somojeon.exception.BusinessLogicException;
import com.codestates.mainproject.group018.somojeon.exception.ExceptionCode;
import com.codestates.mainproject.group018.somojeon.tag.entity.Tag;
import com.codestates.mainproject.group018.somojeon.tag.repository.TagRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TagService {

    private final TagRepository tagRepository;
    private final ClubTagRepository clubTagRepository;

    public TagService(TagRepository tagRepository, ClubTagRepository clubTagRepository) {
        this.tagRepository = tagRepository;
        this.clubTagRepository = clubTagRepository;
    }

    public Tag createTag(String tagName) {
        Tag tag = new Tag();
        tag.setTagName(tagName);
        return tagRepository.save(tag);
    }

    public Tag createTag(Tag tag) {
        return tagRepository.save(tag);
    }

    public List<Tag> findTagsElseCreateTags(List<String> tagNames) {
        return tagNames.stream()
                .map(tagName -> findVerifiedTag(tagName))
                .collect(Collectors.toList());
    }

    public Tag findVerifiedTag(String tagName) {
        Optional<Tag> optionalTag = tagRepository.findByTagName(tagName);
        if (optionalTag.isPresent()) {
            return optionalTag.get();
        } else {
            return createTag(tagName);
        }
    }

    // 태그 수정
    public List<Tag> updateQuestionTags(Club club, List<String> tagNames) {

        // 기존 태그 삭제
        clubTagRepository.findAllByClub(club).stream()
                .forEach(clubTagRepository::delete);

        // 새로운 태그 유효성 검사
        List<Tag> findTags = findTagsElseCreateTags(tagNames);

        // 새로운 questionTag 저장
        findTags.stream()
                .forEach(tag -> {
                    ClubTag clubTag = new ClubTag(club, tag);
                    clubTagRepository.save(clubTag);
                });

        return findTags;
    }
}
