import styled from 'styled-components';

const S_Header = styled.div`
    background-color: #377CFB;
    height: 50px;
    width: 500px;
    position: sticky; top: 0;
    color: wheat;
`

function Header() {
    return (
        <S_Header>
            헤더 영역입니다
        </S_Header>
    )
}

export default Header;