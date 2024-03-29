export interface ClubData {
  // ClubList에 뿌려줄 클럽 데이터 타입 설정
  clubId?: number;
  clubName: string;
  clubImage?: string;
  content: string;
  local: string;
  categoryName: string;
  viewCount?: number;
  memberCount?: number;
  tagList: string[];
  modifiedAt?: string;
  clubPrivateStatus?: 'PUBLIC' | 'SECRET';
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

export interface MemberData {
  // 멤버목록 확인을 위한 데이터 타입
  
  userId?: number;
  nickName: string;
  clubMemberStatus?: string;
  clubRole?: string;
  profileImage: string;
  playCount?: number;
  winCount?: number;
  loseCount?: number;
  drawCount?: number;
  winRate: number;
}

export interface MemberProps {
  // 같은 소모임 멤버 정보 목록 조회
  data?: MemberData[];
  pageInfo?: ClubPage;
}

export interface ClubMemberProps {
  members: MemberData[];
}

export interface myPageUserClubResponses {
  // 마이페이지에서 가져올 유저데이터의 userClubResponses
  clubId: number;
  clubMemberStatus?: string;
  clubRole: string;
  playCount: number;
  winCount: number;
  loseCount: number;
  drawCount: number;
  winRate: number;
}
