import { useEffect, useState } from "react";
import { TiPlus } from "react-icons/ti";

function Books({booksOption, page, setBooksOption, setPage, books}) {

    const color1 = "bg-yellow-100";
    const color2 = "bg-yellow-300";

    return (

        <div className="bg-yellow-200 h-[60vh] rounded-2xl flex flex-col mx-15 mb-5">

            <div>

                <button className={`${booksOption === "all" ? color2 : color1} py-2 px-4 rounded-tl-xl border-r-2 shadow-2xs border-b-1 cursor-pointer`} disabled={booksOption === "all"} onClick={() => setBooksOption("all")}>Library books</button>

                <button className={`${booksOption === "all" ? color1 : color2} py-2 px-4 rounded-r-xl border-r-2 shadow-2xs border-b-1 cursor-pointer`} disabled={booksOption === "user"} onClick={() => setBooksOption("user")}>Your books</button>

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
                                {book.available ? <button className="bg-lime-500 flex items-center justify-center w-fit h-fit p-4 rounded-2xl cursor-pointer"><TiPlus size={20}/></button> : <></>}
                            </div>
                        </div>

                )
                
            ) : <p> Waiting for books...
            </p>}
            </div>

        </div>

    );

}



export default Books;