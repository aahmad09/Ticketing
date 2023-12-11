const ce = React.createElement

const monthToString = {
    "1": "Jan",
    "2": "Feb",
    "3": "Mar",
    "4": "Apr",
    "5": "May",
    "6": "Jun",
    "7": "Jul",
    "8": "Aug",
    "9": "Sep",
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

const getEventColor = (eventMonth, eventDay) => { //yyyy-mm-dd hh:mm:ss
    let currentDate = new Date()
    let currentMonth = monthToString[currentDate.getMonth() + 1]
    let currentDay = currentDate.getDate()
    if (currentDay < eventDay && currentMonth <= eventMonth) {
        return '#BCB6FF'
    } else if (currentDay == eventDay && currentMonth == eventMonth) {
        return '#94BE9A'
    } else {
        return '#C85330'
    }
}

class EventCard extends React.Component {
    constructor(props) {
        super(props)
        this.ticketId = props.ticketId
        this.title = props.title
        this.month = monthToString[props.month]
        this.day = props.day
        this.togglePopup = props.togglePopup
    }

    onCardClick = () => {
        window.location.href = 'getTicket?' + encodeURIComponent('ticketId') + '=' + encodeURIComponent(this.ticketId)
    }

    render() {
        return ce('div', {className: "card", style: {backgroundColor: getEventColor(this.month, this.day)}, onClick: (() => this.togglePopup(this.ticketId))}, 
            this.title,
            ce('div', {className: "date"}, this.month, this.day),
        )
    }
}

export default EventCard