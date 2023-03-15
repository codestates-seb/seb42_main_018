import Tabmenu from "../../../components/TabMenu";
import S_Container from "../../../components/UI/S_Container";
import ClubMemberList from "./ClubMemberList";
import MemberNav from "./MemberNav";

function ClubMember() {
    return (
        <S_Container>
            <Tabmenu />
            <MemberNav />
            <ClubMemberList />
            <ClubMemberList />
            <ClubMemberList />
        </S_Container>
    )
}

export default ClubMember;