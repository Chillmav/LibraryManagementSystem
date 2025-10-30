import { useEffect, useState } from "react";
import { TiPlus, TiMinus } from "react-icons/ti";
import { IoMdCloseCircle } from "react-icons/io";


function Books({booksOption, page, setBooksOption, setPage, books, fetchUserBooks, fetchAllBooks, role, setRole }) {

    const color1 = "bg-yellow-100";
    const color2 = "bg-yellow-300";

    const [loading, setLoading] = useState(true);
    const [modalVisible, setModalVisible] = useState(false);
    const [title, setTitle] = useState("");
    const [author, setAuthor] = useState("");
    const [pages, setPages] = useState("");
    const [kind, setKind] = useState("");
    const [available, setAvailable] = useState("1");

    async function addBook() {
    const newBook = { title, author, pages, kind, available: parseInt(available) };

    try {
        const res = await fetch("http://localhost:9000/addBook", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        credentials: "include",
        body: JSON.stringify(newBook),
        });

        const data = await res.json();
        if (data.message.toLowerCase() === "success") {
        alert("Book added successfully!");
        fetchAllBooks(); // refresh book list
        setModalVisible(false);
        } else {
        alert("Failed to add book");
        }
    } catch (err) {
        console.error(err);
        alert("Something went wrong");
    }
    }
    useEffect(() => {
        const storedRole = localStorage.getItem("role");
        setRole(storedRole);
        setModalVisible(false);
    }, []);

    useEffect(() => {
        if (role === "Employee" || role === "Reader") {
            console.log("Role: " + role);
            setLoading(false);
        }
    }, [role]);

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
        !loading &&
        <div className="bg-yellow-200 h-[60vh] rounded-2xl flex flex-col mx-15 mb-5 relative">

            <div>

                <button className={`${booksOption === "all" ? color2 : color1} py-2 px-4 rounded-tl-xl border-r-2 shadow-2xs border-b-1 cursor-pointer`} disabled={booksOption === "all"} onClick={() => setBooksOption("all")}>Library books</button>

                { role === "Reader" ? (<button className={`${booksOption === "all" ? color1 : color2} py-2 px-4 rounded-r-xl border-r-2 shadow-2xs border-b-1 cursor-pointer`} disabled={booksOption === "user"} onClick={() => setBooksOption("user")}>Your books</button>) :
                (<button className={`bg-amber-500 py-2 px-4 rounded-r-xl border-r-2 shadow-2xs border-b-1 cursor-pointer`} disabled={role === "Reader"} onClick={() => setModalVisible(true)}>Add Book</button>)}
                

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
                                {(book.available && role === "Reader") ? <button className="bg-lime-500 flex items-center justify-center w-fit h-fit p-4 rounded-2xl cursor-pointer" onClick={() => borrowBook(book.id)}><TiPlus size={20}/></button> : <></>}
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

{modalVisible && (
  <div
    className={`absolute inset-0 flex items-center justify-center bg-black/40 backdrop-blur-sm transition-opacity duration-300 ${
      modalVisible ? "opacity-100" : "opacity-0 pointer-events-none"
    }`}
  >
    <div className="modal bg-amber-50 w-[40vw] h-[50vh] rounded-xl shadow-lg p-6 relative flex flex-col justify-between">
      
      <IoMdCloseCircle
        className="top-4 right-4 absolute cursor-pointer"
        size={30}
        color="#fb2c36"
        onClick={() => setModalVisible(false)}
      />

      <h2 className="text-2xl font-semibold text-center text-amber-700 mb-4">
        Add New Book
      </h2>
    <div className="flex flex-row">

        <div className="flex flex-1 flex-col items-center justify-center w-fit h-fit">
            <h1>Add book cover</h1>
            <div className="w-fit h-fit border-2"></div>
        </div>

      <form
        className="flex flex-col gap-4 flex-2"
        onSubmit={(e) => {
          e.preventDefault();
          addBook(); 
        }}
      >

        <div className="flex flex-col">
          <label className="text-sm font-semibold text-gray-700">Title</label>
          <input
            type="text"
            className="border border-gray-300 rounded-md p-2 focus:outline-none focus:ring-2 focus:ring-amber-400"
            placeholder="Enter book title"
            value={title}
            onChange={(e) => setTitle(e.target.value)}
            required
          />
        </div>

        <div className="flex flex-col">
          <label className="text-sm font-semibold text-gray-700">Author</label>
          <input
            type="text"
            className="border border-gray-300 rounded-md p-2 focus:outline-none focus:ring-2 focus:ring-amber-400"
            placeholder="Enter author name"
            value={author}
            onChange={(e) => setAuthor(e.target.value)}
            required
          />
        </div>

        <div className="flex flex-col">
          <label className="text-sm font-semibold text-gray-700">Pages</label>
          <input
            type="number"
            className="border border-gray-300 rounded-md p-2 focus:outline-none focus:ring-2 focus:ring-amber-400"
            placeholder="Number of pages"
            value={pages}
            onChange={(e) => setPages(e.target.value)}
            required
            min="1"
          />
        </div>

        <div className="flex flex-col">

          <label className="text-sm font-semibold text-gray-700">Kind</label>
          <select
                className="border border-gray-300 rounded-md p-2 focus:outline-none focus:ring-2 focus:ring-amber-400"
                value={kind}
                onChange={(e) => setKind(e.target.value)}
                required
                >
                <option value="">Select kind</option>
                <option value="FICTION">Fiction</option>
                <option value="NON_FICTION">Non-Fiction</option>
                <option value="MYSTERY">Mystery</option>
                <option value="FANTASY">Fantasy</option>
                <option value="SCIENCE_FICTION">Science Fiction</option>
                <option value="ROMANCE">Romance</option>
                <option value="HORROR">Horror</option>
                <option value="HISTORICAL">Historical</option>
                <option value="BIOGRAPHY">Biography</option>
                <option value="SELF_HELP">Self Help</option>
                <option value="EDUCATIONAL">Educational</option>
                <option value="POETRY">Poetry</option>
                <option value="CHILDREN">Children</option>
                <option value="YOUNG_ADULT">Young Adult</option>
                <option value="RELIGION">Religion</option>
                <option value="PHILOSOPHY">Philosophy</option>
            </select>

        </div>

        <button
          type="submit"
          className="mt-4 bg-amber-500 hover:bg-amber-600 text-white font-semibold py-2 rounded-lg transition-all"
        >
          Add Book
        </button>

      </form>
      </div>
    </div>
  </div>
)}



        </div>

    )

}



export default Books;