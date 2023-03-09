import { Route, Routes } from 'react-router-dom';
import './App.css';
import CreateClub from './pages/club/club/CreateClub';
import EditClub from './pages/club/club/EditClub';
import ClubIntro from './pages/club/intro/ClubIntro';
import ClubManage from './pages/club/manage/ClubManage';
import MemberManage from './pages/club/manage/MemberManage';
import ClubSchedule from './pages/club/match/ClubSchedule';
import CreateMatch from './pages/club/match/CreateMatch';
import EditMatch from './pages/club/match/EditMatch';
import MatchDetail from './pages/club/match/MatchDetail';
import ClubMember from './pages/club/member/ClubMember';
import Home from './pages/home/Home';
import Intro from './pages/home/Intro';
import EditProfile from './pages/user/EditProfile';
import Login from './pages/user/Login';
import MyPage from './pages/user/MyPage';
import Register from './pages/user/Register';

function App() {
  return (
    <div className='App'>
      <Routes>
        <Route path='/' element={<Intro/>}/>
        <Route path='/home' element={<Home/>}/>
        <Route path='/register' element={<Register/>}/>
        <Route path='/login' element={<Login/>}/>
        <Route path='/mypage' element={<MyPage/>}>
          <Route path="/edit" element={<EditProfile/>}/>
        </Route>
        <Route path='/club/:id' element={<ClubIntro/>}>
          <Route path='/match' element={<ClubSchedule/>}>
              <Route path='/:id' element={<MatchDetail/>}/>
              <Route path='/:id/edit' element={<EditMatch/>}/>
              <Route path='/create' element={<CreateMatch/>}/>
          </Route>
          <Route path='/member' element={<ClubMember/>}/>
        </Route>
        <Route path='/club/create' element={<CreateClub/>}/>
        <Route path='/club/edit' element={<EditClub/>}/>
        <Route path='/club/manage' element={<ClubManage/>}>
          <Route path='/member' element={<MemberManage/>}/>
        </Route>
      </Routes>
    </div>
  )
}

export default App;
