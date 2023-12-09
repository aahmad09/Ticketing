const ce = React.createElement

class EventPage extends React.Component {
    constructor(props) {
        super(props)
    }

    render() {
        return ce('div', {style: {overflow: 'hidden'}}, null)
    }
}


ReactDOM.render(
    React.createElement(EventPage, {}, null),
    document.getElementById('ticket-root')
);