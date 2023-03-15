import { useState } from 'react';
import styled from 'styled-components';
import KakaoMapSearch, { PlaceType } from '../../../components/kakao/KakaoMapSearch';
import KakaoMapView from '../../../components/kakao/KakaoMapView';
import S_Container from '../../../components/UI/S_Container';
import { S_Input } from '../../../components/UI/S_Input';
import {
  S_Button,
  S_ButtonGray,
  S_EditButton,
  S_NegativeButton
} from '../../../components/UI/S_Button';
import { S_Description, S_Label, S_Text, S_Title } from '../../../components/UI/S_Text';
import { S_Tag } from '../../../components/UI/S_Tag';
import AddMemberPopUp from '../../../components/match/AddMemberPopUp';

const S_MapBackdrop = styled.div`
  background-color: rgba(0, 0, 0, 0.3);
  position: fixed;
  left: 0;
  right: 0;
  top: 0;
  bottom: 0;
  display: flex;
  justify-content: center;
  align-items: center;
  z-index: 100;
`;

const S_MapView = styled.div`
  display: flex;
  flex-direction: column;
  background-color: white;
  width: 300px;
  height: 300px;
  border-radius: 20px;
  justify-content: center;
  padding: 20px;
  section {
    width: 260px;
    border: 1px solid black;
    border-radius: 20px;
    padding: 10px;
  }
`;

function CreateMatch() {
  const [isOpenMapSetting, setIsOpenMapSetting] = useState<boolean>(false);
  const [isOpenMapView, setIsOpenMapView] = useState<boolean>(false);
  const [placeValue, setPlaceValue] = useState<PlaceType>();

  const [teamList, setTeamList] = useState([
    {
      id: 0,
      members: []
    }
  ]);

  //match type을 BE table 구조를 보고 정확하게 정하지 못한상태여서 any타입으로 임시지정.
  const [matchResult, setMatchResult] = useState<any>([]);
  
  const candidates: string[] = ['박대운', '우제훈', '김은택', '김아애', '문채리', '전규언'];

  const [isOpenAddMember, setIsOpenAddMember] = useState(false);
  const [addButtonIndex, setAddButtonIndex] = useState(0)

  const mapSettingModalHandler = (): void => {
    setIsOpenMapSetting(!isOpenMapSetting);
  };

  const mapViewModalHandler = (): void => {
    if (!placeValue) return;
    setIsOpenMapView(!isOpenMapView);
  };

  return (
    <S_Container>
      <S_Title>경기 등록</S_Title>
      <br />
      <div>
        <S_Label>날짜/시간 선택</S_Label>
        <S_Input type='date' />
        <S_Input type='time' />
      </div>
      <br />
      <div>
        <S_Label>장소</S_Label>
        <S_Input type='text' value={placeValue?.place_name} readOnly />
        <button onClick={mapSettingModalHandler}>지도설정</button>
        <button onClick={mapViewModalHandler}>지도보기</button>
        {isOpenMapSetting ? (
          <S_MapBackdrop onClick={mapSettingModalHandler}>
            <S_MapView onClick={(e) => e.stopPropagation()}>
              <KakaoMapSearch
                mapSettingModalHandler={mapSettingModalHandler}
                setPlaceValue={setPlaceValue}
              />
            </S_MapView>
          </S_MapBackdrop>
        ) : null}
        {isOpenMapView ? (
          <S_MapBackdrop onClick={mapViewModalHandler}>
            <S_MapView onClick={(e) => e.stopPropagation()}>
              <KakaoMapView place={placeValue} />
            </S_MapView>
          </S_MapBackdrop>
        ) : null}
      </div>
      <br />
      <div>
        <S_Label>참석자</S_Label>
        <S_Description>
          경기를 등록하면 경기정보 페이지에서 참석/불참을 선택할 수 있어요.
        </S_Description>
        <S_Description>참석을 선택한 멤버는 자동으로 등록됩니다. </S_Description>
        {candidates &&
          candidates.map((member: string, idx: number) => {
            return <S_Tag key={idx}>{member}</S_Tag>;
          })}
        <S_EditButton>추가</S_EditButton>
      </div>
      <br />
      <div style={{position: "relative"}}>
        <S_Label>팀구성</S_Label>
        {teamList &&
          teamList.map((team, idx) => {
            return (
              <>
                <div key={team.id} style={{ display: 'flex'}}>
                  <S_Text>{idx + 1}팀</S_Text>
                  <div style={{ border: 'none', width: '350px', marginLeft: '3px', paddingLeft: '1px'}}></div>
                  {/* <select>
                    {candidates.map((member,idx) => <option key={idx} value={member}>{member}</option>)}
                  </select> */}
                  <S_EditButton
                    onClick={() => {
                      setAddButtonIndex(idx);
                      console.log(addButtonIndex);
                      setIsOpenAddMember(!isOpenAddMember);
                    }}
                  >
                    추가
                  </S_EditButton>
                  <S_NegativeButton
                    onClick={() => {
                      const deleted = [...teamList];
                      deleted.splice(idx, 1);
                      // deleted.forEach((el,idx) => el.id = idx+1)
                      setTeamList(deleted);
                    }}
                  >
                    삭제
                  </S_NegativeButton>
                </div>
              </>
            );
          })}
          {isOpenAddMember ? <AddMemberPopUp top={(addButtonIndex + 1) * 32} candidates={candidates}/> : null}
        <S_ButtonGray
          onClick={() => {
            const newTeam = {
              id: teamList.length ? teamList[teamList.length - 1].id + 1 : 0,
              members: []
            };
            setTeamList([...teamList, newTeam]);
          }}
        >
          팀 구성 목록 추가 +
        </S_ButtonGray>
      </div>
      <br />
      <div>
        <S_Label>경기 결과</S_Label>
        <S_Description>경기가 종료된 뒤 결과를 입력해보세요.</S_Description>
        {matchResult &&
          matchResult.map((match: any, idx: number) => {
            //match type을 BE table 구조를 보고 정확하게 정하지 못한상태여서 any타입으로 임시지정.
            return (
              <div key={match?.id}>
                <span>{idx + 1}경기</span>
                <select>
                  {teamList &&
                    teamList.map((team, idx) => {
                      return <option key={idx}>{idx + 1}</option>;
                    })}
                </select>
                팀<S_Input type='number'></S_Input>:<S_Input type='number'></S_Input>
                <select>
                  {teamList &&
                    teamList.map((team, idx) => {
                      return <option key={idx}>{idx + 1}</option>;
                    })}
                </select>
                팀
                <S_NegativeButton
                  onClick={() => {
                    const deleted = [...matchResult];
                    deleted.splice(idx, 1);
                    // deleted.forEach((el,idx) => el.id = idx+1)
                    setMatchResult(deleted);
                  }}
                >
                  삭제
                </S_NegativeButton>
              </div>
            );
          })}
        <S_ButtonGray
          onClick={() => {
            const newResult = {
              id: matchResult.length ? matchResult[matchResult.length - 1].id + 1 : 0,
              winner: [],
              loser: []
            };
            setMatchResult([...matchResult, newResult]);
          }}
        >
          경기 결과 목록 추가+
        </S_ButtonGray>
      </div>
      <div>
        <S_Button>저장하기 +</S_Button>
      </div>
    </S_Container>
  );
}

export default CreateMatch;
