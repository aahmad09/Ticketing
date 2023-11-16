

const ce = React.CreateElement

class EventCard extends React.Component {
    constructor(props) {
        super(props)
    }

    render() {
        return ce('div', null, "test")
    }
}

export default EventCard