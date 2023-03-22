import React from 'react';
import ClubList from '../home/ClubList';
import { S_EditButton } from '../UI/S_Button';
import { clubDataAll } from '../home/ClubListData';

function ClubListSetting() {
  const data = clubDataAll.data;
  console.log(data); // API로 가져올 데이터 임시

  return (
    <div>
      {data.map((e) => (
        <ClubList
          key={e.clubId}
          clubId={e.clubId}
          clubName={e.clubName}
          profileImage={e.profileImage}
          content={e.content}
          local={e.local}
          categoryName={e.categoryName}
          memberCount={e.memberCount}
          tagResponseDtos={e.tagResponseDtos}
        />
      ))}
    </div>
  );
}

export default ClubListSetting;
