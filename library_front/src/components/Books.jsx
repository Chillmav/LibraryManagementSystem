import { useEffect, useState } from "react";
import { TiPlus, TiMinus } from "react-icons/ti";

function Books({booksOption, page, setBooksOption, setPage, books, fetchUserBooks, fetchAllBooks, role, setRole }) {

    const color1 = "bg-yellow-100";
    const color2 = "bg-yellow-300";


    useEffect(() => {
        setRole(localStorage.getItem("role"));
    }, [])

    async function returnBook(bookId) {


        console.log(bookId)
        fetch("http://localhost:9000/return",{
            method: "POST",
            headers: { "Content-Type": "application/json" },
            credentials: "include",
            body: JSON.stringify({
                bookId: bookId + ""
            })
        }).then(res => res.json()).then(data => {

            if (data.message.toLowerCase() == "success") {
                console.log(data)
                fetchUserBooks();
                alert("You returned a book successfully")
            } else {
                console.log(data)
                alert("Something went wrong")
            }

        }).catch(error => console.log(error));

    }

    async function borrowBook(bookId) {


        console.log(bookId)
        fetch("http://localhost:9000/borrow",{
            method: "POST",
            headers: { "Content-Type": "application/json" },
            credentials: "include",
            body: JSON.stringify({
                bookId: bookId + ""
            })
        }).then(res => res.json()).then(data => {

            if (data.message.toLowerCase() == "success") {
                console.log(data)
                fetchAllBooks();
                alert("You borrowed a book successfully")
            } else {
                console.log(data)
                alert("Something went wrong")
            }

        }).catch(error => console.log(error));

    }

    return (

        <div className="bg-yellow-200 h-[60vh] rounded-2xl flex flex-col mx-15 mb-5">

            <div>

                <button className={`${booksOption === "all" ? color2 : color1} py-2 px-4 rounded-tl-xl border-r-2 shadow-2xs border-b-1 cursor-pointer`} disabled={booksOption === "all"} onClick={() => setBooksOption("all")}>Library books</button>

                { role === "Reader" ? <button className={`${booksOption === "all" ? color1 : color2} py-2 px-4 rounded-r-xl border-r-2 shadow-2xs border-b-1 cursor-pointer`} disabled={booksOption === "user"} onClick={() => setBooksOption("user")}>Your books</button> :
                <button className={`bg-amber-500 py-2 px-4 rounded-r-xl border-r-2 shadow-2xs border-b-1 cursor-pointer`} disabled={role === "Reader"} onClick={() => console.log("TODO")}>Add Book</button>}
                

            </div>

            <div className="flex gap-y-5 p-5 flex-col overflow-auto" style={{scrollbarWidth: "none"}}>

            {books ?
            books.map((book) => 
                (
                    <div key={book.id} className="bg-amber-50 max-w-[40vw] px-4 py-3 flex flex-row gap-4 rounded-xl border-1 justify-between">
                        <div className="flex flex-row justify-between space-x-4 h-24">
                            <img src="library_background.jpg" className="rounded-xl object-cover h-24 w-20"/>
                            <div className="bg-black w-0.5 py-4 -my-3"></div>
                                <div className="bottom-1 relative flex flex-col">
                                    <p className="text-sm font-semibold">{book["title"]}</p>
                                    <p className="text-sm">{book["author"]}</p>
                                    <p className="text-sm">{book["kind"].charAt(0) + book["kind"].substring(1).toLowerCase()}</p>
                                    <p className="text-sm">{book["pages"]} pages</p>
                                    {booksOption === "all" ? <p className={book.available ? "text-green-600 text-sm" : "text-red-500 text-sm"}>{book.available === true ? 'Available' : "Not Available"}</p> : <></>}
                                </div>
                            </div>
                            <div className="flex items-center">
                                {book.available ? <button className="bg-lime-500 flex items-center justify-center w-fit h-fit p-4 rounded-2xl cursor-pointer" onClick={() => borrowBook(book.id)}><TiPlus size={20}/></button> : <></>}
                            </div>
                        {(!book.available && booksOption === "user") ?
                            <div className="flex items-center">
                                {<button className="bg-red-500 flex items-center justify-center w-fit h-fit p-4 rounded-2xl cursor-pointer" onClick={() => returnBook(book.id)}><TiMinus size={20}/></button>}
                            </div> : <></>
                        }
                    </div>

                )
                
            ) : <p> Waiting for books...
            </p>}
            </div>

        </div>

    );

}



export default Books;