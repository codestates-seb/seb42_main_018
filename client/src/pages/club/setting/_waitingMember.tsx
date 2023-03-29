import { useEffect, useState } from 'react';
import { useParams } from 'react-router-dom';
import MemberWaitingCard from '../../../components/setting/MemberWaitingCard';
import { getFetch } from '../../../util/api';
import getGlobalState from '../../../util/authorization/getGlobalState';

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
  const [isUpdated, setIsUpdated] = useState(false);
  const { tokens } = getGlobalState();
  const { id } = useParams();
  useEffect(() => {
    getFetch(`${process.env.REACT_APP_URL}/clubs/${id}/joins`, tokens).then((data) => {
      setData(data.data);
    });
  }, [isUpdated]);
  return (
    <>
      {data
        ?.filter((ele) => ele.joinStatus === 'PENDING')
        .map((el) => (
          <MemberWaitingCard
            key={el.userInfo.userId}
            member={el}
            setIsUpdated={setIsUpdated}
            isUpdated={isUpdated}
          />
        ))}
    </>
  );
}

export default WaitingMember;
