import styled from "styled-components";
import { S_SelectButton } from "../UI/S_Button";
import { S_Label, S_Text } from "../UI/S_Text";

const S_CardContainer = styled.div`
    display: flex;
    flex-direction: row;
    justify-content: space-between;
    
`
const S_Information = styled.div`
    display: flex;
    flex-direction: column;
`

const S_ButtonContainer = styled.div`
    display: flex;
    align-items: center;
`

function ScheduleCard() {
    return (
        <>
        <hr style={{margin:"20px 0"}}/>
        <S_CardContainer>
            <S_Information>
                <S_Text>3월 11일 목요일 17:00</S_Text>
                <S_Label>도봉구 약수터 풋살장</S_Label>
            </S_Information>
            <S_ButtonContainer>
                <S_SelectButton>참석</S_SelectButton>
                <S_SelectButton>불참</S_SelectButton>
            </S_ButtonContainer>
        </S_CardContainer>
        </>
    )
}

export default ScheduleCard;