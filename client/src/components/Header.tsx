import styled from 'styled-components';
import logo from '../assets/logo.svg';
import search from '../assets/icon_search.svg';
import mypage from '../assets/icon_mypage.svg';
import { Link, useNavigate } from 'react-router-dom';
import alertPreparingService from '../util/alertPreparingService';
import getGlobalState from '../util/authorization/getGlobalState';

const HeaderContainer = styled.header`
  box-sizing: border-box;
  margin: 0 auto;
  max-width: 500px;
  height: 50px;
  padding: 0px 20px;
  position: fixed;
  top: 0;
  left: 0;
  right: 0;
  background: var(--white);
  border-bottom: 1px solid var(--gray100);
  z-index: 100;

  display: flex;
  align-items: center;
  justify-content: space-between;

  img {
    height: 28px;
    margin-top: 6px;
    cursor: pointer;
  }
`;
const IconContainer = styled.div`
  button {
    border-radius: 8px;
    background-color: var(--white);
    :hover {
      background-color: var(--gray100);
    }
  }
  img {
    height: 25px;
    cursor: pointer;
  }
`;

function Header() {
  // const { isLogin } = getGlobalState;
  const navigate = useNavigate();
  const isLogin = true; // gotoPage 함수 작동여부 확인하기 위한 임시 변수

  const gotoPage = () => {
    // 로그인여부에 따라 마이페이지 또는 로그인 페이지로 보내기
    if (isLogin) navigate('mypage');
    else navigate('login');
  };

  return (
    <HeaderContainer>
      <Link to='/'>
        <img src={logo} alt='소모전 로고' />
      </Link>
      <IconContainer>
        {/* TODO : 클릭시 검색창 모달 열리게, 지금은 준비중인 서비스로 알람 */}
        <button onClick={alertPreparingService}>
          <img src={search} alt='검색 아이콘' />
        </button>
        {/* TODO : 비로그인->로그인페이지, 로그인->마이페이지로 */}
        <button onClick={gotoPage}>
          <img src={mypage} alt='마이페이지 아이콘' />
        </button>
      </IconContainer>
    </HeaderContainer>
  );
}

export default Header;
