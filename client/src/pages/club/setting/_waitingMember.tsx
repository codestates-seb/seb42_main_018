import axios from 'axios';
import { useEffect, useState } from 'react';
import { useParams } from 'react-router-dom';
import MemberWaitingCard from '../../../components/setting/MemberWaitingCard';
import { getFetch } from '../../../util/api';

export interface WaitingUser {
  userClubId: number;
  content: string;
  clubRole: string;
  joinStatus: string;
  userInfo: {
    userId: number;
    nickName: string;
    email: string;
    profileImage: string;
  };
}

function WaitingMember() {
  const [data, setData] = useState<WaitingUser[]>([]);
  // const data: WaitingUser[] = [];
  const { id } = useParams();
  useEffect(() => {
    axios.get(`${process.env.REACT_APP_URL}/clubs/${id}/joins`).then((res) => setData(res.data));
    // getFetch(`${process.env.REACT_APP_URL}/clubs/${id}/joins`);
  }, []);
  return (
    <>
      {data?.map((el) => (
        <MemberWaitingCard key={el.userInfo.userId} member={el} />
      ))}
    </>
  );
}

export default WaitingMember;
