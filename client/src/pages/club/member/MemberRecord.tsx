import Tabmenu from "../../../components/TabMenu";
import S_Container from "../../../components/UI/S_Container";
import MemberNav from "./MemberNav";

function MemberRecord() {
    return (
        <S_Container>
            <Tabmenu />
            <MemberNav />
            소모임 멤버 기록 탭
        </S_Container>
    )
}

export default MemberRecord;