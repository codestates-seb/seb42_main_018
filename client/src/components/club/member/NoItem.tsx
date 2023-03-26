import styled from 'styled-components';
import { S_Label } from '../../UI/S_Text';

const S_Box = styled.div`
  margin-top: 10vh;
  text-align: center;
  img {
    width: 70%;
    margin-top: 3vh;
  }
`;

interface NoItemProps {
  src: string;
  alt: string;
  label: string;
}

function NoItem({ src, alt, label }: NoItemProps) {
  return (
    <S_Box>
      <S_Label>{label}</S_Label>
      <img src={src} alt={alt} />
    </S_Box>
  );
}

export default NoItem;
