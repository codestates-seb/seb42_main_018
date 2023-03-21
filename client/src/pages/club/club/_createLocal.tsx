import { useEffect, useState } from 'react';
import { DIVISIONS_DATA } from './divisions';
import { CreateCategoryAndLocalProps } from './_createCategory';
import { S_Label } from '../../../components/UI/S_Text';
import { S_Select } from '../../../components/UI/S_Select';

interface DistrictType {
  code: string;
  name: string;
}

interface DivisionType {
  code: string;
  name: string;
  districts: DistrictType[];
}

function CreateLocal({ inputValue, setInputValue }: CreateCategoryAndLocalProps) {
  // division: 지역 1단계 (광역시/도)
  // district: 지역 2단계 (시/군/구)
  const [divisionSelectValue, setDivisionSelectValue] = useState('');
  const [districtSelectValue, setDistrictSelectValue] = useState('');

  const divisionList = DIVISIONS_DATA.map((div) => ({
    code: div.code.slice(0, 2),
    name: div.name
  }));

  const [districtList, setDistrictList] = useState<DistrictType[]>([]);
  useEffect(() => {
    if (divisionSelectValue) {
      const districts = DIVISIONS_DATA.find((d) =>
        d.code.startsWith(divisionSelectValue)
      )?.districts;

      if (districts) setDistrictList(districts);
    }
  }, [divisionSelectValue]);

  useEffect(() => {
    if (divisionSelectValue) {
      // division 코드에 맞는 지역1
      const local1 = divisionList.find((d) => d.code.startsWith(divisionSelectValue))?.name;
      // district 코드에 맞는 지역 2
      const local2 = districtList.find((d: DistrictType) => d.code === districtSelectValue)?.name;

      setInputValue(`${local1} ${local2}`);
    }
  }, [divisionSelectValue, districtSelectValue]);

  const handleSelectChange = (e: React.ChangeEvent<HTMLSelectElement>) => {
    if (e.target.name === 'division') {
      setDivisionSelectValue(e.target.value);
    } else if (e.target.name === 'district') {
      setDistrictSelectValue(e.target.value);
    }
  };

  return (
    <div>
      <label htmlFor='local'>
        <S_Label>지역 *</S_Label>
      </label>
      <S_Select id='local' name='division' onChange={handleSelectChange}>
        <option>선택</option>
        {divisionList.map((d) => (
          <option key={d.code} value={d.code}>
            {d.name}
          </option>
        ))}
      </S_Select>
      <S_Select id='local' name='district' onChange={handleSelectChange}>
        <option>선택</option>
        {districtList &&
          districtList.map((d) => (
            <option key={d.code} value={d.code}>
              {d.name}
            </option>
          ))}
      </S_Select>
    </div>
  );
}

export default CreateLocal;
