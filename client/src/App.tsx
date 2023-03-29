import { Outlet } from 'react-router-dom';
import GlobalStyles from './GlobalStyles';
import Header from './components/Header';

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
