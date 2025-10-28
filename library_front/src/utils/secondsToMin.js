
export default function secondsToMin(seconds) {

    let mins = Math.floor(seconds / 60);
    let secs = seconds - mins * 60;

    if (secs < 10) {
        secs = "0" + secs;
    }
    if (mins < 10) {
        mins = "0" + mins;
    }
    
    return mins + " : " + secs;
}