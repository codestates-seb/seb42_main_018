import { S_Label, S_Description } from '../UI/S_Text';
import { S_Input } from '../UI/S_Input';

interface InputNicknameProps {
  value: string;
  onChange: (e: React.ChangeEvent<HTMLInputElement>) => void;
}

function InputNickname({ value, onChange }: InputNicknameProps) {
  return (
    <div>
      <label htmlFor='nickname'>
        <S_Label>닉네임</S_Label>
      </label>
      <S_Description>닉네임은 언제든지 바꿀 수 있어요.</S_Description>
      <S_Input
        id='nickname'
        name='nickName'
        type='text'
        width='96%'
        value={value}
        onChange={onChange}
      />
    </div>
  );
}

export default InputNickname;
