import styled from 'styled-components'
import Pagination from 'react-js-pagination';

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
`
interface PageProps {
  total:number;
  size: number;
  page: number;
  setPage?: number;
  pageHandler: (pageNumber: number) => void;
}

function S_Page({total, size, page, pageHandler}: PageProps) {
  
  // const pageHandler = (page: number) => {
  //   setPage(page);
  // };
  
  return (
    <S_PageBox>
    <Pagination
      activePage={page} // 현재 페이지
      itemsCountPerPage={size} // 한 페이지에 보여줄 아이템 수
      totalItemsCount={total} // 전체 아이템 수
      pageRangeDisplayed={10} // 페이지네이터에서 보여줄 범위
      prevPageText={"이전"} // 이전 페이지 가기
      nextPageText={"다음"} // 다음 페이지 가기
      hideFirstLastPages={true}
      onChange={pageHandler} // 페이지 바뀔 때 핸들링하는 함수
    />
    </S_PageBox>
  );
  }
  
export default S_Page;