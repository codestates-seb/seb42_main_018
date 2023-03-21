import { useNavigate } from 'react-router-dom';
import styled from 'styled-components';
import { S_EditButton } from '../UI/S_Button';
import { S_Label, S_SmallDescription } from '../UI/S_Text';

interface userInfoType {
  userId: number | undefined;
  email: string;
  nickName: string;
  userStatus: string;
}

const S_profileBox = styled.div`
  // 전체 컨테이너
  display: flex;
  align-items: center;
  background-color: var(--gray100); // 크기 확인하기 위한 색상
  margin-bottom: 30px;
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

function UserProfile({ userId, email, nickName, userStatus }: userInfoType) {
  // TODO : 받아온 유저 프로필 정보 뿌려주기
  // TODO : 필요정보 -> 프로필이미지, 별명
  const navigate = useNavigate();

  return (
    <S_profileBox>
      <S_ImgBox img='' />
      <div>
        <S_Label>벌꿀오소리{userId}</S_Label>
        <S_EditButton onClick={() => navigate('edit')}>프로필 수정</S_EditButton>
      </div>
    </S_profileBox>
  );
}

export default UserProfile;
