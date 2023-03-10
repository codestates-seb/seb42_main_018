package com.codestates.mainproject.group018.somojeon.club.service;

import com.codestates.mainproject.group018.somojeon.category.entity.Category;
import com.codestates.mainproject.group018.somojeon.club.entity.Club;
import com.codestates.mainproject.group018.somojeon.club.repository.ClubRepository;
import com.codestates.mainproject.group018.somojeon.tag.entity.Tag;
import com.codestates.mainproject.group018.somojeon.tag.service.TagService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ClubService {

    private final ClubRepository clubRepository;
    private final TagService tagService;

    public ClubService(ClubRepository clubRepository, TagService tagService) {
        this.clubRepository = clubRepository;
        this.tagService = tagService;
    }

    // 소모임 생성
    public Club createClub(Club club, Category category, List<String> tagName) {
        //TODO: 회원검증 추가 해야함
        verifyExistsClubName(club.getClubName());
        List<Tag> tagList = tagService.findTagsElseCreateTags(tagName);
        club.setTagList(tagList);
        club.setCategory(category);

        return clubRepository.save(club);
    }

    // 소모임 수정
    public Club updateClub(Club club, List<String> tagName) {
        //TODO: 리더와 매니저만 수정가능하게 하기 로직 추가 해야함
        Club findClub = findVerifiedClub(club.getClubId());

        Optional.ofNullable(club.getClubName())
                .ifPresent(clubName -> findClub.setClubName(clubName));
        Optional.ofNullable(club.getContent())
                .ifPresent(content -> findClub.setContent(content));
        Optional.ofNullable(club.getLocal())
                .ifPresent(local -> findClub.setLocal(local));
        Optional.ofNullable(club.isPrivate())
                .ifPresent(isPrivate -> findClub.setPrivate(isPrivate));

        List<Tag> tags = tagService.findTagsElseCreateTags(tagName);
        findClub.setTagList(tags);


        return clubRepository.save(findClub);
    }

    // 소모임 단건 조회
    @Transactional(readOnly = true)
    public Club findClub(Long clubId) {
        Club findClub = findVerifiedClub(clubId);
        findClub.setViewCount(findClub.getViewCount() + 1);
        clubRepository.save(findClub);

        return findClub;
    }

    // 소모임 전체 조회
    public Page<Club> findClubs(int page, int size, int viewCount) {
        Pageable pageable = PageRequest.of(page, size);
        return clubRepository.findByViewCount(pageable, viewCount);
    }

    // 키워드로 소모임 찾기
    public List<Club> searchClubs(int page, int size, String keyword) {

        if(keyword.matches(".*[a-zA-Z0-9가-힣]+.*") && keyword.startsWith("\"") && keyword.endsWith("\"")) {
            keyword = keyword.substring(1, keyword.length() - 1);
        }
        Pageable pageable = PageRequest.of(page, size);

        return clubRepository.findByKeyword(pageable, keyword)
                .orElseThrow(() -> new RuntimeException()).getContent();
    }

    public void deleteClub(Long clubId) {
        Club findClub = findVerifiedClub(clubId);
        clubRepository.delete(findClub);

    }

    //TODO: UserGroup DI - GroupRole
    public void changeClubRoles() {

    }
    //TODO: UserGroup DI - GroupRole
    public void changeClubLeader() {

    }
    //TODO: user 연결해야됨
    public void changeClubMemberStatus(Long userId) {

    }
    //TODO: user 연결해야됨
    public void memberQuit(Long userId) {

    }

    public void verifyExistsClubName(String clubName) {
        Optional<Club> club = clubRepository.findByClubName(clubName);
        if (club.isPresent()) {
            throw new RuntimeException();
        }
    }

    public Club findVerifiedClub(Long clubId) {
        Optional<Club> findClub = clubRepository.findById(clubId);
        Club club = findClub.orElseThrow(() -> new RuntimeException());

        return club;
    }


}
