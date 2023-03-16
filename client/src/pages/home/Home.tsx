import ClubList from "./_ClubList";

export interface ClubTag {
    tagId?: number;
    tagName: string;
  }
  
  export interface ClubProps {
    clubId?: number;
    clubName: string;
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
    page: number;
    size: number;
    totalElements: number;
    totalPages: number;
  }

function Home() {

    const data:ClubProps[] = [{
        clubId: 1,
        clubName: "배드민턴 하자",
        content: "재밌게 배드민턴 쳐요",
        local: "주소를 변경합니다",
        categoryName: "배드민턴",
        viewCount: 3,
        memberCount: 1,
        tagResponseDtos: [
            {
                tagId: 6,
                tagName: "수정한 태그1"
            },
            {
                tagId: 7,
                tagName: "수정한 태그2"
            }
        ],
        modifiedAt: "2023-03-16T17:24:49.050597",
        private: false
    }, 
    {
        clubId: 1,
        clubName: "배드민턴 하자",
        content: "재밌게 배드민턴 쳐요",
        local: "주소를 변경합니다",
        categoryName: "배드민턴",
        viewCount: 3,
        memberCount: 3,
        tagResponseDtos: [
            {
                tagId: 6,
                tagName: "수정한 태그1"
            },
            {
                tagId: 7,
                tagName: "수정한 태그2"
            }
        ],
        modifiedAt: "2023-03-16T17:24:49.050597",
        private: false
    }]

    return (
        <div>
            {data.map((e) => 
                <ClubList 
                    key={e.clubId}
                    clubName={e.clubName}
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

export default Home;