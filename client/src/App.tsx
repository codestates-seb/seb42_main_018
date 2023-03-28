import { Outlet } from 'react-router-dom';
import './App.css';

import Header from './components/Header';
import GlobalStyles from './GlobalStyles';

function App() {
  return (
    <div className='App'>
      <GlobalStyles />
      <Header />
      <Outlet />
    </div>
  );
}

export default App;
