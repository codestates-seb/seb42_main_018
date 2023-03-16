import styled from 'styled-components';
import logo from '../assets/logo.svg';
import search from '../assets/icon_search.svg';
import mypage from '../assets/icon_mypage.svg';

const HeaderContainer = styled.div`
    height: 50px;
    padding: 0px 20px;
    position: sticky; top: 0;
    background: var(--white);
    border-bottom: 1px solid var(--gray100);
    
    display: flex;
    align-items: center;
    justify-content: space-between;

    img {
        height: 28px;
        cursor: pointer;
    }
`
const IconContainer = styled.div`
    img {
        height: 25px;
        margin-left: 6px;
        margin-top: 8px;
        cursor: pointer;
    }
`

function Header() {
    return (
        <HeaderContainer>
            <img src={logo} alt="소모전 로고"/>
            <IconContainer>
                <img src={search} alt="검색 아이콘"/>
                <img src={mypage} alt="마이페이지 아이콘"/>
            </IconContainer>
        </HeaderContainer>
    )
}

export default Header;