import MemberUserCard from '../../../components/setting/MemberUserCard';

export interface MemberUser {
  id: number;
  nickName: string;
  winRate: number;
}

function TotalMember() {
  const data: MemberUser[] = [
    {
      id: 0,
      nickName: '탁구왕김제빵',
      winRate: 100
    },
    {
      id: 1,
      nickName: '배드민턴고수',
      winRate: 100
    },
    {
      id: 2,
      nickName: '풋살왕슛돌이',
      winRate: 100
    }
  ];
  return (
    <>
      {data.map((el) => (
        <MemberUserCard key={el.id} member={el} />
      ))}
    </>
  );
}

export default TotalMember;
