export default function handlelogout(navigator) {

    fetch("http://localhost:9000/logout", {
        method: "GET",
        headers: { "Content-Type": "application/json" },
        credentials: "include", 
    }).then(navigator("/")).catch(error => console.log(error));
    
}

