package com.codestates.mainproject.group018.somojeon.club.service;

import com.codestates.mainproject.group018.somojeon.category.service.CategoryService;
import com.codestates.mainproject.group018.somojeon.club.entity.Club;
import com.codestates.mainproject.group018.somojeon.club.entity.ClubTag;
import com.codestates.mainproject.group018.somojeon.club.entity.UserClub;
import com.codestates.mainproject.group018.somojeon.club.repository.ClubRepository;
import com.codestates.mainproject.group018.somojeon.club.repository.UserClubRepository;
import com.codestates.mainproject.group018.somojeon.exception.BusinessLogicException;
import com.codestates.mainproject.group018.somojeon.exception.ExceptionCode;
import com.codestates.mainproject.group018.somojeon.tag.entity.Tag;
import com.codestates.mainproject.group018.somojeon.tag.repository.TagRepository;
import com.codestates.mainproject.group018.somojeon.tag.service.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ClubService {

    private final ClubRepository clubRepository;
    private final TagService tagService;
    private final UserClubRepository userClubRepository;
    private final CategoryService categoryService;
    private final TagRepository tagRepository;

    public ClubService(ClubRepository clubRepository, TagService tagService,
                       UserClubRepository userClubRepository, CategoryService categoryService,
                       TagRepository tagRepository) {
        this.clubRepository = clubRepository;
        this.tagService = tagService;
        this.userClubRepository = userClubRepository;
        this.categoryService = categoryService;
        this.tagRepository = tagRepository;
    }

    // 소모임 생성
    // 소모임 생성시 카테고리 존재여부 검증/ 카테고리이름 저장 로직 추가
//    public Club createClub(Club club, String categoryName, List<String> tagName) {
//        //TODO-DW: 회원검증 추가 해야함 (ROLE이 USER인지 확인)
//
//        if (club.getCategory().getCategoryName().equals(categoryName)) {
//        } else {
//            categoryService.saveCategory(categoryName);
//        }
//        List<Tag> tagList = tagService.findTagsElseCreateTags(tagName);
//        tagList.forEach(tag -> new ClubTag(club, tag));
//        if (tagList.size() > 3) {
//            throw new BusinessLogicException(ExceptionCode.TAG_CAN_NOT_OVER_THREE);
//        } else {
////            categoryService.verifyExistsCategoryName(club.getCategory(), club.getCategoryName());
//            verifyExistsClubName(club.getClubName());
//            club.setCreatedAt(LocalDateTime.now());
//            return clubRepository.save(club);
//        }
//    }

    public Club createClub(Club club, List<String> tagName) {
        //TODO-DW: 회원검증 추가 해야함 (ROLE이 USER인지 확인)

        categoryService.verifyExistsCategoryName(club.getCategoryName());
        List<Tag> tagList = tagService.findTagsElseCreateTags(tagName);
        tagList.forEach(tag -> new ClubTag(club, tag));
        if (tagList.size() > 3) {
            throw new BusinessLogicException(ExceptionCode.TAG_CAN_NOT_OVER_THREE);
        }
        club.setCreatedAt(LocalDateTime.now());
        return clubRepository.save(club);
    }

    // 소모임 수정
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.SERIALIZABLE)
    public Club updateClub(Club club, List<String> tagName) {
        //TODO-DW: 리더와 매니저만 수정가능하게 하기 로직 추가 해야함
        Club findClub = findVerifiedClub(club.getClubId());

        Optional.ofNullable(club.getClubName())
                .ifPresent(findClub::setClubName);
        Optional.ofNullable(club.getContent())
                .ifPresent(findClub::setContent);
        Optional.ofNullable(club.getLocal())
                .ifPresent(findClub::setLocal);
        Optional.of(club.isPrivate())
                .ifPresent(findClub::setPrivate);
        List<Tag> tagList = tagService.updateQuestionTags(findClub,tagName);
        tagList.forEach(tag -> new ClubTag(club, tag));
        if (tagList.size() > 3) {
            throw new BusinessLogicException(ExceptionCode.TAG_CAN_NOT_OVER_THREE);
        } else {
            return clubRepository.save(findClub);
        }
    }

    // 소모임 단건 조회
    // 전체 ROLE 가능
    public Club findClub(Long clubId) {
        Club findClub = findVerifiedClub(clubId);
            findClub.setViewCount(findClub.getViewCount() + 1);
            clubRepository.save(findClub);
        return findClub;
    }

    // 소모임 전체 조회
    // 전체 ROLE 가능
    public Page<Club> findClubs(int page, int size) {
        return clubRepository.findAll(PageRequest.of(page, size, Sort.by("viewCount").descending()));

    }

    // 키워드로 퍼블릭 소모임 찾기
    // 전체 ROLE 가능
    public Page<Club> searchClubs(int page, int size, String keyword) {
        Page<Club> clubPage = clubRepository.findByKeyword(PageRequest.of(page, size), keyword);
        return clubPage;
    }

    // TODO-DW : 로직 수정해야함. data가 안나옴
    // 카테고리별로 소모임 조회
    public Page<Club> findClubsByCategoryName(String categoryName, int page, int size) {
        return clubRepository.findByCategoryName(categoryName, PageRequest.of(page, size, Sort.by("clubId")));
    }

    public void deleteClub(Long clubId) {
        //TODO-DW: 리더 인지 검증
        Club findClub = findVerifiedClub(clubId);
        if (findClub.getMemberCount() > 1) {
            throw new BusinessLogicException(ExceptionCode.CLUB_CAN_NOT_DELETE);
        } else {
            clubRepository.delete(findClub);
        }
    }

     //소모임 회원 등급 설정
    public UserClub changeClubRoles(UserClub userClub, String clubRole) {
        //TODO-DW: 회원검증
        if (!userClub.getClubRole().getRoles().equals("Leader")
                || userClub.getClubRole().getRoles().equals("Manager")) {
            throw new BusinessLogicException(ExceptionCode.REQUEST_FORBIDDEN);
        }
//        String clubRole = userClub.getClubRole().getRoles();

        switch (clubRole) {
            case "Manager" : userClub.getClubRole().setRoles("Manager");
            default : userClub.getClubRole().setRoles("Member");
        }

        return userClubRepository.save(userClub);
    }

    //    //TODO-DW: UserClub DI - ClubRole
    // 소모임 리더 위임
    public UserClub changeClubLeader(UserClub userClub, String clubRole) {
        if (!userClub.getClubRole().getRoles().equals("Leader")) {
            throw new BusinessLogicException(ExceptionCode.REQUEST_FORBIDDEN);
        }

        if (clubRole.equals("Leader")) {
            Long userId = userClub.getUser().getUserId();


        }
        return null;
    }


    //TODO-DW: user 연결해야됨

    // 소모임 멤버 상태 변경
    public void changeClubMemberStatus(Long userId) {

    }
    //TODO-DW: user 연결해야됨

    // 소모임 회원 탈퇴
    public void memberQuit(Long userId) {

    }

    public void verifyExistsClubName(String clubName) {
        Optional<Club> club = clubRepository.findByClubName(clubName);
        if (club.isPresent()) {
            throw new BusinessLogicException(ExceptionCode.CLUB_EXISTS);
        }
    }

    public Club findVerifiedClub(Long clubId) {
        Optional<Club> findClub = clubRepository.findById(clubId);
        Club club = findClub.orElseThrow(() ->
                new BusinessLogicException(ExceptionCode.CLUB_NOT_FOUND));

        return club;
    }


}
