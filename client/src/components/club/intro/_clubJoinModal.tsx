import { useState } from 'react';
import { useParams } from 'react-router-dom';
import getGlobalState from '../../../util/authorization/getGlobalState';
import styled from 'styled-components';
import { postFetch } from '../../../util/api';
import { ModalBackdrop, ModalContainer } from '../../UI/S_Modal';
import { S_Label } from '../../UI/S_Text';
import { S_TextArea } from '../../UI/S_TextArea';
import { S_Button } from '../../UI/S_Button';

const S_RegisterModalContainer = styled(ModalContainer)`
  box-sizing: border-box;
  padding: 30px 20px;
  width: 300px;
  height: 340px;
  display: flex;
  flex-direction: column;
  justify-content: space-between;

  & > .modal-textarea-area {
    padding: 10px;
    margin: 0;
    height: 140px;
  }
`;

const S_ButtonBox = styled.div`
  width: 100%;
  display: flex;
  justify-content: space-between;
`;

interface RegisterModalProps {
  showModal: boolean;
  handleModal: () => void;
  setIsApplied: React.Dispatch<React.SetStateAction<boolean>>;
}

function ClubJoinModal({ showModal, handleModal, setIsApplied }: RegisterModalProps) {
  const { userInfo, tokens } = getGlobalState();
  const { id: clubId } = useParams();
  const [content, setContent] = useState('');

  const onChange = (e: React.ChangeEvent<HTMLTextAreaElement>) => {
    setContent(e.target.value);
  };

  const onsubmit = async () => {
    if (!content) return;

    const POST_URL = `${process.env.REACT_APP_URL}/clubs/${clubId}/joins/${userInfo.userId}`;

    const res = await postFetch(POST_URL, { content }, tokens);
    // console.log(res);

    // TODO : 모달이 떠있는 채로 alert를 띄우면 모바일에서 겹쳐 보이는지 확인
    if (res) {
      setContent('');
      setIsApplied(true);
      handleModal();
      alert('가입신청을 완료했어요. 마이페이지에서 가입신청 승인 현황을 확인할 수 있습니다.');
    }
  };

  return (
    <>
      {showModal && (
        <ModalBackdrop onClick={handleModal}>
          <S_RegisterModalContainer onClick={(e) => e.stopPropagation()}>
            <S_Label>가입 신청글을 작성해주세요</S_Label>
            <S_TextArea
              className='modal-textarea-area'
              maxLength={200}
              placeholder='글자수 제한 200자'
              value={content}
              onChange={onChange}
            ></S_TextArea>
            <S_ButtonBox>
              <S_Button
                addStyle={{ width: '48%' }}
                onClick={() => {
                  onsubmit();
                }}
              >
                가입신청
              </S_Button>
              <S_Button
                addStyle={{
                  width: '48%',
                  backgroundColor: 'var(--gray100)',
                  color: 'var(--gray400)',
                  hoverBgColor: 'var(--gray200)'
                }}
                onClick={handleModal}
              >
                취소
              </S_Button>
            </S_ButtonBox>
          </S_RegisterModalContainer>
        </ModalBackdrop>
      )}
    </>
  );
}

export default ClubJoinModal;
