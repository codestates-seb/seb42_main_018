import { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import styled from 'styled-components';
import { S_TabButton } from '../../../components/UI/S_Button';
import S_Container from '../../../components/UI/S_Container';
import { S_Title } from '../../../components/UI/S_Text';

const S_TapMenu = styled.div``;

function MemberSetting() {
  const navigate = useNavigate();
  const [clickedTab, setClickedTab] = useState<string | null>('total');

  const tabHandler = (e: React.MouseEvent<HTMLElement>) => {
    setClickedTab(e.currentTarget.getAttribute('name'));
  };

  return (
    <S_Container>
      <S_Title>회원 관리</S_Title>
      <S_TapMenu>
        <S_TabButton
          name='total'
          onClick={tabHandler}
          className={clickedTab === 'total' ? 'clicked' : ''}
        >
          전체 멤버 목록
        </S_TabButton>
        <S_TabButton
          name='waiting'
          onClick={tabHandler}
          className={clickedTab === 'waiting' ? 'clicked' : ''}
        >
          가입 대기 목록
        </S_TabButton>
        <S_TabButton
          name='banned'
          onClick={tabHandler}
          className={clickedTab === 'banned' ? 'clicked' : ''}
        >
          차단 목록
        </S_TabButton>
      </S_TapMenu>
      
    </S_Container>
  );
}

export default MemberSetting;
