const ce = React.createElement

const monthToString = {
    "01": "Jan",
    "02": "Feb",
    "03": "Mar",
    "04": "Apr",
    "05": "May",
    "06": "Jun",
    "07": "Jul",
    "08": "Aug",
    "09": "Sep",
    "10": "Oct",
    "11": "Nov",
    "12": "Dec"
}

class EventCard extends React.Component {
    constructor(props) {
        super(props)
        this.ticketId = props.ticketId
        this.title = props.title
        this.month = monthToString[props.month]
        this.day = props.day
        this.color = props.color
        this.togglePopup = props.togglePopup
        console.log(this.togglePopup)
    }

    onCardClick() {
        this.togglePopup(this.ticketId)
        //window.location.href = 'getTicket?' + encodeURIComponent('ticketId') + '=' + encodeURIComponent(this.ticketId)
    }

    render() {
        return ce('div', {className: "card", style: {background: this.color}, onClick: this.onCardClick}, 
            this.title,
            ce('div', {className: "date"}, this.month, this.day),
        )
    }
}

export default EventCard