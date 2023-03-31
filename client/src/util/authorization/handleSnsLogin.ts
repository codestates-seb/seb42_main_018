export const handleKakaoLogin = () => {
  const KAKAO_LOGIN_URL = `${process.env.REACT_APP_URL}/oauth2/authorization/kakao`;
  return window.location.assign(KAKAO_LOGIN_URL);
};
