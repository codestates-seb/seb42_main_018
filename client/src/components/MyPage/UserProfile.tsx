import { useNavigate } from 'react-router-dom';
import styled from 'styled-components';
import { S_EditButton } from '../UI/S_Button';
import { S_Label, S_SmallDescription } from '../UI/S_Text';
import { UserInfoType } from '../../store/store';

const S_profileBox = styled.div`
  // 전체 컨테이너
  display: flex;
  align-items: center;
  padding-bottom: 20px;
  margin-bottom: 40px;
  border-bottom: 1px solid var(--gray100);
`;

const S_ImgBox = styled.div<{ img?: string }>`
  // 유저 프로필이미지
  margin-right: 20px;
  min-width: 80px;
  height: 80px;
  border-radius: 10px;
  background-size: cover;
  background-position: center center;
  background-color: var(--gray200); // 위치 확인하기 위한 색상
  background-image: url(${(props) => props.img});
`;

function UserProfile({ userId, profileImage }: UserInfoType) {
  const navigate = useNavigate();

  return (
    <S_profileBox>
      <S_ImgBox img={profileImage} />
      <div>
        <S_Label>{userId}</S_Label>
        <S_EditButton onClick={() => navigate('edit')}>프로필 수정</S_EditButton>
      </div>
    </S_profileBox>
  );
}

export default UserProfile;
