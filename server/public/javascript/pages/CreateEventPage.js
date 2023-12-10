import EventCreationField from '../components/EventCreationField.js';

const ce = React.createElement

const DashboardRoute = document.getElementById("DashboardRoute").value;
const CreateEventRoute = document.getElementById("CreateEventRoute").value;


class CreateEventPage extends React.Component {
    constructor(props) {
        super(props)
    }

    handleSubmit = (params) => {
        fetch(CreateEventRoute, {
            method: "POST",
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(params)
        }).then(data => {
            if (data.ok) {
                window.location.href = DashboardRoute;
            }
        })
    }

    handleCancel = () => {
        window.location.href = DashboardRoute;
    }

    render() {
        
        return ce('div', {className: "event-creation-page"}, 
            ce('a', {className: 'cancel-creation', onClick: this.handleCancel}, "Cancel"),
            ce(EventCreationField, {submit: this.handleSubmit}, null)
        )
    }
}


ReactDOM.render(
    React.createElement(CreateEventPage, {}, null),
    document.getElementById('createEvent-root')
);