import EventCard from '../components/EventCard.js'
import ProfileDropdown from '../components/ProfileDropdown.js'

const ce = React.createElement

class DashboardPage extends React.Component {
    constructor(props) {
        super(props)
        this.state = {tickets: [], role: ""};
        fetch('viewTickets').then(res => res.json()).then(data => {
            this.setState(s => {return {...s, tickets: data}})
            console.log(data)
        })
        fetch('getProfileInfo').then(res => res.json()).then(data => {
            this.setState(s => {return {...s, role: data.role, name: data.name}})
        })
    }

    render() {
        if (this.state.role === "organizer") {
            return ce('div', {className: 'dashboard-body',style: {overflow: 'hidden'}},
                ce('div', {className: 'top-bar'}, 
                    ce('h2', null, "Hello, " + this.state.name + " Organizer. Manage or create events:"),
                    ce(ProfileDropdown, {isOrganizer: true, test: "bruh"}, null)
                ),
                ce('h4', null, "My Events: "),
                ce('h5', null, "Organizer"),
            )
        } else {
            return ce('div', {className: 'dashboard-body',style: {overflow: 'hidden'}},
                ce('div', {className: 'top-bar'}, 
                    ce('h2', null, "Hello, " + this.state.name + ". Check out what’s going on around campus this week:"),
                    (this.state.role === "attendee" && ce(ProfileDropdown, {isOrganizer: false}, null))
                ),
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
                ce('h5', null, "Attendee"),
            )
        }
    }
}


ReactDOM.render(
    React.createElement(DashboardPage, {}, null),
    document.getElementById('dashboard-root')
);