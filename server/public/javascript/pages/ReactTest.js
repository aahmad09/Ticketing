import EventCard from '../components/EventCard.js'

const csrfToken = document.getElementById("csrfToken").value;

console.log("test")

ReactDOM.render(
    React.createElement(EventCard, {}, null),
    document.getElementById('react-root')
);