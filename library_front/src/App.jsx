import { useState } from 'react'
import LoginComponent from './components/LoginComponent.jsx';
import bgImage from "../public/library_background.jpg";
import { BrowserRouter, Routes, Route } from "react-router-dom"
import Panel from './pages/Panel.jsx';


function App() {

  const [role, setRole] = useState("");
  const [books] = useState([]);

  return (

    <BrowserRouter>

      <div style={{backgroundImage: `url(${bgImage})`,
      backgroundSize: "cover",
      backgroundPosition: "center",
      minHeight: "100vh"
      }} className='flex justify-center items-center relative'>
        
      <Routes>
        <Route path="/" element={<LoginComponent role={role} setRole={setRole}/>} />
        <Route path="/panel" element={<Panel role = {role} books = {books} setRole={setRole}/>} />
      </Routes>

      </div>
    </BrowserRouter>

  )
}

export default App
