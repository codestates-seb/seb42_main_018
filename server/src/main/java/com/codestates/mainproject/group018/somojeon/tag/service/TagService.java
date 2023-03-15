package com.codestates.mainproject.group018.somojeon.tag.service;

import com.codestates.mainproject.group018.somojeon.tag.entity.Tag;
import com.codestates.mainproject.group018.somojeon.tag.repository.TagRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TagService {

    private final TagRepository tagRepository;

    public TagService(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }

    public Tag createTag(String tagName) {
        Tag tag = new Tag();
        tag.setTagName(tagName);
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

//    public List<Tag> updateQuestionTags(Tag tag, List<String> tagNames) {
//
//        // 기존 태그 삭제
//        tagRepository.findByTag(tag).stream()
//                .forEach(tags -> {
//                    tagRepository.delete(tags);
//                });
//
//        // 새로운 태그 유효성 검사
//        List<Tag> findTags = findTagsElseCreateTags(tagNames);
//
//        // 새로운 questionTag 저장
//        findTags.stream()
//                .forEach(tags -> {
//                    Tag tag1 = new Tag(tagName);
//                    QuestionTag saveQuestionTag = questionTagRepository.save(questionTag);
//                    updateQuestionCount(saveQuestionTag.getTag());
//                });
//
//        return findTags;
//    }
}
