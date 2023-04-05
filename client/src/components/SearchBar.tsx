import styled from 'styled-components';
import S_Container from './UI/S_Container';
import { ModalBackdrop } from './UI/S_Modal';
import search from '../assets/icon_search.svg';

const S_SearchBarContainer = styled(ModalBackdrop)`
  margin: 0 auto;
  max-width: 500px;
  box-shadow: 0px 0px 30px -10px var(--gray200);
  background-color: var(--white);
  z-index: 220;
  display: block;
`;

const S_SearchBarHeaderWrapper = styled.div`
  box-sizing: border-box;
  height: 50px;
  padding: 10px 12px;
  border-bottom: 1px solid var(--gray100);
  display: flex;
  align-items: center;
  color: #333d4b;

  & > .back-btn {
    margin-right: 4px;
    font-size: 1.8rem;
  }
`;

const S_SearchBarBox = styled.div`
  box-sizing: border-box;
  padding: 8px;
  height: 35px;
  background-color: var(--gray100);
  border-radius: 5px;
  display: flex;
  flex-grow: 1;

  & > img {
    margin-right: 4px;
  }

  & > input {
    width: calc(100% - 25px);
    background-color: transparent;
    border: none;
  }
  & > input:focus {
    outline: 0;
  }
`;

const S_SearchBarContentWrapper = styled(S_Container)`
  margin-top: 0;
`;

interface SearchBarProps {
  showSearchBar: boolean;
  setShowSearchBar: React.Dispatch<React.SetStateAction<boolean>>;
}

function SearchBar({ showSearchBar, setShowSearchBar }: SearchBarProps) {
  return (
    <>
      {showSearchBar && (
        <S_SearchBarContainer>
          <S_SearchBarHeaderWrapper>
            <button className='back-btn' onClick={() => setShowSearchBar(false)}>
              &lt;
            </button>
            <S_SearchBarBox>
              <img src={search} alt='검색 아이콘' />
              <input />
            </S_SearchBarBox>
          </S_SearchBarHeaderWrapper>
          <S_SearchBarContentWrapper>
            <p style={{ textAlign: 'center' }}>
              찾고 싶은 소모임의 이름이나 키워드를 검색해 보세요.
            </p>
          </S_SearchBarContentWrapper>
        </S_SearchBarContainer>
      )}
    </>
  );
}

export default SearchBar;
