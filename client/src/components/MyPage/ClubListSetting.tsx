import { Link, useNavigate } from 'react-router-dom';
import styled from 'styled-components';
import { S_TagSmall } from '../UI/S_Tag';
import { S_Description, S_Label, S_SmallDescription } from '../UI/S_Text';
import { ClubData } from '../../types';
import getGlobalState from '../../util/authorization/getGlobalState';
import leaderBadgeIcon from '../../assets/icon_leader-badge.svg';
import { S_NegativeButton, S_SelectButton } from '../UI/S_Button';
import { useEffect, useState } from 'react';
import { getFetch, deleteFetch, patchFetch } from '../../util/api';

const S_ClubBox = styled.div`
  // 전체 컨테이너
  display: flex;
  padding: 20px 0px;
  background-color: var(--white);
  border-top: 1px solid var(--gray100);
`;

const S_TitleBox = styled.div`
  // 제목 - 소모임 설정 버튼 정렬
  display: flex;
  flex-grow: 1;
  align-items: center;
  justify-content: space-between;
`;
const S_ImgBox = styled.div<{ img?: string }>`
  // 클럽 대표 이미지 썸네일크기로 자르기
  margin-right: 15px;
  min-width: 80px;
  height: 80px;
  border-radius: 10px;
  background-size: cover;
  background-position: center;
  background-image: url(${(props) => props.img});
`;
const S_ContentsBox = styled.div`
  // 제목 / 카테고리, 지역, 인원 / 설명 / 태그 박스
  width: 100%;
  .tagbox {
    margin-bottom: 20px;
  }
  .settingbox {
    padding: 10px 0px;
    border-top: 1px solid var(--gray100);
  }
`;
const S_Hidden = styled.div`
  // 설명글 길어지면 잘라주기
  display: -webkit-box;
  word-wrap: break-word;
  -webkit-line-clamp: 1;
  -webkit-box-orient: vertical;
  text-overflow: ellipsis;
  overflow: hidden;
  margin-bottom: 5px;
`;

interface ClubListSettingProps {
  clubId?: number;
  clubRole?: string | null;
}

function ClubListSetting({ clubId, clubRole }: ClubListSettingProps) {
  const { userInfo, tokens } = getGlobalState();
  const navigate = useNavigate();
  // 받아온 userClubResponses.clubId로 get 요청 보내기
  // 요청보낼 URI는 API 문서 28번 '소모임 단건 조회'
  const [club, setClub] = useState<ClubData>();

  useEffect(() => {
    // 받아온 유저클럽아이디로 클럽 정보 받아오기
    getFetch(`${process.env.REACT_APP_URL}/clubs/${clubId}`).then((data) => {
      setClub(data.data);
    });
  }, []);

  const leaveClub = async () => {
    patchFetch(
      `${process.env.REACT_APP_URL}/clubs/${clubId}/memberStatus/${userInfo.userId}`,
      {
        clubMemberStatus: 'MEMBER QUIT'
      },
      tokens
    ).then(() => {
      alert('클럽을 탈퇴했습니다');
      navigate('/home');
    });
  };

  const cancelJoinClub = async () => {
    // 가입신청 취소 요청
    if (tokens) {
      const res = await deleteFetch(
        `${process.env.REACT_APP_URL}/clubs/${club?.clubId}/joins/${userInfo.userId}`,
        tokens
      );
      if (res) alert('가입 신청이 취소되었습니다');
      navigate('/');
      // 추후 모달 처리
      // 바로 데이터 반영되는지? 목록 없어지는지?
    }
  };

  return (
    <S_ClubBox>
      <S_ImgBox img={club?.clubImage} />
      <S_ContentsBox>
        <Link to={`/club/${club?.clubId}`}>
          <S_TitleBox>
            <S_Label>
              {club?.clubName}
              {/* 롤이 리더인 경우 아이콘 보여주기 */}
              {clubRole === 'LEADER' && <img src={leaderBadgeIcon} alt='소모임장 아이콘' />}
            </S_Label>
          </S_TitleBox>
          <S_SmallDescription>
            {club?.categoryName} ・ {club?.local} ・ 인원 {club?.memberCount}명
          </S_SmallDescription>
          <S_Hidden>
            <S_Description color='var(--gray600)'>{club?.content}</S_Description>
          </S_Hidden>
          <div className='tagbox'>
            {club?.tagList.map((tag, idx) => (
              <S_TagSmall key={idx}>{tag}</S_TagSmall>
            ))}
          </div>
        </Link>
        <div className='settingbox'>
          {clubRole === 'LEADER' ? (
            // 롤이 리더인 경우 설정으로 가기
            <S_SelectButton width='auto' onClick={() => navigate(`/club/${club?.clubId}/setting`)}>
              소모임 설정
            </S_SelectButton>
          ) : clubRole === 'MANAGER' || clubRole === 'MEMBER' ? (
            // 롤이 멤버 또는 매니저인 경우 탈퇴 요청 하기
            // TODO : 탈퇴 로직 구현 API 34번
            <S_NegativeButton onClick={leaveClub}>소모임 탈퇴</S_NegativeButton>
          ) : (
            // 롤이 null 일때는 가입 취소 버튼
            // TODO : 가입 취소 로직 구현 API 37번
            <S_SelectButton width='auto' onClick={cancelJoinClub}>
              가입 취소
            </S_SelectButton>
          )}
        </div>
      </S_ContentsBox>
    </S_ClubBox>
  );
}

export default ClubListSetting;
