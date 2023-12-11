const ce = React.createElement

const DashboardRoute = document.getElementById("DashboardRoute").value;

const getEventColor = (eventMonth, eventDay) => { //yyyy-mm-dd hh:mm:ss
    let currentDate = new Date()
    let currentMonth = currentDate.getMonth() + 1
    let currentDay = currentDate.getDate()
    if (currentDay < eventDay && (currentMonth < eventMonth || currentMonth === eventMonth)) {
        return '#BCB6FF'
    } else if (currentDay === eventDay && currentMonth === eventMonth) {
        return '#94BE9A'
    } else {
        return '#C85330'
    }
}

class EventDetails extends React.Component {
    constructor(props) {
        super(props)
        this.ticketId = props.data.ticketId
        this.eventId = props.data.eventId
        this.orgId = props.data.orgId
        this.name = props.data.name
        this.image = props.data.image
        this.location = props.data.location
        this.description = props.data.description
        this.month = props.data.date.slice(5, 7)
        this.day = props.data.date.slice(8, 10)
        this.color = getEventColor(this.month, this.day)
        this.isTicket = props.isTicket
        this.close = props.close
        this.organizer = props.organizer
        this.username = props.username
        this.userId = props.userId
    }

    handleSubmit = () => {
        fetch("registerForEvent", {
            method: "POST",
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({userId: this.userId, eventId: this.eventId})
        }).then(data => {
            if (data.ok) {
                fetch("registerForEventTwo?" + encodeURIComponent("eventId") + "=" + encodeURIComponent(this.eventId)).then(res => {
                        window.location.href = DashboardRoute;
                    
                })
            }
        })
}


    render() {
        return ce('div', {className: 'eventDetail_area'}, 
            ce('button',{className:'cancel-button',onClick: (e) => this.close()}, "Cancel"),
            this.organizer && ('button',{className:'delete-button',onClick: (e) => this.close()}, "Delete"),

            ce('div', {className: 'detail'}, 
                ce('h4', {className: 'detail_text'},this.orgId),
            ),
            ce('div', {className: 'detail'}, 
                ce('h4', {className: 'detail_text'},this.name),
            ),
            ce('div', {className: 'detail'}, 
                ce('h4', {className: 'detail_text'},this.date),
            ),
            ce('div', {className: 'detail'}, 
                ce('h4', {className: 'detail_text'},this.location),
            ),
            ce('div', {className: 'detail'}, 
                ce('h4', {className: 'detail_text'},this.description),
            ),
            ce('div', {className: 'detail'}, 
                ce('h4', {className: 'detail_text'},this.image),
            ),
            ce('button',{className:'purchase-button',onClick: this.handleSubmit}, !this.isTicket ? "Register for event" : "Unregister")
           
        );
    }
}

export default EventDetails