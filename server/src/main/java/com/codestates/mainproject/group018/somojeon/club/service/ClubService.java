package com.codestates.mainproject.group018.somojeon.club.service;

import com.codestates.mainproject.group018.somojeon.category.service.CategoryService;
import com.codestates.mainproject.group018.somojeon.club.entity.Club;
import com.codestates.mainproject.group018.somojeon.club.entity.ClubTag;
import com.codestates.mainproject.group018.somojeon.club.entity.UserClub;
import com.codestates.mainproject.group018.somojeon.club.enums.ClubMemberStatus;
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
import com.codestates.mainproject.group018.somojeon.user.repository.UserRepository;
import com.codestates.mainproject.group018.somojeon.user.service.UserService;
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
import java.util.ArrayList;
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
    private final UserRepository userRepository;


//    public Club createClub(Club club, Long userId, List<String> tagName)  {
//
//        User findUser = findVerifiedUser(userId);
//
//        categoryService.verifyExistsCategoryName(club.getCategoryName());
//        List<Tag> tagList = tagService.findTagsElseCreateTags(tagName);
//        tagList.forEach(tag -> new ClubTag(club, tag));
//        if (tagList.size() > 3) {
//            throw new BusinessLogicException(ExceptionCode.TAG_CAN_NOT_OVER_THREE);
//        }
//        club.setCreatedAt(LocalDateTime.now());
//        club.setMemberCount(club.getMemberCount() + 1);
//        // 리더 권한 추가.
////        club.setClubRole(ClubRole.LEADER);
//        club.setClubImageUrl(defaultClubImage);
//        Club createdClub = clubRepository.save(club);
//
//        UserClub userClub = new UserClub();
//        userClub.setUser(findUser);
//        userClub.setClub(createdClub);
//        userClub.setClubRole(ClubRole.LEADER);
//        userClub.setClubMemberStatus(ClubMemberStatus.MEMBER_ACTIVE);
//        userClubRepository.save(userClub);
//
//        List<UserClub> userClubList = new ArrayList<>();
//        userClubList.add(userClub);
//        createdClub.setUserClubList(userClubList);
//
//
//        return clubRepository.save(createdClub);
//    }

    // 소모임 생성
    public Club createClub(Club club, Long userId, List<String> tagName)  {
        Club justClub = clubRepository.save(club);

        User user = findVerifiedUser(userId);
        Club findClub = findVerifiedClub(justClub.getClubId());
        categoryService.verifyExistsCategoryName(club.getCategoryName());
        List<Tag> tagList = tagService.findTagsElseCreateTags(tagName);
        tagList.forEach(tag -> new ClubTag(club, tag));
        if (tagList.size() > 3) {
            throw new BusinessLogicException(ExceptionCode.TAG_CAN_NOT_OVER_THREE);
        }
        findClub.setCreatedAt(LocalDateTime.now());
        findClub.setMemberCount(club.getMemberCount() + 1);

        findClub.setClubImageUrl(defaultClubImage);

        UserClub userClub = new UserClub();
        userClub.setUser(user);
        userClub.setClub(findClub);
        userClub.setClubRole(ClubRole.LEADER);
        userClub.setClubMemberStatus(ClubMemberStatus.MEMBER_ACTIVE);
        userClubRepository.save(userClub);

        List<UserClub> userClubList = new ArrayList<>();
        userClubList.add(userClub);
        findClub.setUserClubList(userClubList);


        return clubRepository.save(findClub);
    }


    // 소모임 수정 (리더, 매니저만 가능)
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.SERIALIZABLE)
    public Club updateClub(Long clubId, Club club, String clubName, String content, String local, List<String> tagName,boolean isSecret, MultipartFile multipartFile) throws IOException {

        Club findClub = findVerifiedClub(clubId);

        if (club.getClubName() != null && club.getContent() != null || club.getLocal() != null || multipartFile != null) {
            findClub.setClubName(clubName);
            findClub.setContent(content);
            findClub.setLocal(local);
            findClub.setSecret(isSecret);
            findClub.setClubImageUrl(imageService.uploadClubImage(multipartFile));
        }

        List<Tag> tagList = tagService.updateQuestionTags(findClub,tagName);
        tagList.forEach(tag -> new ClubTag(club, tag));
        if (tagList.size() > 3) {
            throw new BusinessLogicException(ExceptionCode.TAG_CAN_NOT_OVER_THREE);
        } else {
            clubRepository.save(findClub);
        }

        return clubRepository.save(findClub);
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

    // 내가 가입한 소모임 찾기
    public Page<Club> findMyClubs(int page, int size, Long userId) {
        return clubRepository.findClubsByUserId(PageRequest.of(page, size, Sort.by("clubId")), userId);
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

    //TODO-DW: user 연결해야됨

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

    public User findVerifiedUser(Long userId) {
        Optional<User> findUser = userRepository.findById(userId);
        User user = findUser.orElseThrow(() ->
                new BusinessLogicException(ExceptionCode.USER_NOT_FOUND));
        return user;
    }


}
