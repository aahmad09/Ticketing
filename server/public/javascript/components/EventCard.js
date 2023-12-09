const ce = React.createElement

class EventCard extends React.Component {
    constructor(props) {
        super(props)
        this.title = props.title
        this.month = props.month
        this.day = props.day
        this.color = props.color
        this.togglePopup = props.togglePopup
    }

    onCardClick() {
        fetch('getEvent?' + encodeURIComponent('title') + '=' + encodeURIComponent(this.title))
    }

    render() {
        return ce('div', {className: "card", style: {background: this.color}, onClick: this.onCardClick}, 
            this.title,
            ce('div', {className: "date"}, this.month, this.day),
        )
    }
}

export default EventCard