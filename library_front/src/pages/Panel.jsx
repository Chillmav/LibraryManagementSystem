import { useEffect, useState } from "react";
import Books from "../components/Books";
import { useNavigate } from "react-router-dom";
import handleLogout from "../utils/handleLogout.js";
import secondsToMin from "../utils/secondsToMin.js";

function Panel() {

    const [booksOption, setBooksOption] = useState("all"); // user is second choice
    const [page, setPage] = useState(1);
    const [books, setBooks] = useState([]);
    const [sessionTime, setSessionTime] = useState(0);
    const navigator = useNavigate();

    useEffect(() => {

        const interval = setInterval(() => {
            setSessionTime(prev => (prev > 0 ? prev - 1 : 0));
        }, 1000);

        return () => clearInterval(interval);

    }, []);

    useEffect(() => {
        
        if (booksOption === "all") {

            console.log("Fetching library books...");
            
            fetch("http://localhost:9000/library_books", {
            method: "GET",
            headers: { "Content-Type": "application/json" },
            credentials: "include", 
            }).then(res => res.json()).then(data => {
                setBooks(data)
                console.log(data)}).catch(error => {console.error(error); handleLogout(navigator)});

        } else if (booksOption === "user") {
            
            console.log("Fetching user books...");
            
            fetch("http://localhost:9000/user_books", {
            method: "GET",
            headers: { "Content-Type": "application/json" },
            credentials: "include", 
            }).then(res => res.json()).then(data => {
                setBooks(data)
                console.log(data)}).catch(error => {console.error(error); handleLogout(navigator)});

        }


    }, [booksOption])

    useEffect(() => {

        fetch("http://localhost:9000/user_session_time", {
            method: "GET",
            headers: { "Content-Type": "application/json" },
            credentials: "include", 
        }).then(res => res.json()).then(data => {setSessionTime(data.message); console.log(data); if (data.message == "0") {alert("Session Expired")}}).catch(error => {console.error(error); navigator("/")});

    }, [])

    return (
<>
    <div className="absolute top-2 left-2 w-[9vw] h-10 bg-white flex items-center justify-center gap-x-2 rounded-2xl">Session time: <p className={sessionTime > 150 ? "text-green-500 font-semibold" : "text-red-500 font-semibold"}>{secondsToMin(sessionTime)}</p></div>
    <div className="bg-white w-[60vw] h-[70vh] rounded-2xl shadow-xl flex  flex-col absolute">
        
        <div className="flex justify-between mx-15">

            <div className="flex bg-blue-300 relative w-40 p-3 rounded-2xl mt-5 mb-5 justify-center">
                <p className="text-xl">Hello reader!</p>
            </div>

            <div className="flex bg-lime-500 relative w-40 p-3 rounded-2xl mt-5 mb-5 justify-center">
                <p className="text-xl">Reader Panel</p>
            </div>

            <button className="flex bg-red-500 relative w-40 p-3 rounded-2xl mt-5 mb-5 justify-center" onClick={() => handleLogout(navigator)}>
                <p className="text-xl">Logout</p>
            </button>
        
        </div>

        <Books page={page} booksOption={booksOption} setPage={setPage} setBooksOption = {setBooksOption} books={books}>

        </Books>
        
    </div>
</>
    );

}


export default Panel;

