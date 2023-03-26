import { useEffect, useState } from 'react';
import { useParams, useNavigate, useLocation } from 'react-router-dom';
import styled from 'styled-components';
import getGlobalState from '../../../util/authorization/getGlobalState';
import { getFetch } from '../../../util/api';
import alertPreparingService from '../../../util/alertPreparingService';
import { ClubData } from '../../../types';
import S_Container from '../../../components/UI/S_Container';
import Tabmenu from '../../../components/TabMenu';
import { S_Button, S_SelectButton } from '../../../components/UI/S_Button';
import { S_Title, S_Description } from '../../../components/UI/S_Text';
import { S_Tag } from '../../../components/UI/S_Tag';
import { S_TagWrapper } from '../club/_createTag';
import ClubJoinModal from './_clubJoinModal';
import leaderBadgeIcon from '../../../assets/icon_leader-badge.svg';

const ClubIntroWrapper = styled.div`
  /* position: relative; */
  margin-top: 30px;

  & > .profile-img-box {
    margin: 0 -20px;
    height: 24vh;
    display: flex;
    justify-content: center;
    align-items: center;
    background-color: var(--gray100);
  }
  & .profile-img {
    max-width: 100%;
    max-height: 100%;
  }

  & > .club-info-box {
    margin-top: 1.5rem;
    display: flex;
    border-bottom: 1px solid var(--gray200);
  }
  & .club-info-area {
    flex: 3;
  }
  & .club-title-box {
    margin-bottom: 5px;
    display: flex;
    align-items: center;
    & .club-title {
      margin: 0;
    }
    & > img {
      margin-left: 6px;
      transform: scale(1.2);
    }
  }

  & .club-setting-btn-area {
    /* flex: 1; */
  }

  & > .club-content-box {
    margin-top: 12px;
  }

  & > .join-btn-box {
    /* position: absolute;
    bottom: 0;
    right: 0;
    left: 0; */
    margin-top: 1.5rem;

    display: flex;
    justify-content: space-between;
  }
`;
function ClubIntro() {
  const { id } = useParams();
  const navigate = useNavigate();
  const { isLogin, userInfo } = getGlobalState();
  const [clubInfo, setClubInfo] = useState<ClubData>();

  console.log(userInfo);

  // TODO : 리더에게만 뱃지와 프로필 설정 버튼이 보이는지 확인
  const myClub = userInfo.userClubResponses?.find((club) => club.userClubId === Number(id));
  const isLeader = myClub?.clubRole === 'LEADER';
  const isMember = myClub && myClub.clubRole !== null; // null: 가입신청 후 승인/거절 결정되기 전 pending 상태
  // console.log(myClub);
  // console.log(isMember);

  // ! TODO : 가입신청 후 모달만 꺼지기 때문에 clubRole === null 바로 확인할 수가 없음
  // 가입신청 성공을 확인하는 상태를 하나 더 만들어서 관리하든지
  // useEffect로 get 요청을 보내서 최신 유저 정보를 한 번 더 확인해야 할지?

  useEffect(() => {
    const GET_URL = `${process.env.REACT_APP_URL}/clubs/${id}`;
    const getClubInfo = async () => {
      const res = await getFetch(GET_URL);
      setClubInfo(res.data);
    };
    getClubInfo();
  }, []);

  console.log(clubInfo);

  // TODO : clubImageUrl 이미지 잘 뜨는지 확인
  const {
    clubName,
    content,
    categoryName,
    clubImageUrl,
    local,
    memberCount,
    tagResponseDtos: tags
  } = clubInfo || {};

  // const longText =
  //   'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nunc non est a ligula suscipit euismod vel sed nunc. In eu pellentesque ipsum. Aliquam leo erat, eleifend ut risus ut, condimentum iaculis nulla. In sit amet cursus ante. Donec nec ligula id mauris hendrerit dignissim eu et sem. Morbi sodales venenatis bibendum. Etiam vitae suscipit lectus. Pellentesque vel dolor lorem. Maecenas in consequat felis. Nunc massa nulla, congue quis malesuada vitae, porta non nunc. Maecenas ultricies lacinia ipsum, vel accumsan turpis elementum eget. In in leo vulputate, feugiat nulla eget, mattis dolor.\nSed dignissim velit a tempor ultrices. Aliquam sed congue nisl. Nunc rutrum, ex at rhoncus semper, tortor mi mollis erat, sed auctor ante sem nec diam. Maecenas sollicitudin mattis purus, a pellentesque ante pharetra eget. Cras viverra massa at eleifend elementum. Aenean ut ante laoreet, mattis lacus eget, convallis nunc. Suspendisse imperdiet cursus tortor, eu pulvinar quam ullamcorper sed. In sollicitudin rhoncus nisl at ullamcorper. Nulla tincidunt quam a ipsum vulputate pulvinar. Nam sit amet consectetur augue. Etiam egestas a risus eget posuere. Aenean faucibus ultrices urna, sed commodo quam. Integer felis neque, hendrerit et venenatis id, pulvinar quis ligula. Aliquam tempus sollicitudin augue, vitae gravida dolor fermentum non. Quisque posuere arcu ac mauris commodo, ut malesuada tortor aliquet.\nNullam a neque ipsum. Nunc vitae quam turpis. Donec consequat ligula quis velit sodales tincidunt. In sit amet sodales nunc, eget commodo diam. Mauris dignissim tempus nibh, eget ullamcorper purus vehicula eget. Aliquam vel dolor fringilla, vulputate libero nec, dictum ex. Class aptent taciti sociosqu ad litora torquent per conubia nostra, per inceptos himenaeos. Orci varius natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. Nunc cursus risus eget nisi tempor, vitae porttitor neque convallis. Donec sit amet augue nibh. Vestibulum ac odio vitae tellus scelerisque malesuada.\nCras varius turpis eget felis egestas, eu molestie neque ultricies. Etiam efficitur diam at nulla venenatis, vitae euismod mauris dapibus. Cras condimentum elementum nisl, vitae sodales nisl facilisis et. Etiam lacus lacus, porttitor at sapien id, auctor pulvinar est. Mauris ac congue ipsum, et iaculis enim. Nam lacinia ante ligula, gravida auctor quam accumsan eu. Aliquam eu justo ex. Duis et quam at tortor luctus sollicitudin nec ut mauris. Donec pulvinar placerat nunc, a pulvinar justo dapibus eu. Etiam condimentum nisl in tempor rhoncus. Morbi quis diam lectus. Vestibulum quis ligula id tortor tempus aliquam. Integer bibendum feugiat metus nec blandit. Nulla a tincidunt elit. Donec commodo arcu ut enim tincidunt molestie.\nSuspendisse potenti. Donec ut ex sit amet risus condimentum semper vel ac metus. Quisque sed enim tincidunt, cursus tellus quis, lobortis nibh. Ut a vehicula massa, ac sodales nisi. Nulla in feugiat orci, eu pretium lacus. Nulla eleifend placerat eros eget lobortis. Pellentesque tincidunt tincidunt bibendum. Sed ac fermentum diam. Nullam accumsan metus a scelerisque tempor.\n\n\nLorem ipsum dolor sit amet, consectetur adipiscing elit. Nunc non est a ligula suscipit euismod vel sed nunc. In eu pellentesque ipsum. Aliquam leo erat, eleifend ut risus ut, condimentum iaculis nulla. In sit amet cursus ante. Donec nec ligula id mauris hendrerit dignissim eu et sem. Morbi sodales venenatis bibendum. Etiam vitae suscipit lectus. Pellentesque vel dolor lorem. Maecenas in consequat felis. Nunc massa nulla, congue quis malesuada vitae, porta non nunc. Maecenas ultricies lacinia ipsum, vel accumsan turpis elementum eget. In in leo vulputate, feugiat nulla eget, mattis dolor.\nSed dignissim velit a tempor ultrices. Aliquam sed congue nisl. Nunc rutrum, ex at rhoncus semper, tortor mi mollis erat, sed auctor ante sem nec diam. Maecenas sollicitudin mattis purus, a pellentesque ante pharetra eget. Cras viverra massa at eleifend elementum. Aenean ut ante laoreet, mattis lacus eget, convallis nunc. Suspendisse imperdiet cursus tortor, eu pulvinar quam ullamcorper sed. In sollicitudin rhoncus nisl at ullamcorper. Nulla tincidunt quam a ipsum vulputate pulvinar. Nam sit amet consectetur augue. Etiam egestas a risus eget posuere. Aenean faucibus ultrices urna, sed commodo quam. Integer felis neque, hendrerit et venenatis id, pulvinar quis ligula. Aliquam tempus sollicitudin augue, vitae gravida dolor fermentum non. Quisque posuere arcu ac mauris commodo, ut malesuada tortor aliquet.\nNullam a neque ipsum. Nunc vitae quam turpis. Donec consequat ligula quis velit sodales tincidunt. In sit amet sodales nunc, eget commodo diam. Mauris dignissim tempus nibh, eget ullamcorper purus vehicula eget. Aliquam vel dolor fringilla, vulputate libero nec, dictum ex. Class aptent taciti sociosqu ad litora torquent per conubia nostra, per inceptos himenaeos. Orci varius natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. Nunc cursus risus eget nisi tempor, vitae porttitor neque convallis. Donec sit amet augue nibh. Vestibulum ac odio vitae tellus scelerisque malesuada.\nCras varius turpis eget felis egestas, eu molestie neque ultricies. Etiam efficitur diam at nulla venenatis, vitae euismod mauris dapibus. Cras condimentum elementum nisl, vitae sodales nisl facilisis et. Etiam lacus lacus, porttitor at sapien id, auctor pulvinar est. Mauris ac congue ipsum, et iaculis enim. Nam lacinia ante ligula, gravida auctor quam accumsan eu. Aliquam eu justo ex. Duis et quam at tortor luctus sollicitudin nec ut mauris. Donec pulvinar placerat nunc, a pulvinar justo dapibus eu. Etiam condimentum nisl in tempor rhoncus. Morbi quis diam lectus. Vestibulum quis ligula id tortor tempus aliquam. Integer bibendum feugiat metus nec blandit. Nulla a tincidunt elit. Donec commodo arcu ut enim tincidunt molestie.\nSuspendisse potenti. Donec ut ex sit amet risus condimentum semper vel ac metus. Quisque sed enim tincidunt, cursus tellus quis, lobortis nibh. Ut a vehicula massa, ac sodales nisi. Nulla in feugiat orci, eu pretium lacus. Nulla eleifend placerat eros eget lobortis. Pellentesque tincidunt tincidunt bibendum. Sed ac fermentum diam. Nullam accumsan metus a scelerisque tempor.\n';

  const tabs = [
    { id: 1, title: '소개', path: `/club/${id}` },
    { id: 2, title: '경기정보', path: `/club/${id}/match` },
    { id: 3, title: '멤버', path: `/club/${id}/member` }
  ];

  const [showModal, setShowModal] = useState(false);
  const handleModal = () => {
    setShowModal((current) => !current);
  };

  // * 로그인 여부에 따른 분기 처리
  const { pathname } = useLocation();
  const RETURN_URL_PARAM = 'returnUrl';

  // !! TODO : 이미 가입 신청을 완료한 소모임인지 확인 필요
  // 가입신청 완료한 상태에서 또 클릭하면 사용자에게 알람
  const handleJoinRequest = () => {
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
            {/* TODO: img src 추후 clubImageUrl로 변경 */}
            <img
              src={clubImageUrl}
              alt='소모임 소개 이미지'
              className='profile-img'
              style={{ width: '100%' }}
            />
          </div>
          <div className='club-info-box'>
            <div className='club-info-area'>
              <div className='club-title-box'>
                <S_Title className='club-title'>{clubName}</S_Title>
                {isLeader && <img src={leaderBadgeIcon} alt='소모임장 아이콘' />}
              </div>
              <div className='club-detail-box'>
                <S_Description>
                  {categoryName}&nbsp;&middot;&nbsp;{local}&nbsp;&middot;&nbsp;
                  {`인원 ${memberCount}명`}
                </S_Description>
              </div>
              <S_TagWrapper>
                {tags &&
                  tags.map((tag) => (
                    <li key={tag.tagId}>
                      <S_Tag>{tag.tagName}</S_Tag>
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
          {/* //! 공개 소모임 중에서 소모임 멤버가 아닌 유저에게만 렌더링 */}
          {!isMember && (
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
          )}
        </ClubIntroWrapper>

        <ClubJoinModal handleModal={handleModal} showModal={showModal} />
      </S_Container>
    </>
  );
}

export default ClubIntro;
