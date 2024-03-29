import ReactDOM from 'react-dom/client';
import axios from 'axios';
import { Provider } from 'react-redux';
import { persistStore } from 'redux-persist';
import { PersistGate } from 'redux-persist/integration/react';
import store from './store/store';
import App from './App';

import CreateClub from './pages/club/club/CreateClub';
import EditClub from './pages/club/club/EditClub';
import ClubIntro from './pages/club/intro/ClubIntro';
import ClubSetting from './pages/club/setting/ClubSetting';
import MemberSetting from './pages/club/setting/MemberSetting';
import ClubSchedule from './pages/club/match/ClubSchedule';
import CreateMatch from './pages/club/match/CreateMatch';
import EditMatch from './pages/club/match/EditMatch';
import MatchDetail from './pages/club/match/MatchDetail';
import ClubMember from './pages/club/member/ClubMember';
import Home from './pages/home/Home';
import Intro from './pages/home/Intro';
import EditProfile from './pages/mypage/EditProfile';
import Login from './pages/user/Login';
import MyPage from './pages/mypage/MyPage';
import Register from './pages/user/Register';
import Search from './pages/home/Search';

import Admin from './pages/admin/Admin';
import EditPassword from './pages/mypage/EditPassword';
import DeleteAccount from './pages/mypage/DeleteAccount';
import ReceiveOauth2 from './components/user/_receiveOauth2';
import NotFound from './pages/Error/NotFound';

import * as serviceWorkerRegistration from './serviceWorkerRegistration';
import reportWebVitals from './reportWebVitals';
import { createBrowserRouter, Outlet, RouterProvider } from 'react-router-dom';
import ServerError from './pages/Error/ServerError';

const router = createBrowserRouter([
  {
    path: '/',
    element: <App />,
    children: [
      {
        path: '/',
        element: <Intro />,
        errorElement: <ServerError />
      },
      {
        path: '/home',
        element: <Home />
      },
      {
        path: '/search',
        element: <Search />
      },
      {
        path: '/register',
        element: <Register />
      },
      {
        path: '/login',
        element: <Login />
      },
      {
        path: '/mypage',
        element: <MyPage />
      },
      {
        path: '/mypage/edit',
        element: <EditProfile />
      },
      {
        path: '/mypage/edit/password',
        element: <EditPassword />
      },
      {
        path: '/mypage/edit/account',
        element: <DeleteAccount />
      },
      {
        path: '/admin',
        element: <Admin />
      },
      {
        path: '/oauth2/receive',
        element: <ReceiveOauth2 />
      },
      {
        path: '/club/create',
        element: <CreateClub />
      },
      {
        path: '/club/:id',
        element: <Outlet />,
        errorElement: <NotFound />,
        loader: async ({ params }) => {
          const res = await axios.get(`${process.env.REACT_APP_URL}/clubs/${params.id}`);
          if (res.status === 404) {
            throw new Response('Not Found', { status: 404 });
          }
          return res;
        },
        children: [
          {
            path: '',
            element: <ClubIntro />,
            errorElement: <NotFound />
          },
          {
            path: 'edit',
            element: <EditClub />,
            errorElement: <NotFound />
          },
          {
            path: 'setting',
            element: <ClubSetting />
          },
          {
            path: 'setting/member',
            element: <MemberSetting />
          },
          {
            path: 'member',
            element: <ClubMember />
          },
          {
            path: 'match',
            element: <ClubSchedule />
          },
          {
            path: 'match/create',
            element: <CreateMatch />
          },
          {
            path: 'match/:scid',
            element: <Outlet />,
            loader: async ({ params }) => {
              const res = await axios.get(
                `${process.env.REACT_APP_URL}/clubs/${params.id}/schedules/${params.scid}`
              );
              if (res.status === 404) {
                throw new Response('Not Found', { status: 404 });
              }
              return res;
            },
            children: [
              {
                path: '',
                element: <MatchDetail />
              },
              {
                path: 'edit',
                element: <EditMatch />
              }
            ]
          }
        ]
      },
      {
        path: '*',
        element: <NotFound />
      }
    ],
    errorElement: <ServerError />
  }
]);

export const persistor = persistStore(store);

ReactDOM.createRoot(document.getElementById('root') as HTMLElement).render(
  // <React.StrictMode>
  <Provider store={store}>
    <PersistGate loading={null} persistor={persistor}>
      <RouterProvider router={router} />
    </PersistGate>
  </Provider>
  // </React.StrictMode>
);

// If you want your app to work offline and load faster, you can change
// unregister() to register() below. Note this comes with some pitfalls.
// Learn more about service workers: https://cra.link/PWA
serviceWorkerRegistration.unregister();

// If you want to start measuring performance in your app, pass a function
// to log results (for example: reportWebVitals(console.log))
// or send to an analytics endpoint. Learn more: https://bit.ly/CRA-vitals
reportWebVitals();
