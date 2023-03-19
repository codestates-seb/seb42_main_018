import MainTitle from "./_MainTitle";
import ClubList from "./_ClubList";

export interface ClubTag {
    // 클럽 태그에 타입 설정
    tagId?: number;
    tagName: string;
}
export interface ClubProps {
    // 클럽 아이템에 뿌려줄 데이터 타입 설정
    clubId?: number;
    clubName: string;
    clubImg?: string;
    content: string;
    local: string;
    categoryName: string;
    viewCount?: number;
    memberCount: number;
    tagResponseDtos: ClubTag[];
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
export interface CategoryProps {
  categoryName: string;
}

function MainContents() {
  // TODO : 페이지네이션 기능 추가
  // TODO : data는 추후 axios로 get 요청
  const data:ClubProps[] = [
    {
      clubId: 1,
      clubName: "광진구 풋살클럽",
      clubImg: "https://images.unsplash.com/photo-1575361204480-aadea25e6e68?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxzZWFyY2h8NHx8Zm9vdGJhbGx8ZW58MHx8MHx8&auto=format&fit=crop&w=500&q=60",
      content: "매주 일요일 아침 7시 중랑천 생활체육경기장",
      local: "서울 광진구",
      categoryName: "풋살",
      viewCount: 3,
      memberCount: 26,
      tagResponseDtos: [
        {
          tagId: 1,
          tagName: "20대"
        },
        {
          tagId: 2,
          tagName: "30대"
        },
        {
          tagId: 3,
          tagName: "남녀혼성"
        }
      ],
      modifiedAt: "2023-03-16T17:24:49.050597",
      private: false
    }, 
    {
        clubId: 2,
        clubName: "[여성] 뜨거운 코트를 가르며",
        clubImg: "https://images.unsplash.com/photo-1574623452334-1e0ac2b3ccb4?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxzZWFyY2h8NHx8YmFza2V0YmFsbHxlbnwwfHwwfHw%3D&auto=format&fit=crop&w=500&q=60",
        content: "안녕하세요! 여성 only 농구 경기 진행하고 있습니다",
        local: "서울시 마포구",
        categoryName: "농구",
        viewCount: 3,
        memberCount: 13,
        tagResponseDtos: [
            {
                tagId: 3,
                tagName: "여성"
            },
            {
                tagId: 7,
                tagName: "나이무관"
            }
        ],
        modifiedAt: "2023-03-16T17:24:49.050597",
        private: false
    }, 
    {
        clubId: 3,
        clubName: "제빵왕🍞김탁구🏓",
        clubImg: "https://images.unsplash.com/photo-1611251126118-b1d4f99600a1?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxzZWFyY2h8MTd8fCVFRCU4MyU4MSVFQSVCNSVBQ3xlbnwwfHwwfHw%3D&auto=format&fit=crop&w=500&q=60",
        content: "마포구 대학생 탁구 모임 가입조건 - 남자 20대 - 왕초보 가능 - 가입선물 탁구공 셋트 - 가입비 5,000원 운동 시간 및 장소 - 마포구 농수산물센터 - 화, 목, 토 19:00~21:00 - 주 1회 이상 참여",
        local: "서울시 마포구",
        categoryName: "탁구",
        viewCount: 3,
        memberCount: 12,
        tagResponseDtos: [
            {
                tagId: 1,
                tagName: "20대"
            },
            {
                tagId: 3,
                tagName: "탁구"
            },
            {
                tagId: 4,
                tagName: "탁구왕"
            }
        ],
        modifiedAt: "2023-03-16T17:24:49.050597",
        private: false
    }]
  return (
    <div>
    <MainTitle />
      {data.map((e) => 
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
    </div>
  )
}

export default MainContents