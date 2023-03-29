import { useEffect, useState } from 'react';
import { useParams } from 'react-router-dom';
import MemberUserCard from '../../../components/setting/MemberUserCard';
import { getFetch } from '../../../util/api';
import getGlobalState from '../../../util/authorization/getGlobalState';

export interface MemberUser {
  userId: number;
  nickName: string;
  profileImage: string;
  playCount: number;
  winCount: number;
  loseCount: number;
  drawCount: number;
  winRate: number;
  clubMemberStatus: string;
  clubRole: string;
}

function TotalMember() {
  const { id } = useParams();
  const { tokens } = getGlobalState();
  const [totalMembers, setTotalMembers] = useState<MemberUser[]>([]);

  useEffect(() => {
    getFetch(`${process.env.REACT_APP_URL}/clubs/${id}/members`, tokens).then((data) => {
      setTotalMembers(data.data);
    });
  }, []);
  return (
    <>
      {totalMembers?.map((el) => (
        <MemberUserCard key={el.userId} member={el} />
      ))}
    </>
  );
}

export default TotalMember;
