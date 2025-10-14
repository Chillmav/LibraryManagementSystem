import { useEffect } from "react";

function Books({booksOption, page, setBooksOption, setPage}) {

    const color1 = "bg-yellow-100";
    const color2 = "bg-yellow-300";

    return (

        <div className="bg-yellow-200 h-[60vh] rounded-2xl flex flex-col mx-15 mb-5">
            <div>

                <button className={`${booksOption === "all" ? color2 : color1} py-2 px-4 rounded-tl-xl border-r-2 shadow-2xs border-b-1 cursor-pointer`} disabled={booksOption === "all"} onClick={() => setBooksOption("all")}>Library books</button>

                <button className={`${booksOption === "all" ? color1 : color2} py-2 px-4 rounded-r-xl border-r-2 shadow-2xs border-b-1 cursor-pointer`} disabled={booksOption === "user"} onClick={() => setBooksOption("user")}>Your books</button>

            </div>

        </div>

    );

}



export default Books;