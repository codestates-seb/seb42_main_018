import styled from 'styled-components';
import Pagination from 'react-js-pagination';
import { ClubPage } from '../../types';
import { useState } from 'react';

const S_PageBox = styled.div`
  margin-top: 50px;
  .pagination {
    display: flex;
  }
  ul {
    list-style: none;
    color: var(--gray100);
  }
  ul.pagination li {
    font-size: 13px;
    display: flex;
    align-items: center;
    justify-content: center;
    padding: 3px 8px;
    border-radius: 3px;
    margin-right: 5px;
    :hover {
      background-color: var(--gray100);
    }
  }
  ul.pagination li.active {
    background-color: var(--gray100);
    color: var(--gray600);
    font-weight: 600;
  }
  ul.pagination li:first-child {
    border-radius: 5px 0 0 5px;
  }

  ul.pagination li:last-child {
    border-radius: 0 5px 5px 0;
  }
`;

function S_Page({ page, size, totalElements, totalPages }: ClubPage) {
  const [currentPage, setCurrentPage] = useState(1);
  const changePage = (page: number) => {
    setCurrentPage(page);
  };
  return (
    <S_PageBox>
      <Pagination
        activePage={currentPage} // 현재 페이지
        itemsCountPerPage={size} // 한 페이지에 보여줄 아이템 수
        totalItemsCount={totalElements} // 전체 아이템 수
        pageRangeDisplayed={5} // 페이지네이터에서 보여줄 범위
        prevPageText={'이전'} // 이전 페이지 가기
        nextPageText={'다음'} // 다음 페이지 가기
        // hideNavigation={true} // 이전/다음 버튼 숨기기
        hideFirstLastPages={true} // 맨앞/맨뒤 버튼 숨기기
        onChange={changePage} // 페이지 바뀔 때 핸들링하는 함수
      />
    </S_PageBox>
  );
}

export default S_Page;
