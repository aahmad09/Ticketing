import EventCreationField from '../components/EventCreationField.js';
import LoginField from '../components/LoginField.js'


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
        }).then(res => res.json()).then(data => {
            if (data.ok) {
                window.location.href = CreateEventRoute;
            }
        })
    }

    handleCancel = () => {
        window.location.href = DashboardRoute;
    }

    render() {
        
        return ce('div', {className: "event-creation-page"}, 
            ce(EventCreationField, {submit: this.handleSubmit}, null),
            ce('div', {className: 'cancel-creation', onclick: this.handleCancel}, "Cancel")
        )
    }
}


ReactDOM.render(
    React.createElement(CreateEventPage, {}, null),
    document.getElementById('createEvent-root')
);