const ce = React.createElement

class TicketPage extends React.Component {
    constructor(props) {
        super(props)
    }

    render() {
        return ce('div', {style: {overflow: 'hidden'}}, null)
    }
}


ReactDOM.render(
    React.createElement(TicketPage, {}, null),
    document.getElementById('react-root')
);