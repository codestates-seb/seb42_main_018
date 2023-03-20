import { useEffect, useState } from 'react';
import styled from 'styled-components';
import KakaoMapSearch, { PlaceType } from '../../../components/kakao/KakaoMapSearch';
import KakaoMapView from '../../../components/kakao/KakaoMapView';
import S_Container from '../../../components/UI/S_Container';
import { S_Input } from '../../../components/UI/S_Input';
import {
  S_Button,
  S_ButtonGray,
  S_EditButton,
  S_SelectButton,
  S_NegativeButton
} from '../../../components/UI/S_Button';
import { S_Description, S_Label, S_Text, S_Title } from '../../../components/UI/S_Text';
import { S_NameTag } from '../../../components/UI/S_Tag';
// import { StyledSelect } from '../../../components/UI/S_Select';
import AddMemberPopUp from '../../../components/match/AddMemberPopUp';

import { useForm } from 'react-hook-form';
import { MatchDatas } from './CreateMatch';

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
  height: 320px;
  border-radius: 20px;
  padding: 20px;
  section {
    width: 260px;
    border: 1px solid black;
    border-radius: 20px;
    padding: 10px;
  }
`;


function MatchDetail() {
  const [matchDatas, setMatchDatas] = useState<MatchDatas>({
    schedule: {
      date: '2023-03-18',
      time: '21:05',
      placeName: '서울 숲',
      placeCoordinate: {
        latitude: 37.566826,
        longitude: 126.9786567
      }
    },
    canidates: ['test'],
    teamList: [],
    records: []
  });

  const candidates: string[] = [
    '박대운',
    '우제훈',
    '김은택',
    '김아애',
    '문채리',
    '전규언전규언전규',
    '박대운2',
    '우제훈2',
    '김은택2',
    '김아애2',
    '문채리2',
    '전규언전'
  ];
  const [isOpenMapView, setIsOpenMapView] = useState(false);

  const mapViewModalHandler = () => {
    setIsOpenMapView(!isOpenMapView);
  };

  return (
    <S_Container>
      <S_Title>경기 상세</S_Title>
      <div style={{ marginTop: '15px', marginBottom: '15px' }}>
        <S_Label>날짜/시간 선택 *</S_Label>
        <S_Input type='date' value={matchDatas?.schedule.date} readOnly />
        <S_Input type='time' value={matchDatas?.schedule.time} readOnly />
      </div>
      <div style={{ marginTop: '15px', marginBottom: '15px' }}>
        <S_Label>장소 *</S_Label>
        <S_Input type='text' value={matchDatas?.schedule.placeName} readOnly />
        <S_SelectButton onClick={mapViewModalHandler} style={{ width: 'auto' }}>
          지도보기
        </S_SelectButton>
        {isOpenMapView && (
          <S_MapBackdrop onClick={mapViewModalHandler}>
            <S_MapView onClick={(e) => e.stopPropagation()}>
              <KakaoMapView
                y={matchDatas.schedule.placeCoordinate.latitude}
                x={matchDatas.schedule.placeCoordinate.longitude}
              />
            </S_MapView>
          </S_MapBackdrop>
        )}
      </div>
      <div style={{ marginTop: '15px', marginBottom: '15px' }}>
        <S_Label>참석자</S_Label>
        <S_Description>
          경기를 등록하면 경기정보 페이지에서 참석/불참을 선택할 수 있어요.
        </S_Description>
        <S_Description>참석을 선택한 멤버는 자동으로 등록됩니다.</S_Description>
        <div>
          {candidates &&
            candidates.map((member, idx) => {
              return <S_NameTag key={idx}>{member}</S_NameTag>;
            })}
        </div>
      </div>
      <div style={{ marginTop: '15px', marginBottom: '15px' }}>
        <S_Label>팀구성</S_Label>
        {matchDatas.teamList &&
          matchDatas.teamList.map((team, idx) => {
            return (
              <div key={team.id} style={{ display: 'flex', justifyContent: 'space-between' }}>
                <S_Text style={{ margin: '0' }}>{idx + 1}팀</S_Text>
                <div
                  style={{
                    flexGrow: 1,
                    border: 'none',
                    width: '60%',
                    marginLeft: '3px',
                    paddingLeft: '1px',
                    marginBottom: '3px'
                  }}
                >
                  {matchDatas.teamList[idx].members.map((member, memberIdx) => (
                    <S_NameTag key={team.id++}>{member}&times;</S_NameTag>
                  ))}
                </div>
                <div style={{ height: '100%' }}></div>
              </div>
            );
          })}
      </div>
      <div style={{ marginTop: '15px', marginBottom: '15px' }}>
        <S_Label>전적</S_Label>
        <S_Description>경기가 종료된 뒤 결과를 입력해보세요.</S_Description>
        {matchDatas.records &&
          matchDatas.records.map((record, idx) => {
            return (
              <div
                key={record.id}
                style={{ display: 'flex', alignItems: 'center', justifyContent: 'space-between' }}
              >
                <span>{idx + 1}경기</span>

                <select>
                  <option key={idx + 1}>{idx + 1}</option>;
                </select>
                <span>팀</span>
                <S_Input type='number' style={{ margin: '0', height: '30px' }} readOnly />
                <span>:</span>
                <S_Input type='number' style={{ margin: '0', height: '30px' }} readOnly />
                <select>
                  <option key={idx + 2}>{idx + 1}</option>;
                </select>
                <span>팀</span>
                <S_NegativeButton>삭제</S_NegativeButton>
              </div>
            );
          })}
      </div>
      <div></div>
    </S_Container>
  );
}
export default MatchDetail;
