import { useNavigate } from 'react-router-dom';

function useGoToIntro() {
  const navigate = useNavigate();

  return () => {
    navigate('/');
  };
}

export default useGoToIntro;
