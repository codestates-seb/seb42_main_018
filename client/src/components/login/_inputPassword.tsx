import { S_Label, S_Description } from '../UI/S_Text';
import { S_Input } from '../UI/S_Input';

interface InputPasswordProps {
  name: string;
  label: string;
  desc?: string;
  value: string;
  onChange: (e: React.ChangeEvent<HTMLInputElement>) => void;
  errorState?: boolean;
  errorMsg?: string;
}

function InputPassword({
  name,
  label,
  desc,
  value,
  onChange,
  errorState,
  errorMsg
}: InputPasswordProps) {
  return (
    <div>
      <label htmlFor={name}>
        <S_Label>{label}</S_Label>
      </label>
      {desc && <S_Description>{desc}</S_Description>}
      <S_Input
        id={name}
        name={name}
        type='password'
        width='96%'
        value={value}
        onChange={onChange}
      />
      {errorState && <S_Description color={'var(--red100)'}>{errorMsg}</S_Description>}
    </div>
  );
}

export default InputPassword;
