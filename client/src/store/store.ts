import { configureStore, createSlice } from '@reduxjs/toolkit';

export interface RootState {
  isLogin: boolean;
  userInfo: UserInfoType;
  tokens: JwtTokensType;
}

export interface UserClubResponsesType {
  clubId: number;
  // * clubRole: null - 가입신청을 하였으나 아직 처리(승인 또는 거절)가 되지 않은 pending 상태일 때의 값
  clubRole: null | 'MEMBER' | 'MANAGER' | 'LEADER';
  // * ACTIVE: 활동중 | BLACKED: 추방 | QUIT: 탈퇴
  clubMemberStatus: null | 'MEMBER ACTIVE' | 'MEMBER BLACKED' | 'MEMBER QUIT';
  // TODO: joinStatus 항목 안 보이는데 BE에 확인 필요
  joinStatus?: null | 'PENDING' | 'CONFIRMED' | 'REFUSED' | 'BANISHED';
  level: null | string;
  playCount: number;
  player: boolean;
  winCount: number;
  winRate: number;
  userInfo: {
    userId: number | undefined;
    email: string;
    nickName: string;
    userStatus: string;
    profileImage: string;
  };
}

export interface UserInfoType {
  userId: number | undefined;
  email: string;
  nickName: string;
  userStatus: '' | 'USER_NEW' | 'USER_ACTIVE' | 'USER_SLEEP' | 'USER_QUIT';
  profileImage: string;
  userClubResponses?: UserClubResponsesType[];
}

export interface JwtTokensType {
  accessToken: string;
  refreshToken: string;
}

// 전역상태 #1. 로그인 여부
const isLogin = createSlice({
  name: 'isLogin',
  initialState: false,
  reducers: {
    setIsLogin: (state, data) => data.payload
  }
});

// 전역상태 #2. 로그인한 사용자의 정보
const userInitialState: UserInfoType = {
  userId: undefined,
  email: '',
  nickName: '',
  userStatus: '',
  profileImage: '',
  userClubResponses: []
};

const userInfo = createSlice({
  name: 'userInfo',
  initialState: userInitialState,
  reducers: {
    setUserInfo: (state, data) => data.payload
  }
});

// 전역상태 #3. 로그인한 사용자의 jwt 토큰
const tokensInitialState: JwtTokensType = {
  accessToken: '',
  refreshToken: ''
};

const tokens = createSlice({
  name: 'tokens',
  initialState: tokensInitialState,
  reducers: {
    setTokens: (state, data) => data.payload
  }
});

export const { setIsLogin } = isLogin.actions;
export const { setUserInfo } = userInfo.actions;
export const { setTokens } = tokens.actions;

export default configureStore({
  reducer: {
    isLogin: isLogin.reducer,
    userInfo: userInfo.reducer,
    tokens: tokens.reducer
  }
});
