function LoginComponent() {

    return (
        <div className="bg-white w-[60vw] h-[70vh] rounded-2xl shadow-xl flex justify-center">
                <h1 className="pt-5 font-500 text-2xl">Library Management System</h1>

                <button>Zaloguj</button>
                <p onChange={console.log("registering")}>If you don't have an account, register.</p>
        </div>
    )

}

export default LoginComponent