import { useState } from 'react';
import styled from 'styled-components';
import KakaoMapSearch, { placeType } from '../../../components/kakao/KakaoMapSearch';
import KakaoMapView from '../../../components/kakao/KakaoMapView';
import S_Container from '../../../components/S_Container';

const MapBackdrop = styled.div`
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

const MapView = styled.div`
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
  const [isOpenMapSetting, setIsOpenMapSetting] = useState(false);
  const [isOpenMapView, setIsOpenMapView] = useState(false);
  const [placeValue, setPlaceValue] = useState<placeType>();

  const mapSettingModalHandler = () => {
    setIsOpenMapSetting(!isOpenMapSetting)
  }

  const mapViewModalHandler = () => {
    setIsOpenMapView(!isOpenMapView);
  }
  const TeamList = [
    {
      name: 'A팀'
    }
  ];
  return (
    <S_Container>
      <div>경기 등록</div>
      <div>
        <div>날짜/시간 선택</div>
        <input type='date' />
        <input type='time' />
      </div>
      <div>
        <div>장소</div>
        <input type='text' value={placeValue?.place_name} readOnly/>
        <button onClick={mapSettingModalHandler}>지도설정</button>
        <button onClick={mapViewModalHandler}>지도보기</button>
        {
          isOpenMapSetting ? 
          <MapBackdrop onClick={mapSettingModalHandler}>
            <MapView onClick={(e) => e.stopPropagation()}>
              <KakaoMapSearch mapSettingModalHandler={mapSettingModalHandler} setPlaceValue={setPlaceValue}/>
            </MapView>
          </MapBackdrop>
          : null
        }
        {
          isOpenMapView ?
          <MapBackdrop onClick={mapViewModalHandler}>
            <MapView onClick={(e) => e.stopPropagation()}>
              <KakaoMapView place={placeValue}/>
            </MapView>
          </MapBackdrop>
          : null
        }
        <div>참석자</div>
        <div>경기를 등록하면 경기정보 페이지에서 참석/불참 여부를 받을 수 있어요.</div>
        <div>참석을 선택한 멤버는 자동으로 등록됩니다. </div>
        <button>+</button>
      </div>
      <div>
        <div>팀구성</div>
      </div>
    </S_Container>
  );
}

export default CreateMatch;
