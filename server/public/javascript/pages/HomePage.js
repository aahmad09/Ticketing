import EventCard from '../components/EventCard.js'

const ce = React.createElement

class HomePage extends React.Component {
    constructor(props) {
        super(props)
    }

    render() {
        return ce('div', null,
            ce('h2', null, "Hello, [Name]. Check out what’s going on around campus this week:"),
            ce('h4', null, "My Events: "),
            ce('div', {className: 'card-slider'}, 
                ce(EventCard, {title: "Web Apps Final", color: "#C85330", month: "Feb", day: "17"}, null), /*this is an event card for testing*/ 
                ce(EventCard, {title: "Web Apps Final", color: "#C85330", month: "Feb", day: "17"}, null), /*this is an event card for testing*/ 
                ce(EventCard, {title: "Web Apps Final", color: "#C85330", month: "Feb", day: "17"}, null), /*this is an event card for testing*/ 
                ce(EventCard, {title: "Web Apps Final", color: "#C85330", month: "Feb", day: "17"}, null), /*this is an event card for testing*/ 
                ce(EventCard, {title: "Web Apps Final", color: "#C85330", month: "Feb", day: "17"}, null), /*this is an event card for testing*/ 
                ce(EventCard, {title: "Web Apps Final", color: "#C85330", month: "Feb", day: "17"}, null), /*this is an event card for testing*/ 
                ce(EventCard, {title: "Web Apps Final", color: "#C85330", month: "Feb", day: "17"}, null), /*this is an event card for testing*/ 
                ce(EventCard, {title: "Web Apps Final", color: "#C85330", month: "Feb", day: "17"}, null), /*this is an event card for testing*/ 
                ce(EventCard, {title: "Web Apps Final", color: "#C85330", month: "Feb", day: "17"}, null), /*this is an event card for testing*/ 
                ce(EventCard, {title: "Web Apps Final", color: "#C85330", month: "Feb", day: "17"}, null), /*this is an event card for testing*/ 
                ce(EventCard, {title: "Web Apps Final", color: "#C85330", month: "Feb", day: "17"}, null), /*this is an event card for testing*/ 
            ),
            ce('h4', null, "Upcoming: "),
            ce('div', {className: 'card-slider'}, 
                ce(EventCard, {title: "Web Apps Final", color: "#C85330", month: "Feb", day: "17"}, null), /*this is an event card for testing*/ 
                ce(EventCard, {title: "Web Apps Final", color: "#C85330", month: "Feb", day: "17"}, null), /*this is an event card for testing*/ 
                ce(EventCard, {title: "Web Apps Final", color: "#C85330", month: "Feb", day: "17"}, null), /*this is an event card for testing*/ 
                ce(EventCard, {title: "Web Apps Final", color: "#C85330", month: "Feb", day: "17"}, null), /*this is an event card for testing*/ 
            ),
        )
    }
}


ReactDOM.render(
    React.createElement(HomePage, {}, null),
    document.getElementById('react-root')
);