import styled from "styled-components";
import Tabmenu from "../../../components/TabMenu";
import S_Container from "../../../components/UI/S_Container";
import MemberNav from "./MemberNav";
import MemberRecordList from "./MemberRecordList";
import MemberRecordTitle from "./MemberRecordTitle";

const S_Table = styled.div`
`

function MemberRecord() {
    return (
        <S_Container>
            <Tabmenu />
            <MemberNav />
            <S_Table>
                <MemberRecordTitle/>
                {/* {data.map(e)=>{
                    <MemberRecordList key={e.name} />
                }} */}
            </S_Table>
        </S_Container>
    )
}

export default MemberRecord;