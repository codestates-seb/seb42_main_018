import MemberWaitingCard from '../../../components/setting/MemberWaitingCard';

export interface WaitingUser {
  id: number;
  nickName: string;
  contents: string;
  email?: string;
  userStatus?: string;
  profileImage?: string;
}

function WaitingMember() {
  const data: WaitingUser[] = [
    {
      id: 0,
      nickName: '탁구왕김제빵',
      contents:
        '탁구 초보입니다. 잘부탁드립니다.탁구 초보입니다. 잘부탁드립니다.탁구 초보입니다. 잘부탁드립니다.탁구 초보입니다. 잘부탁드립니다.'
    },
    {
      id: 1,
      nickName: '배드민턴고수',
      contents: '고수가 되고싶은사람입니다. 승인해주시죠'
    },
    {
      id: 2,
      nickName: '풋살왕슛돌이',
      contents: '득점왕이 되고싶어요'
    }
  ];
  return (
    <>
      {data.map((el) => (
        <MemberWaitingCard key={el.id} member={el} />
      ))}
    </>
  );
}

export default WaitingMember;
