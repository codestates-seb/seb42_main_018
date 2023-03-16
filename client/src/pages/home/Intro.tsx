
import Tabmenu from "../../components/TabMenu";
import { S_Button, S_ButtonBlack, S_ButtonGray, S_SelectButton, S_EditButton, S_NegativeButton } from "../../components/UI/S_Button";
import S_Container from "../../components/UI/S_Container";
import { S_Input } from "../../components/UI/S_Input";
import { S_Select } from "../../components/UI/S_Select";
import { S_Tag, S_TagSmall } from "../../components/UI/S_Tag";
import { S_Description, S_Label, S_SmallDescription, S_Text, S_Title } from "../../components/UI/S_Text";
import { S_TextArea } from "../../components/UI/S_TextArea";


function Intro() {
    return (
        <S_Container>
            <Tabmenu/>
            <S_Title>가장 큰 글씨는 이렇게 보입니다</S_Title>
            <S_Label>두번째 제목이나 라벨링은 이렇구요</S_Label>
            <S_Text>안녕하세요~ 가입신청합니다. 혼자 하려고 하다보니 부족한 점이 많아 신청하게 되었습니다! 잘부탁드립니다! 텍스트 세 줄은 어떻게 표시되는지 글자 크기와 줄간격을 살피기 위한 부분입니다.</S_Text>
            <S_Description>설명글은 이렇게 확인할 수 있습니다.</S_Description>
            <S_SmallDescription>탁구 / 서울특별시 마포구/ 인원 12명</S_SmallDescription>
            <S_Button>기본버튼</S_Button>
            <S_ButtonGray>회색버튼</S_ButtonGray>
            <S_ButtonBlack>검정버튼</S_ButtonBlack>
            <S_SelectButton>선택</S_SelectButton>
            <S_EditButton>설정/편집버튼</S_EditButton>
            <S_NegativeButton>삭제/부정버튼</S_NegativeButton>
            <S_Input placeholder='여기에 내용을 입력해주세요' />
            <S_Input type="number" placeholder='점수'/>
            <S_Input type="date" />
            <S_Input type="time" />
            <S_TextArea placeholder='여기에 내용을 입력해주세요'/>
            <S_Select />
            <S_Tag>보통 태그</S_Tag>
            <S_TagSmall>작은 태그</S_TagSmall>
        </S_Container>
    )
}

export default Intro;