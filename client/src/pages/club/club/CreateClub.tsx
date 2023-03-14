import CreateCategory from './createCategory';
import CreateTag from './CreateTag';

function CreateClub() {
  const sampleData = {
    clubName: '배사모',
    content: '배드민턴을 사랑하는 사람들 모임',
    local: '서울특별시 강남구',
    categoryName: '배드민턴',
    tagName: ['20대', '자율참가'],
    isPrivate: false
  };

  return (
    <form>
      <h2>신규 소모임 만들기</h2>
      <div>
        <label htmlFor='clubName'>소모임 이름 *</label>
        <input id='clubName' type='text' />
      </div>
      <div>
        <label htmlFor='content'>소모임 소개글 *</label>
        <textarea
          id='content'
          placeholder='소모임 소개와 함께 가입조건, 모임장소 및 날짜를 입력해 보세요.'
        />
      </div>
      <CreateCategory />
      <div>
        {/* TODO: 지역 두 단계로 나눠서 입력받을 수 있는 api 찾아봐야함 */}
        <label htmlFor='local'>지역 선택 *</label>
        <select id='local1'>
          <option>선택</option>
          <option value='11'>서울특별시</option>
        </select>
        <select id='local2'>
          <option>선택</option>
        </select>
      </div>
      <CreateTag />
      <fieldset>
        <legend>공개여부 선택 *</legend>
        <label htmlFor='public'>공개</label>
        <input type='radio' id='public' name='isPrivate' defaultChecked />
        <label htmlFor='private'>비공개</label>
        <input type='radio' id='private' name='isPrivate' />
      </fieldset>
      <button>소모임 만들기</button>
    </form>
  );
}

export default CreateClub;
