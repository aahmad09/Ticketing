import LoginField from '../components/LoginField.js'

const ce = React.createElement

class LoginPage extends React.Component {
    constructor(props) {
        super(props)
        this.relocate = props.relocate;
        this.login = (params) => {
            fetch("handleLogin", {
                method: "POST",
                headers: {'Content-Type': 'application/json'},
                body: JSON.stringify(params)
            }).then(res => res.json()).then(data => {
                if (data) {
                    this.relocate("home");
                }
            })
        }
    }

    render() {
        return ce('div', null, 
            ce('h2', null, "Login: "),
            ce(LoginField, {submit: this.login}, null),
            ce('hr', null, null),
            ce('h2', null, "Don't have a login? ", ce('a', {href: 'register'}, "Sign up here."))
        )
    }
}


ReactDOM.render(
    React.createElement(LoginPage, {}, null),
    document.getElementById('react-root')
);