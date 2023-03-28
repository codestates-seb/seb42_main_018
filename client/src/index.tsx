import React from 'react';
import ReactDOM from 'react-dom/client';
import { Provider } from 'react-redux';
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
import API_TEST from './API_TEST';

import Admin from './pages/admin/Admin';
import EditPassword from './pages/mypage/EditPassword';
import DeleteAccount from './pages/mypage/DeleteAccount';
import ReceiveOauth2 from './pages/user/_receiveOauth2';
import NotFound from './pages/404/NotFound';

import * as serviceWorkerRegistration from './serviceWorkerRegistration';
import reportWebVitals from './reportWebVitals';
import { createBrowserRouter, Outlet, RouterProvider } from 'react-router-dom';
import axios from 'axios';

const router = createBrowserRouter([
  {
    path: '/',
    element: <App />,
    errorElement: <NotFound />,
    children: [
      {
        path: '/',
        element: <Intro />
      },
      {
        path: '/home',
        element: <Home />
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
        path: '/api',
        element: <API_TEST />
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
      }
    ]
  }
]);

ReactDOM.createRoot(document.getElementById('root') as HTMLElement).render(
  <Provider store={store}>
    <React.StrictMode>
      <RouterProvider router={router} />
    </React.StrictMode>
  </Provider>
);

// If you want your app to work offline and load faster, you can change
// unregister() to register() below. Note this comes with some pitfalls.
// Learn more about service workers: https://cra.link/PWA
serviceWorkerRegistration.unregister();

// If you want to start measuring performance in your app, pass a function
// to log results (for example: reportWebVitals(console.log))
// or send to an analytics endpoint. Learn more: https://bit.ly/CRA-vitals
reportWebVitals();
