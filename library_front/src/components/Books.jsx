function Books() {



    return (
        <div className="bg-yellow-200 h-[60vh] rounded-2xl flex flex-col mx-15 mb-5">
            <div>
                <button className="bg-yellow-100 py-5 px-8 rounded-tl-xl border-r-2 shadow-2xs border-b-1">Library books</button>

                <button className="bg-yellow-100 py-5 px-8 rounded-r-xl shadow-2xs border-b-1">Your books</button>

            </div>

        </div>
    );

}



export default Books;