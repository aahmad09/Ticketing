import LoginField from '../components/LoginField.js'

const ce = React.createElement

const csrfToken = document.getElementById("csrfToken").value;

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
                    this.relocate("home");
                } else {
                    this.relocate("usernametaken")
                }
            })
        }
    }

    render() {
        return ce('div', null, 
            ce('h2', null, "Create an Account:"),
            ce(LoginField, {submit: this.newUser}, null)
        )
    }
}


ReactDOM.render(
    React.createElement(RegisterPage, {}, null),
    document.getElementById('react-root')
);