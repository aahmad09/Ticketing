import RegistrationField from '../components/RegistrationField.js'

const ce = React.createElement

const DashboardRoute = document.getElementById("DashboardRoute").value;
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
                if (data.status === "success") {
                    console.log(data);
                    window.location.href = DashboardRoute; // Redirect to dashboard
                } else {
                    window.location.href = LoginRoute; // Redirect to login page
                }
            }).catch(error => {
                console.error('Error:', error);
            });
        }
        
    }

    render() {
        return ce('div', {className: "login-area"}, 
            ce('h2', null, "Create an Account:"),
            ce(RegistrationField, {submit: this.newUser}, null)
        )
    }
}


ReactDOM.render(
    React.createElement(RegisterPage, {}, null),
    document.getElementById('register-root')
);

