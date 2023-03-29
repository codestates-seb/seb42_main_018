import styled from 'styled-components';
import { S_Label, S_Description } from '../UI/S_Text';
import { S_SelectButton } from '../UI/S_Button';
import { S_Input } from '../UI/S_Input';

export const S_InputWrapper = styled.div`
  height: 120px;
`;

interface InputEmailProps {
  value: string;
  onChange: (e: React.ChangeEvent<HTMLInputElement>) => void;
  errorState: boolean;
  hasDuplicationCheckButton?: boolean;
  onClick?: () => void;
}

function InputEmail({
  value,
  onChange,
  errorState,
  hasDuplicationCheckButton,
  onClick
}: InputEmailProps) {
  return (
    <S_InputWrapper>
      <label htmlFor='email'>
        <S_Label>이메일</S_Label>
      </label>
      <div className='email-input-area'>
        <S_Input
          id='email'
          name='email'
          type='text'
          width='96%'
          value={value}
          onChange={onChange}
        />
        {hasDuplicationCheckButton && (
          <S_SelectButton
            type='button'
            width='auto'
            style={{ whiteSpace: 'nowrap' }}
            onClick={onClick}
          >
            중복 확인
          </S_SelectButton>
        )}
      </div>

      {errorState && (
        <S_Description color={'var(--red100)'}>유효하지 않은 형식의 이메일입니다.</S_Description>
      )}
    </S_InputWrapper>
  );
}

export default InputEmail;
