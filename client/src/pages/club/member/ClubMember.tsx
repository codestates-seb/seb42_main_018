import Tabmenu from "../../../components/TabMenu";
import S_Container from "../../../components/UI/S_Container";
import ClubMemberItem from "./_memberItem";
import MemberNav from "./_memberNav";

export interface ClubMemberProps {
    // 받아올 멤버 정보 타입 설정
    memberId? : number;
    profileImage: string;
    name: string;
    winRate: string;
}

function ClubMember() {
    // TODO: 하드코딩 데이터로 표시, 추후 axios get 요청 구현
    const data:ClubMemberProps[] = [
        {   
            memberId : 1,
            profileImage: '이미지',
            name: '별명',
            winRate: 'string',
        }
    ]
    
    return (
        <S_Container>
            <Tabmenu />
            <MemberNav />
            {data.map((e) => 
                    <ClubMemberItem
                        key={e.memberId}
                        profileImage={e.profileImage}
                        name={e.name}
                        winRate={e.winRate}
                    />
                )}
        </S_Container>
    )
}

export default ClubMember;