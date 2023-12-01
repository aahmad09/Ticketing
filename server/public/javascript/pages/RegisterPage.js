import RegistrationField from '../components/RegistrationField.js'

const ce = React.createElement

const attendeeHomeRoute = document.getElementById("AttendeeHomeRoute").value;
const LoginRoute = document.getElementById("LoginRoute").value;

class RegisterPage extends React.Component {
    constructor(props) {
        super(props)
        this.relocate = props.relocate;

        this.newUser = (params) => {
            fetch("handleRegistration", {
                method: "POST",
                headers: {'Content-Type': 'application/json'},
                body: JSON.stringify(params)
            }).then(res => res.json()).then(data => {
                if (data) {
                    console.log(data);
                    fetch(attendeeHomeRoute);
                } else {
                    fetch(LoginRoute);
                }
            })
        }
    }

    render() {
        return ce('div', null, 
            ce('h2', null, "Create an Account:"),
            ce(RegistrationField, {submit: this.newUser}, null)
        )
    }
}


ReactDOM.render(
    React.createElement(RegisterPage, {}, null),
    document.getElementById('react-root')
);