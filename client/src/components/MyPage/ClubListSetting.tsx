import React from 'react';
import ClubList from '../home/ClubList';
import { S_EditButton } from '../UI/S_Button';
import { clubDataAll } from '../home/ClubListData';

function ClubListSetting() {
  const data = clubDataAll.data;
  console.log(data); // API로 가져올 데이터 임시

  return (
    <div>
      {data.map((el) => (
        <ClubList
          key={el.clubId}
          clubId={el.clubId}
          clubName={el.clubName}
          clubImage={el.profileImage}
          content={el.content}
          local={el.local}
          categoryName={el.categoryName}
          memberCount={el.memberCount}
          tagResponseDtos={el.tagResponseDtos}
        />
      ))}
    </div>
  );
}

export default ClubListSetting;
