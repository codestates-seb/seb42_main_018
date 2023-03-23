import { configureStore, createSlice } from '@reduxjs/toolkit';

export interface RootState {
  isLogin: boolean;
  userInfo: UserInfoType;
  tokens: JwtTokensType;
}

export interface UserClubResponsesType {
  userClubId: number;
  isPlayer: boolean;
  clubRole: string;
  playCount: number;
  winCount: number;
  winRate: number;
}
export interface UserInfoType {
  userId: number | undefined;
  email: string;
  nickName: string;
  userStatus: string;
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
