import {useEffect, useState} from 'react';
import { useNavigate } from 'react-router-dom';

function LoginComponent() {

    const [email, setEmail] = useState("");
    const [password, setPassword] = useState("");
    const [loginError, setLoginError] = useState(false);


    useEffect(() => {
        setLoginError(false);
    }, []);


    const navigate = useNavigate();

    async function handlelogin() {

        await fetch("http://localhost:9000/login", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        credentials: 'include',
        body: JSON.stringify({ email, password }),
        })
        .then(res => res.json())
        .then(data => {
            console.log(data);
            if (data.status === "success") {
            navigate("panel");
            } else {
            setLoginError(true);
            }
        })
        .catch(err => {
            setLoginError(true);
            console.error(err);
        });   
    }

    return (

<div className="bg-white w-[90vw] max-w-md h-auto rounded-2xl shadow-xl flex flex-col items-center p-8 md:p-12">

  <h1 className="font-semibold text-2xl md:text-4xl text-center mt-4">
    Library Management System
  </h1>

  <div className="mt-10 flex flex-col w-full gap-y-6">
    <input
      type="text"
      placeholder="Email"
      className="border-2 rounded-md w-full py-4 px-3 text-lg focus:outline-none focus:border-blue-400"
      value={email}
      onChange={(e) => setEmail(e.target.value)}
    />
    <input
      type="password"
      placeholder="Password"
      className="border-2 rounded-md w-full py-4 px-3 text-lg focus:outline-none focus:border-blue-400"
      value={password}
      onChange={(e) => setPassword(e.target.value)}
    />
    <button
      className="rounded-md w-full py-4 text-xl bg-blue-500 hover:bg-blue-600 transition-colors text-white"
      onClick={handlelogin}
    >
      Login
    </button>

    {loginError && (
      <p className="text-lg text-red-500 text-center">Something went wrong, try again.</p>
    )}
  </div>

  <div className="bg-gray-300 w-full h-[1px] mt-10"></div>

  <button className="mt-6 rounded-md w-full py-4 text-xl bg-green-600 hover:bg-green-700 transition-colors text-white">
    Create new account
  </button>
</div>


    )

}

export default LoginComponent