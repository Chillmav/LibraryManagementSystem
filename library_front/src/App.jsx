import { useState } from 'react'
import LoginComponent from './components/LoginComponent.jsx';
import bgImage from "../public/library_background.jpg";
import { BrowserRouter, Routes, Route } from "react-router-dom"
import Panel from './pages/Panel.jsx';


function App() {

  const [username, setUsername] = useState("");
  const [books, setBooks] = useState([]);

  return (

    <BrowserRouter>

      <div style={{backgroundImage: `url(${bgImage})`,
      backgroundSize: "cover",
      backgroundPosition: "center",
      minHeight: "100vh"
      }} className='flex justify-center items-center'>
        
      <Routes>
        <Route path="/" element={<LoginComponent />} />
        <Route path="/panel" element={<Panel username = {username} books = {books}/>} />
      </Routes>

      </div>
    </BrowserRouter>

  )
}

export default App
