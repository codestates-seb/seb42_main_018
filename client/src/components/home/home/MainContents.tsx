import { useState, useEffect } from 'react';
import styled from 'styled-components';
import ClubList from './ClubList';
import { S_Title } from '../../UI/S_Text';
import { ClubData, ClubPage } from '../../../types';
import { getFetch } from '../../../util/api';
import CreateClubImg from './CreateClubImg';

const S_TitleBox = styled.div`
  // 타이틀영역 전체 박스
  margin-top: 10px;
  padding: 20px 0px;
`;
const S_TagBox = styled.div`
  // 카테고리 태그 박스
  margin-top: 10px;
`;
const S_Category = styled.span`
  // 카테고리 목록
  padding: 6px 11px;
  margin-right: 5px;
  border: 1px solid var(--gray300);
  border-radius: 50px;
  color: var(--gray300);
  font-size: 0.9rem;
  font-weight: 600;
  :hover {
    color: var(--gray600);
    border: 1px solid var(--gray600);
    cursor: pointer;
  }
`;

export interface CategoryProps {
  categoryName: string;
}

function MainContents() {
  // TODO : 페이지네이션 기능 추가 -> 무한스크롤 도전!

  // API로 카테고리 정보 가져오기
  // const [categories, setCategories] = useState<CategoryProps[]>([]);
  // useEffect(() => {
  //   getFetch(`${process.env.REACT_APP_URL}/categories`).then((data) => {
  //     const categories: CategoryProps[] = data.data.slice(0, 4);
  //     setCategories(categories);
  //   });
  // }, []);

  // API로 클럽 리스트 가져오기
  const [clubs, setClubs] = useState<ClubData[]>([]); // 뿌려줄 클럽리스트
  const [pageInfo, setPageInfo] = useState<ClubPage>(); // 페이지 인포

  useEffect(() => {
    getFetch(`${process.env.REACT_APP_URL}/clubs`).then((data) => {
      const clubs: ClubData[] = data.data;
      setClubs(clubs);
      const pageInfo: ClubPage = data.pageInfo;
      setPageInfo(pageInfo);
    });
  }, []);

  return (
    <div>
      <S_TitleBox>
        <S_Title>어떤 소모임이 있을까요?</S_Title>
        {/* <S_TagBox> */}
        {/* 최대 5개의 카테고리만 보여주기 */}
        {/* <S_Category>전체보기</S_Category> */}
        {/* {categories.map((el) => (
            <S_Category key={el}>
              {el}
            </S_Category>
          ))} */}
        {/* </S_TagBox> */}
        <CreateClubImg />
      </S_TitleBox>
      {clubs.map(
        (el) =>
          el.clubPrivateStatus === 'PUBLIC' && (
            <ClubList
              key={el.clubId}
              clubId={el.clubId}
              clubName={el.clubName}
              clubImage={el.clubImage}
              content={el.content}
              local={el.local}
              categoryName={el.categoryName}
              memberCount={el.memberCount}
              tagList={el.tagList}
            />
          )
      )}
      {/* <S_Page /> */}
    </div>
  );
}

export default MainContents;
