import styled from 'styled-components';
import { S_Tag } from '../../components/UI/S_Tag';

interface AddMemberPopUpProps {
    top: number,
    candidates?: string[]
}

const S_PopupContainer = styled.div<{top?: number}>`
    display: grid;
    grid-template-columns: repeat(4, 1fr);
    position: absolute;
    width: 60%;
    height: auto;
    border-radius: 5px;
    z-index: 9;
    background-color: var(--white);
    top: ${(props) => props.top || 0}px;
    left: 35%;
    padding: 10px;
    box-shadow: rgba(0, 0, 0, 0.16) 0px 1px 4px;
`

function AddMemberPopUp(props: AddMemberPopUpProps) {
    const TEAM_ELEMENT_HEIGHT = 34;
    return (
        <S_PopupContainer top={TEAM_ELEMENT_HEIGHT+props.top}>
            {props.candidates && props.candidates.map((member, idx) => {
                return (
                    <S_Tag key={idx}>{member}</S_Tag>
                )
            })}
        </S_PopupContainer>
    )
}

export default AddMemberPopUp;