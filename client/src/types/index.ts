export interface ClubData {
  // ClubList에 뿌려줄 클럽 데이터 타입 설정
  clubId?: number;
  clubName: string;
  profileImage?: string;
  content: string;
  local: string;
  categoryName: string;
  viewCount?: number;
  memberCount?: number;
  tagResponseDtos: {
    tagId: number;
    tagName: string;
  }[];
  modifiedAt?: string;
  private?: boolean;
}
export interface ClubPage {
  // 페이지네이션 정보 타입 설정
  page: number;
  size: number;
  totalElements: number;
  totalPages: number;
}

export interface ClubProps {
  // API로 받아오는 클럽 데이터 타입
  data: ClubData[];
  pageInfo: ClubPage;
}

export interface ClubMemberProps {
  // 클럽-멤버-전체멤버 : 받아올 멤버 정보 타입 설정
  memberId?: number;
  profileImage: string;
  name: string;
  winRate: string;
}

export interface RecodeListProps {
  // 클럽-멤버-멤버기록 : 받아올 멤버 정보 타입 설정
  memberId?: number;
  profileImage: string;
  name: string;
  winRate: string;
  match: string;
  win: string;
  lose: string;
}

export interface SubTab {
  // 탭보다 작은 서브탭 타입
  id: number;
  title: string;
  contents: React.ReactNode;
}

export interface SubTabProps {
  // subtaps props 받아오기 위한 타입
  subtabs: SubTab[];
}
