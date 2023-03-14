import styled from "styled-components";

const S_Card = styled.div`
    display: flex;
    flex-direction: row;
    justify-content: space-between;
    
`
const S_Information = styled.div`
    display: flex;
    flex-direction: column;
`

const S_ButtonContainer = styled.div`
    
`
const S_Date = styled.span`
`
const S_Place = styled.span`
    
`

function ScheduleCard() {
    return (
        <S_Card>
            <S_Information>
                <S_Date>3월 11일 목요일 17:00</S_Date>
                <S_Place>도봉구 약수터 풋살장</S_Place>
            </S_Information>
            <S_ButtonContainer>
                <button>참석</button>
                <button>불참</button>
            </S_ButtonContainer>
        </S_Card>
    )
}

export default ScheduleCard;