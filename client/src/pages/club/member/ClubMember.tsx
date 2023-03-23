import { useParams } from 'react-router-dom';
import Tabmenu from '../../../components/TabMenu';
import S_Container from '../../../components/UI/S_Container';
import MemberRecord from './MemberRecord';
import SubTabMenu from '../../../components/SubTabMenu';
import MemberList from './MemberList';
import { MemberProps, MemberData } from '../../../types';
import { useEffect, useState } from 'react';
import { getFetch } from '../../../util/api';

function ClubMember() {
  const { id } = useParams();

  // TODO: 하드코딩 데이터로 표시, 추후 axios get 요청 구현
  const [members, setMembers] = useState<MemberData[]>([]); // 뿌려줄 멤버 리스트
  useEffect(() => {
    getFetch(`${process.env.REACT_APP_URL}/user/clubs/${id}`).then((data) => {
      const members: MemberData[] = data.data;
      setMembers(members);
    });
  }, []);
  // const data: MemberProps = {
  //   data: [
  //     {
  //       nickName: 'John',
  //       profileImage: {
  //         imageId: 1,
  //         fileName: 'image.jpg',
  //         url: 'https://avatars.githubusercontent.com/u/115607789?s=64&v=4'
  //       },
  //       playCount: 10,
  //       winCount: 7,
  //       loseCount: 2,
  //       drawCount: 1,
  //       winRate: 0.7
  //     },

  //     {
  //       nickName: 'Jane',
  //       profileImage: {
  //         imageId: 2,
  //         fileName: 'image.jpg',
  //         url: 'https://avatars.githubusercontent.com/u/115607789?s=64&v=4'
  //       },
  //       playCount: 15,
  //       winCount: 5,
  //       loseCount: 8,
  //       drawCount: 2,
  //       winRate: 0.33
  //     }
  //   ],
  //   pageInfo: {
  //     page: 1,
  //     size: 5,
  //     totalElements: 2,
  //     totalPages: 1
  //   }
  // };
  console.log(members);

  // 상단탭
  const tabs = [
    { id: 1, title: '소개', path: `/club/${id}` },
    { id: 2, title: '경기정보', path: `/club/${id}/match` },
    { id: 3, title: '멤버', path: `/club/${id}/member` }
  ];

  // 서브 버튼탭
  const subtabs = [
    {
      id: 1,
      title: '전체 멤버',
      contents: <MemberList members={members} />
    },
    { id: 2, title: '멤버 기록', contents: <MemberRecord members={members} /> }
  ];

  return (
    <S_Container>
      <Tabmenu tabs={tabs} />
      <SubTabMenu subtabs={subtabs} />
    </S_Container>
  );
}

export default ClubMember;
