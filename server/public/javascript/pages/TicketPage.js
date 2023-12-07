const ce = React.createElement

class HomePage extends React.Component {
    constructor(props) {
        super(props)
    }

    render() {
        return ce('div', {style: {overflow: 'hidden'}}, null)
    }
}