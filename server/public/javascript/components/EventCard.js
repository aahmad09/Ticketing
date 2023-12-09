const ce = React.createElement

class EventCard extends React.Component {
    constructor(props) {
        super(props)
        this.ticketId = props.ticketId
        this.title = props.title
        this.month = props.month
        this.day = props.day
        this.color = props.color
        this.togglePopup = props.togglePopup
    }

    onCardClick() {
        fetch('getTicket?' + encodeURIComponent('ticketId') + '=' + encodeURIComponent(this.ticketId))
    }

    render() {
        return ce('div', {className: "card", style: {background: this.color}, onClick: this.onCardClick}, 
            this.title,
            ce('div', {className: "date"}, this.month, this.day),
        )
    }
}

export default EventCard