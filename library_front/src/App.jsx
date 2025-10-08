import { useState } from 'react'
import LoginComponent from './components/LoginComponent.jsx';
import bgImage from "../public/library_background.jpg";

function App() {


  const isLogged = useState(false);

  return (
    <div style={{backgroundImage: `url(${bgImage})`,
    backgroundSize: "cover",
    backgroundPosition: "center",
    minHeight: "100vh"
    }} className='flex justify-center items-center'>
    {isLogged ? 
    <LoginComponent>
    
    </LoginComponent>
    : 
    <>

    </>}
    </div>

  )
}

export default App
