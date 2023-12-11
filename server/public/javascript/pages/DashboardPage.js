import EventCard from '../components/EventCard.js'
import ProfileDropdown from '../components/ProfileDropdown.js'
import EventDetails from '../components/EventDetails.js'


const ce = React.createElement

class DashboardPage extends React.Component {
    constructor(props) {
        super(props)
        this.state = {tickets: [], role: "", openPopup: false, ticketData: null, events: []};
        fetch('viewTickets').then(res => res.json()).then(data => {
            this.setState(s => {return {...s, tickets: data}})
            let registeredIds = data.map(t => t.eventId);
            fetch('getAllEvents').then(result => result.json()).then(allEvents => {
                console.log(data)
                console.log(allEvents)
                console.log(allEvents.filter(ev => registeredIds.includes(ev.eventId)))
                this.setState(s => {return {...s, events: allEvents.filter(ev => !registeredIds.includes(ev.eventId)), tickets: allEvents.filter(ev => registeredIds.includes(ev.eventId))}})
            })
        })
        
        fetch('getProfileInfo').then(res => res.json()).then(data => {
            this.setState(s => {return {...s, role: data.role, name: data.name, userId: data.userId}})
        })
    }

    renderTicketPopup = (ticketId) => {
        this.setState(s => {return {...s, openTicketPopup: true, ticketData: this.state.tickets.filter(ticket => ticket.ticketId === ticketId)[0]}})
    }

    renderEventPopup = (eventId) => {
        this.setState(s => {return {...s, openEventPopup: true, eventData: this.state.events.filter(event => event.eventId === eventId)[0]}})
    }

    closePopup = () => {
        this.setState(s => {return {...s, openEventPopup: false, openTicketPopup: false}});
    }

    render() {
        if (this.state.role === "organizer") {
            if (this.state.openEventPopup) {
                return ce('div', {className: "eventDetailPage", style: {overflow: 'hidden'}}, 
                    ce(EventDetails, {data: this.state.eventData, isTicket: false, close: this.closePopup, organizer: true, username: this.state.name, userId: this.state.userId}, null),
                )
            } else { return ce('div', {className: 'dashboard-body',style: {overflow: 'hidden'}},
                ce('div', {className: 'top-bar'}, 
                    ce('h2', null, "Hello, " + this.state.name + ". Manage or create your events:"),
                    ce(ProfileDropdown, {isOrganizer: true}, null)
                ),
                ce('h4', null, "My Events: "),
                ce('div', {className: 'card-slider'}, 
                    this.state.events && this.state.events.filter(e => e.orgId === this.state.userId).map((ev) => ce(EventCard, {
                        key: ev.eventId,
                        ticketId: ev.eventId,
                        title: ev.name,
                        month: ev.date.slice(5, 7),
                        day: ev.date.slice(8, 10),
                        togglePopup: this.renderEventPopup
                    }, null))
                ),
                ce('h5', null, "Organizer"),
            )}
        } else {
            if (this.state.openEventPopup) {
                return ce('div', {className: "eventDetailPage", style: {overflow: 'hidden'}}, 
                    ce(EventDetails, {data: this.state.eventData, isTicket: false, close: this.closePopup, organizer: false, username: this.state.name, userId: this.state.userId}, null),
                )
            } else if (this.state.openTicketPopup) {
                return ce('div', {className: "eventDetailPage", style: {overflow: 'hidden'}}, 
                    ce(EventDetails, {data: this.state.ticketData, isTicket: true, close: this.closePopup, organizer: false, username: this.state.name, userId: this.state.userId}, null),
                )
            } else { return ce('div', {className: 'dashboard-body',style: {overflow: 'hidden'}},
                ce('div', {className: 'top-bar'}, 
                    ce('h2', null, "Hello, " + this.state.name + ". Check out what’s going on around campus this week:"),
                    (this.state.role === "attendee" && ce(ProfileDropdown, {isOrganizer: false}, null))
                ),
                ce('h4', null, "My Events: "),
                ce('div', {className: 'card-slider'}, 
                    this.state.tickets.map(ticket => {
                        (EventCard, {
                            key: ev.eventId,
                            ticketId: ev.eventId,
                            title: ev.name,
                            month: ev.date.slice(5, 7),
                            day: ev.date.slice(8, 10),
                            togglePopup: this.renderEventPopup
                        }, null)
                    })
                ),
                ce('h4', null, "Upcoming: "),
                ce('div', {className: 'card-slider'}, 
                    this.state.events && this.state.events.map((ev) => ce(EventCard, {
                        key: ev.eventId,
                        ticketId: ev.eventId,
                        title: ev.name,
                        month: ev.date.slice(5, 7),
                        day: ev.date.slice(8, 10),
                        togglePopup: this.renderEventPopup
                    }, null))
                ),
                ce('h4', null, "Past: "),
                ce('div', {className: 'card-slider'}, 
                    this.state.events && this.state.events.map((ev) => ce(EventCard, {
                        key: ev.eventId,
                        ticketId: ev.eventId,
                        title: ev.name,
                        month: ev.date.slice(5, 7),
                        day: ev.date.slice(8, 10),
                        togglePopup: this.renderEventPopup
                    }, null))
                ),
                ce('h5', null, "Attendee"),
            )}
        }
    }
}


ReactDOM.render(
    React.createElement(DashboardPage, {}, null),
    document.getElementById('dashboard-root')
);