import { combineReducers } from 'redux';
import { configureStore, createSlice } from '@reduxjs/toolkit';
import { useDispatch } from 'react-redux';
import { persistReducer } from 'redux-persist';
import storageSession from 'redux-persist/lib/storage/session';

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
  level: null | string;
  playCount: number;
  player: boolean;
  winCount: number;
  winRate: number;
}

export interface UserInfoType {
  userId: number | undefined;
  email: string;
  nickName: string;
  userStatus: '' | 'USER_NEW' | 'USER_ACTIVE' | 'USER_SLEEP' | 'USER_QUIT';
  profileImage: string;
  userClubResponses: UserClubResponsesType[];
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
export const userInitialState: UserInfoType = {
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
export const tokensInitialState: JwtTokensType = {
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

const rootReducer = combineReducers({
  isLogin: isLogin.reducer,
  userInfo: userInfo.reducer,
  tokens: tokens.reducer
});

const persistConfig = {
  key: 'root',
  storage: storageSession
  // whitelist: ['isLogin', 'userInfo', 'tokens']
};

const persistedReducer = persistReducer(persistConfig, rootReducer);

const store = configureStore({
  reducer: persistedReducer
});

export type DispatchType = typeof store.dispatch;
export const useAppDispatch: () => DispatchType = useDispatch;

export default store;
