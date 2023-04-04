import { useEffect, useState } from 'react';
import { useParams, useNavigate, useLocation } from 'react-router-dom';
import styled from 'styled-components';
import getGlobalState from '../../../util/authorization/getGlobalState';
import { getFetch } from '../../../util/api';
import alertPreparingService from '../../../util/alertPreparingService';
import { RETURN_URL_PARAM } from '../../../util/commonConstants';
import S_Container from '../../../components/UI/S_Container';
import Tabmenu from '../../../components/TabMenu';
import ClubJoinModal from '../../../components/club/intro/_clubJoinModal';
import { S_Button, S_ButtonGray, S_SelectButton } from '../../../components/UI/S_Button';
import { S_Title, S_Description } from '../../../components/UI/S_Text';
import { S_Tag } from '../../../components/UI/S_Tag';
import { S_TagWrapper } from '../../../components/club/club/_createTag';
import { ClubData } from '../../../types';
import leaderBadgeIcon from '../../../assets/icon_leader-badge.svg';
import defaultClubImg from '../../../assets/default_Img.svg';

const ClubIntroWrapper = styled.div`
  position: relative;
  margin-top: 30px;
  min-height: 88vh;

  & > .profile-img-box {
    margin: 0 -20px;
    height: 24vh;
    display: flex;
    justify-content: center;
    align-items: center;
    background-color: var(--gray100);
  }

  & > .club-info-box {
    margin-top: 1.2rem;
    padding-bottom: 0.4rem;
    display: flex;
    border-bottom: 1px solid var(--gray200);
  }
  & .club-info-area {
    flex: 3;
  }
  & .club-title-box {
    margin-bottom: 4px;
    display: flex;
    align-items: center;
    & .leader-badge-icon {
      margin-left: 6px;
      transform: scale(1.2);
    }
    & .club-title {
      margin: 0;
    }
  }

  & > .club-content-box {
    margin-top: 1rem;
    margin-bottom: calc(50px + 1rem);
  }

  & > .join-btn-box {
    position: absolute;
    bottom: 0;
    right: 0;
    left: 0;

    display: flex;
    justify-content: space-between;
  }
`;

const S_ClubImgArea = styled.img<{ src: string }>`
  max-width: 100%;
  height: 100%;
  background-size: cover;
  background-position: center;
  background-image: url(${(props) => props.src});
`;

interface TabItemType {
  id: number;
  title: string;
  path: string;
}

function ClubIntro() {
  const { id } = useParams();
  const navigate = useNavigate();
  const { isLogin, userInfo, tokens } = getGlobalState();
  const [clubInfo, setClubInfo] = useState<ClubData>();
  // isApplied : 가입신청을 이미 한 번 했으면 true, 아직 안했으면 false
  const [isApplied, setIsApplied] = useState(false);

  const myClub = userInfo.userClubResponses?.find((club) => club.clubId === Number(id));
  const isLeader = myClub?.clubRole === 'LEADER';
  const isMember = myClub && myClub.clubRole !== null; // clubRole === null: 가입신청 후 승인/거절 결정되기 전 pending 상태

  useEffect(() => {
    const CLUB_URL = `${process.env.REACT_APP_URL}/clubs/${id}`;
    const getClubInfo = async () => {
      const res = await getFetch(CLUB_URL, tokens);
      if (res) setClubInfo(res.data);
    };
    getClubInfo();

    if (myClub && myClub.clubRole === null) setIsApplied(true);
  }, []);

  const {
    clubName,
    content,
    categoryName,
    clubImage,
    local,
    memberCount,
    tagList: tags
  } = clubInfo || {};

  let tabs: TabItemType[];
  if (isMember) {
    tabs = [
      { id: 1, title: '소개', path: `/club/${id}` },
      { id: 2, title: '경기정보', path: `/club/${id}/match` },
      { id: 3, title: '멤버', path: `/club/${id}/member` }
    ];
  } else {
    tabs = [{ id: 1, title: '소개', path: `/club/${id}` }];
  }

  const [showModal, setShowModal] = useState(false);
  const handleModal = () => {
    setShowModal((current) => !current);
  };

  // * 로그인 여부에 따른 분기 처리
  const { pathname } = useLocation();
  const handleJoinRequest = () => {
    if (isApplied) {
      alert('이미 가입신청을 하셨어요. 마이페이지에서 가입신청 승인 현황을 확인할 수 있습니다.');
      return;
    }

    if (isLogin) {
      handleModal();
    } else {
      navigate(`/login?${RETURN_URL_PARAM}=${encodeURIComponent(pathname)}`, { replace: true });
    }
  };

  return (
    <>
      <S_Container>
        <Tabmenu tabs={tabs}></Tabmenu>
        <ClubIntroWrapper>
          <div className='profile-img-box'>
            <S_ClubImgArea
              src={clubImage ? clubImage : defaultClubImg}
              alt='소모임 소개 이미지'
            ></S_ClubImgArea>
          </div>
          <div className='club-info-box'>
            <div className='club-info-area'>
              <div className='club-title-box'>
                <S_Title className='club-title'>
                  {clubName}
                  {isLeader && (
                    <img
                      src={leaderBadgeIcon}
                      alt='소모임장 아이콘'
                      className='leader-badge-icon'
                    />
                  )}
                </S_Title>
              </div>
              <div className='club-detail-box'>
                {memberCount && (
                  <S_Description>
                    {categoryName}&nbsp;&middot;&nbsp;{local}&nbsp;&middot;&nbsp;
                    {`인원 ${memberCount}명`}
                  </S_Description>
                )}
              </div>
              <S_TagWrapper>
                {tags &&
                  tags.map((tag, idx) => (
                    <li key={idx}>
                      <S_Tag>{tag}</S_Tag>
                    </li>
                  ))}
              </S_TagWrapper>
            </div>
            {isLeader && (
              <div className='club-setting-btn-area'>
                <S_SelectButton width='auto' onClick={() => navigate(`/club/${id}/setting`)}>
                  소모임 설정
                </S_SelectButton>
              </div>
            )}
          </div>
          <div className='club-content-box'>
            <p style={{ whiteSpace: 'pre-line' }}>{content}</p>
          </div>
          {myClub?.clubMemberStatus === 'MEMBER QUIT' ? (
            <div className='join-btn-box'>
              <S_ButtonGray style={{ cursor: 'not-allowed' }}>탈퇴한 소모임입니다.</S_ButtonGray>
            </div>
          ) : (
            !isMember && (
              <div className='join-btn-box'>
                <S_Button addStyle={{ width: '48%' }} onClick={handleJoinRequest}>
                  가입신청
                </S_Button>
                <S_Button
                  addStyle={{
                    width: '48%',
                    backgroundColor: 'var(--gray100)',
                    color: 'var(--gray400)',
                    hoverBgColor: 'var(--gray200)'
                  }}
                  onClick={alertPreparingService}
                >
                  문의하기
                </S_Button>
              </div>
            )
          )}
        </ClubIntroWrapper>

        <ClubJoinModal
          handleModal={handleModal}
          showModal={showModal}
          setIsApplied={setIsApplied}
        />
      </S_Container>
    </>
  );
}

export default ClubIntro;
