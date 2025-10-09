import {useState} from 'react';

function LoginComponent() {

    const [email, setEmail] = useState("");
    const [password, setPassword] = useState("");

    async function handlelogin() {


        let smth = await fetch("http://localhost:9000/login", {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
            },
            body: JSON.stringify({
                email,
                password
            })
        });

        console.log("Email: " + email + ", Password: " + password);
        console.log(smth.json.body);
        
    }

    return (

        <div className="bg-white w-[60vw] h-[70vh] rounded-2xl shadow-xl flex items-center flex-col">

                <h1 className="pt-10 font-500 text-4xl">Library Management System</h1>
                <div className="mt-[60px] flex flex-col gap-y-10">
                <input type="text" placeholder="Email" className="border-2 rounded-md min-w-[400px] py-5 px-3 text-[20px]" value={email} onChange={(e) => setEmail(e.target.value)}/>
                <input type="password" placeholder="Password" className="border-2 rounded-md min-w-[400px] py-5 px-3 text-[20px]" value={password} onChange={(e) => setPassword(e.target.value)}/>
                <button className=" border-2 rounded-md min-w-[400px] py-5 px-3 text-[25px] bg-blue-500 border-blue-500 cursor-pointer text-white"
                onClick={handlelogin}
                >Login
                </button>
                </div>
                <div className="bg-gray-500 min-w-[450px] h-[1px] mt-10"></div>
                <button className="mt-15 border-2 rounded-md min-w-[400px] py-5 px-3 text-[25px] bg-green-600 border-green-600 cursor-pointer text-white">Create new account</button>

        </div>

    )

}

export default LoginComponent