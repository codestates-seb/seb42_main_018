import { useState, useEffect } from 'react';
import { useNavigate, useSearchParams } from 'react-router-dom';
import styled from 'styled-components';
import { getFetch } from '../../util/api';
import ClubList from '../../components/home/home/ClubList';
import S_Container from '../../components/UI/S_Container';
import { ModalBackdrop } from '../../components/UI/S_Modal';
import { S_Label } from '../../components/UI/S_Text';
import { ClubData } from '../../types';
import search from '../../assets/icon_search.svg';

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

function Search() {
  const navigate = useNavigate();
  const [searchParams] = useSearchParams();
  const keyword = searchParams.get('q');

  const getClubListData = async (str: string) => {
    navigate(`/search?q=${str}`, { replace: true });

    const URL = `${process.env.REACT_APP_URL}/clubs/search?keyword=${str}`;
    const res = await getFetch(URL);

    if (res) {
      const clubList = res.data;
      setClubList(clubList);
      setNoResult(!clubList.length);
      if (!clubList.length) setNoResultKeyword(str);

      setInitialDisplay(false);
    }
  };

  // 소모임 목록에서 상세페이지로 갔다가 뒤로가기로 돌아올 때 대비
  useEffect(() => {
    if (keyword) getClubListData(keyword);
  }, []);

  const [inputValue, setInputValue] = useState('');
  const onChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    setInputValue(e.target.value);
  };

  const [clubList, setClubList] = useState<ClubData[]>([]);
  const [initialDisplay, setInitialDisplay] = useState(true);
  const [noResult, setNoResult] = useState(false);
  const [noResultKeyword, setNoResultKeyword] = useState('');

  const getSearchData = async (e: React.KeyboardEvent<HTMLInputElement>) => {
    if (e.key === 'Enter') getClubListData(inputValue);
  };

  return (
    <>
      {
        <S_SearchBarContainer>
          <S_SearchBarHeaderWrapper>
            <button className='back-btn' onClick={() => navigate(-1)}>
              &lt;
            </button>
            <S_SearchBarBox>
              <img src={search} alt='검색 아이콘' />
              <input
                value={inputValue}
                onChange={onChange}
                onKeyDown={getSearchData}
                placeholder='소모임 이름, 키워드 검색'
              />
            </S_SearchBarBox>
          </S_SearchBarHeaderWrapper>
          <S_SearchBarContentWrapper>
            {initialDisplay && (
              <p style={{ textAlign: 'center' }}>
                찾고 싶은 소모임의 이름이나 키워드를 검색해 보세요.
              </p>
            )}
            {!initialDisplay && noResult && (
              <p style={{ textAlign: 'center' }}>
                <span style={{ color: 'var(--blue300)' }}>{noResultKeyword}</span>에 대한 검색
                결과가 없어요.
              </p>
            )}
            {!initialDisplay && !noResult && (
              <div>
                <S_Label>
                  관련 소모임 <span style={{ color: 'var(--blue300)' }}>{clubList.length}</span>
                </S_Label>
                {clubList.map(
                  (club) =>
                    club.clubPrivateStatus === 'PUBLIC' && (
                      <ClubList
                        key={club.clubId}
                        clubId={club.clubId}
                        clubName={club.clubName}
                        clubImage={club.clubImage}
                        content={club.content}
                        local={club.local}
                        categoryName={club.categoryName}
                        memberCount={club.memberCount}
                        tagList={club.tagList}
                      />
                    )
                )}
              </div>
            )}
          </S_SearchBarContentWrapper>
        </S_SearchBarContainer>
      }
    </>
  );
}

export default Search;
