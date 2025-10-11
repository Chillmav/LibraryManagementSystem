import Books from "../components/Books";

function Panel() {


    return (

    <div className="bg-white w-[60vw] h-[70vh] rounded-2xl shadow-xl flex  flex-col absolute">
        
        <div className="flex justify-between mx-15">

            <div className="flex bg-blue-300 relative w-fit p-5 rounded-2xl mt-15 mb-5">
                <p className="text-4xl">Hello reader!</p>
            </div>

            <div className="flex bg-lime-500 relative w-fit p-5 rounded-2xl mt-15 mb-5">
                <p className="text-4xl">Reader Panel</p>
            </div>

            <div className="flex bg-red-500 relative w-fit p-5 rounded-2xl mt-15 mb-5">
                <p className="text-4xl">Logout</p>
            </div>
        
        </div>

        <Books>

        </Books>
        
    </div>

    );

}


export default Panel;

