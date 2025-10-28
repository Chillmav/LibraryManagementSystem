import {useEffect, useState} from 'react';
import { useNavigate } from 'react-router-dom';
import handlelogout from '../utils/handleLogout';

function LoginComponent({ role, setRole }) {

    const [email, setEmail] = useState("");
    const [password, setPassword] = useState("");
    const [loginError, setLoginError] = useState("");
    const [process, setProcess] = useState("login") // "register" is second option
    const [registerMessage, setRegisterMessage] = useState("");
    const [registerStatus, setRegisterStatus] = useState("");
    // for registering:
    const [firstName, setFirstName] = useState("");
    const [lastName, setLastName] = useState("");
    const [age, setAge] = useState("");
    const [password2, setPassword2] = useState("");

    const navigator = useNavigate();
    
    useEffect(() => {

        handlelogout(navigator);
        setRole("");
        setLoginError(false);
        
    }, []);

    useEffect(() => {

    })

    const navigate = useNavigate();

    function handlelogin() {

        localStorage.clear();
        fetch("http://localhost:9000/login", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        credentials: 'include',
        body: JSON.stringify({ email, password }),
        })
        .then(res => res.json())
        .then(data => {
            console.log(data);
            if (data.status === "success") {
              if (data.role === "Employee") {
                  setRole("Employee");
                  localStorage.setItem("role", "Employee");
              } else {
                setRole("Reader");
                localStorage.setItem("role", "Reader");
              }
              navigate("panel");
            } else if (data.status === "failure") {
              setLoginError(data.message);
            } else {
              setLoginError("something happened.");
            }
        })
        .catch(err => {
            setLoginError("Server isn't responding.");
            console.error(err);
        });   
    }

    function handleRegister() {
      if (password === password2) {

        fetch("http://localhost:9000/register", {
          method: "POST",
          headers: { "Content-Type": "application/json" },
          credentials: 'include',
          body: JSON.stringify({ firstName, lastName, age, email, password }),
        }).then(res => res.json()).then(data => {

          if (data.status == "Success") {
            setRegisterStatus("Success")
            setRegisterMessage(data.message);
            setTimeout(() => {
              setProcess("login");
            }, 2000)
          } else if (data.status == "Failure") {
            setRegisterStatus("Failure")
            setRegisterMessage(data.message)
          }

        })
        
      } else {
        setRegisterStatus("Failure")
        setRegisterMessage("Passwords don't match")
      }
    }
    return (

<div className="bg-white w-[90vw] max-w-md h-auto rounded-2xl shadow-xl flex flex-col items-center p-8 md:p-12">

  <h1 className="font-semibold text-2xl md:text-4xl text-center mt-4">
    Library Management System
  </h1>

  <div className="mt-10 flex flex-col w-full gap-y-4 overflow-auto no-scrollbar">
    {process === "register" && 
    <>

    <input
      type="text"
      placeholder="First name"
      className="border-2 rounded-md w-full h-5 py-4 px-3 text-lg focus:outline-none focus:border-blue-400"
      value={firstName}
      onChange={(e) => setFirstName(e.target.value)}
    />
    <input
      type="text"
      placeholder="Last name"
      className="border-2 rounded-md w-full h-5 py-4 px-3 text-lg focus:outline-none focus:border-blue-400"
      value={lastName}
      onChange={(e) => setLastName(e.target.value)}
    />

    <input
      type="text"
      placeholder="Age"
      className="border-2 rounded-md w-full h-5 py-4 px-3 text-lg focus:outline-none focus:border-blue-400"
      value={age}
      onChange={(e) => setAge(e.target.value)}
    />

    </>
    
    }
    <input
      type="text"
      placeholder="Email"
      className="border-2 rounded-md w-full h-5 py-4 px-3 text-lg focus:outline-none focus:border-blue-400"
      value={email}
      onChange={(e) => setEmail(e.target.value)}
    />
    <input
      type="password"
      placeholder="Password"
      className="border-2 rounded-md w-full h-5 py-4 px-3 text-lg focus:outline-none focus:border-blue-400"
      value={password}
      onChange={(e) => setPassword(e.target.value)}
    />
    {process === "register" && 

    <input
      type="password"
      placeholder="Repeat password"
      className="border-2 rounded-md w-full h-5 py-4 px-3 text-lg focus:outline-none focus:border-blue-400"
      value={password2}
      onChange={(e) => setPassword2(e.target.value)}
    />

    }

    <button
      className="rounded-md w-full py-4 text-xl bg-blue-500 hover:bg-blue-600 transition-colors text-white cursor-pointer"
      onClick={() => {
      if (process === "login") {
          handlelogin()
      } else {
          handleRegister()
      }
      }}
    >
      {process === "login" ? "Login" : "Register"}
    </button>

    
    {loginError && (
      <p className="text-lg text-red-500 text-center">{loginError}</p>
    )}
    {registerMessage.length > 0 && (
      <p className={registerStatus == "Failure" ? "text-lg text-red-500 text-center" : "text-lg text-green-500 text-center"}>{registerMessage}</p>
    )}
  </div>

  <div className="bg-gray-300 w-full h-[1px] mt-10"></div>

  <button className="mt-6 rounded-md w-full py-4 text-xl bg-green-600 hover:bg-green-700 transition-colors text-white cursor-pointer" onClick={() => {
      if (process === "login") {
          setProcess("register")
      } else {
          setProcess("login")
      }
      }}>
    {process === "login" ? "Create new account" : "Log in"}
  </button>
</div>


    )

}

export default LoginComponent