import styled from 'styled-components'
import ClubList from "./_ClubList";
import { S_Title } from '../../components/UI/S_Text'
import { clubDataAll } from './_ClubListData';
import S_Page from '../UI/S_Page';
import { useState } from 'react';

const S_TitleBox = styled.div`
  // 타이틀영역 전체 박스
  padding: 20px 0px;
`
const S_TagBox = styled.div`
  // 카테고리 태그 박스
  margin-top: 10px;
`
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
  }
`

function MainContents() {
  // TODO : 페이지네이션 기능 추가
  // TODO : data는 추후 axios로 get 요청
  // const clubData:ClubProps = getFetch(`${process.env.REACT_APP_URL}/clubs`);

  console.log(clubDataAll)

  const [page, setPage] = useState(1); // 페이지 정보 가져오기
  const [size, setSize] = useState(10); //  한 페이지에 보여줄 아이템 수
  const [total, setTotal] = useState(10); // 전체 아이템 수

  const pageHandler = (page: number) => {
    setPage(page);
  };

  return (
    <div>
      <S_TitleBox>
        <S_Title>어떤 소모임이 있을까요?</S_Title>
          <S_TagBox>
            {/* 최대 5개의 카테고리만 보여주기 */}
            <S_Category>전체보기</S_Category>
            {clubDataAll.data.map((e) => 
            <S_Category key={e.categoryName}>{e.categoryName}</S_Category>)}
          </S_TagBox>
        </S_TitleBox>
        {/* 선택한 카테고리랑 일치하는 카테고리의 리스트만 필터 */}
        {clubDataAll.data.map((e) => 
          <ClubList 
            key={e.clubId}
            clubName={e.clubName}
            clubImg={e.clubImg}
            content={e.content} 
            local={e.local}
            categoryName={e.categoryName} 
            memberCount={e.memberCount}
            tagResponseDtos={e.tagResponseDtos}
          />
        )}
        <S_Page 
          total={total}
          size={size}
          page={page}
          pageHandler={pageHandler}
        />
    </div>
  )
}

export default MainContents