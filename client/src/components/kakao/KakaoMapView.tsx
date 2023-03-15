import { useEffect } from 'react';

const { kakao } = window as any;

function KakaoMapView(props:any) {
  useEffect(() => {
    console.log(props.place)
    const mapContainer = document.getElementById('map'), // 지도를 표시할 div
      mapOption = {
        center: new kakao.maps.LatLng(props.place.y, props.place.x), // 지도의 중심좌표
        level: 3 // 지도의 확대 레벨
      };

    // 지도를 표시할 div와  지도 옵션으로  지도를 생성합니다
    const map = new kakao.maps.Map(mapContainer, mapOption);

    // 마커가 표시될 위치입니다
    const markerPosition = new kakao.maps.LatLng(props.place.y, props.place.x);

    // 마커를 생성합니다
    const marker = new kakao.maps.Marker({
      position: markerPosition
    });

    // 마커가 지도 위에 표시되도록 설정합니다
    marker.setMap(map);
  }, []);
  return <div id='map' style={{ width: '300px', height: '300px' }} />;
}

export default KakaoMapView;
