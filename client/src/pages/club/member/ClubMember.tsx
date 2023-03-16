import Tabmenu from "../../../components/TabMenu";
import S_Container from "../../../components/UI/S_Container";
import ClubMemberList from "./ClubMemberList";
import MemberNav from "./MemberNav";
import MemberRecord from "./MemberRecord";

export interface ClubMemberProps {
    memberId : number;
    profileImage: string;
    name: string;
    email?: string;
    age?: number;
    gender?: string;
    winRate: string;
    match?: string;
    win?: string;
    lose?: string;
}

function ClubMember() {
    
    const DataData:ClubMemberProps = {
            memberId : 1,
            name : "John Doe",
            email : "john.doe@example.com",
            age : 30,
            gender : "남",
            profileImage : "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQcU50X1UOeDaphmUyD6T8ROKs-HjeirpOoapiWbC9cLAqewFy1gthrgUTB9E7nKjRwOVk&usqp=CAU",
            winRate: '70',
            match: '10',
            win: '7',
            lose: '3',
    }

    return (
        <S_Container>
            <Tabmenu />
            <MemberNav />
                <ClubMemberList 
                    memberId={DataData.memberId}
                    profileImage={DataData.profileImage}
                    name={DataData.name}
                    winRate={DataData.winRate} 
                    />
                <MemberRecord />
        </S_Container>
    )
}

export default ClubMember;