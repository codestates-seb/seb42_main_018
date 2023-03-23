export const handleKakaoLogin = async () => {
  const KAKAO_LOGIN_URL = `${process.env.REACT_APP_URL}/oauth2/authorization/kakao?returnurl=/mypage`;
  return window.location.assign(KAKAO_LOGIN_URL);
};
