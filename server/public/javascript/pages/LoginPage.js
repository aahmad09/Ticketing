import LoginField from '../components/LoginField.js'

const ce = React.createElement

const csrfToken = document.getElementById("csrfToken").value;

class LoginPage extends React.Component {
    constructor(props) {
        super(props)
        this.relocate = props.relocate;
        this.login = (params) => {
            fetch("submitLogin", {
                method: "POST",
                headers: {'Content-Type': 'application/json', 'Csrf-Token': csrfToken},
                body: JSON.stringify(params)
            }).then(res => res.json()).then(data => {
                if (data) {
                    this.relocate("home");
                }
            })
        }
        this.newUser = (params) => {
            fetch("newUser", {
                method: "POST",
                headers: {'Content-Type': 'application/json', 'Csrf-Token': csrfToken},
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
            ce('h2', null, "Login: "),
            ce(LoginField, {submit: this.login}, null),
            ce('hr', null, null),
            ce('h2', null, "New User? Create an Account:"),
            ce(LoginField, {submit: this.newUser}, null),
        )
    }
}


ReactDOM.render(
    React.createElement(LoginPage, {}, null),
    document.getElementById('react-root')
);