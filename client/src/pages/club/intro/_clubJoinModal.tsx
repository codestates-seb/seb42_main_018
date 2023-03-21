import { useState } from 'react';
import getGlobalState from '../../../util/authorization/getGlobalState';
import styled from 'styled-components';
import { postFetch } from '../../../util/api';
import { ModalBackdrop, ModalContainer } from '../../../components/UI/S_Modal';
import { S_Label } from '../../../components/UI/S_Text';
import { S_TextArea } from '../../../components/UI/S_TextArea';
import { S_Button } from '../../../components/UI/S_Button';

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
}

function ClubJoinModal({ showModal, handleModal }: RegisterModalProps) {
  const { userInfo, tokens } = getGlobalState();
  const [content, setContent] = useState('');

  const onChange = (e: React.ChangeEvent<HTMLTextAreaElement>) => {
    setContent(e.target.value);
  };

  const onsubmit = async () => {
    if (!content) return;

    // TODO 1): joins/:id post 요청 테스트
    // TODO 2): jwt 전달인자에 추가
    const POST_URL = `${process.env.REACT_APP_URL}/joins/${userInfo.userId}`;
    const res = await postFetch(POST_URL, { content });
    if (res) handleModal();
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
