package com.codestates.mainproject.group018.somojeon.club.service;

import com.codestates.mainproject.group018.somojeon.category.service.CategoryService;
import com.codestates.mainproject.group018.somojeon.club.entity.Club;
import com.codestates.mainproject.group018.somojeon.club.entity.ClubTag;
import com.codestates.mainproject.group018.somojeon.club.entity.UserClub;
import com.codestates.mainproject.group018.somojeon.club.enums.ClubRole;
import com.codestates.mainproject.group018.somojeon.club.repository.ClubRepository;
import com.codestates.mainproject.group018.somojeon.club.repository.UserClubRepository;
import com.codestates.mainproject.group018.somojeon.exception.BusinessLogicException;
import com.codestates.mainproject.group018.somojeon.exception.ExceptionCode;
import com.codestates.mainproject.group018.somojeon.images.entity.Images;
import com.codestates.mainproject.group018.somojeon.images.repository.ImagesRepository;
import com.codestates.mainproject.group018.somojeon.images.service.ImageService;
import com.codestates.mainproject.group018.somojeon.tag.entity.Tag;
import com.codestates.mainproject.group018.somojeon.tag.service.TagService;
import com.codestates.mainproject.group018.somojeon.user.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ClubService {
    @Value("${defaultClub.image.address}")
    private String defaultClubImage;
    private final ClubRepository clubRepository;
    private final TagService tagService;
    private final UserClubRepository userClubRepository;
    private final CategoryService categoryService;
    private final ImageService imageService;
    private final ImagesRepository imagesRepository;


    public Club createClub(Club club, List<String> tagName)  {
        //TODO-DW: 회원검증 추가 해야함 (ROLE이 USER인지 확인)

        categoryService.verifyExistsCategoryName(club.getCategoryName());
        List<Tag> tagList = tagService.findTagsElseCreateTags(tagName);
        tagList.forEach(tag -> new ClubTag(club, tag));
        if (tagList.size() > 3) {
            throw new BusinessLogicException(ExceptionCode.TAG_CAN_NOT_OVER_THREE);
        }
        club.setCreatedAt(LocalDateTime.now());
        club.setMemberCount(club.getMemberCount() + 1);


        // 리더 권한 추가.
        club.setClubRole(ClubRole.LEADER);
        club.setClubImageUrl(defaultClubImage);

        return clubRepository.save(club);
    }


    // 소모임 수정 (리더, 매니저만 가능)
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.SERIALIZABLE)
    public Club updateClub(Long clubId, Club club, String clubName, String content, String local, List<String> tagName,boolean isSecret, MultipartFile multipartFile) throws IOException {

        Club findClub = findVerifiedClub(clubId);

        if (club.getClubName() != null && club.getContent() != null || club.getLocal() != null) {
            findClub.setClubName(clubName);
            findClub.setContent(content);
            findClub.setLocal(local);
            findClub.setSecret(isSecret);
        }

//        if (club.getClubImageUrl() != null) {
//            imageService.uploadClubImage(club, multipartFile);
//        } else {
//            club.setClubImageUrl(defaultClubImage);
//        }

        List<Tag> tagList = tagService.updateQuestionTags(findClub,tagName);
        tagList.forEach(tag -> new ClubTag(club, tag));
        if (tagList.size() > 3) {
            throw new BusinessLogicException(ExceptionCode.TAG_CAN_NOT_OVER_THREE);
        } else {
            clubRepository.save(findClub);
        }


        if (club.getClubImageUrl() != null) {
            Images oldImage = findClub.getImages();
            if (oldImage != null) {
                imageService.deleteClubImage(oldImage);
                imagesRepository.delete(oldImage);
            }
            Images newImage = imageService.uploadClubImage(multipartFile);
            findClub.setImages(newImage);
            findClub.setClubImageUrl(newImage.getUrl());

        }

        return clubRepository.save(findClub);


//        Optional.ofNullable(club.getClubName())
//                .ifPresent(findClub::setClubName);
//        Optional.ofNullable(club.getContent())
//                .ifPresent(findClub::setContent);
//        Optional.ofNullable(club.getLocal())
//                .ifPresent(findClub::setLocal);
//        Optional.of(club.isSecret())
//                .ifPresent(findClub::setSecret);

//        log.info("이미지 저장 시작");
//        String uploadClubImage = imageService.uploadClubImage(multipartFile);

//        findClub.setClubImageUrl(uploadClubImage);


//        log.info("이제 태그 저장합니다.");
//        List<Tag> tagList = tagService.updateQuestionTags(findClub,tagName);
//        log.info("태그 저장 중");
//        tagList.forEach(tag -> new ClubTag(club, tag));
//        if (tagList.size() > 3) {
//            throw new BusinessLogicException(ExceptionCode.TAG_CAN_NOT_OVER_THREE);
//        } else {
//            log.info("소모임 수정 저장 하기 전");
//            clubRepository.save(findClub);
//        }
//        log.info("소모임 수정완료");
//        return clubRepository.save(findClub);
    }

    // 소모임 단건 조회 (전체 ROLE 가능)
    public Club findClub(Long clubId) {
        Club findClub = findVerifiedClub(clubId);
            findClub.setViewCount(findClub.getViewCount() + 1);
            clubRepository.save(findClub);
        return findClub;
    }

    // 소모임 전체 조회 (전체 ROLE 가능)
    public Page<Club> findClubs(int page, int size) {
        return clubRepository.findAll(PageRequest.of(page, size, Sort.by("viewCount").descending()));

    }

    // 키워드로 소모임 찾기 (전체 ROLE 가능)
    public Page<Club> searchClubs(int page, int size, String keyword) {
        Page<Club> clubPage = clubRepository.findByKeyword(PageRequest.of(page, size), keyword);
        return clubPage;
    }

    // 카테고리별로 소모임 조회 (전체 ROLE 가능)
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

    public ClubRole getUserClub(Long userId, Long clubId) {
        //TODO-DW: 검토 부탁드려요 by 제훈
        Optional<UserClub> optionalUserClub =  userClubRepository.findByUserIdAndClubId(userId, clubId);
        UserClub userClub =  optionalUserClub.orElseThrow(()-> new BusinessLogicException(ExceptionCode.USER_CLUB_NOT_FOUND));

        return userClub.getClubRole();
    }

    public List<UserClub> getUserClubs(Long userId) {
        //TODO-DW: 검토 부탁드려요 by 제훈
        List<UserClub> userClubs =  userClubRepository.findAllByUserId(userId);

        return userClubs;
    }



     //소모임 회원 등급 설정

     //소모임 회원 등급 설정 (리더와 매니저만 가능)
//    public UserClub changeClubRoles(UserClub userClub, String clubRole) {
//
////        identifier.checkClubRole(userClub.getUserClubId());
//
//        switch (clubRole) {
//            case "Manager" : userClub.getClubRole().setRoles("Manager");
//            default : userClub.getClubRole().setRoles("Member");
//        }
//
//        return userClubRepository.save(userClub);
//    }

    //    //TODO-DW: UserClub DI - ClubRole
    // 소모임 리더 위임 (리더만 가능)
//    public UserClub changeClubLeader(UserClub userClub, String clubRole) {
//        if (!userClub.getClubRole().getRoles().equals("Leader")) {
//            throw new BusinessLogicException(ExceptionCode.REQUEST_FORBIDDEN);
//        }
//
//        if (clubRole.equals("Leader")) {
//            Long userId = userClub.getUser().getUserId();
//
//
//        }
//        return null;
//    }


    //TODO-DW: user 연결해야됨

    // 소모임 멤버 상태 변경 (리더와 매니저만 가능)
    public UserClub changeClubMemberStatus(Long userId) {
        return null;
    }
    //TODO-DW: user 연결해야됨

    // 소모임 회원 탈퇴 (소모임 리더, 매니저, 멤버 가능)
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

    public List<UserClub> updatePlayer(Long clubId, List<Long> playerIds, boolean flag){
        List<UserClub> userClubs = playerIds.stream().map(
                playerId -> {
                    Optional<UserClub> optionalUserClub = userClubRepository.findByUserIdAndClubId(clubId, playerId);
                    UserClub userClub =  optionalUserClub.orElseThrow(() -> new BusinessLogicException(ExceptionCode.USER_NOT_FOUND));
                    userClub.setPlayer(flag);
                    return userClubRepository.save(userClub);

                }).collect(Collectors.toList());
        return userClubs;
    }


}
