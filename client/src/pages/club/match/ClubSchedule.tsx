import styled from "styled-components";
import ScheduleCard from "../../../components/match/ScheduleCard";
import S_Container from "../../../components/S_Container";

const S_MatchSchedule = styled.div`

`
const S_List = styled.div`
    
`

const S_TapMenu = styled.div`
`


function ClubSchedule() {
    return (
        <S_Container>
            <div>
                <button>새 경기 또는 지난 경기 등록하기 +</button>
            </div>
            <S_TapMenu>
                <button>경기 일정</button>
                <button>지난 경기</button>
            </S_TapMenu>
            <S_MatchSchedule>
                <div>경기 일정</div>
                <div>경기 시작 전까지 참석하기 버튼을 누를 수 있어요.</div>
                <S_List>
                    <ScheduleCard/>
                </S_List>
            </S_MatchSchedule>
        </S_Container>
    )
}

export default ClubSchedule;