import { useState } from 'react';
import styled from 'styled-components';
import { SubTabProps } from '../types';
import { S_TabButton } from './UI/S_Button';

const S_SubTapBox = styled.div`
  margin-top: 40px;
  margin-bottom: 20px;
`;

function SubTabMenu({ subtabs }: SubTabProps) {
  const [tabIndex, setTabIndex] = useState(0);
  const onClickTap = (idx: number) => {
    setTabIndex(idx);
  };

  return (
    <S_SubTapBox>
      {subtabs.map((el, idx) => (
        <S_TabButton
          key={el.id}
          onClick={() => onClickTap(idx)}
          className={tabIndex === idx ? 'clicked' : ''}
        >
          {el.title}
        </S_TabButton>
      ))}
      <div>{subtabs[tabIndex].contents}</div>
    </S_SubTapBox>
  );
}

export default SubTabMenu;
