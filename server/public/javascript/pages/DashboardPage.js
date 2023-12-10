import EventCard from '../components/EventCard.js'
import ProfileDropdown from '../components/ProfileDropdown.js'
import EventDetails from '../components/EventDetails.js'


const ce = React.createElement

class DashboardPage extends React.Component {
    constructor(props) {
        super(props)
        this.state = {tickets: [], role: "", openPopup: false, ticketData: null};
        fetch('viewTickets').then(res => res.json()).then(data => {
            this.setState(s => {return {...s, tickets: data}})
            console.log(data)
        })
        fetch('getAllEvents').then(res => res.json()).then(data => {
            this.setState(s => {return {...s, upcoming: data}})
            console.log(data)
        })
        fetch('getProfileInfo').then(res => res.json()).then(data => {
            this.setState(s => {return {...s, role: data.role, name: data.name}})
        })
    }

    getEventColor(eventDate) { //yyyy-mm-dd hh:mm:ss
        let currentDate = new Date().toJSON().slice(0, 10);
        return "#C85330"
    }

    renderPopup = (ticketId) => {
        fetch('getTicket?' + encodeURIComponent('ticketId') + '=' + encodeURIComponent(ticketId)).then(res => res.json()).then(data => {
            setState(s => {return {...s, openPopup: true, ticketData: data}})
        })
    }


    render() {
        if (this.state.openPopup) {
            return ce('div', {className: "eventDetailPage",style: {overflow: 'hidden'}}, 
                ce(EventDetails, {data: ticketData}, null),
            )
        } else {
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
                        this.state.upcoming && this.state.upcoming.map((ev) => ce(EventCard, {
                            title: ev.name, 
                            color: this.getEventColor(ev.date), 
                            month: ev.date.slice(5, 7),
                            day: ev.date.slice(8, 10),
                            togglePopup: this.renderPopup
                        }, null))
                    ),
                    ce('h4', null, "Past: "),
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
}


ReactDOM.render(
    React.createElement(DashboardPage, {}, null),
    document.getElementById('dashboard-root')
);