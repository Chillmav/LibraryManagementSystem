import { useEffect, useState } from "react";
import Books from "../components/Books";

function Panel() {

    const [booksOption, setBooksOption] = useState("all"); // user is second choice
    const [page, setPage] = useState(1);
    const [books, setBooks] = useState([]);
    

    useEffect(() => {
        
        if (booksOption === "all") {
            
            fetch("http://localhost:9000/library_books", {
            method: "GET",
            credentials: "include", 
            }).then(res => res.json()).then(data => console.log(data)).catch(error => console.error(error));

        } else if (booksOption === "yours") {

        }


    }, [booksOption])

    return (

    <div className="bg-white w-[60vw] h-[70vh] rounded-2xl shadow-xl flex  flex-col absolute">
        
        <div className="flex justify-between mx-15">

            <div className="flex bg-blue-300 relative w-40 p-3 rounded-2xl mt-5 mb-5 justify-center">
                <p className="text-xl">Hello reader!</p>
            </div>

            <div className="flex bg-lime-500 relative w-40 p-3 rounded-2xl mt-5 mb-5 justify-center">
                <p className="text-xl">Reader Panel</p>
            </div>

            <div className="flex bg-red-500 relative w-40 p-3 rounded-2xl mt-5 mb-5 justify-center">
                <p className="text-xl">Logout</p>
            </div>
        
        </div>

        <Books page={page} booksOption={booksOption} setPage={setPage} setBooksOption = {setBooksOption}>

        </Books>
        
    </div>

    );

}


export default Panel;

